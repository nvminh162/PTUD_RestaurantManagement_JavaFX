package com.huongbien.entity;

import com.huongbien.utils.Utils;

import java.util.Objects;

public class Cuisine {
    private String cuisineId;
    private String name;
    private double price;
    private String description;
    private byte[] image;
    private String status;
    private Category category;

    public Cuisine(String cuisineId, String name, double price, String description, byte[] image, String status, Category category) {
        setCuisineId(cuisineId);
        setName(name);
        setPrice(price);
        setDescription(description);
        setImage(image);
        setStatus(status);
        setCategory(category);
    }

    public Cuisine(String name, double price, String description, byte[] image, String status, Category category) {
        setCuisineId(null);
        setName(name);
        setPrice(price);
        setDescription(description);
        setImage(image);
        setStatus(status);
        setCategory(category);
    }

    public Cuisine() {
    }

    public void setCuisineId(String cuisineId) {
        if (cuisineId == null) {
            this.cuisineId = String.format("M%03d", Utils.randomNumber(1, 999));
            return;
        }
        if (cuisineId.matches("^M\\d{3}$")) {
            this.cuisineId = cuisineId;
            return;
        }
        throw new IllegalArgumentException("Invalid cuisine ID format");
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        this.price = price;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.description = description;
    }

    public void setImage(byte[] image) {
        if (image == null) {
            this.image = new byte[0];
            return;
        }
        this.image = image;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCuisineId() {
        return cuisineId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Cuisine{" +
                "cuisineId='" + cuisineId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", category=" + category +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuisine cuisine = (Cuisine) o;
        return Objects.equals(cuisineId, cuisine.cuisineId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cuisineId);
    }
}

