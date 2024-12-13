package com.huongbien.dao;

import com.huongbien.database.StatementHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class GenericDAO<T> {
    protected final StatementHelper statementHelper;

    protected GenericDAO() {
        this.statementHelper = StatementHelper.getInstances();
    }

    public abstract T resultMapper(ResultSet resultSet) throws SQLException;

    public List<T> getMany(String sql, Object... args) {
        try {
            PreparedStatement statement = statementHelper.prepareStatement(sql, args);
            ResultSet resultSet = statement.executeQuery();
            List<T> objects = new java.util.ArrayList<>();
            while (resultSet.next()) {
                objects.add(resultMapper(resultSet));
            }
            return objects;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public T getOne(String sql, Object... args) {
        try {
            PreparedStatement statement = statementHelper.prepareStatement(sql, args);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultMapper(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int count(String sql, Object... args) {
        try {
            PreparedStatement statement = statementHelper.prepareStatement(sql, args);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(String sql, Object... args) {
        try {
            PreparedStatement statement = statementHelper.prepareStatement(sql, args);
            int rowAffected = statement.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract boolean add(T object) throws SQLException;
}
