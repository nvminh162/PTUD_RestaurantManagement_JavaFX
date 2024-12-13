USE HuongBien;

--GO
--BACKUP DATABASE HuongBien
--TO DISK = 'D:\HuongBien\HuongBienChangePwd.bak'
--   WITH FORMAT,
--      MEDIANAME = 'SQLServerBackups',
--      NAME = 'Full Backup of HuongBien';
--GO

INSERT INTO TableType (id, name, description)
VALUES
('LB001', N'Bàn thường', N'Dành cho thực khách phổ thông.'),
('LB002', N'Bàn VIP', N'Dành cho thực khách muốn không gian riêng tư.');

-- Insert tables for the ground floor (Tầng Trệt)
INSERT INTO [Table] (id, name, seats, [floor], tableTypeId, [status])
VALUES
('T0B001', N'Bàn 01', 4, 0, 'LB001', N'Bàn đóng'),
('T0B002', N'Bàn 02', 4, 0, 'LB001', N'Đặt trước'),
('T0B003', N'Bàn 03', 4, 0, 'LB001', N'Đặt trước'),
('T0B004', N'Bàn 04', 4, 0, 'LB001', N'Đặt trước'),
('T0B005', N'Bàn 05', 4, 0, 'LB001', N'Đặt trước'),
('T0B006', N'Bàn 06', 4, 0, 'LB001', N'Bàn trống'),
('T0B007', N'Bàn 07', 4, 0, 'LB001', N'Bàn trống'),
('T0B008', N'Bàn 08', 4, 0, 'LB001', N'Bàn trống'),
('T0B009', N'Bàn 09', 4, 0, 'LB001', N'Phục vụ'),
('T0B010', N'Bàn 10', 4, 0, 'LB001', N'Phục vụ'),
('T0B011', N'Bàn 11', 6, 0, 'LB002', N'Phục vụ'),
('T0B012', N'Bàn 12', 6, 0, 'LB002', N'Đặt trước'),
('T0B013', N'Bàn 13', 6, 0, 'LB002', N'Đặt trước'),
('T0B014', N'Bàn 14', 6, 0, 'LB002', N'Bàn trống'),
('T0B015', N'Bàn 15', 6, 0, 'LB002', N'Bàn trống');

-- Insert tables for floor "Tầng 1"
INSERT INTO [Table] (id, name, seats, [floor], tableTypeId, [status])
VALUES
('T1B001', N'Bàn 01', 4, 1, 'LB001', N'Bàn trống'),
('T1B002', N'Bàn 02', 4, 1, 'LB001', N'Bàn trống'),
('T1B003', N'Bàn 03', 4, 1, 'LB001', N'Bàn trống'),
('T1B004', N'Bàn 04', 4, 1, 'LB001', N'Phục vụ'),
('T1B005', N'Bàn 05', 4, 1, 'LB001', N'Phục vụ'),
('T1B006', N'Bàn 06', 4, 1, 'LB001', N'Phục vụ'),
('T1B007', N'Bàn 07', 4, 1, 'LB001', N'Phục vụ'),
('T1B008', N'Bàn 08', 4, 1, 'LB001', N'Đặt trước'),
('T1B009', N'Bàn 09', 4, 1, 'LB001', N'Đặt trước'),
('T1B010', N'Bàn 10', 4, 1, 'LB001', N'Đặt trước'),
('T1B011', N'Bàn 11', 6, 1, 'LB002', N'Bàn trống'),
('T1B012', N'Bàn 12', 6, 1, 'LB002', N'Bàn đóng'),
('T1B013', N'Bàn 13', 6, 1, 'LB002', N'Bàn trống');

-- Insert tables for floor "Tầng 2"
INSERT INTO [Table] (id, name, seats, [floor], tableTypeId, [status])
VALUES
('T2B001', N'Bàn 01', 4, 2, 'LB001', N'Bàn đóng'),
('T2B002', N'Bàn 02', 4, 2, 'LB001', N'Bàn trống'),
('T2B003', N'Bàn 03', 4, 2, 'LB001', N'Phục vụ'),
('T2B004', N'Bàn 04', 4, 2, 'LB001', N'Phục vụ'),
('T2B005', N'Bàn 05', 4, 2, 'LB001', N'Phục vụ'),
('T2B006', N'Bàn 06', 4, 2, 'LB001', N'Bàn trống'),
('T2B007', N'Bàn 07', 4, 2, 'LB001', N'Bàn trống'),
('T2B008', N'Bàn 08', 4, 2, 'LB001', N'Đặt trước'),
('T2B009', N'Bàn 09', 4, 2, 'LB001', N'Đặt trước'),
('T2B010', N'Bàn 10', 4, 2, 'LB001', N'Đặt trước'),
('T2B011', N'Bàn 11', 6, 2, 'LB002', N'Bàn trống');

-- Insert 5 rows into the Promotion table
INSERT INTO Promotion (id, name, startDate, endDate, discount, [description], minimumOrderAmount, membershipLevel, [status])
VALUES
('KM231023001', N'Ưu đãi cho thành viên bạc', '2023-10-23', '2025-10-23', 0.10, N'Giảm 10% cho hóa đơn trên 500.000đ cho thành viên bạc', 500000.0, 1, N'Còn hiệu lực'),
('KM231023002', N'Ưu đãi cho thành viên vàng', '2023-10-23', '2025-10-23', 0.20, N'Giảm 20% cho hóa đơn trên 500.000đ cho thành viên vàng', 500000.0, 2, N'Còn hiệu lực'),
('KM231023003', N'Ưu đãi cho thành viên kim cương', '2023-10-23', '2025-10-23', 0.30, N'Giảm 30% cho hóa đơn trên 500.000đ cho thành viên kim cương', 500000.0, 3, N'Còn hiệu lực');


INSERT INTO Category (id, name, description)
VALUES
('CG001', N'Khai vị', N'Món ăn nhẹ nhàng, kích thích vị giác.'),
('CG002', N'Súp', N'Món súp nóng hổi, dễ ăn, giàu dinh dưỡng.'),
('CG003', N'Salad', N'Rau xanh tươi mát, ăn kèm với sốt.'),
('CG004', N'Món nướng', N'Các loại thịt nướng thơm ngon.'),
('CG005', N'Món hấp', N'Món ăn lành mạnh, giữ nguyên hương vị.'),
('CG006', N'Món chiên', N'Giòn rụm, đậm đà gia vị.'),
('CG007', N'Hải sản', N'Các món chế biến từ tôm, cua, cá.'),
('CG008', N'Cơm', N'Món cơm trắng kết hợp với nhiều nguyên liệu.'),
('CG009', N'Lẩu', N'Món lẩu đa dạng, phù hợp ăn chung nhóm.'),
('CG010', N'Nước uống', N'Nước uống giải khát.');

