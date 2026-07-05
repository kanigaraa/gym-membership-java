package model;

import java.time.LocalDate;

public class PremiumMember extends Member {
    public PremiumMember(Integer id, String name, String phone, LocalDate registrationDate, LocalDate expiryDate) {
        super(id, name, phone, "Premium", registrationDate, expiryDate);
    }

    @Override
    public void extendMembership(int months) {
        // Bonus 1 minggu (7 hari) untuk setiap kelipatan 1 bulan perpanjangan
        super.extendMembership(months);
        int bonusDays = months * 7;
        setExpiryDate(getExpiryDate().plusDays(bonusDays));
    }
}
