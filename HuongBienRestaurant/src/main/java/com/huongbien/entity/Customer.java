package com.huongbien.entity;

import com.huongbien.utils.Utils;
import jdk.jshell.execution.Util;

import java.time.LocalDate;
import java.util.Objects;

public class Customer {
    private String customerId;
    private String name;
    private String address;
    private int gender;
    private String phoneNumber;
    private String email;
    private LocalDate birthday;
    private LocalDate registrationDate;
    private int accumulatedPoints;
    private int membershipLevel;

    public Customer() {
    }

    public Customer(String customerId, String name, String address, int gender,
                    String phoneNumber, String email, LocalDate birthday,
                    LocalDate registrationDate, int accumulatedPoints, int memberShip) {
        setCustomerId(customerId);
        setName(name);
        setAddress(address);
        setGender(gender);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setBirthday(birthday);
        setRegistrationDate(registrationDate);
        setAccumulatedPoints(accumulatedPoints);
        setMembershipLevel(memberShip);
    }

    public Customer(String name, String address, int gender,
                    String phoneNumber, String email, LocalDate birthday) {
        setCustomerId(null);
        setName(name);
        setAddress(address);
        setGender(gender);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setBirthday(birthday);
        setRegistrationDate(LocalDate.now());
        setAccumulatedPoints(0);
        setMembershipLevel(0);
    }

    public void setCustomerId(String customerId) {
        if (customerId == null) {
            LocalDate currentDate = LocalDate.now();
            this.customerId = String.format("KH%02d%02d%02d%03d",
                    currentDate.getYear() % 100,
                    currentDate.getMonthValue(),
                    currentDate.getDayOfMonth(),
                    Utils.randomNumber(1, 999)
            );
            return;
        }
        if (customerId.matches("^KH\\d{9}$")) {
            this.customerId = customerId;
            return;
        }
        throw new IllegalArgumentException("Invalid customer ID");
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.matches("^0\\d{9}$")) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Invalid phone number");
        }
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

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setAccumulatedPoints(int accumulatedPoints) {
        if (accumulatedPoints < 0) {
            throw new IllegalArgumentException("Accumulated points must be non-negative");
        }
        this.accumulatedPoints = accumulatedPoints;
    }

    public void setMembershipLevel(int membershipLevel) {
        if (membershipLevel < 0) {
            throw new IllegalArgumentException("Membership cannot lower 0");
        }
        this.membershipLevel = membershipLevel;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public int getAccumulatedPoints() {
        return accumulatedPoints;
    }

    public int getMembershipLevel() {
        return membershipLevel;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + Utils.toStringGender(gender) +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", registrationDate=" + registrationDate +
                ", accumulatedPoints=" + accumulatedPoints +
                ", membershipLevel='" + Utils.toStringMembershipLevel(membershipLevel) + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(customerId);
    }
}

