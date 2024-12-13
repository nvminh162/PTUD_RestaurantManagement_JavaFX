package com.huongbien.entity;

import com.huongbien.config.Variable;
import com.huongbien.utils.Utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class Reservation {
    private String reservationId;
    private String partyType;
    private int partySize;
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private LocalDate receiveDate;
    private LocalTime receiveTime;
    private String status;
    private double deposit;
    private double refundDeposit;
    private String note;
    private Payment payment;
    private Employee employee;
    private Customer customer;
    private ArrayList<Table> tables;
    private ArrayList<FoodOrder> foodOrders;

    public Reservation() {}

    public Reservation(String reservationId, String partyType, int partySize, LocalDate reservationDate,
                       LocalTime reservationTime, LocalDate receiveDate, LocalTime receiveTime, String status,
                       double deposit, double refundDeposit, String note, Payment payment, Employee employee,
                       Customer customer, ArrayList<Table> tables, ArrayList<FoodOrder> foodOrders) {
        setReservationId(reservationId);
        setPartyType(partyType);
        setPartySize(partySize);
        setReservationDate(reservationDate);
        setReservationTime(reservationTime);
        setReceiveDate(receiveDate);
        setReceiveTime(receiveTime);
        setStatus(status);
        setDeposit(deposit);
        setRefundDeposit(refundDeposit);
        setNote(note);
        setPayment(payment);
        setEmployee(employee);
        setTables(tables);
        setCustomer(customer);
        setFoodOrders(foodOrders);
    }

    public void setReservationId(String reservationId) {
        if (reservationId == null) {
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();
            this.reservationId = String.format("DB%02d%02d%02d%02d%02d%02d%03d",
                    currentDate.getYear() % 100,
                    currentDate.getMonthValue(),
                    currentDate.getDayOfMonth(),
                    currentTime.getHour(),
                    currentTime.getMinute(),
                    currentTime.getSecond(),
                    Utils.randomNumber(1, 999)
            );
            return;
        }
        if (reservationId.matches("^DB\\d{15}$")) {
            this.reservationId = reservationId;
            return;
        }
        throw new IllegalArgumentException("Invalid reservation ID format. Expected format: DB-yy-mm-dd-hh-MM-ss-xxx");
    }

    public void setPartyType(String partyType) {
        if (partyType == null || partyType.trim().isEmpty()) {
            throw new IllegalArgumentException("Party type cannot be empty");
        }
        this.partyType = partyType;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setReservationTime(LocalTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        if (receiveDate == null || receiveDate.isBefore(getReservationDate())) {
            throw new IllegalArgumentException("Receive date must be today or later");
        }
        this.receiveDate = receiveDate;
    }

    public void setReceiveTime(LocalTime receiveTime) {
        this.receiveTime = receiveTime;
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }
        this.status = status;
    }

    public void setDeposit(double deposit) {
        if (deposit < 0) {
            throw new IllegalArgumentException("Deposit must be greater than or equal to 0");
        }
        this.deposit = deposit;
    }

    public void setRefundDeposit(double refundDeposit) {
        if (refundDeposit < 0) {
            throw new IllegalArgumentException("Refund deposit must be greater than or equal to 0");
        }
        this.refundDeposit = refundDeposit;
    }

    public void setNote (String note) {
        this.note = note;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setTables(ArrayList<Table> tables) {
        if (tables == null) {
            throw new IllegalArgumentException("Table cannot be null");
        }
        this.tables = tables;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setFoodOrders(ArrayList<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getPartyType() {
        return partyType;
    }

    public int getPartySize() {
        return partySize;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public LocalTime getReservationTime() {
        return reservationTime;
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public LocalTime getReceiveTime() {
        return receiveTime;
    }

    public String getStatus() { return status; }

    public double getDeposit() {
        return deposit;
    }

    public double getRefundDeposit() {
        return refundDeposit;
    }

    public String getNote() {
        return note;
    }

    public Payment getPayment() {
        return payment;
    }

    public Employee getEmployee() {
        return employee;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<FoodOrder> getFoodOrders() {
        return foodOrders;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + reservationId + '\'' +
                ", partyType='" + partyType + '\'' +
                ", partySize=" + partySize +
                ", reservationDate=" + reservationDate +
                ", reservationTime=" + reservationTime +
                ", receiveDate=" + receiveDate +
                ", receiveTime=" + receiveTime +
                ", status='" + status + '\'' +
                ", deposit=" + deposit +
                ", refundDeposit=" + refundDeposit +
                ", note='" + note + '\'' +
                ", payment=" + payment +
                ", employee=" + employee +
                ", customer=" + customer +
                ", tables=" + tables +
                ", foodOrders=" + foodOrders +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(reservationId, that.reservationId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reservationId);
    }
}
