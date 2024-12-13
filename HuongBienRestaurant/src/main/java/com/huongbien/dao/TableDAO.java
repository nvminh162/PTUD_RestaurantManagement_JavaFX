package com.huongbien.dao;

import com.huongbien.config.Variable;
import com.huongbien.entity.Table;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class    TableDAO extends GenericDAO<Table> {
    private final TableTypeDAO tableTypeDAO;
    private static final TableDAO instance = new TableDAO();

    private TableDAO() {
        super();
        this.tableTypeDAO = TableTypeDAO.getInstance();
    }

    public static TableDAO getInstance() {
        return instance;
    }

    @Override
    public Table resultMapper(ResultSet resultSet) throws SQLException {
        Table table = new Table();
        table.setId(resultSet.getString("id"));
        table.setName(resultSet.getString("name"));
        table.setFloor(resultSet.getInt("floor"));
        table.setSeats(resultSet.getInt("seats"));
        table.setStatus(resultSet.getString("status"));
        table.setTableType(tableTypeDAO.getById(resultSet.getString("tableTypeId")));
        return table;
    }

    public List<Table> getAll() {
        return getMany("SELECT * FROM [Table];");
    }

    public Table getById(String id) {
        return getOne("SELECT * FROM [Table] WHERE id = ?", id);
    }

    public List<Table> getByName(String name) {
        return getMany("SELECT * FROM [Table] WHERE name LIKE ?", name + "%");
    }

    public List<Table> getAllByReservationId(String reservationId) {
        return getMany("SELECT * FROM [Table] WHERE id IN (SELECT tableId FROM Reservation_Table WHERE reservationId = ?)", reservationId);
    }

    public List<Table> getAllByOrderId(String orderId) {
        return getMany("SELECT * FROM [Table] WHERE id IN (SELECT tableId FROM Order_Table WHERE orderId = ?)", orderId);
    }

    public int getCountStatisticalOverviewTable() {
        return count("SELECT COUNT(*) FROM [Table] WHERE status != N'Bàn đóng'");
    }

    public int getCountStatisticalOverviewTableEmpty() {
        return count("SELECT COUNT(*) FROM [Table] WHERE status = N'Bàn trống'");
    }

    public int getCountStatisticalFloorTable(int floor) {
        return count("SELECT COUNT(*) FROM [Table] WHERE status != N'Bàn đóng' AND floor = ?", floor);
    }

    public int getCountStatisticalFloorTableEmpty(int floor) {
        return count("SELECT COUNT(*) FROM [Table] WHERE status = N'Bàn trống' AND floor = ?", floor);
    }

    public int getCountStatisticalFloorTablePreOrder(int floor) {
        return count("SELECT COUNT(*) FROM [Table] WHERE status = N'Đặt trước' AND floor = ?", floor);
    }

    public int getCountStatisticalFloorTableOpen(int floor) {
        return count("SELECT COUNT(*) FROM [Table] WHERE status = N'Phục vụ' AND floor = ?", floor);
    }

    //TODO: Sửa return getMany cho nó gọn (- Minh -)
    public List<Table> getByCriteria(String floor, String status, String typeID, String seat) {
        List<Table> tables = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM [Table] WHERE status != N'Bàn đóng'");
        List<String> parameters = new ArrayList<>();

        if (!floor.isEmpty()) {
            sqlBuilder.append(" AND floor = ?");
            parameters.add(floor);
        }

        if (!status.equals(Variable.status) && !status.isEmpty()) {
            sqlBuilder.append(" AND status = ?");
            parameters.add(status);
        }

        if (!typeID.equals(Variable.tableTypeName) && !typeID.isEmpty()) {
            sqlBuilder.append(" AND tableTypeId = ?");
            parameters.add(typeID);
        }

        if (!seat.equals(Variable.seats) && !seat.isEmpty()) {
            sqlBuilder.append(" AND seats = ?");
            parameters.add(seat);
        }

        String sql = sqlBuilder.toString();

        try {
            PreparedStatement stmt = statementHelper.prepareStatement(sql);
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setString(i + 1, parameters.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tables.add(resultMapper(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying tables by criteria", e);
        }
        return tables;
    }

    public List<String> getDistinctFloor() {
        List<String> floors = new ArrayList<>();
        String sql = "SELECT DISTINCT floor FROM [Table]";

        try {
            PreparedStatement statement = statementHelper.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                floors.add(resultSet.getString("floor"));
            }
            return floors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getDistinctSeat() {
        List<String> seats = new ArrayList<>();
        String sql = "SELECT DISTINCT seats FROM [Table]";

        try {
            PreparedStatement statement = statementHelper.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                seats.add(resultSet.getString("seats"));
            }
            return seats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: Tối ưu cho gọn giúp t (- Minh -)
    public Table getTopFloor() {
        try {
            PreparedStatement statement = statementHelper.prepareStatement("SELECT TOP 1 floor FROM [Table] ORDER BY floor ASC");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int floor = resultSet.getInt("floor");
                Table table = new Table();
                table.setFloor(floor);
                return table;
            }
            return null; // Nếu không tìm thấy floor nào
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Table> getReservedTables(LocalDate receiveDate) {
        try {
            CallableStatement procedure = statementHelper.callProcedure("{call GetReservedTable(?)}", receiveDate);
            ResultSet resultSet = procedure.executeQuery();
            List<Table> tables = new ArrayList<>();
            while (resultSet.next()) {
                tables.add(resultMapper(resultSet));
            }
            return tables;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getDistinctStatuses() {
        try {
            PreparedStatement statement = statementHelper.prepareStatement("SELECT DISTINCT status FROM [Table] WHERE status != N'Bàn đóng'");
            ResultSet resultSet = statement.executeQuery();
            List<String> statuses = new ArrayList<>();
            while (resultSet.next()) {
                statuses.add(resultSet.getString("status"));
            }
            return statuses;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getDistinctFloors() {
        try {
            PreparedStatement statement = statementHelper.prepareStatement("SELECT DISTINCT floor FROM [Table]");
            ResultSet resultSet = statement.executeQuery();
            List<Integer> floors = new ArrayList<>();
            while (resultSet.next()) {
                floors.add(resultSet.getInt("floor"));
            }
            return floors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getDistinctSeats() {
        try {
            PreparedStatement statement = statementHelper.prepareStatement("SELECT DISTINCT seats FROM [Table]");
            ResultSet resultSet = statement.executeQuery();
            List<Integer> seats = new ArrayList<>();
            while (resultSet.next()) {
                seats.add(resultSet.getInt("seats"));
            }
            return seats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getDistinctTableType() {
        try {
            PreparedStatement statement = statementHelper.prepareStatement("SELECT DISTINCT tableTypeId FROM [Table]");
            ResultSet resultSet = statement.executeQuery();
            List<String> tableTypes = new ArrayList<>();
            while (resultSet.next()) {
                tableTypes.add(resultSet.getString("tableTypeId"));
            }
            return tableTypes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Table> getLookUpTable(int floor, String name, int seat, String type, String status, int pageIndex){
        String sqlQuery = "SELECT * FROM [Table] WHERE name LIKE N'%" + name + "%' AND tableTypeId LIKE N'%" + type + "%' AND status LIKE N'%" + status + "%'";
        String seatQuery = "";
        String floorQuery = "";
        if(floor != -1) {
            floorQuery = " AND floor = " + floor;
        }
        if(seat != -1){
            seatQuery = " AND seats = " + seat;
        }
        sqlQuery += floorQuery + seatQuery + "ORDER BY floor OFFSET " + pageIndex + " ROWS FETCH NEXT 7 ROWS ONLY";
        return getMany(sqlQuery);
    }

    public int getCountLookUpTable(int floor, String name, int seat, String type, String status){
        String sqlQuery = "SELECT COUNT (*) AS countRow FROM [Table] WHERE name LIKE N'%" + name + "%' AND tableTypeId LIKE N'%" + type + "%' AND status LIKE N'%" + status + "%'";
        String seatQuery = "";
        String floorQuery = "";
        if(floor != -1) {
            floorQuery = " AND floor = " + floor;
        }
        if(seat != -1){
            seatQuery = " AND seats = " + seat;
        }
        sqlQuery += floorQuery + seatQuery;
        return count(sqlQuery);
    }

    public List<Table> getAllWithPagination(int offset, int limit) {
        return getMany("SELECT * FROM [Table] ORDER BY floor OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", offset, limit);
    }

    public List<Table> getByNameWithPagination(String name, int offset, int limit) {
        return getMany("SELECT * FROM [Table] WHERE name LIKE ? ORDER BY floor OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", "%" + name + "%", offset, limit);
    }

    public List<Table> getByFloorWithPagination(int offset, int limit, int floor) {
        return getMany("SELECT * FROM [Table] WHERE floor = ? ORDER BY floor OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", floor, offset, limit);
    }

    public int countTotalTables() {
        return count("SELECT COUNT(*) FROM [Table]");
    }

    public int countTotalTablesByFloor(int floor) {
        return count("SELECT COUNT(*) FROM [Table] WHERE floor = ?", floor);
    }

    public int countTotalTablesByName(String name) {
        return count("SELECT COUNT(*) FROM [Table] WHERE name LIKE ?", "%" + name + "%");
    }

    public boolean updateStatus(String tableId, String status) {
        return update("UPDATE [Table] SET status = ? WHERE id = ?", status, tableId);
    }

    @Override
    public boolean add(Table object) {
        try {
            String sql = "INSERT INTO [Table] (id, name, floor, seats, status, tableTypeId) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = statementHelper.prepareStatement(sql, object.getId(), object.getName(), object.getFloor(), object.getSeats(), object.getStatus(), object.getTableType() != null ? object.getTableType().getTableId() : null);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateTableInfo(Table table) {
        String sql = "UPDATE [Table] SET name = ?, floor = ?, seats = ?, status = ?, tableTypeId = ? WHERE id = ?";
        return update(sql, table.getName(), table.getFloor(), table.getSeats(), table.getStatus(), table.getTableType().getTableId(), table.getId());
    }

    public boolean add(List<Table> tables) throws SQLException {
        for (Table table : tables) {
            if (!add(table)) {
                return false;
            }
        }
        return true;
    }

    public boolean addTablesToOrder(String orderId, List<Table> tables) {
        try {
            String sql = "INSERT INTO Order_Table (orderId, tableId) VALUES (?, ?)";
            PreparedStatement statement = statementHelper.prepareStatement(sql, orderId);
            for (Table table : tables) {
                statement.setString(2, table.getId());
                if (statement.executeUpdate() <= 0) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateTablesToReservation(String reservationId, List<Table> tables) {
        try {
            String sql = "DELETE FROM Reservation_Table WHERE reservationId = ?";
            PreparedStatement statement = statementHelper.prepareStatement(sql, reservationId);
            statement.executeUpdate();
            return addTablesToReservation(reservationId, tables);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addTablesToReservation(String reservationId, List<Table> tables) {
        try {
            String sql = "INSERT INTO Reservation_Table (reservationId, tableId) VALUES (?, ?)";
            PreparedStatement statement = statementHelper.prepareStatement(sql, reservationId);
            for (Table table : tables) {
                statement.setString(2, table.getId());
                if (statement.executeUpdate() <= 0) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(String id) {
        try {
            String sql = "DELETE FROM [Table] WHERE id = ?";
            PreparedStatement statement = statementHelper.prepareStatement(sql, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStatusTable(String id, String status) {
        String sql = "UPDATE [Table] SET status = ? WHERE id = ?";
        update(sql, status, id);
    }
}
