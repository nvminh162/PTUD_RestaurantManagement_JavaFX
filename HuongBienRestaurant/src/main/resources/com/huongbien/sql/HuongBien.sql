-- Tạo database HuongBien
USE master;
CREATE DATABASE HuongBien;
GO
-- Server=localhost;Database=HuongBien;User Id=sa;Password=sapassword;
-- Sử dụng database HuongBien
USE HuongBien;
GO

-- Tạo bảng Customer
CREATE TABLE Customer (
    id CHAR(11) PRIMARY KEY,
    name NVARCHAR(40) NOT NULL,
    [address] NVARCHAR(200),
    gender INT DEFAULT 0,
    phoneNumber CHAR(10) NOT NULL,
    email NVARCHAR(40),
    birthday DATE,
    registrationDate DATE NOT NULL,
    accumulatedPoints INT DEFAULT 1 NOT NULL,
    membershipLevel INT DEFAULT 0 NOT NULL,
);
GO

-- Tạo bảng Employee
CREATE TABLE Employee (
    id CHAR(11) PRIMARY KEY,
    name NVARCHAR(40) NOT NULL,
    address NVARCHAR(200) NOT NULL,
    gender INT NOT NULL,
    birthday DATE NOT NULL,
    citizenIDNumber CHAR(13) NOT NULL,
    status NVARCHAR(15) NOT NULL,
    phoneNumber CHAR(10) NOT NULL,
    email NVARCHAR(40),
    hireDate DATE NOT NULL,
    position NVARCHAR(20) NOT NULL,
	workHours REAL NOT NULL,
    hourlyPay REAL, -- CHO NHÂN VIÊN BỒI BÀN
    salary REAL, -- CHO QUẢN LÝ, ĐẦU BẾP, TIẾP TÂN
    profileImage VARBINARY(MAX),

    managerId CHAR(11) NULL,
    FOREIGN KEY (managerId) REFERENCES Employee(id),
);
GO

-- Tạo bảng Promotion
CREATE TABLE Promotion (
    id CHAR(11) PRIMARY KEY,
    name NVARCHAR(40) NOT NULL,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    discount REAL NOT NULL,
    description NVARCHAR(100) NOT NULL,
    minimumOrderAmount REAL NOT NULL,
    membershipLevel INT NOT NULL,
    status NVARCHAR(20) NOT NULL
);
GO


-- Tạo bảng Payment
CREATE TABLE Payment (
    id CHAR(17) PRIMARY KEY,
    amount REAL NOT NULL,
    paymentDate DATE NOT NULL,
    paymentMethod NVARCHAR(20) NOT NULL,
    paymentTime TIME NOT NULL,
);
GO

-- Tạo bảng Account
CREATE TABLE Account (
    username CHAR(11) PRIMARY KEY, -- employeeId
    hashcode CHAR(64) NOT NULL,
    role NVARCHAR(20) NOT NULL,
    email NVARCHAR(40) NOT NULL,
    isActive BIT DEFAULT 1 NOT NULL,

    FOREIGN KEY (username) REFERENCES Employee(id),
);
GO

-- Tạo bảng TableType
CREATE TABLE TableType (
    id CHAR(5) PRIMARY KEY,
    name NVARCHAR(40) NOT NULL,
    description NVARCHAR(100) NOT NULL
);
GO

-- Tạo bảng Table
CREATE TABLE [Table] (
    id CHAR(6) PRIMARY KEY,
    name NVARCHAR(20) NOT NULL,
	seats INT NOT NULL,
    [floor] INT NOT NULL,
    status NVARCHAR(20) NOT NULL,

    tableTypeId CHAR(5) NOT NULL,
    FOREIGN KEY (tableTypeId) REFERENCES TableType(id),
);
GO

-- Tạo bảng Category
CREATE TABLE Category (
    id CHAR(5) PRIMARY KEY,
    name NVARCHAR(40) NOT NULL,
    description NVARCHAR(100) NOT NULL
);
GO

