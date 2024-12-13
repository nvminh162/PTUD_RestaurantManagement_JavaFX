package com.huongbien.dao;

import com.huongbien.entity.Promotion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO extends GenericDAO<Promotion> {
    private static final PromotionDAO instance = new PromotionDAO();

    private PromotionDAO() {
        super();
    }

    public static PromotionDAO getInstance() {
        return instance;
    }

    @Override
    public Promotion resultMapper(ResultSet resultSet) throws SQLException {
        Promotion promotion = new Promotion();
        promotion.setPromotionId(resultSet.getString("id"));
        promotion.setName(resultSet.getString("name"));
        promotion.setStartDate(resultSet.getDate("startDate").toLocalDate());
        promotion.setEndDate(resultSet.getDate("endDate").toLocalDate());
        promotion.setDiscount(resultSet.getDouble("discount"));
        promotion.setDescription(resultSet.getString("description"));
        promotion.setMembershipLevel(resultSet.getInt("membershipLevel"));
        promotion.setMinimumOrderAmount(resultSet.getDouble("minimumOrderAmount"));
        promotion.setStatus(resultSet.getString("status"));
        return promotion;
    }

    public List<Promotion> getAll() {
        return getMany("SELECT * FROM Promotion");
    }

    public List<Promotion> getAllById(String id) {
        return getMany("SELECT * FROM Promotion WHERE id LIKE ?", id + "%");
    }

    public List<Promotion> getForCustomer(int customerMembershipLevel, double orderAmount) {
        String sql = "SELECT * FROM Promotion WHERE membershipLevel <= ? AND minimumOrderAmount <= ? AND status = N'Còn hiệu lực' ORDER BY discount DESC;";
        return getMany(sql, customerMembershipLevel, orderAmount);
    }

    public List<Promotion> getByStatus(String status) {
        return getMany("SELECT * FROM Promotion WHERE status = ?", status);
    }

    public List<Promotion> getByStatusWithPagination(int offset, int limit, String status) {
        return getMany("SELECT * FROM Promotion WHERE status = ? ORDER BY endDate DESC, membershipLevel DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", status, offset, limit);
    }

    public List<Promotion> getAllWithPagination(int offset, int limit) {
        return getMany("SELECT * FROM Promotion ORDER BY endDate DESC, membershipLevel DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", offset, limit);
    }

    public List<Promotion> getAllByIdWithPagination(int offset, int limit, String id) {
        return getMany("SELECT * FROM Promotion WHERE id LIKE ? ORDER BY endDate DESC, membershipLevel DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY", id + "%", offset, limit);
    }

    public Promotion getById(String id) {
        return getOne("SELECT * FROM Promotion WHERE id = ?", id);
    }

    public int countTotal() {
        return count("SELECT COUNT(*) FROM Promotion");
    }

    public int countTotalByStatus(String status) {
        return count("SELECT COUNT(*) FROM Promotion WHERE status = ?", status);
    }

    public int countTotalById(String id) {
        return count("SELECT COUNT(*) FROM Promotion WHERE id LIKE ?", id + "%");
    }

    public boolean updateInfo(Promotion promotion) {
        return update("UPDATE promotion SET name = ?, startDate = ?, endDate = ?, discount = ?, description = ?, minimumOrderAmount = ?, membershipLevel = ?, status = ? WHERE id = ?",
                promotion.getName(),
                promotion.getStartDate(),
                promotion.getEndDate(),
                promotion.getDiscount(),
                promotion.getDescription(),
                promotion.getMinimumOrderAmount(),
                promotion.getMembershipLevel(),
                promotion.getStatus(),
                promotion.getPromotionId()
        );
    }

    @Override
    public boolean add(Promotion promotion) {
        String sql = "INSERT INTO promotion (id, name, startDate, endDate, discount, description, minimumOrderAmount, membershipLevel, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = statementHelper.prepareStatement(
                    sql,
                    promotion.getPromotionId(),
                    promotion.getName(),
                    promotion.getStartDate(),
                    promotion.getEndDate(),
                    promotion.getDiscount(),
                    promotion.getDescription(),
                    promotion.getMinimumOrderAmount(),
                    promotion.getMembershipLevel(),
                    promotion.getStatus()
            );
            int rowAffected = statement.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM promotion WHERE id = ?";
        try (PreparedStatement statement = statementHelper.prepareStatement(sql)) {
            statement.setString(1, id);

            int rowAffected = statement.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    public List<String> getDistinctPromotionDiscount(){
        try {
            PreparedStatement statement = statementHelper.prepareStatement("SELECT DISTINCT discount FROM [Promotion]");
            ResultSet resultSet = statement.executeQuery();
            List<String> promotionDiscount = new ArrayList<>();
            while (resultSet.next()) {
                promotionDiscount.add(String.format("%.0f%%", resultSet.getDouble("discount") * 100));
            }
            return promotionDiscount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getDistinctPromotionStatus(){
        try {
            PreparedStatement statement = statementHelper.prepareStatement("SELECT DISTINCT status FROM [Promotion]");
            ResultSet resultSet = statement.executeQuery();
            List<String> promotionStatus = new ArrayList<>();
            while (resultSet.next()) {
                promotionStatus.add(resultSet.getString("status"));
            }
            return promotionStatus;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getDistinctPromotionMinimumOrderAmount(){
        try {
            PreparedStatement statement = statementHelper.prepareStatement("SELECT DISTINCT minimumOrderAmount FROM [Promotion]");
            ResultSet resultSet = statement.executeQuery();
            List<String> promotionStatus = new ArrayList<>();
            DecimalFormat priceFormat = new DecimalFormat("#,###");
            while (resultSet.next()) {
                promotionStatus.add(priceFormat.format(resultSet.getDouble("minimumOrderAmount")));
            }
            return promotionStatus;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Promotion> getLookUpPromotion(String promotionName, LocalDate startDate, LocalDate endDate, double discount, double minimumOrderAmount, String status, int pageIndex){
        String sqlQuery = "SELECT * FROM promotion WHERE name LIKE N'%" + promotionName + "%' AND status LIKE N'%" + status + "%'";
        String startDateSQL = "";
        String endDateSQL = "";
        String discountSQL = "";
        String minimumOrderAmountSQL = "";
        if (startDate != null){
            startDateSQL = "AND startDate = '" + startDate + "' ";
        }
        if (endDate != null){
            endDateSQL = "AND endDate = '" + endDate + "' ";
        }
        if (discount != 0){
            discountSQL = "AND discount = " + discount +" ";
        }
        if (minimumOrderAmount != 0){
            minimumOrderAmountSQL = "AND minimumOrderAmount >= "+ minimumOrderAmount +" ";
        }
        sqlQuery += startDateSQL + endDateSQL + discountSQL + minimumOrderAmountSQL + "ORDER BY discount OFFSET " + pageIndex + " ROWS FETCH NEXT 7 ROWS ONLY";
        return getMany(sqlQuery);
    }

    public int getCountLookUpPromotion(String promotionName, LocalDate startDate, LocalDate endDate, double discount, double minimumOrderAmount, String status){
        String sqlQuery = "SELECT COUNT (*) AS countRow FROM promotion WHERE name LIKE N'%" + promotionName + "%' AND status LIKE N'%" + status + "%'";
        String startDateSQL = "";
        String endDateSQL = "";
        String discountSQL = "";
        String minimumOrderAmountSQL = "";
        if (startDate != null){
            startDateSQL = "AND startDate = '" + startDate + "' ";
        }
        if (endDate != null){
            endDateSQL = "AND endDate = '" + endDate + "' ";
        }
        if (discount != 0){
            discountSQL = "AND discount = " + discount +" ";
        }
        if (minimumOrderAmount != 0){
            minimumOrderAmountSQL = "AND minimumOrderAmount >= "+ minimumOrderAmount +" ";
        }
        sqlQuery += startDateSQL + endDateSQL + discountSQL + minimumOrderAmountSQL;
        return count(sqlQuery);
    }

    public List<Promotion> getPaymentPromotion(int memberShipLevel, double totalMoney){
        return getMany("SELECT * FROM promotion WHERE memberShipLevel <= "+memberShipLevel+" AND status LIKE N'Còn hiệu lực' AND minimumOrderAmount <= "+totalMoney);
    }
}
