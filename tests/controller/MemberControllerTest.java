package controller;

import model.Member;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberControllerTest {
    private final MemberController controller = new MemberController();

    @Test
    void buildsMemberFromValidFormValues() {
        Member member = controller.buildMember(null, "  Budi Santoso  ", "+62 812-3456",
                "Regular", "2026-07-05", "2026-08-05");

        assertEquals("Budi Santoso", member.getName());
        assertEquals("+62 812-3456", member.getPhone());
        assertEquals("Regular", member.getMembershipType());
        assertEquals(LocalDate.of(2026, 7, 5), member.getRegistrationDate());
        assertEquals(LocalDate.of(2026, 8, 5), member.getExpiryDate());
    }

    @Test
    void rejectsEmptyRequiredField() {
        assertThrows(ValidationException.class, () ->
                controller.buildMember(null, " ", "0812", "Regular",
                        "2026-07-05", "2026-08-05"));
    }

    @Test
    void rejectsPhoneWithoutDigitOrWithUnsupportedCharacters() {
        assertThrows(ValidationException.class, () ->
                controller.buildMember(null, "Budi", "+-() ", "Regular",
                        "2026-07-05", "2026-08-05"));
        assertThrows(ValidationException.class, () ->
                controller.buildMember(null, "Budi", "0812#34", "Regular",
                        "2026-07-05", "2026-08-05"));
    }

    @Test
    void rejectsUnknownMembershipType() {
        assertThrows(ValidationException.class, () ->
                controller.buildMember(null, "Budi", "0812", "VIP",
                        "2026-07-05", "2026-08-05"));
    }

    @Test
    void rejectsMalformedDate() {
        assertThrows(ValidationException.class, () ->
                controller.buildMember(null, "Budi", "0812", "Premium",
                        "05-07-2026", "2026-08-05"));
    }

    @Test
    void rejectsExpiryBeforeRegistration() {
        assertThrows(ValidationException.class, () ->
                controller.buildMember(null, "Budi", "0812", "Premium",
                        "2026-08-05", "2026-07-05"));
    }
}
