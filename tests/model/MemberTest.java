package model;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberTest {
    private static final Clock CLOCK = Clock.fixed(
            Instant.parse("2026-07-05T00:00:00Z"), ZoneOffset.UTC);

    @Test
    void returnsInactiveWhenMembershipHasExpired() {
        Member member = memberExpiringOn(LocalDate.of(2026, 7, 4));

        assertEquals("Tidak Aktif", member.getStatus(CLOCK));
    }

    @Test
    void returnsActiveWhenMembershipExpiresToday() {
        Member member = memberExpiringOn(LocalDate.of(2026, 7, 5));

        assertEquals("Aktif", member.getStatus(CLOCK));
    }

    @Test
    void returnsActiveWhenMembershipExpiresInFuture() {
        Member member = memberExpiringOn(LocalDate.of(2026, 7, 6));

        assertEquals("Aktif", member.getStatus(CLOCK));
    }

    private Member memberExpiringOn(LocalDate expiryDate) {
        return new Member(null, "Budi", "08123456789", "Regular",
                LocalDate.of(2026, 7, 1), expiryDate);
    }
}
