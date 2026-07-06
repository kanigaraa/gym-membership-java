package model;

import java.time.LocalDate;

public class RegularMember extends Member {
    public RegularMember(Integer id, String name, String phone, LocalDate registrationDate, LocalDate expiryDate) {
        super(id, name, phone, "Regular", registrationDate, expiryDate);
    }
}
