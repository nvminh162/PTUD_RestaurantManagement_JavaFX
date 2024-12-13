USE HuongBien;

GO

BACKUP DATABASE HuongBien 
TO DISK = 'E:\Workspace\Project\ApplicationDevelopment\HuongBien\HuongBienRestaurant\src\main\resources\com\huongbien\backupDatabase\HuongBienRestaurantVerFinal.bak'
WITH FORMAT,
MEDIANAME = 'SQLServerBackups',
NAME = 'Full Backup of HuongBien';