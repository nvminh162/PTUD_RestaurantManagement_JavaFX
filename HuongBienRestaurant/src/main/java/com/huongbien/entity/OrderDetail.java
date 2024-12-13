package com.huongbien.entity;

import com.huongbien.utils.Utils;

import java.util.Objects;

public class OrderDetail {
    private String orderDetailId;
    private int quantity;
    private String note;
    private double salePrice; // giá bán của món ăn luu rieng^
    private Cuisine cuisine;

    public OrderDetail() {}

    //  truyền mã OrderDetail hoặc truyền mã Order để tự động tạo mã mới nếu tạo OrderDetail mới
    public OrderDetail(String orderDetailId, int quantity, String note, double salePrice, Cuisine cuisine) {
        setOrderDetailId(orderDetailId);
        setQuantity(quantity);
        setNote(note);
        setSalePrice(salePrice);
        setCuisine(cuisine);
    }

    public double calculateSubTotal() {
        return salePrice * quantity;
    }

    public void setOrderDetailId(String orderDetailId) {
        if (orderDetailId != null && orderDetailId.length() == 17) {
            this.orderDetailId = String.format("%sCT%03d", orderDetailId, Utils.randomNumber(1, 999));
            return;
        }
//        if (orderDetailId == null || !orderDetailId.matches("^HD\\d{15}CT\\d{3}$")) {
//            throw new IllegalArgumentException("Invalid order detail ID format");
//        }
        this.orderDetailId = orderDetailId;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = quantity;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setSalePrice(double salePrice) {
        if (salePrice <= 0) {
            throw new IllegalArgumentException("Sale price must be greater than 0");
        }
        this.salePrice = salePrice;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getNote() {
        return note;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailId='" + orderDetailId + '\'' +
                ", quantity=" + quantity +
                ", note='" + note + '\'' +
                ", salePrice=" + salePrice +
                ", cuisine=" + cuisine +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(orderDetailId, that.orderDetailId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderDetailId);
    }
}

