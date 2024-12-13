package com.huongbien.database.datafaker;

import com.huongbien.dao.CuisineDAO;
import com.huongbien.dao.CustomerDAO;
import com.huongbien.dao.EmployeeDAO;
import com.huongbien.dao.TableDAO;
import com.huongbien.entity.*;
import com.huongbien.utils.Utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Faker {
    private final List<Customer> customers;
    private final List<Employee> receptionist;
    private final List<Cuisine> cuisines;
    private final List<Table> tables;

    public Faker() {
        this.customers = CustomerDAO.getInstance().getAll();
        this.receptionist = EmployeeDAO.getInstance().getMany("SELECT * FROM Employee WHERE position = N'Tiếp tân';");
        this.cuisines = CuisineDAO.getInstance().getAll();
        this.tables = TableDAO.getInstance().getAll();
    }

    public Customer getRandomCustomer() {
        return customers.get(Utils.randomNumber(0, customers.size() - 1));
    }

    public Employee getRandomEmployee() {
        return receptionist.get(Utils.randomNumber(0, receptionist.size() - 1));
    }

    public Cuisine getRandomCuisine() {
        return cuisines.get(Utils.randomNumber(0, cuisines.size() - 1));
    }

    public List<Table> getRandomTables(int quantity) {
        return getRandom((ArrayList<Table>) tables, quantity);
    }

    public Table getRandomTable() {
        return tables.get(Utils.randomNumber(0, tables.size() - 1));
    }

    public Payment fakePayment(double amount, LocalDate paymentDate, LocalTime paymentTime) {
        String[] paymentsMethods = {"Tiền mặt", "Chuyển khoản"};
        return new Payment(amount, paymentDate, paymentTime, paymentsMethods[Utils.randomNumber(0, 1)]);
    }

    public LocalTime fakeTime() {
        return LocalTime.of(Utils.randomNumber(0, 23), Utils.randomNumber(0, 59));
    }

    public int getDaysOfAMonth(int month, int year) {
        return YearMonth.of(year, month).lengthOfMonth();
    }

    public <T> ArrayList<T> getRandom(ArrayList<T> list, int quantity) {
        Collections.shuffle(list);
        return new ArrayList<>(list.subList(0, Math.min(quantity, list.size())));
    }

    public <T> ArrayList<T> getRandom(ArrayList<T> list, int min, int max) {
        Collections.shuffle(list);
        return new ArrayList<>(list.subList(0, Utils.randomNumber(min, Math.min(max, list.size()))));
    }
}
