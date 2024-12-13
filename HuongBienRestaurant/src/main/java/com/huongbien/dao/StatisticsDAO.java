package com.huongbien.dao;

import com.huongbien.bus.OrderBUS;
import com.huongbien.bus.ReservationBUS;
import com.huongbien.database.StatementHelper;
import com.huongbien.utils.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticsDAO {

    // Phương thức lấy tổng doanh thu từ bảng Order theo tiêu chí tháng, quý hoặc năm
    public static double getTotalRevenue(String criteria, int period, int year) {
        String query = switch (criteria) {
            case "Tháng" -> "SELECT SUM(totalAmount) AS total_revenue FROM [HuongBien].[dbo].[Order] " +
                    "WHERE MONTH(orderDate) = ? AND YEAR(orderDate) = ?";
            case "Quý" -> "SELECT SUM(totalAmount) AS total_revenue FROM [HuongBien].[dbo].[Order] " +
                    "WHERE DATEPART(QUARTER, orderDate) = ? AND YEAR(orderDate) = ?";
            case "Năm" -> "SELECT SUM(totalAmount) AS total_revenue FROM [HuongBien].[dbo].[Order] " +
                    "WHERE YEAR(orderDate) = ?";
            default -> "";
        };

        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(query);
            if ("Tháng".equals(criteria) || "Quý".equals(criteria)) {
                stmt.setInt(1, period);
                stmt.setInt(2, year);
            } else if ("Năm".equals(criteria)) {
                stmt.setInt(1, year);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_revenue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy tổng số hóa đơn từ bảng Order theo tiêu chí tháng, quý hoặc năm
    public static int getTotalInvoices(String criteria, int period, int year) {
        String query = switch (criteria) {
            case "Tháng" -> "SELECT COUNT(id) AS total_invoices FROM [HuongBien].[dbo].[Order] " +
                    "WHERE MONTH(orderDate) = ? AND YEAR(orderDate) = ?";
            case "Quý" -> "SELECT COUNT(id) AS total_invoices FROM [HuongBien].[dbo].[Order] " +
                    "WHERE DATEPART(QUARTER, orderDate) = ? AND YEAR(orderDate) = ?";
            case "Năm" -> "SELECT COUNT(id) AS total_invoices FROM [HuongBien].[dbo].[Order] " +
                    "WHERE YEAR(orderDate) = ?";
            default -> "";
        };

        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(query);
            if ("Tháng".equals(criteria) || "Quý".equals(criteria)) {
                stmt.setInt(1, period);
                stmt.setInt(2, year);
            } else if ("Năm".equals(criteria)) {
                stmt.setInt(1, year);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_invoices");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Phương thức lấy tổng số lượng món ăn từ OrderDetail
    public static int getCuisineRevenue(String criteria, String cuisineName, int year, int period) {
        String query = switch (criteria) {
            case "Tháng" ->
                    "SELECT SUM(quantity * salePrice) AS totalRevenue FROM [HuongBien].[dbo].[OrderDetail] OD " +
                            "JOIN [HuongBien].[dbo].[Order] O ON OD.orderId = O.id " +
                            "JOIN [HuongBien].[dbo].[Cuisine] C ON OD.cuisineId = C.id " +
                            "WHERE MONTH(O.orderDate) = ? AND YEAR(O.orderDate) = ? AND C.name = ?";
            case "Quý" -> "SELECT SUM(quantity * salePrice) AS totalRevenue FROM [HuongBien].[dbo].[OrderDetail] OD " +
                    "JOIN [HuongBien].[dbo].[Order] O ON OD.orderId = O.id " +
                    "JOIN [HuongBien].[dbo].[Cuisine] C ON OD.cuisineId = C.id " +
                    "WHERE DATEPART(QUARTER, O.orderDate) = ? AND YEAR(O.orderDate) = ? AND C.name = ?";
            case "Năm" -> "SELECT SUM(quantity * salePrice) AS totalRevenue FROM [HuongBien].[dbo].[OrderDetail] OD " +
                    "JOIN [HuongBien].[dbo].[Order] O ON OD.orderId = O.id " +
                    "JOIN [HuongBien].[dbo].[Cuisine] C ON OD.cuisineId = C.id " +
                    "WHERE YEAR(O.orderDate) = ? AND C.name = ?";
            default -> "";
        };

        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(query);
            if ("Tháng".equals(criteria) || "Quý".equals(criteria)) {
                stmt.setInt(1, period);
                stmt.setInt(2, year);
                stmt.setString(3, cuisineName);
            } else if ("Năm".equals(criteria)) {
                stmt.setInt(1, year);
                stmt.setString(2, cuisineName);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("totalRevenue");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static int getNewCustomerQuantity(String criteria, int period, int year) {
        String query = switch (criteria) {
            case "Tháng" -> "SELECT COUNT(id) AS newCustomers FROM [HuongBien].[dbo].[Customer] " +
                    "WHERE MONTH(registrationDate) = ? AND YEAR(registrationDate) = ?";
            case "Quý" -> "SELECT COUNT(id) AS newCustomers FROM [HuongBien].[dbo].[Customer] " +
                    "WHERE DATEPART(QUARTER, registrationDate) = ? AND YEAR(registrationDate) = ?";
            case "Năm" -> "SELECT COUNT(id) AS newCustomers FROM [HuongBien].[dbo].[Customer] " +
                    "WHERE YEAR(registrationDate) = ?";
            default -> "";
        };

        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(query);
            if ("Tháng".equals(criteria) || "Quý".equals(criteria)) {
                stmt.setInt(1, period);
                stmt.setInt(2, year);
            } else if ("Năm".equals(criteria)) {
                stmt.setInt(1, year);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("newCustomers");
            }

            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static double getPersonalRevenue(String id, String criteria, int year, int period) {
        String query = switch (criteria) {
            case "Tháng" -> """
                        SELECT
                            E.id AS EmployeeID,
                            E.name AS EmployeeName,
                            SUM(O.totalAmount) AS TotalRevenue
                        FROM [Order] O
                        INNER JOIN Employee E ON O.employeeId = E.id
                        WHERE MONTH(O.orderDate) = ? AND YEAR(O.orderDate) = ? AND E.id = ?
                        GROUP BY E.id, E.name;
                    """;
            case "Quý" -> """
                        SELECT
                            E.id AS EmployeeID,
                            E.name AS EmployeeName,
                            SUM(O.totalAmount) AS TotalRevenue
                        FROM [Order] O
                        INNER JOIN Employee E ON O.employeeId = E.id
                        WHERE DATEPART(QUARTER, O.orderDate) = ? AND YEAR(O.orderDate) = ? AND E.id = ?
                        GROUP BY E.id, E.name;
                    """;
            case "Năm" -> """
                        SELECT
                            E.id AS EmployeeID,
                            E.name AS EmployeeName,
                            SUM(O.totalAmount) AS TotalRevenue
                        FROM [Order] O
                        INNER JOIN Employee E ON O.employeeId = E.id
                        WHERE YEAR(O.orderDate) = ? AND E.id = ?
                        GROUP BY E.id, E.name;
                    """;
            default -> throw new IllegalStateException("Unexpected value: " + criteria);
        };

        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(query);
            if ("Tháng".equals(criteria) || "Quý".equals(criteria)) {
                stmt.setInt(1, period);
                stmt.setInt(2, year);
                stmt.setString(3, id);
            } else if ("Năm".equals(criteria)) {
                stmt.setInt(1, year);
                stmt.setString(2, id);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("totalRevenue");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static int getPersonalTotalOrder(String id, String criteria, int year, int period) {
        String query = switch (criteria) {
            case "Tháng" -> """
                        SELECT
                            E.id AS EmployeeID,
                            E.name AS EmployeeName,
                            COUNT(o.id) AS TotalOrder
                        FROM [Order] O
                        INNER JOIN Employee E ON O.employeeId = E.id
                        WHERE MONTH(O.orderDate) = ? AND YEAR(O.orderDate) = ? AND E.id = ?
                        GROUP BY E.id, E.name;
                    """;
            case "Quý" -> """
                        SELECT
                            E.id AS EmployeeID,
                            E.name AS EmployeeName,
                            COUNT(o.id) AS TotalOrder
                        FROM [Order] O
                        INNER JOIN Employee E ON O.employeeId = E.id
                        WHERE DATEPART(QUARTER, O.orderDate) = ? AND YEAR(O.orderDate) = ? AND E.id = ?
                        GROUP BY E.id, E.name;
                    """;
            case "Năm" -> """
                        SELECT
                            E.id AS EmployeeID,
                            E.name AS EmployeeName,
                            COUNT(o.id) AS TotalOrder
                        FROM [Order] O
                        INNER JOIN Employee E ON O.employeeId = E.id
                        WHERE YEAR(O.orderDate) = ? AND E.id = ?
                        GROUP BY E.id, E.name;
                    """;
            default -> throw new IllegalStateException("Unexpected value: " + criteria);
        };

        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(query);
            if ("Tháng".equals(criteria) || "Quý".equals(criteria)) {
                stmt.setInt(1, period);
                stmt.setInt(2, year);
                stmt.setString(3, id);
            } else if ("Năm".equals(criteria)) {
                stmt.setInt(1, year);
                stmt.setString(2, id);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("TotalOrder");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static int getPersonalTotalReservation(String id, String criteria, int year, int period) {
        String query = switch (criteria) {
            case "Tháng" -> """
                        SELECT
                            E.id AS EmployeeID,
                            E.name AS EmployeeName,
                            COUNT(R.id) AS TotalReservation
                        FROM [Reservation] R
                        INNER JOIN Employee E ON R.employeeId = E.id
                        WHERE MONTH(R.reservationDate) = ? AND YEAR(R.reservationDate) = ? AND E.id = ?
                        GROUP BY E.id, E.name;
                    """;
            case "Quý" -> """
                        SELECT
                            E.id AS EmployeeID,
                            E.name AS EmployeeName,
                            COUNT(R.id) AS TotalReservation
                        FROM [Reservation] R
                        INNER JOIN Employee E ON R.employeeId = E.id
                        WHERE DATEPART(QUARTER, R.reservationDate) = ? AND YEAR(R.reservationDate) = ? AND E.id = ?
                        GROUP BY E.id, E.name;
                    """;
            case "Năm" -> """
                        SELECT
                            E.id AS EmployeeID,
                            E.name AS EmployeeName,
                            COUNT(R.id) AS TotalReservation
                        FROM [Reservation] R
                        INNER JOIN Employee E ON R.employeeId = E.id
                        WHERE YEAR(R.reservationDate) = ? AND E.id = ?
                        GROUP BY E.id, E.name;
                    """;
            default -> throw new IllegalStateException("Unexpected value: " + criteria);
        };

        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(query);
            if ("Tháng".equals(criteria) || "Quý".equals(criteria)) {
                stmt.setInt(1, period);
                stmt.setInt(2, year);
                stmt.setString(3, id);
            } else if ("Năm".equals(criteria)) {
                stmt.setInt(1, year);
                stmt.setString(2, id);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("TotalReservation");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static int getCuisineTotalSell(String criteria, String cuisineName, int year, int period) {
        String query = switch (criteria) {
            case "Tháng" -> "SELECT SUM(quantity) AS totalSell FROM [HuongBien].[dbo].[OrderDetail] OD " +
                    "JOIN [HuongBien].[dbo].[Order] O ON OD.orderId = O.id " +
                    "JOIN [HuongBien].[dbo].[Cuisine] C ON OD.cuisineId = C.id " +
                    "WHERE MONTH(O.orderDate) = ? AND YEAR(O.orderDate) = ? AND C.name = ?";
            case "Quý" -> "SELECT SUM(quantity) AS totalSell FROM [HuongBien].[dbo].[OrderDetail] OD " +
                    "JOIN [HuongBien].[dbo].[Order] O ON OD.orderId = O.id " +
                    "JOIN [HuongBien].[dbo].[Cuisine] C ON OD.cuisineId = C.id " +
                    "WHERE DATEPART(QUARTER, O.orderDate) = ? AND YEAR(O.orderDate) = ? AND C.name = ?";
            case "Năm" -> "SELECT SUM(quantity) AS totalSell FROM [HuongBien].[dbo].[OrderDetail] OD " +
                    "JOIN [HuongBien].[dbo].[Order] O ON OD.orderId = O.id " +
                    "JOIN [HuongBien].[dbo].[Cuisine] C ON OD.cuisineId = C.id " +
                    "WHERE YEAR(O.orderDate) = ? AND C.name = ?";
            default -> "";
        };

        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(query);
            if ("Tháng".equals(criteria) || "Quý".equals(criteria)) {
                stmt.setInt(1, period);
                stmt.setInt(2, year);
                stmt.setString(3, cuisineName);
            } else if ("Năm".equals(criteria)) {
                stmt.setInt(1, year);
                stmt.setString(2, cuisineName);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("totalSell");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static List<HashMap<String, String>> getBestSellerCuisines(int year, int limit) {
        String sql = """
                    SELECT TOP (?)
                        C.id AS CuisineID,
                        C.name AS CuisineName,
                        CAT.name AS CategoryName,
                        C.price AS SalePrice,
                        SUM(OD.quantity * OD.salePrice) AS TotalRevenue
                    FROM OrderDetail OD
                    INNER JOIN Cuisine C ON OD.cuisineId = C.id
                    INNER JOIN Category CAT ON C.categoryId = CAT.id
                    INNER JOIN [Order] O ON OD.orderId = O.id
                    WHERE YEAR(O.orderDate) = ?
                    GROUP BY C.id, C.name, CAT.name, C.price
                    ORDER BY TotalRevenue DESC;
                """;

        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(sql, limit, year);
            ResultSet rs = stmt.executeQuery();
            List<HashMap<String, String>> bestSellers = new ArrayList<>();
            while (rs.next()) {
                HashMap<String, String> bestSeller = new HashMap<>();
                bestSeller.put("cuisineId", String.valueOf(rs.getString("CuisineID")));
                bestSeller.put("cuisineName", rs.getString("CuisineName"));
                bestSeller.put("categoryName", rs.getString("CategoryName"));
                bestSeller.put("salePrice", Utils.formatMoney(rs.getDouble("SalePrice")));
                bestSeller.put("totalRevenue", Utils.formatMoney(rs.getDouble("TotalRevenue")));
                bestSellers.add(bestSeller);
            }

            return bestSellers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<HashMap<String, String>> getRevenueProportionByCategory(int year) {
        String sql = """
                    DECLARE @Year INT = ?;
                    WITH CategoryRevenue AS (
                        SELECT
                            CAT.id AS CategoryID,
                            CAT.name AS CategoryName,
                            SUM(OD.quantity * OD.salePrice) AS TotalRevenue
                        FROM OrderDetail OD
                        INNER JOIN Cuisine C ON OD.cuisineId = C.id
                        INNER JOIN Category CAT ON C.categoryId = CAT.id
                        INNER JOIN [Order] O ON OD.orderId = O.id
                        WHERE YEAR(O.orderDate) = @Year
                        GROUP BY CAT.id, CAT.name
                    ),
                    TotalRevenueYear AS (
                        SELECT SUM(TotalRevenue) AS YearTotalRevenue
                        FROM CategoryRevenue
                    )
                    SELECT
                        CR.CategoryID,
                        CR.CategoryName,
                        CR.TotalRevenue,
                        ROUND((CR.TotalRevenue * 100.0) / TRY_CAST(TR.YearTotalRevenue AS FLOAT), 1) AS RevenuePercentage
                    FROM CategoryRevenue CR
                    CROSS JOIN TotalRevenueYear TR
                    ORDER BY RevenuePercentage DESC;
                """;

        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(sql, year);
            ResultSet rs = stmt.executeQuery();
            List<HashMap<String, String>> proportions = new ArrayList<>();
            while (rs.next()) {
                HashMap<String, String> proportion = new HashMap<>();
                proportion.put("categoryId", rs.getString("CategoryID"));
                proportion.put("categoryName", rs.getString("CategoryName"));
                proportion.put("categoryRevenue", Utils.formatMoney(rs.getDouble("TotalRevenue")));
                proportion.put("revenuePercentage", String.valueOf(rs.getDouble("RevenuePercentage")));
                proportions.add(proportion);
            }

            return proportions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<String, Integer> getDailyRevenue(LocalDate date) {
        String sql = """
                    DECLARE @inputDate DATE = ?;
                    WITH DailyOrders AS (
                        SELECT
                            orderDate,
                            COUNT(*) AS TotalOrders,
                            SUM(totalAmount) AS TotalRevenue
                        FROM [Order]
                        WHERE orderDate = @inputDate
                        GROUP BY orderDate
                    ),
                    DailyReservations AS (
                        SELECT
                            reservationDate,
                            COUNT(*) AS TotalReservations
                        FROM Reservation
                        WHERE reservationDate = @inputDate
                        GROUP BY reservationDate
                    ),
                    NewCustomers AS (
                        SELECT
                            registrationDate,
                            COUNT(*) AS NewCustomers
                        FROM Customer
                        WHERE registrationDate = @inputDate
                        GROUP BY registrationDate
                    )
                    SELECT
                        ISNULL(DO.TotalRevenue, 0) AS TotalRevenue,
                        ISNULL(DO.TotalOrders, 0) AS TotalOrders,
                        ISNULL(DR.TotalReservations, 0) AS TotalReservations,
                        ISNULL(NC.NewCustomers, 0) AS NewCustomers
                    FROM DailyOrders DO
                    FULL OUTER JOIN DailyReservations DR ON DO.orderDate = DR.reservationDate
                    FULL OUTER JOIN NewCustomers NC ON COALESCE(DO.orderDate, DR.reservationDate) = NC.registrationDate;
                """;

        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(sql, date);
            ResultSet rs = stmt.executeQuery();
            HashMap<String, Integer> revenue = new HashMap<>();
            if (rs.next()) {
                revenue.put("totalRevenue", rs.getInt("TotalRevenue"));
                revenue.put("totalReservations", rs.getInt("TotalReservations"));
                revenue.put("totalOrders", rs.getInt("TotalOrders"));
                revenue.put("newCustomers", rs.getInt("NewCustomers"));
            }

            return revenue;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<String, Integer> getYearlyRevenue(int year) {
        String sql = """
                    SELECT
                        YEAR(orderDate) AS Year,
                        SUM(totalAmount) AS TotalRevenue
                    FROM [Order]
                    WHERE YEAR(orderDate) = ?
                    GROUP BY YEAR(orderDate)
                    ORDER BY Year;
                """;

        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(sql, year);
            ResultSet rs = stmt.executeQuery();
            HashMap<String, Integer> revenue = new HashMap<>();
            if (rs.next()) {
                revenue.put("year", rs.getInt("Year"));
                revenue.put("totalRevenue", rs.getInt("TotalRevenue"));
            }

            return revenue;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<String, Integer> getMonthlyRevenue(int year, int month) {
        String sql = """
                    SELECT
                        MONTH(orderDate) AS Month,
                        SUM(totalAmount) AS TotalRevenue
                    FROM [Order]
                    WHERE YEAR(orderDate) = ?
                    AND MONTH(orderDate) = ?
                    GROUP BY MONTH(orderDate)
                    ORDER BY Month;
                """;

        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(sql, year, month);
            ResultSet rs = stmt.executeQuery();
            HashMap<String, Integer> revenue = new HashMap<>();
            if (rs.next()) {
                revenue.put("month", rs.getInt("Month"));
                revenue.put("totalRevenue", rs.getInt("TotalRevenue"));
            }

            return revenue;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<String, Integer> getQuarterRevenue(int year, int quarter) {
        String sql = """
                    SELECT
                        DATEPART(QUARTER, orderDate) AS Quarter,
                        SUM(totalAmount) AS TotalRevenue
                    FROM [Order]
                    WHERE YEAR(orderDate) = ?
                    AND DATEPART(QUARTER, orderDate) = ?
                    GROUP BY DATEPART(QUARTER, orderDate)
                    ORDER BY Quarter;
                """;

        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(sql, year, quarter);
            ResultSet rs = stmt.executeQuery();
            HashMap<String, Integer> revenue = new HashMap<>();
            if (rs.next()) {
                revenue.put("quarter", rs.getInt("Quarter"));
                revenue.put("totalRevenue", rs.getInt("TotalRevenue"));
            }

            return revenue;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<String, String> getYearlyStat(int year) {
        String sql = """
                    WITH YearlyStats AS (
                       SELECT
                           YEAR(orderDate) AS Year,
                           COUNT(*) AS TotalOrders,
                           SUM(totalAmount) AS TotalRevenue
                       FROM [Order]
                       GROUP BY YEAR(orderDate)
                       ),
                       GrowthStats AS (
                           SELECT
                               Y1.Year,
                               Y1.TotalOrders,
                               Y1.TotalRevenue,
                               CASE\s
                                   WHEN Y2.TotalRevenue IS NULL THEN NULL
                                   ELSE ((Y1.TotalRevenue - Y2.TotalRevenue) / Y2.TotalRevenue) * 100
                               END AS GrowthRate
                           FROM YearlyStats Y1
                           LEFT JOIN YearlyStats Y2
                               ON Y1.Year = Y2.Year + 1
                       )
                       SELECT
                           Year,
                           TotalRevenue,
                           TotalOrders,
                           CASE\s
                               WHEN GrowthRate IS NULL THEN '0.00%'
                               ELSE FORMAT(ROUND(GrowthRate, 2), 'N2') + '%'
                           END AS GrowthRate
                       FROM GrowthStats
                       WHERE Year = ?
                       ORDER BY Year DESC;
                """;

        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(sql, year);
            ResultSet rs = stmt.executeQuery();
            HashMap<String, String> stats = new HashMap<>();
            if (rs.next()) {
                stats.put("year", String.valueOf(rs.getInt("Year")));
                stats.put("totalRevenue", Utils.formatMoney(rs.getDouble("TotalRevenue")));
                stats.put("totalOrders", String.valueOf(rs.getInt("TotalOrders")));
                stats.put("growthRate", rs.getString("GrowthRate"));
            }

            return stats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static double getGrowthRate() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        double currentRevenue = getTotalRevenue("Năm", 0, currentYear);
        double previousRevenue = getTotalRevenue("Năm", 0, currentYear - 1);
        return previousRevenue == 0 ? 0 : (currentRevenue - previousRevenue) / previousRevenue * 100;
    }

    public static double getAverageRevenuePerOrder() {
        double totalRevenue = getTotalRevenue();
        int totalInvoices = getTotalOrders();
        return totalInvoices == 0 ? 0 : totalRevenue / totalInvoices;
    }

    // Các phương thức tính tổng toàn bộ hệ thống
    public static int getTotalCustomers() {
        int totalCustomers = 0;
        String query = "SELECT COUNT(*) AS Total FROM [HuongBien].[dbo].[Customer]";
        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalCustomers = rs.getInt("Total");
            }
            return totalCustomers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static double getTotalRevenue() {
        double totalRevenues = 0.0;
        String query = "SELECT SUM(totalAmount) AS Total FROM [HuongBien].[dbo].[Order]";
        try {
            StatementHelper statementHelper = StatementHelper.getInstances();
            PreparedStatement stmt = statementHelper.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalRevenues = rs.getDouble("Total");
            }
            return totalRevenues;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int getTotalOrders() {
        OrderBUS orderBUS = new OrderBUS();
        return orderBUS.countTotalOrders();
    }

    public static int getTotalReservations() {
        ReservationBUS reservationBUS = new ReservationBUS();
        return reservationBUS.countTotalReservations();
    }
}