INSERT INTO Cuisine (id, name, price, description, image, status, categoryId)
VALUES
-- Khai vị
('M001', N'Gỏi tôm', 120000, N'Tôm tươi trộn cùng rau và sốt chua ngọt.', NULL, 'Còn bán', 'CG001'),
('M002', N'Chả mực', 150000, N'Mực giã nhuyễn, chiên giòn, thơm ngon.', NULL, 'Còn bán', 'CG001'),
('M003', N'Gỏi cá hồi', 170000, N'Cá hồi tươi trộn rau củ và nước sốt.', NULL, 'Còn bán', 'CG001'),
('M004', N'Nghêu hấp', 110000, N'Nghêu hấp với sả và ớt, thơm lừng.', NULL, 'Còn bán', 'CG001'),
('M005', N'Tôm chiên', 140000, N'Tôm chiên giòn rụm, ăn kèm nước chấm.', NULL, 'Còn bán', 'CG001'),

-- Súp
('M006', N'Súp cua', 90000, N'Súp cua bổ dưỡng với thịt cua tươi ngon.', NULL, 'Còn bán', 'CG002'),
('M007', N'Súp tôm', 95000, N'Súp tôm ngọt, đậm đà với rau củ.', NULL, 'Còn bán', 'CG002'),
('M008', N'Súp hải sản', 120000, N'Súp hải sản tổng hợp, thơm ngọt.', NULL, 'Còn bán', 'CG002'),
('M009', N'Súp cá hồi', 130000, N'Súp từ cá hồi, giàu dinh dưỡng.', NULL, 'Còn bán', 'CG002'),
('M010', N'Súp nghêu', 85000, N'Nghêu nấu súp cùng rau củ, đậm vị.', NULL, 'Còn bán', 'CG002'),

-- Salad
('M011', N'Salad tôm', 100000, N'Tôm tươi kết hợp rau xanh và sốt.', NULL, 'Còn bán', 'CG003'),
('M012', N'Salad cá ngừ', 120000, N'Cá ngừ cùng rau xanh tươi mát.', NULL, 'Còn bán', 'CG003'),
('M013', N'Salad mực', 110000, N'Mực tươi và rau sống, sốt chua ngọt.', NULL, 'Còn bán', 'CG003'),
('M014', N'Salad hải sản', 130000, N'Hải sản kết hợp cùng salad rau.', NULL, 'Còn bán', 'CG003'),
('M015', N'Sasimi bạch tuộc', 140000, N'Sasimi bạch tuộc tươi ngon.', NULL, 'Còn bán', 'CG003'),

-- Món nướng
('M016', N'Tôm nướng', 180000, N'Tôm nướng muối ớt thơm lừng.', NULL, 'Còn bán', 'CG004'),
('M017', N'Cá nướng giấy bạc', 200000, N'Cá nướng giấy bạc giữ nguyên hương vị.', NULL, 'Còn bán', 'CG004'),
('M018', N'Mực nướng sa tế', 170000, N'Mực tươi nướng sa tế cay nồng.', NULL, 'Còn bán', 'CG004'),
('M019', N'Hàu nướng phô mai', 190000, N'Hàu nướng phô mai béo ngậy.', NULL, 'Còn bán', 'CG004'),
('M020', N'Sò điệp nướng', 150000, N'Sò điệp nướng thơm ngon.', NULL, 'Còn bán', 'CG004'),

-- Món hấp
('M021', N'Cua hấp bia', 220000, N'Cua biển hấp bia, giữ nguyên vị ngọt.', NULL, 'Còn bán', 'CG005'),
('M022', N'Tôm hấp nước dừa', 180000, N'Tôm hấp với nước dừa tươi.', NULL, 'Còn bán', 'CG005'),
('M023', N'Nghêu hấp sả', 120000, N'Nghêu hấp sả, cay thơm.', NULL, 'Còn bán', 'CG005'),
('M024', N'Mực hấp gừng', 160000, N'Mực hấp với gừng tươi, thơm ngon.', NULL, 'Còn bán', 'CG005'),
('M025', N'Cá hồi hấp xì dầu', 200000, N'Cá hồi hấp xì dầu.', NULL, 'Còn bán', 'CG005'),

-- Món chiên
('M026', N'Tôm chiên xù', 150000, N'Tôm chiên giòn, ăn kèm nước sốt.', NULL, 'Còn bán', 'CG006'),
('M027', N'Mực chiên giòn', 140000, N'Mực chiên giòn, vàng ươm.', NULL, 'Còn bán', 'CG006'),
('M028', N'Cá chiên sốt me', 160000, N'Cá chiên giòn sốt me chua ngọt.', NULL, 'Còn bán', 'CG006'),
('M029', N'Tôm chiên tempura', 170000, N'Tôm chiên tNV0ura kiểu Nhật.', NULL, 'Còn bán', 'CG006'),
('M030', N'Cua chiên bơ tỏi', 210000, N'Cua chiên với bơ tỏi.', NULL, 'Còn bán', 'CG006'),

-- Hải sản
('M031', N'Gỏi cá trích', 130000, N'Cá trích tươi trộn rau và nước mắm.', NULL, 'Còn bán', 'CG007'),
('M032', N'Tôm hùm hấp', 350000, N'Tôm hùm hấp giữ nguyên hương vị.', NULL, 'Còn bán', 'CG007'),
('M033', N'Cua rang me', 250000, N'Cua rang me chua ngọt.', NULL, 'Còn bán', 'CG007'),
('M034', N'Hàu nướng phô mai', 180000, N'Hàu nướng phô mai béo ngậy.', NULL, 'Còn bán', 'CG007'),
('M035', N'Sò huyết sốt me', 140000, N'Sò huyết sốt me đậm vị.', NULL, 'Còn bán', 'CG007'),

