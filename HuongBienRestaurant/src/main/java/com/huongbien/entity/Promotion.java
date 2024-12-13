package com.huongbien.entity;

import com.huongbien.utils.Utils;

import java.time.LocalDate;
import java.util.Objects;

public class Promotion {
    private String promotionId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private double discount;
    private String description;
    private double minimumOrderAmount;
    private int membershipLevel;
    private String status;

    public Promotion() {
    }

    public Promotion(String promotionId, String name, LocalDate startDate,
                     LocalDate endDate, double discount, String description,
                     double minimumOrderAmount, int membershipLevel, String status) {
        setPromotionId(promotionId);
        setName(name);
        setStartDate(startDate);
        setEndDate(endDate);
        setDiscount(discount);
        setDescription(description);
        setMinimumOrderAmount(minimumOrderAmount);
        setMembershipLevel(membershipLevel);
    }

    public Promotion(String name, LocalDate startDate,
                     LocalDate endDate, double discount, String description,
                     double minimumOrderAmount, int membershipLevel, String status) {
        setPromotionId(null);
        setName(name);
        setStartDate(startDate);
        setEndDate(endDate);
        setDiscount(discount);
        setDescription(description);
        setMinimumOrderAmount(minimumOrderAmount);
        setMembershipLevel(membershipLevel);
        setStatus(status);
    }

    public void setPromotionId(String promotionId) {
        if (promotionId == null) {
            LocalDate currentDate = LocalDate.now();
            String dateStr = String.format("%02d%02d%02d", currentDate.getYear() % 100,
                    currentDate.getMonthValue(), currentDate.getDayOfMonth());
            this.promotionId = String.format("KM%s%03d", dateStr, Utils.randomNumber(1, 999));
            return;
        }

        if (promotionId.matches("^KM\\d{9}$")) {
            this.promotionId = promotionId;
            return;
        }
        throw new IllegalArgumentException("Invalid promotionId format");
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be empty");
        }
        this.name = name;
    }

    public void setStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate cannot be empty");
        }
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        if (endDate == null) {
            throw new IllegalArgumentException("endDate cannot be empty");
        }
        this.endDate = endDate;
    }

    public void setDiscount(double discount) {
        if (discount <= 0) {
            throw new IllegalArgumentException("discount cannot be less than 0");
        }
        this.discount = discount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMinimumOrderAmount(double minimumOrderAmount) {
        if (minimumOrderAmount <= 0) {
            throw new IllegalArgumentException("MinimumOrderAmount must greater than 0");
        }
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public void setMembershipLevel(int membershipLevel) {
        if (membershipLevel < 0 || membershipLevel > 4) {
            throw new IllegalArgumentException("Invalid membershipLevel");
        }

        this.membershipLevel = membershipLevel;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getDiscount() {
        return discount;
    }

    public String getDescription() {
        return description;
    }

    public double getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public int getMembershipLevel() {
        return membershipLevel;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "promotionId='" + promotionId + '\'' +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", discount=" + discount +
                ", description='" + description + '\'' +
                ", minimumOrderAmount=" + minimumOrderAmount +
                ", membershipLevel=" + membershipLevel +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promotion promotion = (Promotion) o;
        return Objects.equals(promotionId, promotion.promotionId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(promotionId);
    }
}
