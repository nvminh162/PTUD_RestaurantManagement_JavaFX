package com.huongbien.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Test {

    public static void main(String[] args) {
        // Ví dụ dữ liệu
        LocalDate reservationDate = LocalDate.of(2024, 12, 11);
        LocalTime reservationTime = LocalTime.of(10, 30); // 10:30 AM

        // Gọi hàm kiểm tra
        LocalDateTime result = checkReservationTime(reservationDate, reservationTime, 12);

        // In kết quả
        System.out.println("Ngày giờ sau khi cộng thêm 12 tiếng: " + result);
    }

    /**
     * Hàm kiểm tra ngày giờ đặt và cộng thêm số giờ chỉ định.
     *
     * @param reservationDate Ngày đặt.
     * @param reservationTime Giờ đặt.
     * @param hoursToAdd Số giờ cần cộng thêm.
     * @return Ngày giờ sau khi cộng thêm.
     */
    public static LocalDateTime checkReservationTime(LocalDate reservationDate, LocalTime reservationTime, int hoursToAdd) {
        // Kết hợp ngày và giờ đặt thành LocalDateTime
        LocalDateTime reservationDateTime = LocalDateTime.of(reservationDate, reservationTime);

        // Cộng thêm giờ
        LocalDateTime updatedDateTime = reservationDateTime.plusHours(hoursToAdd);

        return updatedDateTime;
    }
}
