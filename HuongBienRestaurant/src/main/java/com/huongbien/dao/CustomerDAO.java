package com.huongbien.dao;

import com.huongbien.entity.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.huongbien.database.Database.connection;

public class CustomerDAO extends GenericDAO<Customer> {
    private static final CustomerDAO instance = new CustomerDAO();

    private CustomerDAO() {
        super();
    }

    public static CustomerDAO getInstance() {
        return instance;
    }

    @Override
    public Customer resultMapper(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(resultSet.getString("id"));
        customer.setName(resultSet.getString("name"));
        customer.setAddress(resultSet.getString("address"));

        customer.setPhoneNumber(resultSet.getString("phoneNumber"));
        customer.setEmail(resultSet.getString("email"));
        customer.setBirthday(resultSet.getDate("birthday") == null ? null : resultSet.getDate("birthday").toLocalDate());
        customer.setRegistrationDate(resultSet.getDate("registrationDate").toLocalDate());
        customer.setAccumulatedPoints(resultSet.getInt("accumulatedPoints"));
        customer.setMembershipLevel(resultSet.getInt("membershipLevel"));

        customer.setGender(resultSet.getInt("gender"));
        return customer;
    }

    public List<Customer> getAll() {
        return getMany("SELECT * FROM Customer");
    }

    public Customer getByOnePhoneNumber(String phoneNumber) {
        return getOne("SELECT * FROM Customer WHERE phoneNumber = ?", phoneNumber);
    }

    public List<Customer> getByManyPhoneNumber(String phoneNumber) {
        return getMany("SELECT * FROM Customer WHERE phoneNumber LIKE ?", phoneNumber + "%");
    }

    public List<Customer> getByName(String name) {
        return getMany("SELECT * FROM Customer WHERE name LIKE ?", "%" + name + "%");
    }

    public List<Customer> getAllWithPagination(int offset, int limit) {
        return getMany("SELECT * FROM Customer ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", offset, limit);
    }

    public List<Customer> getAllWithPaginationByPhoneNumber(String phoneNumber, int offset, int limit) {
        return getMany("SELECT * FROM Customer WHERE phoneNumber LIKE ? ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", phoneNumber + "%", offset, limit);
    }

    public List<Customer> getAllWithPaginationByName(String name, int offset, int limit) {
        return getMany("SELECT * FROM Customer WHERE name LIKE ? ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", "%" + name + "%", offset, limit);
    }

    public List<Customer> getAllWithPaginationById(String id, int offset, int limit) {
        return getMany("SELECT * FROM Customer WHERE id LIKE ? ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", id + "%", offset, limit);
    }

    public List<Customer> getCustomerInDay(LocalDate date) {
        return getMany("SELECT * FROM Customer WHERE registrationDate = ?", date);
    }

    public List<Customer> getNewCustomersInYear(int year) {
        return getMany("SELECT * FROM Customer WHERE YEAR(registrationDate) = ?", year);
    }

    public List<Customer> getTopMembershipCustomers(int year, int limit) {
        return getMany("SELECT * FROM Customer WHERE YEAR(registrationDate) = ? ORDER BY membershipLevel DESC OFFSET 0 ROWS FETCH NEXT ? ROWS ONLY", year, limit);
    }

    public int countNewCustomerQuantityByYear(int year) {
        return count("SELECT COUNT(*) FROM Customer WHERE YEAR(registrationDate) = ?", year);
    }

    public int countTotalById(String id) {
        return count("SELECT COUNT(*) FROM Customer WHERE id LIKE ?", id + "%");
    }

    public int countTotal() {
        return count("SELECT COUNT(*) FROM Customer");
    }

    public int countTotalByPhoneNumber(String phoneNumber) {
        return count("SELECT COUNT(*) FROM Customer WHERE phoneNumber LIKE ?", phoneNumber + "%");
    }

    public int countTotalByName(String name) {
        return count("SELECT COUNT(*) FROM Customer WHERE name LIKE ?", "%" + name + "%");
    }

    public List<String> getPhoneNumber() {
        List<String> customersPhone = new ArrayList<>();
        String sql = "SELECT phoneNumber FROM Customer";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String phone = rs.getString("phoneNumber");
                customersPhone.add(phone);
            }
            return customersPhone;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer getById(String customerId) {
        return getOne("SELECT * FROM Customer WHERE id = ?", customerId);
    }

    public boolean updateCustomerInfo(Customer customer) {
        String sql = "UPDATE Customer SET name = ?, address = " +
                "?, phoneNumber = ?, email = ?, birthday = ?, gender = ?, accumulatedPoints = ?, membershipLevel = ? WHERE id = ?";
        return update(sql,
                customer.getName(), customer.getAddress(),
                customer.getPhoneNumber(), customer.getEmail(),
                customer.getBirthday(), customer.getGender(),
                customer.getAccumulatedPoints(), customer.getMembershipLevel(), customer.getCustomerId());
    }

    public Customer getCustomerSearchReservation(String search){
        return getOne("SELECT * FROM customer WHERE phoneNumber LIKE N'%"+search+"%'");
    }

    public boolean increasePoint (String id, int point){
        return update("UPDATE customer SET accumulatedPoints = accumulatedPoints + "+point+" WHERE id = '"+id+"'");
    }

    @Override
    public boolean add(Customer customer) {
        String sql = "INSERT INTO Customer (id, name, address, gender, phoneNumber, email, birthday, registrationDate, accumulatedPoints, membershipLevel) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = statementHelper.prepareStatement(sql, customer.getCustomerId(), customer.getName(), customer.getAddress(),
                    customer.getGender(), customer.getPhoneNumber(), customer.getEmail(), customer.getBirthday(), customer.getRegistrationDate(),
                    customer.getAccumulatedPoints(), customer.getMembershipLevel());
            int rowAffected = statement.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
