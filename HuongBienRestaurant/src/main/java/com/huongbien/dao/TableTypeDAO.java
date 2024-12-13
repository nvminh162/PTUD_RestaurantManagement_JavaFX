package com.huongbien.dao;

import com.huongbien.entity.TableType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableTypeDAO extends GenericDAO<TableType> {
    private static final TableTypeDAO instance = new TableTypeDAO();

    private TableTypeDAO() {
        super();
    }

    public static TableTypeDAO getInstance() {
        return instance;
    }

    @Override
    public TableType resultMapper(ResultSet resultSet) throws SQLException {
        TableType tableType = new TableType();
        tableType.setTableId(resultSet.getString("id"));
        tableType.setName(resultSet.getString("name"));
        tableType.setDescription(resultSet.getString("description"));
        return tableType;
    }

    public List<TableType> getAll() {
        return getMany("SELECT * FROM TableType;");
    }

    public TableType getById(String id) {
        return getOne("SELECT * FROM TableType WHERE id = ?;", id);
    }

    //    TODO: viết lại hàm và chuyển logic xử lý vào bus
    public TableType getByName(String name) {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new NullPointerException("Name is null");
        }
        String query = "SELECT id, name, description FROM TableType WHERE name = ?";
        try {
            PreparedStatement statement = statementHelper.prepareStatement(query, name);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return null;
            return resultMapper(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTypeName(String name) {
        String query = "SELECT * FROM TableType WHERE name LIKE N'%" + name + "%'";
        return getOne(query).getTableId();
    }

    public List<String> getDistinctTableType() {
        List<String> typeList = new ArrayList<>();
        String sql = "SELECT DISTINCT name FROM [TableType]";

        try {
            PreparedStatement statement = statementHelper.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                typeList.add(resultSet.getString("name"));
            }
            return typeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean add(TableType object) {
        try {
            String query = "INSERT INTO TableType (id, name, description) VALUES (?, ?, ?)";
            PreparedStatement statement = statementHelper.prepareStatement(query, object.getTableId(), object.getName(), object.getDescription());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(String id) {
        try {
            String query = "DELETE FROM TableType WHERE id = ?";
            PreparedStatement statement = statementHelper.prepareStatement(query, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
