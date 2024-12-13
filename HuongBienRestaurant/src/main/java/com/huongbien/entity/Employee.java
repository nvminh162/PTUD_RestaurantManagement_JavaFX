package com.huongbien.entity;

import com.huongbien.utils.Utils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Employee {
    private String employeeId;
    private String name;
    private String address;
    private int gender;
    private LocalDate birthday;
    private String citizenIDNumber;
    private String status;
    private String phoneNumber;
    private String email;
    private LocalDate hireDate;
    private String position;
    private double workHours;
    private double hourlyPay;
    private double salary;
    private byte[] profileImage;
    private Employee manager;

    public Employee() {
    }

    public Employee(String employeeId, String name, String phoneNumber, String citizenIDNumber,
                    int gender, String address, LocalDate birthday, String email,
                    String status, LocalDate hireDate, String position, double workHours,
                    double hourlyPay, double salary, Employee manager, byte[] profileImage) {
        setEmployeeId(employeeId);
        setName(name);
        setAddress(address);
        setGender(gender);
        setBirthday(birthday);
        setCitizenIDNumber(citizenIDNumber);
        setStatus(status);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setHireDate(hireDate);
        setPosition(position);
        setWorkHours(workHours);
        setHourlyPay(hourlyPay);
        setSalary(salary);
        setProfileImage(profileImage);
        setManager(manager);
    }

    public Employee(String name, String phoneNumber, String citizenIDNumber,
                    int gender, String address, LocalDate birthday, String email,
                    String position, double workHours, double hourlyPay, double salary,
                    Employee manager, byte[] profileImage) {
        setEmployeeId(null);
        setName(name);
        setPhoneNumber(phoneNumber);
        setCitizenIDNumber(citizenIDNumber);
        setGender(gender);
        setAddress(address);
        setBirthday(birthday);
        setEmail(email);
        setStatus("Đang làm");
        setHireDate(LocalDate.now());
        setPosition(position);
        setWorkHours(workHours);
        setHourlyPay(hourlyPay);
        setSalary(salary);
        setProfileImage(profileImage);
        setManager(manager);
    }

    public void setEmployeeId(String employeeId) {
        if (employeeId == null) {
            LocalDate currentDate = LocalDate.now();
            this.employeeId = String.format("NV%02d%02d%02d%03d",
                    currentDate.getYear() % 100,
                    currentDate.getMonthValue(),
                    currentDate.getDayOfMonth(),
                    Utils.randomNumber(1, 999)
            );
            return;
        }
        if (employeeId.matches("^NV\\d{9}$")) {
            this.employeeId = employeeId;
            return;
        }
        throw new IllegalArgumentException("Invalid employeeId format");
    }

    public void setName(String name) {
        if (name == null || !name.matches("^[a-zA-ZÀ-ỹ]+(?:[ ][a-zA-ZÀ-ỹ]+)*$")) {
            System.out.println(name);
            throw new IllegalArgumentException("Invalid name");
        }
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("^0\\d{9}$")) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        this.phoneNumber = phoneNumber;
    }

    public void setCitizenIDNumber(String citizenIDNumber) {
        if (citizenIDNumber == null || citizenIDNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid citizenIDNumber");
        }
        this.citizenIDNumber = citizenIDNumber;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthday(LocalDate birthday) {
        if (birthday != null && LocalDate.now().minusYears(18).isBefore(birthday)) {
            throw new IllegalArgumentException("Employee age must greater than 18");
        }
        this.birthday = birthday;
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

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid status");
        }
        this.status = status;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public void setPosition(String position) {
        if (position == null || position.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid position");
        }
        this.position = position;
    }

    public void setWorkHours(double workHours) {
        if (workHours < 0) {
            throw new IllegalArgumentException("workHour must greater or equal 0");
        }
        this.workHours = workHours;
    }

    public void setHourlyPay(double hourlyPay) {
        if (hourlyPay < 20000) {
            throw new IllegalArgumentException("hourlyPay must greater than or equal 20000");
        }
        this.hourlyPay = hourlyPay;
    }

    public void setProfileImage(byte[] profileImage) {
        if (profileImage == null) {
            this.profileImage = new byte[0];
            return;
        }
        this.profileImage = profileImage;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCitizenIDNumber() {
        return citizenIDNumber;
    }

    public int getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public String getPosition() {
        return position;
    }

    public double getWorkHours() {
        return workHours;
    }

    public double getHourlyPay() {
        return hourlyPay;
    }

    public double getSalary() {
        return salary;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public Employee getManager() {
        return manager;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + Utils.toStringGender(gender) +
                ", birthday=" + birthday +
                ", citizenIDNumber='" + citizenIDNumber + '\'' +
                ", status='" + status + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", hireDate=" + hireDate +
                ", position='" + position + '\'' +
                ", workHours=" + workHours +
                ", hourlyPay=" + hourlyPay +
                ", salary=" + salary +
                ", profileImage=" + Arrays.toString(profileImage) +
                ", manager=" + (manager == null ? "Không có" : manager.getEmployeeId()) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(employeeId);
    }
}