package com.huongbien.entity;

import com.huongbien.utils.Utils;

import java.util.Objects;

public class TableType {
    private String tableId;
    private String name;
    private String description;

    public TableType() {
    }

    public TableType(String tableId, String name, String description) {
        this.setTableId(tableId);
        this.setName(name);
        this.setDescription(description);
    }

    public TableType(String name, String description) {
        this.setTableId(null);
        this.setName(name);
        this.setDescription(description);
    }

    public void setTableId(String tableId) {
        if (tableId == null) {
            this.tableId = "LB" + Utils.randomNumber(1, 999);
            return;
        }
        if (tableId.matches("^LB\\d{3}$")) {
            this.tableId = tableId;
            return;
        }
        throw new IllegalArgumentException("Invalid tableId format");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTableId() {
        return tableId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "TableType{" +
                "tableId='" + tableId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableType tableType = (TableType) o;
        return Objects.equals(tableId, tableType.tableId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tableId);
    }
}
