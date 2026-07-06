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
import java.util.ArrayList;
import java.util.List;

public class MembershipFrame extends JFrame {
    private final MemberController controller;
    private final JTextField searchField = new JTextField(22);
    private final JLabel totalLabel = new JLabel("Total member: 0");
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"ID", "Nama", "Tipe", "Tanggal Daftar", "Tanggal Berakhir", "Status"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable memberTable = new JTable(tableModel);
    private final JTextField selectedNameField = new JTextField(15);
    private final JTextField selectedTypeField = new JTextField(15);
    private final JTextField selectedExpiryField = new JTextField(15);
    private final JTextField selectedStatusField = new JTextField(15);
    
    // Pilihan perpanjangan
    private final JComboBox<String> packageBox = new JComboBox<>(new String[]{
            "1 Bulan", "3 Bulan", "6 Bulan", "12 Bulan"
    });
    
    private List<Member> displayedMembers = new ArrayList<>();
    private Integer selectedMemberId;

    public MembershipFrame() {
        this(new MemberController());
    }

    MembershipFrame(MemberController controller) {
        this.controller = controller;
        configureWindow();
        JPanel content = createContent();
        AkaliFitTheme.apply(content);
        setContentPane(content);
        bindEvents();
        resetForm();
        refreshMembers();
    }

    private void configureWindow() {
        setTitle("Akali Fit - Membership");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(900, 600));
        setSize(1000, 650);
        setLocationRelativeTo(null);
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(12, 12));
        content.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JButton backButton = new JButton("Kembali");
        JLabel title = new JLabel("Akali Fit - Paket Membership");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        content.add(createHeader(title, backButton), BorderLayout.NORTH);
        content.add(createFormPanel(), BorderLayout.WEST);
        content.add(createTablePanel(), BorderLayout.CENTER);

        backButton.addActionListener(event -> backToDashboard());
        return content;
    }

    static JPanel createHeader(JLabel title, JButton backButton) {
        JPanel header = new JPanel(new BorderLayout());
        header.add(title, BorderLayout.WEST);
        header.add(backButton, BorderLayout.EAST);
        return header;
    }

    private void backToDashboard() {
        new DashboardFrame().setVisible(true);
        dispose();
    }

    private JPanel createFormPanel() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Perpanjang Membership"));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(8, 8, 8, 8);

        selectedNameField.setEditable(false);
        selectedTypeField.setEditable(false);
        selectedExpiryField.setEditable(false);
        selectedStatusField.setEditable(false);

        addField(form, constraints, "Nama Member", selectedNameField);
        addField(form, constraints, "Tipe Membership", selectedTypeField);
        addField(form, constraints, "Status Saat Ini", selectedStatusField);
        addField(form, constraints, "Tanggal Berakhir", selectedExpiryField);
        
        form.add(new JSeparator(), constraints);
        constraints.gridy++;
        
        addField(form, constraints, "Pilih Paket Perpanjangan", packageBox);

        JPanel actions = new JPanel(new GridLayout(1, 1, 6, 6));
        JButton extendButton = new JButton("Perpanjang");
        actions.add(extendButton);
        
        constraints.gridy++;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        form.add(actions, constraints);

        extendButton.addActionListener(event -> extendMembership());
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
        toolbar.add(new JLabel("Cari nama:"), BorderLayout.WEST);
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
            public void insertUpdate(DocumentEvent event) { refreshMembers(); }
            @Override
            public void removeUpdate(DocumentEvent event) { refreshMembers(); }
            @Override
            public void changedUpdate(DocumentEvent event) { refreshMembers(); }
        });
    }

    private void extendMembership() {
        if (selectedMemberId == null) {
            showError(new ValidationException("Pilih member dari tabel terlebih dahulu."));
            return;
        }
        
        int months = 1;
        String selectedPackage = (String) packageBox.getSelectedItem();
        if (selectedPackage != null) {
            if (selectedPackage.startsWith("3")) months = 3;
            else if (selectedPackage.startsWith("6")) months = 6;
            else if (selectedPackage.startsWith("12")) months = 12;
        }

        int answer = JOptionPane.showConfirmDialog(this,
                "Perpanjang membership selama " + months + " bulan?", "Konfirmasi Perpanjangan",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer != JOptionPane.YES_OPTION) {
            return;
        }
        
        try {
            controller.extendMembership(selectedMemberId, months);
            afterMutation("Membership berhasil diperpanjang.");
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
                tableModel.addRow(new Object[]{
                        member.getId(), 
                        member.getName(), 
                        member.getMembershipType(), 
                        member.getRegistrationDate(), 
                        member.getExpiryDate(),
                        member.getStatus()
                });
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
        
        selectedNameField.setText(member.getName());
        selectedTypeField.setText(member.getMembershipType());
        selectedExpiryField.setText(member.getExpiryDate().toString());
        selectedStatusField.setText(member.getStatus());
    }

    private void resetForm() {
        selectedMemberId = null;
        memberTable.clearSelection();
        selectedNameField.setText("-");
        selectedTypeField.setText("-");
        selectedExpiryField.setText("-");
        selectedStatusField.setText("-");
        packageBox.setSelectedIndex(0);
    }

    private void showError(Exception exception) {
        JOptionPane.showMessageDialog(this, exception.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
}
