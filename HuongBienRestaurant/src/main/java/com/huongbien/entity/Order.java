package com.huongbien.entity;

import com.huongbien.config.Constants;
import com.huongbien.utils.Utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class Order {
    private String orderId;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private String notes;
    private final double vatTax = Constants.VAT;
    private double paymentAmount;
    private double dispensedAmount;
    private double totalAmount;
    private double discount;
    private Customer customer;
    private Employee employee;
    private Promotion promotion;
    private Payment payment;
    private ArrayList<Table> tables = new ArrayList<>();
    private ArrayList<OrderDetail> orderDetails = new ArrayList<>();

    public Order() {}

    public Order(String orderId, LocalDate orderDate, LocalTime localTime, String notes,
                 double paymentAmount, double dispensedAmount, double totalAmount, double discount,
                 Customer customer, Employee employee, Promotion promotion, Payment payment,
                 ArrayList<Table> tables, ArrayList<OrderDetail> orderDetails) {
        setOrderId(orderId);
        setOrderDate(orderDate);
        setOrderTime(localTime);
        setNotes(notes);
        setPaymentAmount(paymentAmount);
        setDispensedAmount(dispensedAmount);
        setTotalAmount(totalAmount);
        setDiscount(discount);
        setCustomer(customer);
        setEmployee(employee);
        setPromotion(promotion);
        setPayment(payment);
        setTables(tables);
        setOrderDetails(orderDetails);
    }

    public Order(String notes, Employee employee, Customer customer,
                 Payment payment, Promotion promotion,
                 ArrayList<OrderDetail> orderDetails, ArrayList<Table> tables) {
        setOrderId(null);
        setOrderDate(LocalDate.now());
        setOrderTime(LocalTime.now());
        setTotalAmount(calculateGrandTotal());
        setPaymentAmount(payment.getAmount());
        setDiscount(promotion == null ? 0 : promotion.getDiscount());
        setDispensedAmount(getTotalAmount() - getPaymentAmount());

        setCustomer(customer);
        setEmployee(employee);
        setTables(tables);
        setOrderDetails(orderDetails);
        setPayment(payment);
        setPromotion(promotion);
        setNotes(notes);
    }

    public double calculateTotalAmount() {
        return Math.round(orderDetails
                .stream()
                .map((OrderDetail::calculateSubTotal))
                .reduce(0.0, Double::sum));
    }

    public double calculateReducedAmount() {
        double discount = (promotion == null ? 0 : promotion.getDiscount());
        return Math.round(calculateTotalAmount() * discount);
    }

    public double calculateVatTaxAmount() {
        return Math.round((calculateTotalAmount() - calculateReducedAmount()) * vatTax);
    }

    public double calculateGrandTotal() {
        double reducedAmount = calculateReducedAmount();
        return Math.round((calculateTotalAmount() - reducedAmount) + calculateVatTaxAmount());
    }

    public static String generateOrderId(LocalDate orderDate, LocalTime orderTime) {
        LocalDate currentDate = orderDate == null ? LocalDate.now() : orderDate;
        LocalTime currentTime = orderTime == null ? LocalTime.now() : orderTime;
        return String.format("HD%02d%02d%02d%02d%02d%02d%03d",
                currentDate.getYear() % 100,
                currentDate.getMonthValue(),
                currentDate.getDayOfMonth(),
                currentTime.getHour(),
                currentTime.getMinute(),
                currentTime.getSecond(),
                Utils.randomNumber(1, 999)
        );
    }

    public void setOrderId(String orderId) {
        if (orderId == null) {
            this.orderId = Order.generateOrderId(orderDate, orderTime);
            return;
        }
        if (orderId.matches("^HD\\d{15}$")) {
            this.orderId = orderId;
            return;
        }
        throw new IllegalArgumentException("Invalid order ID format");
    }

    public void setOrderDate(LocalDate orderDate) {
        if (orderDate == null) {
            throw new IllegalArgumentException("Order date cannot be null");
        }
        this.orderDate = orderDate;
    }

    public void setOrderTime(LocalTime orderTime) {
        this.orderTime = orderTime;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPaymentAmount(double paymentAmount) {
        if (paymentAmount <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than 0");
        }
        this.paymentAmount = paymentAmount;
    }

    public void setDispensedAmount(double dispensedAmount) {
        if (dispensedAmount <= 0) {
            dispensedAmount = 0;
        }
        this.dispensedAmount = dispensedAmount;
    }

    public void setTotalAmount(double totalAmount) {
//        if (totalAmount <= 0) {
//            throw new IllegalArgumentException("Total amount must be greater than 0");
//        }
        this.totalAmount = totalAmount;
    }

    public void setDiscount(double discount) {
        if (discount < 0) {
            throw new IllegalArgumentException("Discount cannot be negative");
        }
        this.discount = discount;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        if (orderDetails == null) {
            throw new IllegalArgumentException("Order details cannot be null");
        }
        this.orderDetails = orderDetails;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void setPayment(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null");
        }
        this.payment = payment;
    }

    public void setTables(ArrayList<Table> tables) {
        if (tables == null) {
            throw new IllegalArgumentException("Tables cannot be null");
        }
        this.tables = tables;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        this.employee = employee;
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public String getNotes() {
        return notes;
    }

    public double getVatTax() {
        return vatTax;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public double getDispensedAmount() {
        return dispensedAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public Payment getPayment() {
        return payment;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", orderTime=" + orderTime +
                ", notes='" + notes + '\'' +
                ", vatTax=" + vatTax +
                ", paymentAmount=" + paymentAmount +
                ", dispensedAmount=" + dispensedAmount +
                ", totalAmount=" + totalAmount +
                ", discount=" + discount +
                ", customer=" + customer +
                ", employee=" + employee +
                ", promotion=" + promotion +
                ", payment=" + payment +
                ", tables=" + tables +
                ", orderDetails=" + orderDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderId);
    }
}