-- Cơm
('M036', N'Cơm chiên hải sản', 120000, N'Cơm chiên với tôm, mực, sò.', NULL, 'Còn bán', 'CG008'),
('M037', N'Cơm chiên Dương Châu', 100000, N'Cơm chiên kiểu Dương Châu.', NULL, 'Còn bán', 'CG008'),
('M038', N'Cơm hải sản xốt XO', 140000, N'Cơm xào hải sản với xốt XO.', NULL, 'Còn bán', 'CG008'),
('M039', N'Cơm chiên cua', 130000, N'Cơm chiên cùng cua tươi ngon.', NULL, 'Còn bán', 'CG008'),
('M040', N'Cơm chiên hải sản trứng muối', 150000, N'Cơm chiên hải sản với trứng muối.', NULL, 'Còn bán', 'CG008'),

-- Lẩu
('M041', N'Lẩu hải sản', 300000, N'Lẩu tổng hợp các loại hải sản.', NULL, 'Còn bán', 'CG009'),
('M042', N'Lẩu cua đồng', 280000, N'Lẩu cua đồng tươi ngon.', NULL, 'Còn bán', 'CG009'),
('M043', N'Lẩu tôm chua cay', 270000, N'Tôm nấu lẩu chua cay.', NULL, 'Còn bán', 'CG009'),
('M044', N'Lẩu cá hồi', 320000, N'Lẩu cá hồi giàu dinh dưỡng.', NULL, 'Còn bán', 'CG009'),
('M045', N'Lẩu nghêu', 250000, N'Nghêu nấu lẩu thanh ngọt.', NULL, 'Còn bán', 'CG009'),

-- Nước uống
('M046', N'Coca cola', 15000, N'Nước uống có ga Coca cola.', NULL, 'Còn bán', 'CG010'),
('M047', N'Pepsi', 15000, N'Nước uống có ga Pepsi.', NULL, 'Còn bán', 'CG010'),
('M048', N'Bia Tiger', 37000, N'Đồ uống có cồn bia Tiger.', NULL, 'Còn bán', 'CG010'),
('M049', N'Bia Sài Gòn', 32000, N'Đồ uống có cồn bia Sài Gòn.', NULL, 'Còn bán', 'CG010'),
('M050', N'Bia Heineken', 35000, N'Đồ uống có cồn bia Heineken.', NULL, 'Còn bán', 'CG010');

--GO
--BEGIN TRANSACTION
--	DECLARE @i INT = 1;
--	DECLARE @filePath NVARCHAR(255);
--	DECLARE @id NVARCHAR(5);
--	DECLARE @sql NVARCHAR(MAX);

--	WHILE @i <= 50
--	BEGIN
--		--	Tạo mã món ăn (M001, M002, ..., M050)
--		SET @id = 'M' + RIGHT('000' + CAST(@i AS NVARCHAR(3)), 3);

--		--	Tạo đường dẫn ảnh dựa trên mã món ăn
--		SET @filePath = 'D:\HuongBien\Cuisine\' + @id + '.jpg';

--		--	Tạo câu lệnh động để chèn ảnh từ đường dẫn
--		SET @sql = N'
--		BEGIN TRY
--			BEGIN TRANSACTION
--				UPDATE Cuisine
--				SET image = (SELECT BulkColumn FROM OPENROWSET(BULK ''' + @filePath + ''', SINGLE_BLOB) AS ImageData)
--				WHERE id = ''' + @id + ''';
--			COMMIT;
--		END TRY
--		BEGIN CATCH
--		--		Nếu có lỗi, in ra đường dẫn bị lỗi
--			PRINT ''Error loading file: ' + @filePath + ''';
--			ROLLBACK;
--		END CATCH;';

--		--	Thực thi câu lệnh SQL động
--		EXEC sp_executesql @sql;

--		--	Tăng biến đếm
--		SET @i = @i + 1;
--	END;
--COMMIT;
--GO


