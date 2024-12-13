package com.huongbien.ui.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.huongbien.config.Constants;
import com.huongbien.dao.CuisineDAO;
import com.huongbien.entity.Cuisine;
import com.huongbien.utils.Converter;
import com.huongbien.utils.ToastsMessage;
import com.huongbien.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class OrderCuisineItemController {
    @FXML private Circle cuisineImageCircle;
    @FXML private Label cuisineIdLabel;
    @FXML private Label cuisineNameLabel;
    @FXML private Label cuisineSalePriceLabel;

    //Controller area
    public OrderCuisineController orderCuisineController;
    public void setOrderCuisineController(OrderCuisineController orderCuisineController) {
        this.orderCuisineController = orderCuisineController;
    }

    public void setCuisineData(Cuisine cuisine) {
        cuisineIdLabel.setText(cuisine.getCuisineId());
        cuisineNameLabel.setText(cuisine.getName());
        cuisineSalePriceLabel.setText(String.format("%,.0f VNĐ", cuisine.getPrice()));
        byte[] imageBytes = cuisine.getImage();
        Image image;
        //-----------------
        if (imageBytes != null) {
            image = Converter.bytesToImage(imageBytes);
        } else {
            image = new Image("/com/huongbien/icon/mg_cuisine/empty-256px.png");
        }
        //design circle cuisine image
        cuisineImageCircle.setFill(new ImagePattern(image));
        cuisineImageCircle.setStroke(Color.BLUE);
        cuisineImageCircle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
    }

    private void writeDataToJSONFile(String cuisineID, String cuisineName, double cuisinePrice, String cuisineNote, int cuisineQuantity) {
        boolean idExists = false;
        JsonArray jsonArray;
        //-----------------
        try {
            jsonArray = Utils.readJsonFromFile(Constants.CUISINE_PATH);
        } catch (FileNotFoundException e) {
            jsonArray = new JsonArray();
        }
        //-----------------
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            String existingCuisineID = jsonObject.get("Cuisine ID").getAsString();
            if (existingCuisineID.equals(cuisineID)) {
                int currentQuantity = jsonObject.get("Cuisine Quantity").getAsInt();
                jsonObject.addProperty("Cuisine Quantity", currentQuantity + 1);
                double updatedMoney = jsonObject.get("Cuisine Price").getAsDouble() * (currentQuantity + 1);
                jsonObject.addProperty("Cuisine Money", updatedMoney);
                idExists = true;
                break;
            }
        }

        if (!idExists) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Cuisine ID", cuisineID);
            jsonObject.addProperty("Cuisine Name", cuisineName);
            jsonObject.addProperty("Cuisine Price", cuisinePrice);
            jsonObject.addProperty("Cuisine Note", cuisineNote);
            jsonObject.addProperty("Cuisine Quantity", cuisineQuantity);
            double cuisineMoney = cuisinePrice * cuisineQuantity;
            jsonObject.addProperty("Cuisine Money", cuisineMoney);
            jsonArray.add(jsonObject);
        }
        Utils.writeJsonToFile(jsonArray, Constants.CUISINE_PATH);
    }

    @FXML
    void onCuisineItemClicked(MouseEvent event) throws FileNotFoundException, SQLException {
        String id = cuisineIdLabel.getText();
        CuisineDAO cuisineDAO = CuisineDAO.getInstance();
        Cuisine cuisine = cuisineDAO.getById(id);
        writeDataToJSONFile(cuisine.getCuisineId(), cuisine.getName(), cuisine.getPrice(), "", 1);
        orderCuisineController.billGridPane.getChildren().clear();
        orderCuisineController.loadBill();
        //update lbl
        orderCuisineController.setCuisinesInfoFromJSON();
        ToastsMessage.showMessage("Đã thêm món: "+cuisine.getName()+" vào danh sách", "success");
    }
}
