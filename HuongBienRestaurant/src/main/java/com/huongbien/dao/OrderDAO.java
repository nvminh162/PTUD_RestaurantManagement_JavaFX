package com.huongbien.dao;

import com.huongbien.entity.Order;
import com.huongbien.entity.OrderDetail;
import com.huongbien.entity.Table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends GenericDAO<Order> {
    private final CustomerDAO customerDao;
    private final EmployeeDAO employeeDao;
    private final PromotionDAO promotionDao;
    private final PaymentDAO paymentDao;
    private final OrderDetailDAO orderDetailDao;
    private final TableDAO tableDao;
    private static final OrderDAO instance = new OrderDAO();

    private OrderDAO() {
        super();
        this.customerDao = CustomerDAO.getInstance();
        this.employeeDao = EmployeeDAO.getInstance();
        this.promotionDao = PromotionDAO.getInstance();
        this.paymentDao = PaymentDAO.getInstance();
        this.orderDetailDao = OrderDetailDAO.getInstance();
        this.tableDao = TableDAO.getInstance();
    }

    public static OrderDAO getInstance() {
        return instance;
    }

    @Override
    public Order resultMapper(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setOrderId(resultSet.getString("id"));
        order.setOrderDate(resultSet.getDate("orderDate").toLocalDate());
        order.setOrderTime(resultSet.getTime("orderTime").toLocalTime());
        order.setNotes(resultSet.getString("notes"));
        order.setPaymentAmount(resultSet.getDouble("paymentAmount"));
        order.setDispensedAmount(resultSet.getDouble("dispensedAmount"));
        order.setTotalAmount(resultSet.getDouble("totalAmount"));
        order.setDiscount(resultSet.getDouble("discount"));

        order.setCustomer(customerDao.getById(resultSet.getString("customerId")));
        order.setEmployee(employeeDao.getManyById(resultSet.getString("employeeId")).getFirst());
        order.setPromotion(promotionDao.getById(resultSet.getString("promotionId")));
        order.setPayment(paymentDao.getById(resultSet.getString("paymentId")));

        order.setOrderDetails((ArrayList<OrderDetail>) orderDetailDao.getAllByOrderId(order.getOrderId()));
        order.setTables((ArrayList<Table>) tableDao.getAllByOrderId(order.getOrderId()));

        return order;
    }

    public List<Order> getAllByCustomerId(String customerId) {
        return getMany("SELECT * FROM [Order] WHERE customerId = ?", customerId);
    }

    public List<Order> getAllByEmployeeId(String employeeId) {
        return getMany("SELECT * FROM [Order] WHERE employeeId = ?", employeeId);
    }

    public List<Order> getAllWithPagination(int offset, int limit) {
        return getMany("SELECT * FROM [Order] ORDER BY orderDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", offset, limit);
    }

    public List<Order> getAllByCustomerPhoneNumberWithPagination(int offset, int limit, String customerPhoneNumber) {
        return getMany("SELECT * FROM [Order] WHERE customerId IN (SELECT id FROM Customer WHERE phoneNumber LIKE ?) ORDER BY orderDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", customerPhoneNumber + "%", offset, limit);
    }

    public List<Order> getAllByEmployeeIdWithPagination(int offset, int limit, String employeeId) {
        return getMany("SELECT * FROM [Order] WHERE employeeId LIKE ? ORDER BY orderDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", employeeId + "%", offset, limit);
    }

    public List<Order> getAllByIdWithPagination(int offset, int limit, String orderId) {
        return getMany("SELECT * FROM [Order] WHERE id LIKE ? ORDER BY orderDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY",  orderId + "%", offset, limit);
    }

    public List<Order> getAll() {
        return getMany("SELECT * FROM [Order]");
    }

    public Order getById(String id) {
        return getOne("SELECT * FROM [Order] WHERE id = ?", id);
    }

    public int countTotal() {
        return count("SELECT COUNT(*) AS totalOrder FROM [Order]");
    }

    public int countTotalByOrderId(String orderId) {
        return count("SELECT COUNT(*) AS totalOrder FROM [Order] WHERE id LIKE ?", orderId + '%');
    }

    public int countTotalByCustomerPhoneNumber(String customerPhoneNumber) {
        return count("SELECT COUNT(*) AS totalOrder FROM [Order] WHERE customerId IN (SELECT id FROM Customer WHERE phoneNumber LIKE ?)", customerPhoneNumber + "%");
    }

    public int countTotalByEmployeeId(String employeeId) {
        return count("SELECT COUNT(*) AS totalOrder FROM [Order] WHERE employeeId LIKE ?", employeeId + "%");
    }

    @Override
    public boolean add(Order object) {
        String sql = """
            INSERT INTO [Order] (
                id, orderDate, orderTime, notes, vatTax, paymentAmount, dispensedAmount, totalAmount, discount,
                customerId, employeeId, promotionId, paymentId)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try {
            PreparedStatement statement = statementHelper.prepareStatement(
                    sql,
                    object.getOrderId(),
                    object.getOrderDate(),
                    object.getOrderTime(),
                    object.getNotes(),
                    object.getVatTax(),
                    object.getPaymentAmount(),
                    object.getDispensedAmount(),
                    object.getTotalAmount(),
                    object.getDiscount(),
                    object.getCustomer() != null ? object.getCustomer().getCustomerId() : null,
                    object.getEmployee().getEmployeeId(),
                    object.getPromotion() != null ? object.getPromotion().getPromotionId() : null,
                    object.getPayment() != null ? object.getPayment().getPaymentId() : null
            );
            paymentDao.add(object.getPayment());

            int rowAffected = statement.executeUpdate();

            tableDao.addTablesToOrder(object.getOrderId(), object.getTables());
            orderDetailDao.add(object.getOrderDetails());

            return rowAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
