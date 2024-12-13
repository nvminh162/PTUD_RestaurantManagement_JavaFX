package com.huongbien.entity;

import com.huongbien.utils.Utils;

import java.util.Objects;

public class Table {
    private String id;
    private String name;
    private int seats;
    private int floor;
    private String status;
    private TableType tableType;

    public Table() {
    }

    public Table(String id, String name, int seats, int floor, String status, TableType tableType) {
        setId(id);
        setName(name);
        setFloor(floor);
        setSeats(seats);
        setStatus(status);
        setTableType(tableType);
    }

    public Table(String name, int seats, int floor, String status, TableType tableType) {
        setId(null);
        setName(name);
        setFloor(floor);
        setSeats(seats);
        setStatus(status);
        setTableType(tableType);
    }

    public void setId(String id) {
        if (id == null) {
            this.id = String.format("T%01dB%03d", this.floor, Utils.randomNumber(1, 999));
            return;
        }

        if (id.matches("^T\\dB\\d{3}$")) {
            this.id = id;
            return;
        }

        throw new IllegalArgumentException("Invalid table ID format");
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setFloor(int floor) {
        if (floor < 0) {
            throw new IllegalArgumentException("Floor must be 0 or greater");
        }
        this.floor = floor;
    }

    public void setSeats(int seats) {
        if (seats < 2) {
            throw new IllegalArgumentException("Seats must be 2 or greater");
        }
        this.seats = seats;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFloor() {
        return floor;
    }

    public int getSeats() {
        return seats;
    }

    public String getStatus() {
        return status;
    }

    public TableType getTableType() {
        return tableType;
    }

    public String getTableTypeName() {
        return tableType != null ? tableType.getName() : "";
    }

    @Override
    public String toString() {
        return "Table{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", seats=" + seats +
                ", floor=" + floor +
                ", status='" + status + '\'' +
                ", tableType=" + tableType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return Objects.equals(id, table.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

