package com.huongbien.dao;

import com.huongbien.entity.Account;
import com.huongbien.entity.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountDAO extends GenericDAO<Account> {
    private static final AccountDAO instance = new AccountDAO();

    private AccountDAO() {
        super();
    }

    public static AccountDAO getInstance() {
        return instance;
    }

    @Override
    public Account resultMapper(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setUsername(resultSet.getString("username"));
        account.setHashcode(resultSet.getString("hashcode"));
        account.setRole(resultSet.getString("role"));
        account.setEmail(resultSet.getString("email"));
        account.setIsActive(resultSet.getBoolean("isActive"));
        List<Employee> employees = EmployeeDAO.getInstance().getManyById(account.getUsername());
        account.setEmployeeInfo(!employees.isEmpty() ? employees.get(0) : null);
        return account;
    }

    public List<Account> getAll() {
        return getMany("SELECT * FROM Account");
    }

    public Account getByUsername(String username) {
        return getOne("SELECT * FROM Account WHERE username LIKE ?;", username);
    }
    public Account getByEmail(String email) {
        return getOne("SELECT * FROM Account WHERE email = ?;", email);
    }

    public boolean changePassword(String email, String newPassword) {
        return update("UPDATE Account SET hashcode = ? WHERE email = ?;", newPassword, email);
    }
    public boolean updateEmail(String username, String newEmail) {
        return update("UPDATE Account SET email = ? WHERE username = ?;", newEmail, username);
    }


    @Override
    public boolean add(Account object) {
        try {
            String sql = "INSERT INTO Account (username, hashcode, role, email, isActive) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = statementHelper.prepareStatement(
                    sql,
                    object.getUsername(),
                    object.getHashcode(),
                    object.getRole(),
                    object.getEmail(),
                    object.getIsActive()
            );
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}