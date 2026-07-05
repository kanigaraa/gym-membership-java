package model;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;

public class Member {
    private Integer id;
    private final String name;
    private final String phone;
    private final String membershipType;
    private final LocalDate registrationDate;
    private final LocalDate expiryDate;

    public Member(Integer id, String name, String phone, String membershipType,
                  LocalDate registrationDate, LocalDate expiryDate) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name");
        this.phone = Objects.requireNonNull(phone, "phone");
        this.membershipType = Objects.requireNonNull(membershipType, "membershipType");
        this.registrationDate = Objects.requireNonNull(registrationDate, "registrationDate");
        this.expiryDate = Objects.requireNonNull(expiryDate, "expiryDate");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getStatus() {
        return getStatus(Clock.systemDefaultZone());
    }

    public String getStatus(Clock clock) {
        LocalDate today = LocalDate.now(Objects.requireNonNull(clock, "clock"));
        return expiryDate.isBefore(today) ? "Tidak Aktif" : "Aktif";
    }
}
