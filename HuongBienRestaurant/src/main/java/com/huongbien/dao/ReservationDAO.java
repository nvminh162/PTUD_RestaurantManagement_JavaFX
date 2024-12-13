package com.huongbien.dao;

import com.huongbien.entity.FoodOrder;
import com.huongbien.entity.Reservation;
import com.huongbien.entity.Table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO extends GenericDAO<Reservation> {
    private static final ReservationDAO instance = new ReservationDAO();
    private final CustomerDAO customerDao;
    private final EmployeeDAO employeeDao;
    private final PaymentDAO paymentDao;
    private final TableDAO tableDao;
    private final FoodOrderDAO foodOrderDao;

    private ReservationDAO() {
        super();
        customerDao = CustomerDAO.getInstance();
        employeeDao = EmployeeDAO.getInstance();
        paymentDao = PaymentDAO.getInstance();
        tableDao = TableDAO.getInstance();
        foodOrderDao = FoodOrderDAO.getInstance();
    }

    public static ReservationDAO getInstance() {
        return instance;
    }

    @Override
    public Reservation resultMapper(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(resultSet.getString("id"));
        reservation.setPartyType(resultSet.getString("partyType"));
        reservation.setPartySize(resultSet.getInt("partySize"));
        reservation.setReservationDate(resultSet.getDate("reservationDate").toLocalDate());
        reservation.setReservationTime(resultSet.getTime("reservationTime").toLocalTime());
        reservation.setReceiveDate(resultSet.getDate("receiveDate").toLocalDate());
        reservation.setReceiveTime(resultSet.getTime("receiveTime").toLocalTime());
        reservation.setStatus(resultSet.getString("status"));
        reservation.setDeposit(resultSet.getDouble("deposit"));
        reservation.setRefundDeposit(resultSet.getDouble("refundDeposit"));
        reservation.setNote(resultSet.getString("note"));
        reservation.setCustomer(customerDao.getById(resultSet.getString("customerId")));
        reservation.setEmployee(employeeDao.getManyById(resultSet.getString("employeeId")).getFirst());
        reservation.setPayment(paymentDao.getById(resultSet.getString("paymentId")));
        reservation.setTables((ArrayList<Table>) tableDao.getAllByReservationId(reservation.getReservationId()));
        reservation.setFoodOrders((ArrayList<FoodOrder>) foodOrderDao.getAllByReservationId(reservation.getReservationId()));
        return reservation;
    }

    public List<Reservation> getAllByCustomerId(String customerId) {
        return getMany("SELECT * FROM reservation WHERE customerId = ?", customerId);
    }

    public List<Reservation> getAllByEmployeeId(String employeeId) {
        return getMany("SELECT * FROM reservation WHERE employeeId = ?", employeeId);
    }

    public List<Reservation> getAll() {
        return getMany("SELECT * FROM reservation");
    }

    public List<Reservation> getStatusReservationByDate(LocalDate date, String status, String cusId) {
        return getMany("SELECT * FROM reservation WHERE status = ? AND receiveDate = ? AND customerId LIKE N'%"+cusId+"%'", status, date);
    }

    public int getCountStatusReservationByDate(LocalDate date, String status, String cusId){
        return count("SELECT COUNT(*) FROM reservation WHERE status = ? AND receiveDate = ? AND customerId LIKE N'%"+cusId+"%'", status, date);
    }

    public List<Reservation> getListWaitedToday(){
        return getMany("SELECT * FROM reservation WHERE status LIKE N'Chưa nhận' AND receiveDate = CONVERT(DATE, GETDATE())");
    }

    public List<Table> getListTableStatusToday(List<Reservation> reservationList) throws SQLException {
        List<Table> tableList = new ArrayList<>();
        try{
            if(reservationList != null){
                for (Reservation reservation : reservationList) {
                    String sql = "SELECT * FROM reservation_table WHERE reservationId LIKE N'"+reservation.getReservationId()+"'";
                    PreparedStatement statement = statementHelper.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        Table table = new Table();
                        table.setId(resultSet.getString("tableId"));
                        assert false;
                        tableList.add(table);
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return tableList;
    }
    public void updateStatus (String reservationId, String status){
        update("UPDATE reservation SET status = ? WHERE id = ?", status, reservationId);
    }

    public void updateRefundDeposit (String reservationId, double refundDeposit){
        update("UPDATE reservation SET refundDeposit = ? WHERE id = ?", refundDeposit, reservationId);
    }

    public Reservation getById(String id) {
        return getOne("SELECT * FROM reservation WHERE id = ?", id);
    }

    public List<Reservation> getLookUpReservation(String reservationId, String reservationCusId, LocalDate reservationDate, LocalDate receiveDate, int pageIndex){
        String sqlQuery = "SELECT * FROM reservation WHERE id LIKE N'%" + reservationId + "%' AND customerId LIKE N'%" + reservationCusId + "%' AND status LIKE N'Chưa nhận'";
        String reserDate = "";
        String receiDate = "";
        if (reservationDate != null){
            reserDate = " AND reservationDate = '" + reservationDate + "'";
        }
        if (receiveDate != null){
            receiDate = " AND receiveDate = '" + receiveDate + "'";
        }
        sqlQuery += reserDate + receiDate + " ORDER BY receiveDate DESC OFFSET " + pageIndex + " ROWS FETCH NEXT 7 ROWS ONLY";
        return getMany(sqlQuery);
    }

    public int getCountLookUpReservation(String reservationId, String reservationCusId, LocalDate reservationDate, LocalDate receiveDate){
        String sqlQuery = "SELECT COUNT (*) AS countRow FROM reservation WHERE id LIKE N'%" + reservationId + "%' AND customerId LIKE N'%" + reservationCusId + "%' AND status LIKE N'Chưa nhận'";
        String reserDate = "";
        String receiDate = "";
        if (reservationDate != null){
            reserDate = " AND reservationDate = '" + reservationDate + "'";
        }
        if (receiveDate != null){
            receiDate = " AND receiveDate = '" + receiveDate + "'";
        }
        sqlQuery += reserDate + receiDate;
        return count(sqlQuery);
    }

    public int countTotal() {
        return count("SELECT COUNT(*) AS totalReservation FROM reservation");
    }

    public boolean update(Reservation reservation) {
        String sql = """
               UPDATE reservation
               SET partyType = ?, partySize = ?, reservationDate = ?, reservationTime = ?, receiveDate = ?, receiveTime = ?,
               status = ?, deposit = ?, refundDeposit = ?, note = ?, employeeId = ?, customerId = ?, paymentId = ?
               WHERE id = ?
        """;
        try {
            PreparedStatement statement = statementHelper.prepareStatement(
                    sql,
                    reservation.getPartyType(),
                    reservation.getPartySize(),
                    reservation.getReservationDate(),
                    reservation.getReservationTime(),
                    reservation.getReceiveDate(),
                    reservation.getReceiveTime(),
                    reservation.getStatus(),
                    reservation.getDeposit(),
                    reservation.getRefundDeposit(),
                    reservation.getNote(),
                    reservation.getEmployee().getEmployeeId(),
                    reservation.getCustomer().getCustomerId(),
                    reservation.getPayment() == null ? null : reservation.getPayment().getPaymentId(),
                    reservation.getReservationId()
            );

            if (reservation.getPayment() != null) paymentDao.update(reservation.getPayment());

            int rowAffected = statement.executeUpdate();

            foodOrderDao.updateReservationFoodOrders(reservation.getReservationId(), reservation.getFoodOrders());
            tableDao.updateTablesToReservation(reservation.getReservationId(), reservation.getTables());

            return rowAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean add(Reservation object) {
        String sql = """
               INSERT INTO reservation (
               id, partyType, partySize, reservationDate, reservationTime, receiveDate, receiveTime, status,
               deposit, refundDeposit, note, employeeId, customerId, paymentId)
               VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try {
            PreparedStatement statement = statementHelper.prepareStatement(
                    sql,
                    object.getReservationId(),
                    object.getPartyType(),
                    object.getPartySize(),
                    object.getReservationDate(),
                    object.getReservationTime(),
                    object.getReceiveDate(),
                    object.getReceiveTime(),
                    object.getStatus(),
                    object.getDeposit(),
                    object.getRefundDeposit(),
                    object.getNote(),
                    object.getEmployee().getEmployeeId(),
                    object.getCustomer().getCustomerId(),
                    object.getPayment() == null ? null : object.getPayment().getPaymentId()
            );

            if (object.getPayment() != null) paymentDao.add(object.getPayment());

            int rowAffected = statement.executeUpdate();

            foodOrderDao.add(object.getFoodOrders());
            tableDao.addTablesToReservation(object.getReservationId(), object.getTables());

            return rowAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
