package com.huongbien.dao;

import com.huongbien.entity.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDAO extends GenericDAO<Employee> {
    private static final EmployeeDAO instance = new EmployeeDAO();

    private EmployeeDAO() {
        super();
    }

    public static EmployeeDAO getInstance() {
        return instance;
    }

    @Override
    public Employee resultMapper(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(resultSet.getString("id"));
        employee.setName(resultSet.getString("name"));
        employee.setPhoneNumber(resultSet.getString("phoneNumber"));
        employee.setCitizenIDNumber(resultSet.getString("citizenIDNumber"));
        employee.setGender(resultSet.getInt("gender"));
        employee.setAddress(resultSet.getString("address"));
        employee.setBirthday(resultSet.getDate("birthday").toLocalDate());
        employee.setEmail(resultSet.getString("email"));
        employee.setStatus(resultSet.getString("status"));
        employee.setHireDate(resultSet.getDate("hireDate").toLocalDate());
        employee.setPosition(resultSet.getString("position"));
        employee.setWorkHours(resultSet.getDouble("workHours"));
        employee.setHourlyPay(resultSet.getDouble("hourlyPay"));
        employee.setSalary(resultSet.getDouble("salary"));
        employee.setProfileImage(resultSet.getBytes("profileImage"));
        String managerId = resultSet.getString("managerId");
        employee.setManager(managerId == null ? null : getOne("SELECT * FROM Employee WHERE id = ?;", managerId));
        return employee;
    }

    public List<Employee> getAll() {
        return getMany("SELECT * FROM Employee;");
    }

    public Employee getOneById(String id) {
        return getOne("SELECT * FROM [Employee] WHERE id = ?", id);
    }

    public List<Employee> getManyById(String id) {
        return getMany("SELECT * FROM Employee WHERE id LIKE ?;", id + "%");
    }
    public Employee getByEmail(String email) {
        return getOne("SELECT * FROM Employee WHERE email = ?;", email);
    }

    public List<Employee> getByPhoneNumber(String phoneNumber) {
        return getMany("SELECT * FROM Employee WHERE phoneNumber LIKE ?;", phoneNumber + "%");
    }

    public List<Employee> getByPosition(String position) {
        return getMany("SELECT * FROM Employee WHERE position = ?;", position);
    }

    public List<Employee> getByName(String name) {
        return getMany("SELECT * FROM Employee WHERE name LIKE ?;", name + "%");
    }

    public List<Employee> getBycitizenIDNumber(String citizenIDNumber) {
        return getMany("SELECT * FROM Employee WHERE citizenIDNumber LIKE ?;", citizenIDNumber + "%");
    }

    public List<Employee> getByCriteria(String phoneNumber, String name, String employeeId) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM Employee WHERE 1=1");
        int parameterCase = 0;

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            sqlBuilder.append(" AND phoneNumber LIKE ?");
            phoneNumber += "%";
            parameterCase += 4;
        }
        if (name != null && !name.isEmpty()) {
            sqlBuilder.append(" AND name LIKE ?");
            name = "%" + name + "%";
            parameterCase += 2;
        }
        if (employeeId != null && !employeeId.isEmpty()) {
            sqlBuilder.append(" AND id LIKE ?");
            employeeId += "%";
            parameterCase += 1;
        }

        String sql = sqlBuilder.toString();

        return switch (parameterCase) {
            case 1 -> getMany(sql, employeeId);
            case 2 -> getMany(sql, name);
            case 3 -> getMany(sql, name, employeeId);
            case 4 -> getMany(sql, phoneNumber);
            case 5 -> getMany(sql, phoneNumber, employeeId);
            case 6 -> getMany(sql, phoneNumber, name);
            case 7 -> getMany(sql, phoneNumber, name, employeeId);
            default -> throw new RuntimeException("Invalid arguments");
        };
    }

    public List<Employee> getAllWithPagination(int offset, int limit) {
        return getMany("SELECT * FROM Employee ORDER BY position OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;", offset, limit);
    }

    public List<Employee> getAllStillWorkingWithPagination(int offset, int limit) {
        return getMany("SELECT * FROM Employee WHERE status = N'Đang làm' ORDER BY position OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;", offset, limit);
    }

    public List<Employee> getByPositionWithPagination(String position, int offset, int limit) {
        return getMany("SELECT * FROM Employee WHERE position LIKE ? ORDER BY status OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;", "%" + position + "%", offset, limit);
    }

    public List<Employee> getByPhoneNumberWithPagination(String phoneNumber, int offset, int limit) {
        return getMany("SELECT * FROM Employee WHERE phoneNumber LIKE ? ORDER BY status OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;", phoneNumber + "%", offset, limit);
    }

    public List<Employee> getByNameWithPagination(String name, int offset, int limit) {
        return getMany("SELECT * FROM Employee WHERE name LIKE ? ORDER BY status OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;", "%" + name + "%", offset, limit);
    }

    public int countByPhoneNumber(String phoneNumber) {
        return count("SELECT COUNT(*) FROM Employee WHERE phoneNumber LIKE ?;", phoneNumber + "%");
    }

    public int countByName(String name) {
        return count("SELECT COUNT(*) FROM Employee WHERE name LIKE ?;", "%" + name + "%");
    }

    public int countByPosition(String position) {
        return count("SELECT COUNT(*) FROM Employee WHERE position LIKE ?;", "%" + position + "%");
    }

    public int countStillWorking() {
        return count("SELECT COUNT(*) FROM Employee WHERE status = N'Đang làm';");
    }

    public int countAll() {
        return count("SELECT COUNT(*) FROM Employee;");
    }

    public boolean updateStatus(String id, String status) {
        return update("UPDATE Employee SET status = ? WHERE id = ?;", status, id);
    }

    public void updateWorkHour(String id, double workHours) {
        update("UPDATE Employee SET workHours = ? WHERE id = ?;", workHours, id);
    }

    public boolean updateEmployeeInfo(Employee employee) {
        String sql = "UPDATE Employee SET name = ?, phoneNumber = ?, citizenIDNumber = ?, gender = ?, address = ?, birthday = ?, email = ?, status = ?, hireDate = ?, position = ?, workHours = ?, hourlyPay = ?, salary = ?, profileImage = ?, managerId = ? WHERE id = ?";
        return update(sql,
                employee.getName(),
                employee.getPhoneNumber(),
                employee.getCitizenIDNumber(),
                employee.getGender(),
                employee.getAddress(),
                employee.getBirthday(),
                employee.getEmail(),
                employee.getStatus(),
                employee.getHireDate(),
                employee.getPosition(),
                employee.getWorkHours(),
                employee.getHourlyPay(),
                employee.getSalary(),
                employee.getProfileImage(),
                employee.getManager() != null ? employee.getManager().getEmployeeId() : null,
                employee.getEmployeeId());
    }

    public boolean updateEmployeeInfoProfile(Employee employee) {
        String sql = "UPDATE Employee SET name = ?, phoneNumber = ?, birthday = ?, email = ?, profileImage = ? WHERE id = ?";
        return update(sql,
                employee.getName(),
                employee.getPhoneNumber(),
                employee.getBirthday(),
                employee.getEmail(),
                employee.getProfileImage(),
                employee.getEmployeeId());
    }

    @Override
    public boolean add(Employee object) {
        String sql = """
            INSERT INTO Employee (
                id, name, phoneNumber, citizenIDNumber, gender, address, birthday, email,
                status, hireDate, position, workHours, hourlyPay, salary, profileImage, managerId)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try {
            PreparedStatement statement = statementHelper.prepareStatement(
                    sql,
                    object.getEmployeeId(),
                    object.getName(),
                    object.getPhoneNumber(),
                    object.getCitizenIDNumber(),
                    object.getGender(),
                    object.getAddress(),
                    object.getBirthday(),
                    object.getEmail(),
                    object.getStatus(),
                    object.getHireDate(),
                    object.getPosition(),
                    object.getWorkHours(),
                    object.getHourlyPay(),
                    object.getSalary(),
                    object.getProfileImage(),
                    object.getManager() != null ? object.getManager().getEmployeeId() : null
            );
            int rowAffected = statement.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}