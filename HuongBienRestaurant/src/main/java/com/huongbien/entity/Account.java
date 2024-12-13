package com.huongbien.entity;

import com.huongbien.utils.Utils;

import java.util.Objects;

public class Account {
    private String username;
    private String hashcode;
    private String role;
    private String email;
    private boolean isActive;
    private Employee employeeInfo;

    public Account(String username, String password, String role, String email, Employee employeeInfo) {
        setUsername(username);
        setHashcode(Utils.hashPassword(password));
        setEmail(email);
        setRole(role);
        setIsActive(true);
        setEmployeeInfo(employeeInfo);
    }

    public Account(String username, String hashcode, String role, String email, boolean isActive, Employee employeeInfo) {
        setUsername(username);
        setHashcode(hashcode);
        setEmail(email);
        setRole(role);
        setIsActive(isActive);
        setEmployeeInfo(employeeInfo);
    }

    public Account() {
    }

    public void setUsername(String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty");
        }
        this.username = employeeId;
    }

    public void setHashcode(String hashcode) {
        if (hashcode == null || hashcode.trim().isEmpty()) {
            throw new IllegalArgumentException("Hashcode cannot be empty");
        }
        this.hashcode = hashcode;
    }

    public void setRole(String role) {
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Role cannot be empty");
        }
        this.role = role;
    }

    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            this.email = null;
            return;
        }

        if (email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            this.email = email;
            return;
        }
        throw new IllegalArgumentException("Invalid email");
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setEmployeeInfo(Employee employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public String getUsername() {
        return username;
    }

    public String getHashcode() {
        return hashcode;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public Employee getEmployeeInfo() {
        return employeeInfo;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", hashcode='" + hashcode + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", employeeInfo=" + employeeInfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(username, account.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }
}


