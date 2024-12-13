package com.huongbien.entity;

import com.huongbien.utils.Utils;

import java.util.Objects;

public class Category {
    private String categoryId;
    private String name;
    private String description;

    public Category(String categoryId, String name, String description) {
        setCategoryId(categoryId);
        setName(name);
        setDescription(description);
    }

    public Category(String name, String description) {
        setCategoryId(null);
        setName(name);
        setDescription(description);
    }

    public Category() {
    }

    public void setCategoryId(String categoryId) {
        if (categoryId == null) {
            this.categoryId = String.format("CG%03d", Utils.randomNumber(1, 999));
            return;
        }
        if (categoryId.matches("^CG\\d{3}$")) {
            this.categoryId = categoryId;
            return;
        }
        throw new IllegalArgumentException("Invalid categoryId format. Expected format: CG-xxx.");
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
    }

    public void setDescription(String description) {
        if (description != null && !description.trim().isEmpty()) {
            this.description = description;
        } else {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId='" + categoryId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryId, category.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(categoryId);
    }
}
