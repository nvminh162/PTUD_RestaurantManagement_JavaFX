package com.huongbien.database.datafaker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.huongbien.database.Database;

import java.sql.SQLException;

public class MainFaker {
    public static void main(String[] args) throws SQLException, JsonProcessingException {
        final boolean INSERTED = true;
        OrderFaker orderFaker = new OrderFaker();
        orderFaker.fakingData(INSERTED);

        ReservationFaker reservationFaker = new ReservationFaker();
        reservationFaker.fakingData(INSERTED);

        Database.closeConnection();
    }
}
