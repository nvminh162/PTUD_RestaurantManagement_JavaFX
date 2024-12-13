package com.huongbien.entity;

import com.huongbien.utils.Utils;

import java.util.Objects;

public class FoodOrder {
    private String foodOrderId;
    private int quantity;
    private String note;
    private double salePrice;
    private Cuisine cuisine;

    //  truyền mã FoodOrder hoặc truyền mã Reservation để tự động tạo mã mới nếu tạo FoodOrder mới
    public FoodOrder(String foodOrderId, int quantity, double salePrice, Cuisine cuisine, String note) {
        setFoodOrderId(foodOrderId);
        setQuantity(quantity);
        setNote(note);
        setSalePrice(salePrice);
        setCuisine(cuisine);
    }

    public FoodOrder() {}

    public void setFoodOrderId(String foodOrderId) {
        if (foodOrderId != null && foodOrderId.length() == 17) {
            this.foodOrderId = String.format("%sDM%03d", foodOrderId, Utils.randomNumber(1, 999));
            return;
        }
        if (foodOrderId == null || !foodOrderId.matches("^DB\\d{15}DM\\d{3}$")) {
            throw new IllegalArgumentException("Invalid food order ID format");
        }
        this.foodOrderId = foodOrderId;
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

    public Cuisine getCuisine() {
        return cuisine;
    }

    public String getFoodOrderId() {
        return foodOrderId;
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

    @Override
    public String toString() {
        return "FoodOrder{" +
                "foodOrderId='" + foodOrderId + '\'' +
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
        FoodOrder foodOrder = (FoodOrder) o;
        return Objects.equals(foodOrderId, foodOrder.foodOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(foodOrderId);
    }
}