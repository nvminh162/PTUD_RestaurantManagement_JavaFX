package com.huongbien.ui.controller;


import com.huongbien.bus.CustomerBUS;
import com.huongbien.config.AppConfig;
import com.huongbien.dao.CustomerDAO;
import com.huongbien.entity.Customer;
import com.huongbien.service.EmailService;
import com.huongbien.service.QRCodeHandler;
import com.huongbien.utils.ToastsMessage;
import com.huongbien.utils.Utils;
import com.huongbien.utils.Pagination;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerManagementController implements Initializable {
    @FXML
    public Button createCustomerQRButton;

    @FXML
    public Button searchCustomerButton;

    @FXML
    public ComboBox<String> searchMethodComboBox;

    @FXML
    private Button handleActionCustomerButton;

    @FXML
    private Button swapModeCustomerButton;

    @FXML
    private DatePicker customerBirthdayDatePicker;

    @FXML
    private DatePicker customerRegistrationDateDatePicker;

    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private TableColumn<Customer, Integer> customerAccumulatedPointColumn;

    @FXML
    private TableColumn<Customer, String> customerPhoneNumberColumn;

    @FXML
    private TableColumn<Customer, String> customerGenderColumn;

    @FXML
    private TableColumn<Customer, String> customerIdColumn;

    @FXML
    private TableColumn<Customer, String> customerMembershipLevelColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TextField customerAccumulatedPointsField;

    @FXML
    private TextField customerAddressField;

    @FXML
    private TextField customerEmailField;

    @FXML
    private TextField customerMembershipLevelField;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField customerPhoneField;

    @FXML
    private TextField searchCustomerField;

    @FXML
    private ImageView clearSearchButton;

    @FXML
    public Label pageIndexLabel;

    private final CustomerBUS customerBUS = new CustomerBUS();

    private Pagination<Customer> customerPagination;

    private void setCustomerTableColumns() {
        customerTable.setPlaceholder(new Label("Không có dữ liệu"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerGenderColumn.setCellValueFactory(cellData -> {
            int gender = cellData.getValue().getGender();
            String genderText = Utils.toStringGender(gender);
            return new SimpleStringProperty(genderText);
        });
        customerMembershipLevelColumn.setCellValueFactory(cellData -> {
            int memberShip = cellData.getValue().getMembershipLevel();
            String memberShipLevel = Utils.toStringMembershipLevel(memberShip);
            return new SimpleStringProperty(memberShipLevel);
        });
        customerPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerAccumulatedPointColumn.setCellValueFactory(new PropertyValueFactory<>("accumulatedPoints"));
    }

    public void setPageIndexLabel() {
        int currentPage = customerPagination.getCurrentPageIndex();
        int totalPage = customerPagination.getTotalPages();
        pageIndexLabel.setText(currentPage + "/" + totalPage);
    }

    public void setSearchMethodComboBoxValue() {
        searchMethodComboBox.getItems().addAll("Tên", "Số điện thoại", "Mã khách hàng", "Tất cả");
        searchMethodComboBox.setValue("Tất cả");
        searchCustomerField.setPromptText("Nhập thông tin tìm kiếm");
    }

    public void setPaginationGetAllCustomer() {
        int totalItems = customerBUS.getTotalCustomerCount();
        int itemsPerPage = 10;
        boolean isRollBack = false;
        customerPagination = new Pagination<>(
                customerBUS::getAllCustomersWithPagination,
                itemsPerPage,
                totalItems,
                isRollBack
        );
    }

    public void setPaginationGetByCustomerPhoneNumber(String phoneNumber) {
        int totalItems = customerBUS.getTotalCustomersCountByPhoneNumber(phoneNumber);
        int itemsPerPage = 10;
        boolean isRollBack = false;
        customerPagination = new Pagination<>(
                (offset, limit) -> customerBUS.getCustomersByPhoneNumberWithPagination(offset, limit, phoneNumber),
                itemsPerPage,
                totalItems,
                isRollBack
        );
    }

    public void setPaginationGetByCustomerName(String name) {
        int totalItems = customerBUS.getTotalCustomersCountByName(name);
        int itemsPerPage = 10;
        boolean isRollBack = false;
        customerPagination = new Pagination<>(
                (offset, limit) -> customerBUS.getCustomersByNameWithPagination(offset, limit, name),
                itemsPerPage,
                totalItems,
                isRollBack
        );
    }

    public void setPaginationGetByCustomerId(String id) {
        int totalItems = customerBUS.getTotalCustomersCountById(id);
        int itemsPerPage = 10;
        boolean isRollBack = false;
        customerPagination = new Pagination<>(
                (offset, limit) -> customerBUS.getCustomersByIdWithPagination(offset, limit, id),
                itemsPerPage,
                totalItems,
                isRollBack
        );
    }

    public void setCustomerTableValues() {
        customerTable.getItems().clear();
        setPageIndexLabel();
        ObservableList<Customer> listCustomer = FXCollections.observableArrayList(customerPagination.getCurrentPage());
        customerTable.setItems(listCustomer);
    }

    public Customer getCustomerInfoFromForm() {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        String id = customer == null ? null : customer.getCustomerId();
        int accumulatedPoints = Integer.parseInt(customerAccumulatedPointsField.getText());
        int membershipLevel = customerMembershipLevelField.getText().isEmpty() ? 0 : Utils.toIntMembershipLevel(customerMembershipLevelField.getText().trim());
        LocalDate registrationDate = customerRegistrationDateDatePicker.getValue();
        String name = customerNameField.getText();
        String phone = customerPhoneField.getText();
        String email = customerEmailField.getText();
        String address = customerAddressField.getText();
        LocalDate birthday = customerBirthdayDatePicker.getValue();
        int gender = 0;
        if (maleRadioButton.isSelected()) {
            gender = 1;
        } else if (femaleRadioButton.isSelected()) {
            gender = 2;
        }
        return new Customer(id, name, address, gender, phone, email, birthday, registrationDate, accumulatedPoints, membershipLevel);
    }

    public void addNewCustomer() {
        Customer customer = getCustomerInfoFromForm();
        if (!validateCustomerData()) return;

        if (customerBUS.addCustomer(customer)) {
            setCustomerTableValues();
            clearCustomerForm();
            disableInput();
            ToastsMessage.showMessage("Thêm khách hàng thành công", "success");
        } else {
            ToastsMessage.showMessage("Thêm thất bại bui lòng xem lại", "error");
        }
    }

    public void updateCustomerInfo() {
        Customer customer = getCustomerInfoFromForm();
        String customerId = customerTable.getSelectionModel().getSelectedItem().getCustomerId();
        customer.setCustomerId(customerId);

        if (!validateCustomerData()) {
            return;
        }

        if (customerBUS.updateCustomerInfo(customer)) {
            setCustomerTableValues();
            ToastsMessage.showMessage("Cập nhật thông tin khách hàng thành công", "success");
        } else {
            ToastsMessage.showMessage("Cập nhật thông tin khách hàng thất bại", "error");
        }
    }

    public void clearCustomerForm() {
        customerNameField.setText("");
        customerPhoneField.setText("");
        customerEmailField.setText("");
        customerBirthdayDatePicker.setValue(null);
        genderGroup.selectToggle(null);
        customerRegistrationDateDatePicker.setValue(null);
        customerAddressField.setText("");
        customerMembershipLevelField.setText("");
        customerAccumulatedPointsField.setText("");
        customerTable.getSelectionModel().clearSelection();
    }

    public void enableInput() {
        customerNameField.setDisable(false);
        customerEmailField.setDisable(false);
        customerAddressField.setDisable(false);
        customerPhoneField.setDisable(false);
        customerBirthdayDatePicker.setDisable(false);
        maleRadioButton.setDisable(false);
        customerAccumulatedPointsField.setDisable(false);
        customerMembershipLevelField.setDisable(false);
        femaleRadioButton.setDisable(false);
    }

    public void disableInput() {
        customerNameField.setDisable(true);
        customerEmailField.setDisable(true);
        customerAddressField.setDisable(true);
        customerPhoneField.setDisable(true);
        customerBirthdayDatePicker.setDisable(true);
        customerMembershipLevelField.setDisable(true);
        customerAccumulatedPointsField.setDisable(true);
        maleRadioButton.setDisable(true);
        femaleRadioButton.setDisable(true);
    }

    public void setHandleActionButtonToEditCustomer() {
        swapModeCustomerButton.setText("Thêm");
        handleActionCustomerButton.setText("Sửa");
        swapModeCustomerButton.setStyle("-fx-background-color:   #1D557E");
        handleActionCustomerButton.setStyle("-fx-background-color:  #761D7E");
    }

    public void setHandleActionButtonToAddCustomer() {
        swapModeCustomerButton.setText("Sửa");
        handleActionCustomerButton.setText("Thêm");
        swapModeCustomerButton.setStyle("-fx-background-color:   #761D7E");
        handleActionCustomerButton.setStyle("-fx-background-color:  #1D557E");

        customerMembershipLevelField.setText("0");
        customerAccumulatedPointsField.setText("0");
        customerRegistrationDateDatePicker.setValue(LocalDate.now());
    }

    public boolean validateCustomerData() {
        if (customerNameField.getText().trim().isEmpty()) {
            ToastsMessage.showMessage("Tên khách hàng không được để trống", "error");
            return false;
        }
        if(!swapModeCustomerButton.getText().equals("Thêm")) {
            if (!customerPhoneField.getText().trim().isEmpty()) {
                CustomerDAO customerDAO = CustomerDAO.getInstance();
                List<String> customerList = customerDAO.getPhoneNumber();
                for (String phone : customerList) {
                    if (customerPhoneField.getText().equals(phone)) {
                        ToastsMessage.showMessage("Số điện thoại đã được đăng kí thành viên", "error");
                        return false;
                    }
                }
            }
        }
        if (customerPhoneField.getText().trim().isEmpty()) {
            ToastsMessage.showMessage("Số điện thoại không được để trống", "error");
            return false;
        }
        if (genderGroup.getSelectedToggle() == null) {
            ToastsMessage.showMessage("Vui lòng chọn giới tính", "error");
            return false;
        }

        return true;
    }

    public void setCustomerSearchPagination() {
        String searchValue = searchCustomerField.getText();
        String searchMethod = searchMethodComboBox.getValue();
        switch (searchMethod) {
            case "Tên" -> setPaginationGetByCustomerName(searchValue);
            case "Số điện thoại" -> setPaginationGetByCustomerPhoneNumber(searchValue);
            case "Mã khách hàng" -> setPaginationGetByCustomerId(searchValue);
            case "Tất cả" -> setPaginationGetAllCustomer();
        }

        setCustomerTableValues();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearSearchButton.setVisible(false);
        searchCustomerField.setDisable(true);
        enableInput();
        setHandleActionButtonToAddCustomer();
        setSearchMethodComboBoxValue();
        setCustomerTableColumns();
        setPaginationGetAllCustomer();
        setCustomerTableValues();
    }

    @FXML
    void onClearCustomerFormButtonClicked(ActionEvent event) {
        clearCustomerForm();
        customerNameField.requestFocus();
    }

    @FXML
    void onCustomerHandleActionButtonClicked(ActionEvent event) {
        String handleActionType = handleActionCustomerButton.getText();
        switch (handleActionType) {
            case "Thêm" -> addNewCustomer();
            case "Sửa" -> updateCustomerInfo();
        }
    }

    @FXML
    void onCustomerSwapModeButtonClicked(ActionEvent event) {
        if (handleActionCustomerButton.getText().equals("Thêm")) {
            setHandleActionButtonToEditCustomer();
            disableInput();
        } else {
            setHandleActionButtonToAddCustomer();
            clearCustomerForm();
            enableInput();
        }
    }

    @FXML
    void onCustomerTableClicked(MouseEvent event) {
        setHandleActionButtonToEditCustomer();
        Customer selectedItem = customerTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            enableInput();
            customerNameField.setText(selectedItem.getName());
            customerEmailField.setText(selectedItem.getEmail());
            customerAddressField.setText(selectedItem.getAddress());
            customerPhoneField.setText(selectedItem.getPhoneNumber());
            customerBirthdayDatePicker.setValue(selectedItem.getBirthday());
            customerRegistrationDateDatePicker.setValue(selectedItem.getRegistrationDate());
            customerAccumulatedPointsField.setText(selectedItem.getAccumulatedPoints() + "");
            customerMembershipLevelField.setText(Utils.toStringMembershipLevel(selectedItem.getMembershipLevel()));

            if (selectedItem.getGender() == 1) {
                genderGroup.selectToggle(maleRadioButton);
            } else if (selectedItem.getGender() == 2) {
                genderGroup.selectToggle(femaleRadioButton);
            }
        }
    }

    @FXML
    void onSearchMethodComboBoxSelected(ActionEvent actionEvent) {
        searchCustomerField.setText("");
        searchCustomerField.setDisable(false);
        clearSearchButton.setVisible(false);
        String searchMethod = searchMethodComboBox.getValue();
        switch (searchMethod) {
            case "Tên":
                searchCustomerField.setPromptText("Nhập tên khách hàng");
                break;
            case "Số điện thoại":
                searchCustomerField.setPromptText("Nhập số điện thoại khách hàng");
                break;
            case "Mã khách hàng":
                searchCustomerField.setPromptText("Nhập mã khách hàng");
                break;
            case "Tất cả":
                searchCustomerField.setPromptText("Thông tin tìm kiếm");
                searchCustomerField.setDisable(true);
                setCustomerSearchPagination();
                break;
        }
    }

    @FXML
    void onClearSearchFieldButtonClicked(MouseEvent event) {
        clearSearchButton.setVisible(false);
        searchCustomerField.setText("");
        searchMethodComboBox.setValue("Tất cả");
        setPaginationGetAllCustomer();
        setCustomerTableValues();
    }

    @FXML
    void onSearchCustomerButtonClicked(ActionEvent actionEvent) {
        setCustomerSearchPagination();
    }

    @FXML
    void onSearchCustomerPhoneFieldKeyReleased(KeyEvent event) {
        boolean isSearchFieldEmpty = searchCustomerField.getText().isEmpty();
        clearSearchButton.setVisible(isSearchFieldEmpty);
        setCustomerSearchPagination();
    }

    @FXML
    void onCreateCustomerQRButtonClicked(ActionEvent actionEvent) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            String qrContent = selectedCustomer.getCustomerId() + ","
                    + selectedCustomer.getName() + ","
                    + selectedCustomer.getMembershipLevel() + ","
                    + selectedCustomer.getPhoneNumber() + ","
                    + selectedCustomer.getEmail();

            QRCodeHandler qrCodeHandler = new QRCodeHandler();
            qrCodeHandler.createQRCode(selectedCustomer, qrContent);

            String qrImagePath = "src/main/resources/com/huongbien/qrCode/QrCode_Ma" + selectedCustomer.getCustomerId() + ".png";
            File qrFile = new File(qrImagePath);

            if (qrFile.exists()) {
                EmailService emailService = new EmailService(AppConfig.getEmailUsername(), AppConfig.getEmailPassword());
                boolean emailSent = emailService.sendEmailWithQRCode(selectedCustomer.getEmail(), qrImagePath);

                if (emailSent) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "QR code created and email sent successfully!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to send email!");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to create QR code for customer ID: " + selectedCustomer.getCustomerId());
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a customer to generate a QR code!");
            alert.showAndWait();
        }
    }

    @FXML
    void onNextPageButtonClicked(MouseEvent mouseEvent) {
        customerPagination.goToNextPage();
        setCustomerTableValues();
    }

    @FXML
    void onLastPageButtonClicked(MouseEvent mouseEvent) {
        customerPagination.goToLastPage();
        setCustomerTableValues();
    }

    @FXML
    void onFirstPageButtonClicked(MouseEvent mouseEvent) {
        customerPagination.goToFirstPage();
        setCustomerTableValues();
    }

    @FXML
    void onPrevPageButtonClicked(MouseEvent mouseEvent) {
        customerPagination.goToPreviousPage();
        setCustomerTableValues();
    }
}