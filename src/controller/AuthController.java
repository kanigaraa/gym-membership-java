package controller;

import dao.AdminDAO;

public class AuthController {
    private final AdminDAO adminDAO;

    public AuthController() {
        this.adminDAO = new AdminDAO();
    }

    public boolean login(String username, String password) {
        return adminDAO.login(username, password);
    }
}
