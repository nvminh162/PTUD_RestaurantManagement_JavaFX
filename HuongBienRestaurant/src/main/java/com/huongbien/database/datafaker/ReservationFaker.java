package com.huongbien.database.datafaker;

import com.huongbien.dao.ReservationDAO;
import com.huongbien.entity.*;
import com.huongbien.utils.Utils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ReservationFaker {
    private final Faker faker = new Faker();

    public ReservationFaker() {
    }

    public ArrayList<FoodOrder> fakeFoodOrders(String reservationOrderId) {
        int foodOrderQuantity = Utils.randomNumber(0, 6);
        ArrayList<FoodOrder> foodOrders = new ArrayList<>();

        for (int i = 0; i < foodOrderQuantity; i++) {
            Cuisine cuisine = faker.getRandomCuisine();
            foodOrders.add(new FoodOrder(reservationOrderId, Utils.randomNumber(1, 3), cuisine.getPrice(), cuisine, ""));
        }

        return foodOrders;
    }

    public Reservation fakeReservation(LocalDate reservationDate) {
        LocalDate receiveDate = reservationDate.plusDays(Utils.randomNumber(1, 5));
        LocalTime reservationTime = faker.fakeTime();
        String[] partyTypes = {"Tiệc sinh nhật", "Tiệc gia đình", "Gặp mặt bạn bè", "Tiệc công ty", "Tiệc tất niên", "Ăn bình thường"};
        String[] statuses = {"Đã hủy", "Đã hoàn thành"};

        Reservation reservation = new Reservation();
        reservation.setReservationDate(reservationDate);
        reservation.setReservationTime(reservationTime);
        reservation.setReservationId(null);
        reservation.setReceiveDate(receiveDate);
        reservation.setReceiveTime(faker.fakeTime());
        reservation.setPartySize(Utils.randomNumber(2, 16));
        reservation.setPartyType(partyTypes[Utils.randomNumber(0, partyTypes.length - 1)]);
        reservation.setStatus(statuses[Utils.randomNumber(0, statuses.length - 1)]);
        reservation.setEmployee(faker.getRandomEmployee());
        reservation.setCustomer(faker.getRandomCustomer());
        reservation.setTables((ArrayList<Table>) faker.getRandomTables(Utils.randomNumber(1, 1)));

        ArrayList<FoodOrder> foodOrders = fakeFoodOrders(reservation.getReservationId());
        reservation.setFoodOrders(foodOrders.isEmpty() ? null : foodOrders);

        int vipTables = (int) reservation.getTables().stream()
                .filter(table -> table.getTableTypeName().equals("Bàn VIP"))
                .count();
        reservation.setDeposit(vipTables > 0 ? 100_000 * vipTables : 0);
        reservation.setRefundDeposit(
                reservation.getStatus().equals("Đã hủy")
                ? reservation.getDeposit() * 0.5
                : 0
        );
        reservation.setPayment(
                reservation.getDeposit() > 0
                ? faker.fakePayment(reservation.getDeposit(), reservationDate, reservationTime)
                : null
        );

        return reservation;
    }

    public void fakingData(boolean inserted) throws SQLException {
        Set<Reservation> reservationSet = new HashSet<>();
        for (int year = 2021; year <= 2024; year++) {
            for (int month = 1; month <= 12; month++) {
                int dayOfMonth = faker.getDaysOfAMonth(month, year);
                for (int day = 1; day <= dayOfMonth; day++) {
                    int orderQuantity = Utils.randomNumber(0, 3);
                    for (int i = 0; i < orderQuantity; i++) {
                        Reservation reservation = fakeReservation(LocalDate.of(year, month, day));
                        reservationSet.add(reservation);
                        System.out.println("Reservation added!: " + reservation);
                    }
                }
            }
        }

        System.out.println("Fake success: " + reservationSet.size() + " reservation, start inserted?");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        if (!inserted) {
            return;
        }

        ReservationDAO daoReservation = ReservationDAO.getInstance();
        int successCount = 0;
        int failCount = 0;
        for (Reservation reservation : reservationSet) {
            try {
                if (!daoReservation.add(reservation)) {
                    System.out.println("Add fail object: " + reservation);
                    failCount++;
                    break;
                } else {
                    successCount++;
                    System.out.println("Add success object: " + reservation);
                }
            } catch (Exception e) {
                System.out.println("Add fail object: " + reservation);
                failCount++;
            }
        }

        System.out.println("Total reservation inserted successfully: " + successCount);
        System.out.println("Total reservation inserted fail: " + failCount);
        sc.nextLine();
    }
}
