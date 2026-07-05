package controller;

import dao.MemberDAO;
import model.Member;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

public class MemberController {
    private static final Set<String> MEMBERSHIP_TYPES = Set.of("Regular", "Premium");
    private final MemberDAO memberDAO;

    public MemberController() {
        this(new MemberDAO());
    }

    public MemberController(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    public List<Member> getMembers() throws SQLException {
        return memberDAO.findAll();
    }

    public List<Member> searchMembers(String query) throws SQLException {
        return memberDAO.search(query);
    }

    public int getMemberCount() throws SQLException {
        return memberDAO.count();
    }

    public Member addMember(String name, String phone, String membershipType,
                            String registrationDate, String expiryDate) throws SQLException {
        Member member = buildMember(null, name, phone, membershipType, registrationDate, expiryDate);
        memberDAO.insert(member);
        return member;
    }

    public void updateMember(Integer id, String name, String phone, String membershipType,
                             String registrationDate, String expiryDate) throws SQLException {
        requireSelection(id);
        Member member = buildMember(id, name, phone, membershipType, registrationDate, expiryDate);
        if (!memberDAO.update(member)) {
            throw new ValidationException("Member yang dipilih tidak ditemukan.");
        }
    }

    public void deleteMember(Integer id) throws SQLException {
        requireSelection(id);
        if (!memberDAO.delete(id)) {
            throw new ValidationException("Member yang dipilih tidak ditemukan.");
        }
    }

    public Member buildMember(Integer id, String name, String phone, String membershipType,
                              String registrationDate, String expiryDate) {
        String cleanName = required(name, "Nama member wajib diisi.");
        String cleanPhone = required(phone, "Nomor telepon wajib diisi.");
        String cleanType = required(membershipType, "Jenis membership wajib dipilih.");
        String cleanRegistration = required(registrationDate, "Tanggal daftar wajib diisi.");
        String cleanExpiry = required(expiryDate, "Tanggal berakhir wajib diisi.");

        if (!cleanPhone.matches("[0-9+()\\- ]*") || !cleanPhone.matches(".*\\d.*")) {
            throw new ValidationException("Nomor telepon hanya boleh berisi angka, spasi, +, -, dan tanda kurung.");
        }
        if (!MEMBERSHIP_TYPES.contains(cleanType)) {
            throw new ValidationException("Jenis membership harus Regular atau Premium.");
        }

        LocalDate registered = parseDate(cleanRegistration, "Tanggal daftar");
        LocalDate expires = parseDate(cleanExpiry, "Tanggal berakhir");
        if (expires.isBefore(registered)) {
            throw new ValidationException("Tanggal berakhir tidak boleh sebelum tanggal daftar.");
        }
        return new Member(id, cleanName, cleanPhone, cleanType, registered, expires);
    }

    private String required(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(message);
        }
        return value.trim();
    }

    private LocalDate parseDate(String value, String label) {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException exception) {
            throw new ValidationException(label + " harus menggunakan format yyyy-MM-dd.");
        }
    }

    private void requireSelection(Integer id) {
        if (id == null) {
            throw new ValidationException("Pilih member dari tabel terlebih dahulu.");
        }
    }
}