-- Insert 5 rows into the Customer
-- Customer
INSERT INTO Customer (id, name, [address], gender, phoneNumber, email, birthday, registrationDate, accumulatedPoints, membershipLevel)
VALUES
-- 2023
('KH230101001', N'Nguyễn Văn Cường', N'101 Lý Thái Tổ, Quận 10, TP. Hồ Chí Minh, Việt Nam', 1, '0931234560', N'cuongnguyen@example.com', '1987-01-05', '2023-01-01', 30, 1),
('KH230102002', N'Phạm Thu Hà', N'45 Phạm Ngọc Thạch, Quận 3, TP. Hồ Chí Minh, Việt Nam', 2, '0932345671', N'thuha@example.com', '1990-03-12', '2023-01-02', 45, 1),
('KH230103003', N'Lê Văn Tuấn', N'21 Lý Chính Thắng, Quận 3, TP. Hồ Chí Minh, Việt Nam', 1, '0933456782', N'tuanle@example.com', '1992-08-18', '2023-01-03', 55, 1),
('KH230104004', N'Trần Thị Tuyết', N'12 Phan Đình Phùng, Quận Phú Nhuận, TP. Hồ Chí Minh, Việt Nam', 2, '0934567893', N'tuyettran@example.com', '1988-12-25', '2023-01-04', 80, 1),
('KH230105005', N'Bùi Văn Phong', N'98 Bạch Đằng, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 1, '0935678904', N'phongbui@example.com', '1995-04-20', '2023-01-05', 110, 1),
('KH230106006', N'Nguyễn Hồng Phúc', N'77 Tôn Thất Tùng, Quận 1, TP. Hồ Chí Minh, Việt Nam', 1, '0936789015', N'hongphuc@example.com', '1989-10-07', '2023-01-06', 125, 1),
('KH230107007', N'Lý Thị Lan Anh', N'33 Võ Thị Sáu, Quận 3, TP. Hồ Chí Minh, Việt Nam', 2, '0937890126', N'lananly@example.com', '1993-11-29', '2023-01-07', 65, 1),
('KH230108008', N'Trần Quốc Anh', N'42 Điện Biên Phủ, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 1, '0938901237', N'quoctran@example.com', '1994-05-15', '2023-01-08', 90, 1),
('KH230109009', N'Phạm Thị Mai', N'35 Trần Hưng Đạo, Quận 5, TP. Hồ Chí Minh, Việt Nam', 2, '0939012348', N'maipham@example.com', '1986-09-10', '2023-01-09', 75, 1),
('KH230110010', N'Trịnh Văn Hùng', N'22 Nguyễn Trãi, Quận 1, TP. Hồ Chí Minh, Việt Nam', 1, '0931123459', N'vanhungtrinh@example.com', '1991-03-25', '2023-01-10', 110, 1),
('KH230111011', N'Nguyễn Thị Xuân', N'101 Đường 3 Tháng 2, Quận 10, TP. Hồ Chí Minh, Việt Nam', 2, '0932234560', N'xuannguyen@example.com', '1997-07-18', '2023-01-11', 55, 1),
('KH230112012', N'Trần Quốc Khánh', N'12 Lê Quang Định, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 1, '0933345671', N'quockhanh@example.com', '1990-12-12', '2023-01-12', 130, 2),
('KH230113013', N'Phan Thị Thu', N'22 Ngô Quyền, Quận 5, TP. Hồ Chí Minh, Việt Nam', 2, '0934456782', N'thuphan@example.com', '1985-09-30', '2023-01-13', 140, 2),
('KH230114014', N'Hoàng Văn Tân', N'65 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh, Việt Nam', 1, '0935567893', N'vantanhoang@example.com', '1988-06-21', '2023-01-14', 75, 1),
('KH230115015', N'Trần Quốc Việt', N'9 Tô Hiến Thành, Quận 10, TP. Hồ Chí Minh, Việt Nam', 1, '0936678904', N'vietquoc@example.com', '1991-11-15', '2023-01-15', 90, 1),
('KH230116016', N'Lê Thị Hải', N'47 Trường Sa, Quận 3, TP. Hồ Chí Minh, Việt Nam', 2, '0937789015', N'hailt@example.com', '1993-05-20', '2023-01-16', 105, 1),
('KH230117017', N'Nguyễn Văn Bình', N'55 Đinh Tiên Hoàng, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 1, '0938890126', N'vanbinh@example.com', '1987-03-18', '2023-01-17', 60, 1),
('KH230118018', N'Trương Thị Hạnh', N'78 Tân Sơn Nhì, Quận Tân Phú, TP. Hồ Chí Minh, Việt Nam', 2, '0939901237', N'thihanh@example.com', '1995-10-22', '2023-01-18', 115, 2),
('KH230119019', N'Phạm Quốc Toàn', N'123 Lê Văn Sỹ, Quận Phú Nhuận, TP. Hồ Chí Minh, Việt Nam', 1, '0941012348', N'quoctoan@example.com', '1992-04-04', '2023-01-19', 85, 1),
('KH230120020', N'Lê Thị Lệ', N'35 Nguyễn Trãi, Quận 5, TP. Hồ Chí Minh, Việt Nam', 2, '0942123459', N'lelt@example.com', '1984-02-11', '2023-01-20', 125, 2),
('KH230121021', N'Nguyễn Văn Nam', N'90 Nguyễn Văn Linh, Quận 7, TP. Hồ Chí Minh, Việt Nam', 1, '0943234560', N'vannam@example.com', '1990-09-08', '2023-02-01', 130, 2),
('KH230122022', N'Phan Thị Huệ', N'10 Lê Lợi, Quận 1, TP. Hồ Chí Minh, Việt Nam', 2, '0944345671', N'huephan@example.com', '1986-06-18', '2023-02-02', 95, 1),
('KH230123023', N'Bùi Thị Yến', N'102 Cao Thắng, Quận 10, TP. Hồ Chí Minh, Việt Nam', 2, '0945456782', N'yenbui@example.com', '1993-08-22', '2023-02-03', 75, 1),
('KH230124024', N'Trần Văn Tài', N'50 Điện Biên Phủ, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 1, '0946567893', N'vantai@example.com', '1989-05-11', '2023-02-04', 85, 1),
('KH230125025', N'Nguyễn Thị Hạnh', N'12 Nguyễn Hữu Cảnh, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 2, '0947678904', N'hanhnt@example.com', '1992-12-03', '2023-02-05', 95, 1),
('KH230126026', N'Hoàng Văn Phát', N'77 Hoàng Sa, Quận 3, TP. Hồ Chí Minh, Việt Nam', 1, '0948789015', N'vanphath@example.com', '1988-01-01', '2023-02-06', 65, 1),
('KH230127027', N'Lê Thị Bích', N'8 Trần Não, Quận 2, TP. Hồ Chí Minh, Việt Nam', 2, '0949890126', N'lethibich@example.com', '1994-10-25', '2023-02-07', 75, 1),
('KH230128028', N'Phạm Văn Hải', N'10 Đỗ Xuân Hợp, Quận 9, TP. Hồ Chí Minh, Việt Nam', 1, '0951012348', N'hai_pham@example.com', '1987-07-12', '2023-02-08', 145, 2),
('KH230129029', N'Trần Thị Minh', N'99 Lê Đại Hành, Quận 11, TP. Hồ Chí Minh, Việt Nam', 2, '0952123459', N'trminh@example.com', '1985-05-20', '2023-02-09', 70, 1),
('KH230130030', N'Nguyễn Minh Tâm', N'33 Xô Viết Nghệ Tĩnh, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 1, '0953234560', N'minhtam@example.com', '1996-09-19', '2023-02-10', 115, 2),
('KH230301031', N'Nguyễn Thị Hồng', N'40 Nguyễn Thái Học, Quận 1, TP. Hồ Chí Minh, Việt Nam', 2, '0954345671', N'hongnguyen@example.com', '1986-03-15', '2023-03-01', 85, 1),
('KH230302032', N'Phạm Văn Thanh', N'25 Cách Mạng Tháng Tám, Quận 3, TP. Hồ Chí Minh, Việt Nam', 1, '0955456782', N'thanhpham@example.com', '1992-08-10', '2023-03-02', 105, 1),
('KH230303033', N'Trần Văn Hòa', N'101 Nguyễn Đình Chiểu, Quận 3, TP. Hồ Chí Minh, Việt Nam', 1, '0956567893', N'hoatran@example.com', '1989-11-11', '2023-03-03', 120, 1),
('KH230304034', N'Lê Thị Mai', N'22 Đinh Tiên Hoàng, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 2, '0957678904', N'maile@example.com', '1990-01-18', '2023-03-04', 90, 1),
('KH230305035', N'Nguyễn Văn Kiên', N'45 Võ Văn Kiệt, Quận 5, TP. Hồ Chí Minh, Việt Nam', 1, '0958789015', N'kiennguyen@example.com', '1993-05-22', '2023-03-05', 95, 1),
('KH230401036', N'Lê Văn Thành', N'60 Nguyễn Trãi, Quận 1, TP. Hồ Chí Minh, Việt Nam', 1, '0961234567', N'lethanh@example.com', '1990-02-15', '2023-04-01', 70, 1),
('KH230402037', N'Phạm Thị Thúy', N'45 Phan Xích Long, Quận Phú Nhuận, TP. Hồ Chí Minh, Việt Nam', 2, '0962345678', N'thuypham@example.com', '1987-10-05', '2023-04-02', 110, 1),
('KH230403038', N'Nguyễn Văn Dũng', N'123 Trần Hưng Đạo, Quận 5, TP. Hồ Chí Minh, Việt Nam', 1, '0963456789', N'dungnguyen@example.com', '1985-07-07', '2023-04-03', 135, 2),
('KH230404039', N'Trần Thị Ngọc', N'89 Bùi Thị Xuân, Quận 1, TP. Hồ Chí Minh, Việt Nam', 2, '0964567890', N'ngocnguyen@example.com', '1992-08-08', '2023-04-04', 100, 1),
('KH230405040', N'Phan Văn Bảo', N'12 Xô Viết Nghệ Tĩnh, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 1, '0965678901', N'baophan@example.com', '1991-09-09', '2023-04-05', 125, 1),
('KH230501041', N'Trương Minh Nhật', N'56 Lê Văn Sỹ, Quận Tân Bình, TP. Hồ Chí Minh, Việt Nam', 1, '0966789012', N'minhnhat@example.com', '1990-05-15', '2023-05-01', 90, 1),
('KH230502042', N'Võ Thị Nhung', N'34 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh, Việt Nam', 2, '0967890123', N'nhungvo@example.com', '1988-03-25', '2023-05-02', 85, 1),
('KH230503043', N'Lê Quang Minh', N'12 Cao Thắng, Quận 10, TP. Hồ Chí Minh, Việt Nam', 1, '0968901234', N'lequang@example.com', '1985-10-12', '2023-05-03', 70, 1),
('KH230504044', N'Phạm Thị Thanh', N'45 Lê Lợi, Quận 1, TP. Hồ Chí Minh, Việt Nam', 2, '0971234567', N'thanhpham@example.com', '1993-12-19', '2023-05-04', 65, 1),
('KH230505045', N'Nguyễn Văn Đức', N'77 Trường Chinh, Quận 12, TP. Hồ Chí Minh, Việt Nam', 1, '0972345678', N'vnduc@example.com', '1989-04-30', '2023-05-05', 130, 2),
('KH230601046', N'Trần Thị Phương', N'10 Điện Biên Phủ, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 2, '0973456789', N'ttphuong@example.com', '1987-06-20', '2023-06-01', 75, 1),
('KH230602047', N'Lê Văn Quang', N'35 Tôn Thất Tùng, Quận 1, TP. Hồ Chí Minh, Việt Nam', 1, '0974567890', N'lvquang@example.com', '1990-01-10', '2023-06-02', 65, 1),
('KH230603048', N'Nguyễn Thị Tâm', N'100 Cách Mạng Tháng 8, Quận 10, TP. Hồ Chí Minh, Việt Nam', 2, '0975678901', N'nttam@example.com', '1991-08-21', '2023-06-03', 105, 1),
('KH230604049', N'Phạm Văn Thịnh', N'45 Trần Hưng Đạo, Quận 5, TP. Hồ Chí Minh, Việt Nam', 1, '0976789012', N'pvthinh@example.com', '1989-09-09', '2023-06-04', 120, 1),
('KH230605050', N'Lê Văn Thanh', N'77 Trường Sa, Quận 3, TP. Hồ Chí Minh, Việt Nam', 1, '0977890123', N'lvthanh@example.com', '1985-11-13', '2023-06-05', 90, 1),
('KH230701051', N'Trần Thị Ngân', N'22 Nguyễn Đình Chiểu, Quận 3, TP. Hồ Chí Minh, Việt Nam', 2, '0978901234', N'thingan@example.com', '1988-03-04', '2023-07-01', 95, 1),
('KH230702052', N'Phan Văn Hiệp', N'66 Lý Tự Trọng, Quận 1, TP. Hồ Chí Minh, Việt Nam', 1, '0981012345', N'phanvh@example.com', '1992-02-14', '2023-07-02', 80, 1),
('KH230703053', N'Trần Thị Thu', N'89 Nguyễn Văn Trỗi, Quận Phú Nhuận, TP. Hồ Chí Minh, Việt Nam', 2, '0982123456', N'ttran@example.com', '1990-04-23', '2023-07-03', 125, 1),
('KH230704054', N'Nguyễn Minh Châu', N'12 Trần Não, Quận 2, TP. Hồ Chí Minh, Việt Nam', 2, '0983234567', N'nguyenchau@example.com', '1996-11-09', '2023-07-04', 150, 2),
('KH230705055', N'Lê Văn Khánh', N'45 Cao Thắng, Quận 10, TP. Hồ Chí Minh, Việt Nam', 1, '0984345678', N'levank@example.com', '1987-07-19', '2023-07-05', 135, 2),
('KH230801056', N'Nguyễn Thanh Bình', N'23 Võ Văn Ngân, Quận Thủ Đức, TP. Hồ Chí Minh, Việt Nam', 1, '0985456789', N'thanhbinh@example.com', '1987-08-15', '2023-08-01', 75, 1),
('KH230802057', N'Phạm Thị Lan', N'78 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh, Việt Nam', 2, '0986567890', N'thilan@example.com', '1993-03-14', '2023-08-02', 65, 1),
('KH230803058', N'Lê Văn Quý', N'102 Nam Kỳ Khởi Nghĩa, Quận 1, TP. Hồ Chí Minh, Việt Nam', 1, '0987678901', N'vanquy@example.com', '1989-12-25', '2023-08-03', 115, 2),
('KH230804059', N'Trần Thị Cúc', N'55 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh, Việt Nam', 2, '0988789012', N'trancuc@example.com', '1990-05-10', '2023-08-04', 130, 2),
('KH230805060', N'Nguyễn Văn Lâm', N'88 Trường Sa, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 1, '0989890123', N'ngvanlam@example.com', '1988-07-08', '2023-08-05', 120, 1),
('KH230901061', N'Lê Thị Phương', N'45 Tân Kỳ Tân Quý, Quận Tân Phú, TP. Hồ Chí Minh, Việt Nam', 2, '0991012345', N'phuongle@example.com', '1992-11-21', '2023-09-01', 85, 1),
('KH230902062', N'Phạm Minh Tùng', N'77 Hòa Bình, Quận Tân Phú, TP. Hồ Chí Minh, Việt Nam', 1, '0992123456', N'minhtung@example.com', '1989-02-19', '2023-09-02', 140, 2),
('KH230903063', N'Nguyễn Thanh Huy', N'12 Phan Đăng Lưu, Quận Phú Nhuận, TP. Hồ Chí Minh, Việt Nam', 1, '0993234567', N'thanhhuy@example.com', '1990-09-14', '2023-09-03', 150, 2),
('KH230904064', N'Trần Thị Hương', N'23 Tân Sơn Nhì, Quận Tân Phú, TP. Hồ Chí Minh, Việt Nam', 2, '0994345678', N'thuongt@example.com', '1985-04-27', '2023-09-04', 75, 1),
('KH230905065', N'Lê Văn Hoàng', N'98 Phạm Văn Đồng, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 1, '0995456789', N'lvhoang@example.com', '1994-10-03', '2023-09-05', 110, 1),
('KH231001066', N'Nguyễn Thị Hằng', N'56 Nguyễn Văn Linh, Quận 7, TP. Hồ Chí Minh, Việt Nam', 2, '0996567890', N'thang@example.com', '1991-12-08', '2023-10-01', 95, 1),
('KH231002067', N'Trần Văn Minh', N'40 Kha Vạn Cân, Quận Thủ Đức, TP. Hồ Chí Minh, Việt Nam', 1, '0997678901', N'tvminh@example.com', '1988-08-18', '2023-10-02', 125, 2),
('KH231003068', N'Phan Thị Tuyết', N'35 Nguyễn Chí Thanh, Quận 5, TP. Hồ Chí Minh, Việt Nam', 2, '0998789012', N'ttuyet@example.com', '1986-06-29', '2023-10-03', 105, 1),
('KH231004069', N'Nguyễn Thành Đạt', N'77 Lạc Long Quân, Quận 11, TP. Hồ Chí Minh, Việt Nam', 1, '0999890123', N'ntdat@example.com', '1992-03-15', '2023-10-04', 135, 2),
('KH231005070', N'Lê Thị Kim', N'66 Lý Thường Kiệt, Quận Tân Bình, TP. Hồ Chí Minh, Việt Nam', 2, '0910012345', N'lethik@example.com', '1985-07-22', '2023-10-05', 115, 1),
('KH231101071', N'Nguyễn Văn Phú', N'90 Tôn Đức Thắng, Quận 1, TP. Hồ Chí Minh, Việt Nam', 1, '0911123456', N'ngvanphu@example.com', '1990-02-20', '2023-11-01', 85, 1),
('KH231102072', N'Phạm Thị Loan', N'23 An Dương Vương, Quận 5, TP. Hồ Chí Minh, Việt Nam', 2, '0912234567', N'phamloan@example.com', '1989-08-14', '2023-11-02', 100, 1),
('KH231103073', N'Lê Văn Khang', N'45 Lý Tự Trọng, Quận 1, TP. Hồ Chí Minh, Việt Nam', 1, '0913345678', N'lvkhang@example.com', '1991-04-22', '2023-11-03', 90, 1),
('KH231104074', N'Trần Thị Bích', N'10 Cách Mạng Tháng Tám, Quận 3, TP. Hồ Chí Minh, Việt Nam', 2, '0914456789', N'tranbich@example.com', '1987-11-30', '2023-11-04', 125, 1),
('KH231105075', N'Phan Văn Lộc', N'12 Điện Biên Phủ, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 1, '0915567890', N'locphan@example.com', '1995-05-25', '2023-11-05', 135, 2),
('KH231201076', N'Lê Minh Hiếu', N'23 Phạm Văn Đồng, Quận Thủ Đức, TP. Hồ Chí Minh, Việt Nam', 1, '0916678901', N'lmhieu@example.com', '1986-03-13', '2023-12-01', 95, 1),
('KH231202077', N'Nguyễn Thị Luyến', N'88 Hoàng Văn Thụ, Quận Tân Bình, TP. Hồ Chí Minh, Việt Nam', 2, '0917789012', N'thuynguyen@example.com', '1993-07-17', '2023-12-02', 105, 1),
('KH231203078', N'Phạm Thị Đào', N'102 Điện Biên Phủ, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 2, '0918890123', N'thidao@example.com', '1991-09-09', '2023-12-03', 110, 1),
('KH231204079', N'Trần Văn Thành', N'12 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh, Việt Nam', 1, '0919901234', N'tvthanh@example.com', '1985-10-21', '2023-12-04', 140, 2),
('KH231205080', N'Nguyễn Thị Quỳnh', N'45 Lý Thái Tổ, Quận 10, TP. Hồ Chí Minh, Việt Nam', 2, '0920012345', N'quynhng@example.com', '1992-01-18', '2023-12-05', 125, 2),
-- 2024
('KH241020101', N'Trần Văn An', N'123 Lê Lợi, Quận 1, TP. Hồ Chí Minh, Việt Nam', 1, '0901123456', N'vanan@example.com', '1990-05-20', '2024-10-24', 100, 1),
('KH241020102', N'Nguyễn Thị Bích', N'45 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh, Việt Nam', 2, '0902234567', N'bichnguyen@example.com', '1985-08-15', '2024-10-24', 200, 2),
('KH241020103', N'Lê Minh Tâm', N'789 Điện Biên Phủ, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 1, '0903345678', N'minhtam@example.com', '1992-11-10', '2024-10-24', 50, 1),
('KH241020104', N'Phạm Quỳnh Hoa', N'25 Võ Văn Kiệt, Quận 5, TP. Hồ Chí Minh, Việt Nam', 2, '0904456789', N'quynhhoa@example.com', '1988-02-28', '2024-10-24', 150, 2),
('KH241020105', N'Đỗ Thành Nhân', N'678 Trường Sa, Quận Phú Nhuận, TP. Hồ Chí Minh, Việt Nam', 1, '0905567890', N'thanhnhan@example.com', '1995-07-07', '2024-10-24', 300, 3),
('KH241020106', N'Nguyễn Hữu Trí', N'12 Tôn Đức Thắng, Quận 1, TP. Hồ Chí Minh, Việt Nam', 1, '0906123456', N'huutri@example.com', '1989-03-15', '2024-10-24', 120, 1),
('KH241020107', N'Lý Thị Thu', N'33 Lê Lai, Quận 1, TP. Hồ Chí Minh, Việt Nam', 2, '0907234567', N'thuly@example.com', '1993-09-18', '2024-10-24', 60, 1),
('KH241020108', N'Trương Thanh Phong', N'88 Phan Xích Long, Quận Phú Nhuận, TP. Hồ Chí Minh, Việt Nam', 1, '0908345678', N'phongtruong@example.com', '1986-12-25', '2024-10-24', 250, 2),
('KH241020109', N'Lê Thị Hồng', N'25 Nguyễn Trãi, Quận 5, TP. Hồ Chí Minh, Việt Nam', 2, '0909456789', N'hongle@example.com', '1983-06-10', '2024-10-24', 70, 1),
('KH241020110', N'Bùi Văn Hùng', N'456 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh, Việt Nam', 1, '0910567890', N'vanhung@example.com', '1991-04-22', '2024-10-24', 110, 1),
('KH241020111', N'Hoàng Thị Linh', N'123 Lê Văn Sỹ, Quận Tân Bình, TP. Hồ Chí Minh, Việt Nam', 2, '0911678901', N'linhhoang@example.com', '1997-10-05', '2024-10-24', 40, 1),
('KH241020112', N'Phạm Thái Sơn', N'89 Hoàng Sa, Quận 1, TP. Hồ Chí Minh, Việt Nam', 1, '0912789012', N'thaison@example.com', '1985-01-30', '2024-10-24', 90, 1),
('KH241020113', N'Đặng Thu Hòa', N'77 Trần Hưng Đạo, Quận 1, TP. Hồ Chí Minh, Việt Nam', 2, '0913890123', N'hoadang@example.com', '1992-08-21', '2024-10-24', 140, 2),
('KH241020114', N'Nguyễn Hoàng Minh', N'58 Nguyễn Thái Học, Quận 1, TP. Hồ Chí Minh, Việt Nam', 1, '0914901234', N'hoangminh@example.com', '1988-12-12', '2024-10-24', 80, 1),
('KH241020115', N'Trần Văn Kiên', N'66 Trường Sa, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 1, '0915012345', N'kienvan@example.com', '1987-03-03', '2024-10-24', 300, 3),
('KH241020116', N'Lê Thị Lan', N'90 Lê Văn Lương, Quận 7, TP. Hồ Chí Minh, Việt Nam', 2, '0916123456', N'lanle@example.com', '1993-11-19', '2024-10-24', 200, 2),
('KH241020117', N'Phan Quốc Bảo', N'45 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh, Việt Nam', 1, '0917234567', N'quocbao@example.com', '1996-05-10', '2024-10-24', 150, 2),
('KH241020118', N'Nguyễn Thanh Tuyền', N'31 Võ Văn Ngân, Quận Thủ Đức, TP. Hồ Chí Minh, Việt Nam', 2, '0918345678', N'thantuyen@example.com', '1994-04-04', '2024-10-24', 50, 1),
('KH241020119', N'Bùi Văn Khánh', N'129 Đinh Tiên Hoàng, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 1, '0919456789', N'vankhanh@example.com', '1990-09-15', '2024-10-24', 175, 2),
('KH241020120', N'Trần Thị Mai', N'23 Hoàng Diệu, Quận 4, TP. Hồ Chí Minh, Việt Nam', 2, '0920567890', N'vanmai@example.com', '1984-10-18', '2024-10-24', 130, 2),
('KH241020121', N'Lê Quang Huy', N'111 Nguyễn Văn Trỗi, Quận Phú Nhuận, TP. Hồ Chí Minh, Việt Nam', 1, '0921678901', N'quanghuy@example.com', '1982-06-29', '2024-10-24', 65, 1),
('KH241020122', N'Phạm Minh Hùng', N'45 Cộng Hòa, Quận Tân Bình, TP. Hồ Chí Minh, Việt Nam', 1, '0922789012', N'minhhung@example.com', '1997-11-11', '2024-10-24', 90, 1),
('KH241020123', N'Trần Thị Kim Dung', N'8 Trường Sơn, Quận Tân Bình, TP. Hồ Chí Minh, Việt Nam', 2, '0923890123', N'kimdung@example.com', '1990-02-23', '2024-10-24', 95, 1),
('KH241020124', N'Nguyễn Văn Phát', N'22 Tân Sơn Nhì, Quận Tân Phú, TP. Hồ Chí Minh, Việt Nam', 1, '0924901234', N'vanphat@example.com', '1985-05-05', '2024-10-24', 115, 1),
('KH241020125', N'Lê Thị Ngọc', N'100 Bạch Đằng, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 2, '0925012345', N'ngocle@example.com', '1989-09-09', '2024-10-24', 210, 2);

-- Insert the manager
INSERT INTO Employee (id, name, address, gender, birthday, citizenIDNumber, status, phoneNumber, email, hireDate, position, workHours, hourlyPay, salary, profileImage, managerId)
VALUES
('NV001122001', N'Nguyễn Trung Nguyên', N'12 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh, Viêt Nam', 2, '1980-01-01', '0123456789012', N'Đang làm', '0901234567', N'trungnguyen@example.com', '2020-01-01', N'Quản lý', 40.0, 20000.0, 800000.0, NULL, NULL);

-- Insert the Employee
INSERT INTO Employee (id, name, address, gender, birthday, citizenIDNumber, status, phoneNumber, email, hireDate, position, workHours, hourlyPay, salary, profileImage, managerId)
VALUES
('NV001122002', N'Đào Quốc Tuấn', N'120 Lý Tự Trọng, Quận 1, TP. Hồ Chí Minh, Viêt Nam', 2, '1990-02-01', '0123456789013', N'Đang làm', '0901234568', N'quoctuan@example.com', '2021-02-01', N'Tiếp tân', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV001122004', N'Nguyễn Trần Gia Sĩ', N'50 Đinh Tiên Hoàng, Quận Bình Thạnh, TP. Hồ Chí Minh, Viêt Nam', 2, '1991-04-01', '0123456789015', N'Đang làm', '0901234570', N'nguyensi@example.com', '2021-04-01', N'Tiếp tân', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV001122005', N'Nguyễn Văn Minh', N'25 Phan Đăng Lưu, Quận Phú Nhuận, TP. Hồ Chí Minh, Viêt Nam', 1, '1985-05-01', '0123456789016', N'Đang làm', '0901234571', N'nguyenminh@example.com', '2021-05-01', N'Tiếp tân', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV001122003', N'Lê Văn Đạt', N'45 Võ Văn Tần, Quận 3, TP. Hồ Chí Minh, Viêt Nam', 2, '1992-03-01', '0123456789014', N'Đã nghỉ', '0901234569', N'ledat@example.com', '2021-03-01', N'Tiếp tân', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV230801011', N'Hoàng Thị Mai', N'90 Nguyễn Văn Trỗi, Quận Phú Nhuận, TP. Hồ Chí Minh, Việt Nam', 2, '1990-01-02', '0123456789022', N'Đang làm', '0902345678', N'hoangmai@example.com', '2023-01-02', N'Tiếp tân', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV230801012', N'Lê Văn Kiệt', N'110 Trường Chinh, Quận Tân Bình, TP. Hồ Chí Minh, Việt Nam', 1, '1991-01-05', '0123456789023', N'Đã nghỉ', '0903456789', N'levankiet@example.com', '2023-01-05', N'Tạp vụ', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV230801013', N'Nguyễn Thị Hồng', N'78 Nguyễn Đình Chiểu, Quận 3, TP. Hồ Chí Minh, Việt Nam', 2, '1992-01-10', '0123456789024', N'Đang làm', '0904567890', N'ngthihong@example.com', '2023-01-10', N'Bồi bàn', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV230801014', N'Phạm Văn Long', N'33 Điện Biên Phủ, Quận Bình Thạnh, TP. Hồ Chí Minh, Việt Nam', 1, '1993-01-15', '0123456789025', N'Đang làm', '0905678901', N'pvanlong@example.com', '2023-01-15', N'Bảo vệ', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV230801015', N'Bùi Thị Hạnh', N'45 Lý Chính Thắng, Quận 3, TP. Hồ Chí Minh, Việt Nam', 2, '1994-01-20', '0123456789026', N'Đang làm', '0906789012', N'buithihan@example.com', '2023-01-20', N'Tiếp tân', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV230802016', N'Trần Văn Tú', N'120 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh, Việt Nam', 1, '1989-02-05', '0123456789027', N'Đang làm', '0907890123', N'trvantu@example.com', '2023-02-05', N'Đầu bếp', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV230802017', N'Ngô Thị Thu', N'88 Nguyễn Văn Linh, Quận 7, TP. Hồ Chí Minh, Việt Nam', 2, '1990-02-12', '0123456789028', N'Đã nghỉ', '0908901234', N'ngothithu@example.com', '2023-02-12', N'Bồi bàn', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV230802018', N'Đoàn Văn Đức', N'50 Hòa Hưng, Quận 10, TP. Hồ Chí Minh, Việt Nam', 1, '1991-02-19', '0123456789029', N'Đang làm', '0909012345', N'doanduc@example.com', '2023-02-19', N'Tạp vụ', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV230802019', N'Hoàng Thị Lan', N'150 Tô Hiến Thành, Quận 10, TP. Hồ Chí Minh, Việt Nam', 2, '1992-02-25', '0123456789030', N'Đang làm', '0910123456', N'hoanglan@example.com', '2023-02-25', N'Bảo vệ', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV230802020', N'Vũ Văn Tiến', N'70 Phạm Văn Đồng, Quận Thủ Đức, TP. Hồ Chí Minh, Việt Nam', 1, '1988-02-28', '0123456789031', N'Đang làm', '0911234567', N'vuvantien@example.com', '2023-02-28', N'Bồi bàn', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV210701006', N'Ngô Thị Bình', N'38 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh, Viêt Nam', 2, '1993-06-01', '0123456789017', N'Đã nghỉ', '0901234572', NULL, '2021-06-01', N'Bồi bàn', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV210702007', N'Vũ Văn Cường', N'22 Lê Văn Sỹ, Quận Tân Bình, TP. Hồ Chí Minh, Viêt Nam', 1, '1994-07-01', '0123456789018', N'Đang làm', '0901234573', NULL, '2021-07-01', N'Bồi bàn', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV210703008', N'Bùi Thị Hải', N'150 Trần Hưng Đạo, Quận 5, TP. Hồ Chí Minh, Viêt Nam', 2, '1995-08-01', '0123456789019', N'Đang làm', '0901234574', NULL, '2021-08-01', N'Bồi bàn', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV210704009', N'Doãn Văn Đường', N'120 Âu Cơ, Quận Tân Phú, TP. Hồ Chí Minh, Viêt Nam', 1, '1996-09-01', '0123456789020', N'Đang làm', '0901234575', NULL, '2021-09-01', N'Bồi bàn', 40.0, 20000.0, 2400.0, NULL, 'NV001122001'),
('NV210705010', N'Tạ Thị Kim Ngân', N'200 Xô Viết Nghệ Tĩnh, Quận Bình Thạnh, TP. Hồ Chí Minh, Viêt Nam', 2, '1997-10-01', '0123456789021', N'Đang làm', '0901234576', NULL, '2021-10-01', N'Đầu bếp', 40.0, 20000.0, 2400.0, NULL, 'NV001122001');

-- Insert accounts for the first 5 Employee
INSERT INTO Account (username, hashcode, role, email, isActive)
VALUES
('NV001122001', '02d03b4538505309655c15780962858fa410fd85d8bbeffae8514fb345d01656', N'Quản lý', N'trungnguyen@example.com', 1),
('NV001122002', '52872b962111f3b6f9f8d882750471b32b0e4da893011d79425d6e0e9c5cf77e', N'Tiếp tân', N'quoctuan@example.com', 1),
('NV001122003', '6a11bb748f97e023ce442e4d9b21002690ee75485e0f1d33a5cf5797da843c7a', N'Tiếp tân', N'ledat@example.com', 0),
('NV001122004', '533400a5b1efdba4056c13b605451ac4c3f834589f6ea84ae14aea77b5996998', N'Tiếp tân', N'nguyensi@example.com', 1),
('NV001122005', 'd27cbb487826f9528d44011bcdef1566e48a842b99df60858424260de7c6f6d5', N'Tiếp tân', N'nguyenminh@example.com', 1);