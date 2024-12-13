package com.huongbien.database.datafaker;

import com.huongbien.dao.OrderDAO;
import com.huongbien.dao.PromotionDAO;
import com.huongbien.entity.*;
import com.huongbien.utils.Utils;

import java.time.LocalDate;
import java.util.*;

public class OrderFaker {
    public ArrayList<Promotion> promotions;
    private final Faker faker = new Faker();

    public OrderFaker() {
        PromotionDAO daoPromotion = PromotionDAO.getInstance();
        this.promotions = (ArrayList<Promotion>) daoPromotion.getAll();
    }

    public ArrayList<OrderDetail> fakeOrderDetails(String orderId) {
        int orderDetailQuantity = Utils.randomNumber(3, 6);
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();

        for (int i = 0; i < orderDetailQuantity; i++) {
            Cuisine cuisine = faker.getRandomCuisine();
            orderDetails.add(
                    new OrderDetail(orderId, Utils.randomNumber(1, 3), "", cuisine.getPrice(), cuisine)
            );
        }

        return orderDetails;
    }

    public Order fakeOrder(LocalDate orderDate) {
        Order order = new Order();

        order.setNotes("");
        order.setOrderDate(orderDate);
        order.setOrderId(null);
        order.setOrderTime(faker.fakeTime());
        order.setTables((ArrayList<Table>) faker.getRandomTables(Utils.randomNumber(1, 1)));
        order.setCustomer(faker.getRandomCustomer());
        order.setEmployee(faker.getRandomEmployee());
        order.setOrderDetails(fakeOrderDetails(order.getOrderId()));

        order.setPromotion(getPromotion(order.getCustomer().getMembershipLevel()));
        order.setDiscount(order.getPromotion() == null ? 0 : order.getPromotion().getDiscount());
        order.setTotalAmount(order.calculateGrandTotal());
        order.setPaymentAmount(Utils.suggestMoneyReceived((int) order.getTotalAmount())[0]);
        order.setDispensedAmount(order.getPaymentAmount() - order.getTotalAmount());
        order.setPayment(faker.fakePayment(order.getPaymentAmount(), orderDate, faker.fakeTime()));

        return order;
    }

    public Promotion getPromotion(int membershipLevel) {
        for (Promotion promotion : promotions) {
            if (membershipLevel == promotion.getMembershipLevel())
                return promotion;
        }
        return null;
    }

    public void fakingData(boolean inserted) {
        OrderDAO daoOrder = OrderDAO.getInstance();
        PromotionDAO daoPromotion = PromotionDAO.getInstance();

//        System.out.println(fakeOrder(LocalDate.of(2023, 12, 1)));
//        PrettyPrint.objectPrint(fakeOrder(LocalDate.of(2023, 12, 1)));

        ArrayList<Order> orders = new ArrayList<>();
        Set<Order> orderSet = new HashSet<>();

        for (int year = 2021; year <= 2024; year++) {
            for (int month = 1; month <= 12; month++) {
                int dayOfMonth = faker.getDaysOfAMonth(month, year);
                for (int day = 1; day <= dayOfMonth; day++) {
                    int orderQuantity = Utils.randomNumber(0, 3);
                    for (int i = 0; i < orderQuantity; i++) {
                        Order order = fakeOrder(LocalDate.of(year, month, day));
                        orderSet.add(order);
                        System.out.println("Order added!: " + order);
                    }
                }
            }
        }

        System.out.println("Fake success: " + orderSet.size() + " orders, start inserted?");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        if (!inserted) {
            return;
        }
//
        int successCount = 0;
        int failCount = 0;
        for (Order order : orderSet) {
            try {
                if (!daoOrder.add(order)) {
                    System.out.println("Add fail object: " + order);
                    failCount++;
                    break;
                } else {
                    successCount++;
                    System.out.println("Add success object: " + order);
                }
            } catch (Exception e) {
                System.out.println("Add fail object: " + order);
                failCount++;
            }
        }

        System.out.println("Total order inserted successfully: " + successCount);
        System.out.println("Total order inserted fail: " + failCount);
        sc.nextLine();
    }

}
