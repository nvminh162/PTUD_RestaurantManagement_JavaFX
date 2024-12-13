package com.huongbien.bus;

import com.huongbien.dao.AccountDAO;
import com.huongbien.entity.Account;

import java.util.List;

public class AccountBUS {
    private final AccountDAO accountDao = AccountDAO.getInstance();

    public List<Account> getAllAccount() {
        return accountDao.getAll();
    }

    public Account getAccount(String username) {
        if (username.isBlank() || username.isEmpty()) return null;
        return accountDao.getByUsername(username);
    }

    public boolean changePassword(String email, String newPassword) {
        if (email.isBlank() || email.isEmpty() || newPassword.isBlank() || newPassword.isEmpty()) return false;
        return accountDao.changePassword(email, newPassword);
    }
}
