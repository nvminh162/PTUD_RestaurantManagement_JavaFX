package com.huongbien.dao;

import com.huongbien.entity.Cuisine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuisineDAO extends GenericDAO<Cuisine> {
    private static final CuisineDAO instance = new CuisineDAO();
    private final CategoryDAO categoryDao;

    public CuisineDAO() {
        super();
        this.categoryDao = CategoryDAO.getInstance();
    }

    public static CuisineDAO getInstance() {
        return instance;
    }

    @Override
    public Cuisine resultMapper(ResultSet resultSet) throws SQLException {
        Cuisine cuisine = new Cuisine();
        cuisine.setCuisineId(resultSet.getString("id"));
        cuisine.setName(resultSet.getString("name"));
        cuisine.setPrice(resultSet.getDouble("price"));
        cuisine.setDescription(resultSet.getString("description"));
        cuisine.setImage(resultSet.getBytes("image"));
        cuisine.setStatus(resultSet.getString("status"));
        cuisine.setCategory(categoryDao.getById(resultSet.getString("categoryId")));
        return cuisine;
    }

    public List<String> getAllCuisineNames() {
        List<String> cuisineNames = new ArrayList<>();
        String sql = "SELECT name FROM cuisine";
        try {
            PreparedStatement statement = statementHelper.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                cuisineNames.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cuisineNames;
    }

    public List<String> getCuisineNamesByCategory(String categoryName) {
        List<String> cuisineNames = new ArrayList<>();
        String sql = "SELECT name FROM cuisine WHERE categoryId IN (SELECT id FROM Category WHERE name LIKE ?)";
        try {
            PreparedStatement statement = statementHelper.prepareStatement(sql, "%" + categoryName + "%");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                cuisineNames.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cuisineNames;
    }

    public List<Cuisine> getByName(String name) {
        return getMany("SELECT * FROM cuisine WHERE name LIKE ?", "%" + name + "%");
    }

    public List<Cuisine> getAll() {
        return getMany("SELECT * FROM cuisine");
    }

    public  List<Cuisine> getLookUpCuisine(String name, String category){
        return getMany("SELECT * FROM cuisine WHERE name LIKE N'%" + name + "%' AND categoryId LIKE N'%"+ category +"%'");
    }

    public List<Cuisine> getLookUpCuisine(String name, String category, int pageIndex){
        return getMany("SELECT * FROM cuisine WHERE name LIKE N'%" + name + "%' AND categoryId LIKE N'%"+ category +"%' ORDER BY categoryId OFFSET " + pageIndex + " ROWS FETCH NEXT 7 ROWS ONLY");
    }

    public int getCountLookUpCuisine(String name, String category){
        return count("SELECT COUNT (*) AS countRow FROM cuisine WHERE name LIKE N'%" + name + "%' AND categoryId LIKE N'%"+ category +"%'");
    }

    public Cuisine getById(String id) {
        return getOne("SELECT * FROM cuisine WHERE id = ?", id);
    }

    public List<String> getCuisineCategory(){
        List<String> categoryList = new ArrayList<String>();
        String sql = "SELECT DISTINCT categoryId FROM Cuisine";
        try {
            PreparedStatement statement = statementHelper.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                categoryList.add(rs.getString("categoryId"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoryList;
    }

    public List<Cuisine> getByCategoryWithPagination(int offset, int limit, String category) {
        return getMany("SELECT * FROM cuisine WHERE categoryId IN (SELECT id FROM Category WHERE name LIKE ?) ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", "%" + category + "%", offset, limit);
    }

    public List<Cuisine> getByNameWithPagination(int offset, int limit, String name) {
        return getMany("SELECT * FROM cuisine WHERE name LIKE ? ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", "%" + name + "%", offset, limit);
    }

    public List<Cuisine> getAllWithPagination(int offset, int limit) {
        return getMany("SELECT * FROM cuisine ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", offset, limit);
    }

    public int countCuisinesByName(String name) {
        return count("SELECT COUNT(*) AS countRow FROM cuisine WHERE name LIKE ?", "%" + name + "%");
    }

    public int countCuisinesByCategory(String category) {
        return count("SELECT COUNT(*) AS countRow FROM cuisine WHERE categoryId IN (SELECT id FROM Category WHERE name LIKE ?)", "%" + category + "%");
    }

    public int countTotal() {
        return count("SELECT COUNT(*) AS countRow FROM cuisine");
    }

    public boolean updateCuisineStatus(String cuisineId, String status) {
        return update("UPDATE cuisine SET status = ? WHERE id = ?", status, cuisineId);
    }

    public boolean updateCuisineInfo(Cuisine cuisine) {
        String sql = "UPDATE cuisine SET name = ?, price = ?, description = ?, image = ?, status = ?, categoryID = ? WHERE id = ?";
        return update(sql, cuisine.getName(), cuisine.getPrice(), cuisine.getDescription(), cuisine.getImage(), cuisine.getStatus(), cuisine.getCategory().getCategoryId(), cuisine.getCuisineId());
    }

    @Override
    public boolean add(Cuisine object) {
        String sql = "INSERT INTO cuisine (id, name, price, description, image, status, categoryID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = statementHelper.prepareStatement(sql, object.getCuisineId(), object.getName(), object.getPrice(), object.getDescription(), object.getImage(), object.getStatus(), object.getCategory().getCategoryId());
            int rowAffected = statement.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}