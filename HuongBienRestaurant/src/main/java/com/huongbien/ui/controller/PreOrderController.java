package com.huongbien.ui.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.huongbien.bus.ReservationBUS;
import com.huongbien.bus.TableBUS;
import com.huongbien.config.AppConfig;
import com.huongbien.config.Constants;
import com.huongbien.config.Variable;
import com.huongbien.dao.CustomerDAO;
import com.huongbien.dao.EmployeeDAO;
import com.huongbien.dao.ReservationDAO;
import com.huongbien.dao.TableDAO;
import com.huongbien.entity.*;
import com.huongbien.service.EmailService;
import com.huongbien.service.QRCodeHandler;
import com.huongbien.utils.ClearJSON;
import com.huongbien.utils.Converter;
import com.huongbien.utils.ToastsMessage;
import com.huongbien.utils.Utils;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PreOrderController implements Initializable {
    @FXML
    private Label reservationIDLabel;
    @FXML
    private TextField numOfAttendeesField;
    @FXML
    private ComboBox<String> hourComboBox;
    @FXML
    private ComboBox<String> minuteComboBox;
    @FXML
    private TextField tableInfoField;
    @FXML
    private Label tableAmountLabel;
    @FXML
    private Label cuisineAmountLabel;
    @FXML
    private Label totalAmoutLabel;
    @FXML
    private Label preOrderCuisineLabel;
    @FXML
    private DatePicker receiveDatePicker;
    @FXML
    private ComboBox<String> partyTypeComboBox;
    @FXML
    private TextField phoneNumField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField customerIDField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField noteField;
    @FXML
    public Label tableFeeLabel;

    private VideoCapture capture;
    private Timer timer;
    private QRCodeHandler qrCodeHandler;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    //Controller area
    public RestaurantMainManagerController restaurantMainManagerController;
    public void setRestaurantMainManagerController(RestaurantMainManagerController restaurantMainManagerController) {
        this.restaurantMainManagerController = restaurantMainManagerController;
    }

    public RestaurantMainStaffController restaurantMainStaffController;
    public void setRestaurantMainStaffController(RestaurantMainStaffController restaurantMainStaffController) {
        this.restaurantMainStaffController = restaurantMainStaffController;
    }

    //initialize area
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.qrCodeHandler = new QRCodeHandler();
            setInfoPreOrder();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //function area
    private void setTimeComboBox() {
        hourComboBox.getItems().clear();
        for (int i = 0; i < 24; i++) {
            hourComboBox.getItems().add(String.format("%02d", i));
        }
        minuteComboBox.getItems().clear();
        for (int i = 0; i < 60; i++) {
            minuteComboBox.getItems().add(String.format("%02d", i));
        }
        hourComboBox.setValue(String.format("%02d", LocalTime.now().getHour()));
        minuteComboBox.setValue(String.format("%02d", LocalTime.now().getMinute()));
    }

    private void setInfoPreOrder() throws FileNotFoundException {
        tableFeeLabel.setText(tableFeeLabel.getText()+ Converter.formatMoney(Variable.tableVipPrice)+" VNĐ");
        receiveDatePicker.setValue(LocalDate.now());
        setTimeComboBox();
        ObservableList<String> partyTypes = FXCollections.observableArrayList(Variable.partyTypesArray);
        partyTypeComboBox.setItems(partyTypes);
        partyTypeComboBox.getSelectionModel().selectLast();
        //get table info from json file
        JsonArray jsonArrayTable = Utils.readJsonFromFile(Constants.TABLE_PATH);
        JsonArray jsonArrayCuisine = Utils.readJsonFromFile(Constants.CUISINE_PATH);
        JsonArray jsonArrayCustomer = Utils.readJsonFromFile(Constants.CUSTOMER_PATH);
        JsonArray jsonArrayReservation = Utils.readJsonFromFile(Constants.RESERVATION_PATH);
        //table
        StringBuilder tableInfoBuilder = new StringBuilder();
        double tableAmount = 0;
        for (JsonElement element : jsonArrayTable) {
            JsonObject jsonObject = element.getAsJsonObject();
            String id = jsonObject.get("Table ID").getAsString();
            TableDAO dao_table = TableDAO.getInstance();
            Table table = dao_table.getById(id);
            if (table != null) {
                //set table text
                String floorStr = (table.getFloor() == 0) ? "Tầng trệt" : "Tầng " + table.getFloor();
                tableInfoBuilder.append(table.getName()).append(" (").append(floorStr).append("), ");
            } else {
                tableInfoBuilder.append("Thông tin bàn không xác định, ");
            }
            //calculate table fee
            assert table != null;
            tableAmount += (table.getTableType().getTableId().equals(Variable.tableVipID)) ? Variable.tableVipPrice : 0;
        }
        if (!tableInfoBuilder.isEmpty()) {
            tableInfoBuilder.setLength(tableInfoBuilder.length() - 2);
        }
        //Cuisine
        int cuisineQuantity = 0;
        double cuisineAmount = 0;
        for (JsonElement element : jsonArrayCuisine) {
            JsonObject jsonObject = element.getAsJsonObject();
            int quantity = jsonObject.get("Cuisine Quantity").getAsInt();
            double money = jsonObject.get("Cuisine Money").getAsDouble();
            cuisineQuantity += quantity;
            cuisineAmount += money;
        }
        //customer
        for (JsonElement element : jsonArrayCustomer) {
            JsonObject jsonObject = element.getAsJsonObject();
            String id = jsonObject.get("Customer ID").getAsString();
            Customer customer = CustomerDAO.getInstance().getById(id);
            if (customer != null) {
                customerIDField.setText(customer.getCustomerId());
                nameField.setText(customer.getName());
                phoneNumField.setText(customer.getPhoneNumber());
                emailField.setText(customer.getEmail() == null ? "" : customer.getEmail());
            } else {
                customerIDField.setText("");
                nameField.setText("");
                phoneNumField.setText("");
                emailField.setText("");
            }
        }
        for (JsonElement element : jsonArrayReservation) {
            JsonObject jsonObject = element.getAsJsonObject();
            String id = jsonObject.has("Reservation ID") ? jsonObject.get("Reservation ID").getAsString() : "";
            Reservation reservation = ReservationDAO.getInstance().getById(id);
            if (reservation != null) {
                reservationIDLabel.setText("Mã đặt bàn: " + reservation.getReservationId());
                hourComboBox.setValue((reservation.getReceiveTime() != null) ? String.format("%02d", reservation.getReceiveTime().getHour()) : "23");
                minuteComboBox.setValue((reservation.getReceiveTime() != null) ? String.format("%02d", reservation.getReceiveTime().getMinute()) : "55");
                numOfAttendeesField.setText((reservation.getPartySize() != 0) ? String.valueOf(reservation.getPartySize()) : "1");
                receiveDatePicker.setValue((reservation.getReceiveDate() != null) ? reservation.getReceiveDate() : LocalDate.now());
                noteField.setText((reservation.getNote() != null) ? reservation.getNote() : "");
                partyTypeComboBox.getSelectionModel().select(reservation.getPartyType() != null ? reservation.getPartyType() : "Khác...");
            }
            break;
        }
        //set info to reservation
        tableInfoField.setText(tableInfoBuilder.toString());
        preOrderCuisineLabel.setText(cuisineQuantity + " món");
        tableAmountLabel.setText(String.format("%,.0f VNĐ", tableAmount));
        cuisineAmountLabel.setText(String.format("%,.0f VNĐ", cuisineAmount));
        totalAmoutLabel.setText(String.format("%,.0f VNĐ", tableAmount + cuisineAmount));
    }

    private void openSwingWindow() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cửa sổ quét mã QR");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(640, 480);
            frame.setLayout(new BorderLayout());
            frame.setLocationRelativeTo(null);
            JLabel cameraLabel = new JLabel();
            frame.add(cameraLabel, BorderLayout.CENTER);
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    stopCamera();
                }
            });

            frame.setVisible(true);
            readQRCode(cameraLabel, frame);
        });
    }

    private void readQRCode(JLabel cameraLabel, JFrame frame) {
        capture = new VideoCapture(0);
        if (!capture.isOpened()) {
            ToastsMessage.showMessage("Không thể mở camera!", "error");
            return;
        }

        timer = new Timer(100, e -> {
            Mat frameMat = new Mat();
            if (capture != null && capture.read(frameMat)) {
                if (!frameMat.empty()) {
                    BufferedImage bufferedImage = qrCodeHandler.matToBufferedImage(frameMat);
                    String qrCodeContent = qrCodeHandler.decodeQRCode(bufferedImage);
                    if (qrCodeContent != null) {
                        try {
                            updateCustomerFields(qrCodeContent);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        stopCamera();
                        capture.release();
                        cameraLabel.setIcon(null);
                        frame.dispose();
                    } else {
                        ImageIcon icon = new ImageIcon(bufferedImage);
                        cameraLabel.setIcon(icon);
                    }
                }
            } else {
                ToastsMessage.showMessage("Không thể đọc frame từ camera.", "error");
            }
        });

        timer.start();
    }

    private void stopCamera() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        if (capture != null && capture.isOpened()) {
            capture.release();
        }
    }

    private void updateCustomerFields(String qrCodeContent) throws SQLException, FileNotFoundException {
        String[] parts = qrCodeContent.split(",");
        if (parts.length >= 4) {
            Platform.runLater(() -> {
                Customer customer = CustomerDAO.getInstance().getById(parts[0]);
                customerIDField.setText(customer.getCustomerId() == null ? "" : customer.getCustomerId());
                nameField.setText(customer.getName() == null ? "" : customer.getName());
                phoneNumField.setText(customer.getPhoneNumber() == null ? "" : customer.getPhoneNumber());
                emailField.setText(customer.getEmail() == null ? "" : customer.getEmail());

                JsonArray jsonArray = new JsonArray();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("Customer ID", parts[0]);
                jsonArray.add(jsonObject);
                Utils.writeJsonToFile(jsonArray, Constants.CUSTOMER_PATH);
            });
        }
    }

    @FXML
    void onBackButtonAction(ActionEvent event) throws IOException {
        if(restaurantMainManagerController != null) {
            restaurantMainManagerController.openOrderTable();
        } else {
            restaurantMainStaffController.openOrderTable();
        }
    }

    @FXML
    void onQRButtonAction(ActionEvent event) throws IOException {
        openSwingWindow();
    }

    @FXML
    void onReservationManagementButtonAction(ActionEvent event) throws IOException {
        if(restaurantMainManagerController != null) {
            restaurantMainManagerController.openReservationManagement();
        } else {
            restaurantMainStaffController.openReservationManagement();
        }
    }

    @FXML
    void onDecreaseButtonAction(ActionEvent event) {
        int currentValue = Integer.parseInt(numOfAttendeesField.getText());
        if (currentValue > 1) {
            numOfAttendeesField.setText(String.valueOf(currentValue - 1));
        }
    }

    @FXML
    void onIncreaseButtonAction(ActionEvent event) {
        int currentValue = Integer.parseInt(numOfAttendeesField.getText());
        numOfAttendeesField.setText(String.valueOf(currentValue + 1));
    }

    @FXML
    void onEditTableButtonAction(ActionEvent event) throws IOException {
        if(restaurantMainManagerController != null) {
            restaurantMainManagerController.openOrderTable();
        } else {
            restaurantMainStaffController.openOrderTable();
        }
    }

    @FXML
    void onPreOrderCuisineButtonAction(ActionEvent event) throws IOException {
        if(restaurantMainManagerController != null) {
            restaurantMainManagerController.openPreOrderCuisine();
        } else {
            restaurantMainStaffController.openPreOrderCuisine();
        }
    }

    @FXML
    void onSearchCustomerExisKeyType(KeyEvent event) {
        String phone = phoneNumField.getText();
        if (!phone.matches("\\d*")) {
            phoneNumField.setText(phone.replaceAll("[^\\d]", ""));
            phoneNumField.positionCaret(phoneNumField.getText().length());
            ToastsMessage.showMessage("Chỉ nhập số, vui lòng không nhập ký tự khác", "warning");
            return;
        }

        if (phone.length() > 10) {
            ToastsMessage.showMessage("Số điện thoại không hợp lệ, phải bao gồm 10 số", "warning");
            phoneNumField.setText(phone.substring(0, 10));
            phoneNumField.positionCaret(10);
            return;
        }

        if (phone.length() < 10) {
            customerIDField.setText("");
            nameField.setText("");
            emailField.setText("");
        } else {
            Customer customer = CustomerDAO.getInstance().getByOnePhoneNumber(phone);
            if (customer != null) {
                customerIDField.setText(customer.getCustomerId());
                nameField.setText(customer.getName());
                emailField.setText(customer.getEmail() == null ? "" : customer.getEmail());
                //Write JSON
                JsonArray jsonArray = new JsonArray();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("Customer ID", customer.getCustomerId());
                jsonArray.add(jsonObject);
                Utils.writeJsonToFile(jsonArray, Constants.CUSTOMER_PATH);
            } else {
                ToastsMessage.showMessage("Không tìm thấy khách hàng, nhập thông tin khách hàng để đăng ký mới", "warning");
            }
        }
    }

    @FXML
    void onSavePreOrderTableButtonAction(ActionEvent event) throws IOException {
        JsonArray jsonArrayReservation = Utils.readJsonFromFile(Constants.RESERVATION_PATH);
        JsonArray jsonArrayTable = Utils.readJsonFromFile(Constants.TABLE_PATH);
        JsonArray jsonArrayCuisine = Utils.readJsonFromFile(Constants.CUISINE_PATH);
        JsonArray jsonArrayEmployee = Utils.readJsonFromFile(Constants.LOGIN_SESSION_PATH);

        if (phoneNumField.getText().isEmpty()) {
            ToastsMessage.showMessage("Vui lòng nhập số điện thoại để kiểm tra khách hàng", "warning");
            return;
        }

        if (phoneNumField.getText().length() != 10) {
            ToastsMessage.showMessage("Số điện thoại không hợp lệ, phải bao gồm 10 số", "warning");
            return;
        }

        if (nameField.getText().isEmpty() || phoneNumField.getText().isEmpty()) {
            ToastsMessage.showMessage("Vui lòng nhập đầy đủ thông tin khách hàng để thực hiện đăng ký", "warning");
            return;
        }

        if (customerIDField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setHeaderText("Thông báo");
            alert.setContentText("Không tìm thấy khách hàng trong hệ thống, bạn có muốn đăng ký khách hàng mới không?");
            ButtonType btn_ok = new ButtonType("Đăng ký");
            ButtonType btn_cancel = new ButtonType("Không");
            alert.getButtonTypes().setAll(btn_ok, btn_cancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == btn_ok) {
                String phone = phoneNumField.getText();
                String name = nameField.getText();
                String email = emailField.getText().isEmpty() ? null : emailField.getText();
                CustomerDAO customerDAO = CustomerDAO.getInstance();
                customerDAO.add(new Customer(name, null, 0, phone, email, null));
                Customer customer = customerDAO.getByOnePhoneNumber(phone);
                customerIDField.setText(customer.getCustomerId());
                ToastsMessage.showMessage("Đăng ký khách hàng mới thành công, nhấn LƯU để tạo đơn đặt mới", "success");
                return;
            }
        }

        if (jsonArrayTable.isEmpty()) {
            ToastsMessage.showMessage("Vui lòng chọn ít nhất 1 bàn", "warning");
            return;
        }

        Reservation reservation = new Reservation();

        String reservationID = null;
        for (JsonElement element : jsonArrayReservation) {
            JsonObject jsonObject = element.getAsJsonObject();
            reservationID = jsonObject.has("Reservation ID") ? jsonObject.get("Reservation ID").getAsString() : null;
            break;
        }

        String employeeID = null;
        for (JsonElement element : jsonArrayEmployee) {
            JsonObject jsonObject = element.getAsJsonObject();
            employeeID = jsonObject.get("Employee ID").getAsString();
            break;
        }

        String customerID = customerIDField.getText();
        LocalDate receiveDate = receiveDatePicker.getValue();
        LocalTime receiveTime = LocalTime.of(Integer.parseInt(hourComboBox.getValue()), Integer.parseInt(minuteComboBox.getValue()));
        int partySize = Integer.parseInt(numOfAttendeesField.getText());
        String note = noteField.getText();
        String partyType = partyTypeComboBox.getValue();
        Customer customer = CustomerDAO.getInstance().getById(customerID);
        Employee employee = EmployeeDAO.getInstance().getOneById(employeeID);
        double deposit = Double.parseDouble(
                totalAmoutLabel.getText()
                        .replaceAll(",", "")
                        .replaceAll(" VNĐ", "")
        );

        reservation.setReservationId(reservationID);
        reservation.setPartyType(partyType);
        reservation.setPartySize(partySize);
        reservation.setReservationDate(LocalDate.now());
        reservation.setReservationTime(LocalTime.now());
        reservation.setReceiveDate(receiveDate);
        reservation.setReceiveTime(receiveTime);
        reservation.setDeposit(deposit);
        reservation.setNote(note);
        reservation.setEmployee(employee);
        reservation.setCustomer(customer);

        ArrayList<Table> tables = new ArrayList<>();
        for (JsonElement element : jsonArrayTable) {
            JsonObject jsonObject = element.getAsJsonObject();
            String tableID = jsonObject.get("Table ID").getAsString();
            Table table = new Table();
            table.setId(tableID);
            tables.add(table);
        }

        ArrayList<FoodOrder> foodOrders = new ArrayList<>();
        for (JsonElement element : jsonArrayCuisine) {
            JsonObject jsonObject = element.getAsJsonObject();

            String cuisineID = jsonObject.get("Cuisine ID").getAsString();
            double cuisinePrice = jsonObject.get("Cuisine Price").getAsDouble();
            String cuisineNote = jsonObject.get("Cuisine Note").getAsString();
            int cuisineQuantity = jsonObject.get("Cuisine Quantity").getAsInt();

            FoodOrder foodOrder = new FoodOrder();
            foodOrder.setFoodOrderId(reservation.getReservationId());
            foodOrder.setQuantity(cuisineQuantity);
            foodOrder.setNote(cuisineNote);
            foodOrder.setSalePrice(cuisinePrice);

            Cuisine cuisine = new Cuisine();
            cuisine.setCuisineId(cuisineID);

            foodOrder.setCuisine(cuisine);
            foodOrders.add(foodOrder);
        }

        reservation.setTables(tables);
        reservation.setFoodOrders(foodOrders);

        ReservationBUS reservationBUS = new ReservationBUS();
        if (reservationID == null) {
            //Check receive time is valid
            LocalDateTime receiveDateTime = LocalDateTime.of(receiveDatePicker.getValue(), LocalTime.of(Integer.parseInt(hourComboBox.getValue()), Integer.parseInt(minuteComboBox.getValue())));
            LocalDateTime currentDateTime = LocalDateTime.now();
            if(receiveDateTime.isBefore(currentDateTime)){
                ToastsMessage.showMessage("Thời gian nhận phải lớn hơn thời gian hiện tại", "warning");
                return;
            }

            reservation.setStatus(Variable.statusReservation[0]);
            Payment payment = new Payment(deposit, Variable.paymentMethods[0]); //Default payment method is cash
            reservation.setPayment(payment);

            if (reservationBUS.addReservation(reservation)) {
                TableBUS tableBUS = new TableBUS();
                //Update status table
                for (Table table : tables) {
                    tableBUS.updateStatusTable(table.getId(), Constants.tableReserved);
                }
                String tableInfo = "Không xác định";

                if (!jsonArrayTable.isEmpty()) {
                    JsonObject tableObject = jsonArrayTable.get(0).getAsJsonObject();
                    tableInfo = tableObject.get("Table ID").getAsString();
                }

                String pickupTime = receiveDate.toString() + " " + receiveTime.toString();

                String htmlContent = "<html>" +
                        "<body style=\"font-family: Arial, sans-serif; line-height: 1.6;\">" +
                        "<h2 style=\"color: #2c3e50;\">Cảm ơn quý khách đã đặt món tại nhà hàng!</h2>" +
                        "<p>Quý khách đã đặt bàn: " + tableInfo + "</p>" +
                        "<p>Thời gian đến nhận: " + pickupTime + "</p>" +
                        "<p><b>Thông tin khách hàng:</b></p>" +
                        "<ul style=\"list-style-type: none; padding: 0;\">" +
                        "<li><b>Tên khách hàng:</b> " + customer.getName() + "</li>" +
                        "<li><b>Loại khách:</b> " + partyType + "</li>" +
                        "<li><b>Số khách:</b> " + partySize + "</li>" +
                        "<li><b>Ghi chú:</b> " + note + "</li>" +
                        "</ul>" +
                        "<p><b>Thông tin đặt cọc:</b> " + String.format("%,.0f", deposit) + " VNĐ</p>" +
                        "<p>Phí hủy đặt bàn:</p>" +
                        "<ul style=\"list-style-type: none; padding: 0;\">" +
                        "<li><b>Phí hủy sau thời gian quy định:</b> Nếu khách hàng hủy đặt bàn sau thời gian quy định, sẽ có khoản phí hủy được áp dụng. Phí này sẽ được thông báo rõ ràng trong quá trình đặt bàn.</li>" +
                        "<li><b>Chính sách phí hủy:</b></li>" +
                        "<ul>" +
                        "<li>Hủy trước 24 giờ: Miễn phí.</li>" +
                        "<li>Hủy trong vòng 24 giờ đến 1 giờ trước giờ đặt: Phí hủy bằng 50% số tiền cọc.</li>" +
                        "<li>Hủy trong vòng 1 giờ hoặc không đến: Phí hủy bằng toàn bộ số tiền cọc.</li>" +
                        "</ul>" +
                        "</ul>" +
                        "<p style=\"color: #34495e;\">Nếu có bất kỳ câu hỏi nào, xin vui lòng liên hệ với chúng tôi qua email hoặc số điện thoại được cung cấp.</p>" +
                        "<p style=\"margin-top: 20px;\">Trân trọng,<br><b>Nhà Hàng Hương Biển</b></p>" +
                        "</body>" +
                        "</html>";

                EmailService.sendEmailWithReservation(customer.getEmail(), "Thông tin đặt trước", htmlContent, AppConfig.getEmailUsername(), AppConfig.getEmailPassword());
                ToastsMessage.showMessage("Tạo đơn đặt trước thành công", "success");
            } else {
                ToastsMessage.showMessage("Tạo đơn đặt trước thất bại, vui lòng thử lại", "error");
            }
        } else {
            //Temporary solution for updating reservation
            Reservation reservationUpdate = ReservationDAO.getInstance().getById(reservationID);
            reservation.setStatus(reservationUpdate.getStatus());
            reservation.setPayment(reservationUpdate.getPayment());

            if (reservationBUS.updateReservation(reservation)) {
                ToastsMessage.showMessage("Cập nhật đơn đặt trước: " + reservationID + " thành công", "success");
            } else {
                ToastsMessage.showMessage("Cập nhật đơn đặt trước: " + reservationID + " thất bại", "error");
            }
        }

        ClearJSON.clearAllJsonWithoutLoginSession_PaymentQueue();
        if(restaurantMainManagerController != null) {
            restaurantMainManagerController.openReservationManagement();
        } else {
            restaurantMainStaffController.openReservationManagement();
        }
    }

    @FXML
    void onChangeValueQuantityKeyTyped(KeyEvent event) {
        char c = event.getCharacter().charAt(0);
        if (!Character.isDigit(c) || c == '0') {
            numOfAttendeesField.setText("1");
            ToastsMessage.showMessage("Vui lòng chỉ nhập số, từ 1 người trở lên", "warning");
        }
    }

    @FXML
    void onClearPhoneNumFieldButton(ActionEvent event) {
        phoneNumField.setText("");
        emailField.setText("");
        customerIDField.setText("");
        nameField.setText("");
        noteField.setText("");
        ClearJSON.clearCustomerJson();
    }
}