package view;

import controller.MemberController;
import controller.ValidationException;
import model.Member;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DashboardFrame extends JFrame {
    private final MemberController controller;
    private final JTextField nameField = new JTextField(18);
    private final JTextField phoneField = new JTextField(18);
    private final JComboBox<String> membershipBox = new JComboBox<>(new String[]{"Regular", "Premium"});
    private final JTextField registrationField = new JTextField(18);
    private final JTextField expiryField = new JTextField(18);
    private final JTextField searchField = new JTextField(22);
    private final JLabel totalLabel = new JLabel("Total member: 0");
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"ID", "Nama", "Telepon", "Membership", "Tanggal Daftar", "Berakhir", "Status"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable memberTable = new JTable(tableModel);
    private List<Member> displayedMembers = new ArrayList<>();
    private Integer selectedMemberId;

    public DashboardFrame() {
        this(new MemberController());
    }

    DashboardFrame(MemberController controller) {
        this.controller = controller;
        configureWindow();
        setContentPane(createContent());
        bindEvents();
        resetForm();
        refreshMembers();
    }

    private void configureWindow() {
        setTitle("Gym Membership - Kelola Member");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 620));
        setSize(1100, 680);
        setLocationRelativeTo(null);
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(12, 12));
        content.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Manajemen Member Gym");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        content.add(title, BorderLayout.NORTH);
        content.add(createFormPanel(), BorderLayout.WEST);
        content.add(createTablePanel(), BorderLayout.CENTER);
        return content;
    }

    private JPanel createFormPanel() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Data Member"));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 8, 5, 8);

        addField(form, constraints, "Nama", nameField);
        addField(form, constraints, "Nomor Telepon", phoneField);
        addField(form, constraints, "Jenis Membership", membershipBox);
        addField(form, constraints, "Tanggal Daftar (yyyy-MM-dd)", registrationField);
        addField(form, constraints, "Tanggal Berakhir (yyyy-MM-dd)", expiryField);

        JPanel actions = new JPanel(new GridLayout(2, 2, 6, 6));
        JButton addButton = new JButton("Tambah");
        JButton updateButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");
        JButton resetButton = new JButton("Reset");
        actions.add(addButton);
        actions.add(updateButton);
        actions.add(deleteButton);
        actions.add(resetButton);
        constraints.gridy++;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        form.add(actions, constraints);

        addButton.addActionListener(event -> addMember());
        updateButton.addActionListener(event -> updateMember());
        deleteButton.addActionListener(event -> deleteMember());
        resetButton.addActionListener(event -> resetForm());
        return form;
    }

    private void addField(JPanel panel, GridBagConstraints constraints, String label, JComponent field) {
        panel.add(new JLabel(label), constraints);
        constraints.gridy++;
        panel.add(field, constraints);
        constraints.gridy++;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JPanel toolbar = new JPanel(new BorderLayout(8, 0));
        toolbar.add(new JLabel("Cari nama / telepon:"), BorderLayout.WEST);
        toolbar.add(searchField, BorderLayout.CENTER);
        toolbar.add(totalLabel, BorderLayout.EAST);
        panel.add(toolbar, BorderLayout.NORTH);

        memberTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        memberTable.setAutoCreateRowSorter(true);
        memberTable.getTableHeader().setReorderingAllowed(false);
        panel.add(new JScrollPane(memberTable), BorderLayout.CENTER);
        return panel;
    }

    private void bindEvents() {
        memberTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                loadSelectedMember();
            }
        });
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                refreshMembers();
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                refreshMembers();
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                refreshMembers();
            }
        });
    }

    private void addMember() {
        try {
            controller.addMember(nameField.getText(), phoneField.getText(), selectedMembership(),
                    registrationField.getText(), expiryField.getText());
            afterMutation("Member berhasil ditambahkan.");
        } catch (ValidationException | SQLException exception) {
            showError(exception);
        }
    }

    private void updateMember() {
        try {
            controller.updateMember(selectedMemberId, nameField.getText(), phoneField.getText(),
                    selectedMembership(), registrationField.getText(), expiryField.getText());
            afterMutation("Data member berhasil diperbarui.");
        } catch (ValidationException | SQLException exception) {
            showError(exception);
        }
    }

    private void deleteMember() {
        if (selectedMemberId == null) {
            showError(new ValidationException("Pilih member dari tabel terlebih dahulu."));
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this,
                "Hapus member yang dipilih?", "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (answer != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            controller.deleteMember(selectedMemberId);
            afterMutation("Member berhasil dihapus.");
        } catch (ValidationException | SQLException exception) {
            showError(exception);
        }
    }

    private void afterMutation(String message) {
        resetForm();
        refreshMembers();
        JOptionPane.showMessageDialog(this, message, "Berhasil", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshMembers() {
        try {
            displayedMembers = controller.searchMembers(searchField.getText());
            tableModel.setRowCount(0);
            for (Member member : displayedMembers) {
                tableModel.addRow(new Object[]{member.getId(), member.getName(), member.getPhone(),
                        member.getMembershipType(), member.getRegistrationDate(), member.getExpiryDate(),
                        member.getStatus()});
            }
            totalLabel.setText("Total member: " + controller.getMemberCount());
        } catch (SQLException exception) {
            showError(exception);
        }
    }

    private void loadSelectedMember() {
        int viewRow = memberTable.getSelectedRow();
        if (viewRow < 0) {
            return;
        }
        int modelRow = memberTable.convertRowIndexToModel(viewRow);
        Member member = displayedMembers.get(modelRow);
        selectedMemberId = member.getId();
        nameField.setText(member.getName());
        phoneField.setText(member.getPhone());
        membershipBox.setSelectedItem(member.getMembershipType());
        registrationField.setText(member.getRegistrationDate().toString());
        expiryField.setText(member.getExpiryDate().toString());
    }

    private void resetForm() {
        selectedMemberId = null;
        memberTable.clearSelection();
        nameField.setText("");
        phoneField.setText("");
        membershipBox.setSelectedIndex(0);
        LocalDate today = LocalDate.now();
        registrationField.setText(today.toString());
        expiryField.setText(today.plusMonths(1).toString());
        nameField.requestFocusInWindow();
    }

    private String selectedMembership() {
        return String.valueOf(membershipBox.getSelectedItem());
    }

    private void showError(Exception exception) {
        JOptionPane.showMessageDialog(this, exception.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
}
