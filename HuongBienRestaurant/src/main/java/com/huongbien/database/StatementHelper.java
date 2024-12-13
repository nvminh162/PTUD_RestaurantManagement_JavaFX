package com.huongbien.database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class StatementHelper {
    private final Connection connection;
    private static StatementHelper instance;

    private StatementHelper() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static StatementHelper getInstances() {
        try {
            if (instance == null || instance.connection == null || instance.connection.isClosed()) {
                instance = new StatementHelper();
                System.out.println("StatementHelper created");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }


    public CallableStatement callProcedure(String callProcedure, Object... args) {
        try {
            CallableStatement callStatement = connection.prepareCall(callProcedure);
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case LocalDate localDate -> callStatement.setDate(i + 1, Date.valueOf(localDate));
                    case LocalTime localTime -> callStatement.setTime(i + 1, Time.valueOf(localTime));
                    case null, default -> callStatement.setObject(i + 1, args[i]);
                }
            }

            return callStatement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PreparedStatement prepareStatement(String sql, Object... args) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case LocalDate localDate -> statement.setDate(i + 1, Date.valueOf(localDate));
                    case LocalTime localTime -> statement.setTime(i + 1, Time.valueOf(localTime));
                    case null, default -> statement.setObject(i + 1, args[i]);
                }
            }
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}