package com.huongbien.dao;

import com.huongbien.entity.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CategoryDAO extends GenericDAO<Category> {
    private static final CategoryDAO instance = new CategoryDAO();

    private CategoryDAO() {
        super();
    }

    public static CategoryDAO getInstance() {
        return instance;
    }

    @Override
    public Category resultMapper(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setCategoryId(resultSet.getString("id"));
        category.setName(resultSet.getString("name"));
        category.setDescription(resultSet.getString("description"));
        return category;
    }

    public List<String> getAllCategoryNames() {
        try {
            PreparedStatement statement = statementHelper.prepareStatement("SELECT name FROM category");
            ResultSet resultSet = statement.executeQuery();
            List<String> categoryNames = new ArrayList<>();
            while (resultSet.next()) {
                categoryNames.add(resultSet.getString("name"));
            }
            return categoryNames;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Category> getAll() {
        return getMany("SELECT id, name, description FROM category");
    }

    public List<Category> getByName(String name) {
        return getMany("SELECT id, name, description FROM category WHERE name LIKE ?", "%" + name + "%");
    }

    public Category getCategoryName(String name) {
        return getOne("SELECT * FROM category WHERE name LIKE ?", "%" + name + "%");
    }
    public Category getById(String id) {
        return getOne("SELECT id, name, description FROM category WHERE id = ?", id);
    }

    @Override
    public boolean add(Category object) {
        String sql = "INSERT INTO category (id, name, description) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = statementHelper.prepareStatement(sql, object.getCategoryId(), object.getName(), object.getDescription());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM category WHERE id = ?";
        try {
            PreparedStatement statement = statementHelper.prepareStatement(sql, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}