-- Tạo bảng Cuisine
CREATE TABLE Cuisine (
    id CHAR(4) PRIMARY KEY,
    name NVARCHAR(40) NOT NULL,
    price REAL NOT NULL,
    description NVARCHAR(50) NOT NULL,
    image VARBINARY(MAX),
    status NVARCHAR(50) NOT NULL,

    categoryId CHAR(5) NOT NULL,
    FOREIGN KEY (categoryId) REFERENCES Category(id),
);
GO

-- Tạo bảng Reservation
CREATE TABLE Reservation (
    id CHAR(17) PRIMARY KEY,
    partyType NVARCHAR(20) NOT NULL,
    partySize INT NOT NULL,
    reservationDate DATE NOT NULL,
    reservationTime TIME NOT NULL,
    receiveDate DATE NOT NULL,
    receiveTime TIME NOT NULL,
    status NVARCHAR(50) NOT NULL,
    deposit REAL DEFAULT 0 NOT NULL,
    refundDeposit REAL DEFAULT 0 NOT NULL,
    note NVARCHAR(100),

    employeeId CHAR(11) NOT NULL,
    customerId CHAR(11) NOT NULL,
    paymentId CHAR(17),
    FOREIGN KEY (employeeId) REFERENCES Employee(id),
    FOREIGN KEY (customerId) REFERENCES Customer(id),
    FOREIGN KEY (paymentId) REFERENCES Payment(id),
);
GO

-- Tạo bảng FoodOrder
CREATE TABLE FoodOrder (
    id CHAR(22) PRIMARY KEY,
    quantity INT NOT NULL,
    note NVARCHAR(100),
    salePrice REAL NOT NULL,

    cuisineId CHAR(4) NOT NULL,
    reservationId CHAR(17) NOT NULL,
    FOREIGN KEY (cuisineId) REFERENCES Cuisine(id),
    FOREIGN KEY (reservationId) REFERENCES Reservation(id),
);
GO

-- Tạo bảng Order
CREATE TABLE [Order] (
    id CHAR(17) PRIMARY KEY,
    orderDate DATE NOT NULL,
    orderTime TIME NOT NULL,
    notes NVARCHAR(100) NOT NULL,
    vatTax REAL DEFAULT 0.1 NOT NULL,
    paymentAmount REAL NOT NULL,
    dispensedAmount REAL NOT NULL,
    totalAmount REAL NOT NULL,
    discount REAL DEFAULT 0 NOT NULL,

    customerId CHAR(11),
    employeeId CHAR(11) NOT NULL,
    promotionId CHAR(11),
    paymentId CHAR(17) NOT NULL,
    FOREIGN KEY (customerId) REFERENCES Customer(id),
    FOREIGN KEY (employeeId) REFERENCES Employee(id),
    FOREIGN KEY (promotionId) REFERENCES Promotion(id),
    FOREIGN KEY	(paymentId ) REFERENCES Payment(id)
);
GO

-- Tạo bảng OrderDetail
CREATE TABLE OrderDetail (
    id CHAR(22) PRIMARY KEY,
    quantity INT NOT NULL,
    note NVARCHAR(100),
    salePrice REAL NOT NULL,

    cuisineId CHAR(4) NOT NULL,
    orderId CHAR(17) NOT NULL,
    FOREIGN KEY (cuisineId) REFERENCES Cuisine(id),
    FOREIGN KEY (orderId) REFERENCES [Order](id),
);
GO

-- Tạo bảng Order_Table
CREATE TABLE Order_Table (
    orderId CHAR(17),
    tableId CHAR(6),
    PRIMARY KEY (orderId, tableId),
    FOREIGN KEY (orderId) REFERENCES [Order](id),
	FOREIGN KEY (tableId) REFERENCES [Table](id)
);
GO

-- Tạo bảng Reservation_Table
CREATE TABLE Reservation_Table (
    reservationId CHAR(17),
    tableId CHAR(6),
    PRIMARY KEY (reservationId, tableId),
    FOREIGN KEY (reservationId) REFERENCES [Reservation](id),
	FOREIGN KEY (tableId) REFERENCES [Table](id)
);
GO