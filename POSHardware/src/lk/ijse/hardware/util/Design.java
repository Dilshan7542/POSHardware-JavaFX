package lk.ijse.hardware.util;

import com.jfoenix.controls.JFXDatePicker;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Design {
    public static HBox editBtn(){
        ImageView editBtn=new ImageView("/lk/ijse/hardware/assets/logo/pencil.png");
        editBtn.setFitWidth(40);
        editBtn.setFitHeight(35);
        editBtn.setCursor(Cursor.HAND);
        HBox hBox=new HBox(editBtn);
        hBox.setFillHeight(true);
        hBox.setPrefWidth(40);

        return hBox;
    }public static HBox removeBtn(){
        ImageView editBtn=new ImageView("/lk/ijse/hardware/assets/logo/remove.png");
        editBtn.setFitWidth(40);
        editBtn.setFitHeight(35);
        editBtn.setCursor(Cursor.HAND);
        HBox hBox=new HBox(editBtn);
        hBox.setFillHeight(true);
        hBox.setPrefWidth(40);

        return hBox;
    }public static Optional getAlertConfirmation(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.CANCEL,ButtonType.APPLY);
        Optional<ButtonType> result=alert.showAndWait();
        return  result;
        // design
    }public static Pane getSelectButton(){
        Pane pane =new Pane();
        pane.setStyle("-fx-background-image: url('/lk/ijse/hardware/assets/logo/add.png');-fx-background-repeat: no-repeat;-fx-background-position: center");
        pane.setPrefWidth(40);
        pane.setMaxWidth(40);
        pane.setMaxHeight(35);
        pane.setPrefHeight(35);
        pane.setCursor(Cursor.HAND);
        return pane;

    }public static Pane btnRemove(){
        Pane pane =new Pane();
        pane.setStyle("-fx-background-image: url('/lk/ijse/hardware/assets/logo/remove.png');-fx-background-repeat: no-repeat;-fx-background-position: center");
        pane.setPrefWidth(40);
        pane.setMaxWidth(40);
        pane.setMaxHeight(35);
        pane.setPrefHeight(35);
        pane.setCursor(Cursor.HAND);
        return pane;

    }public static Pane viewDetailBtn(){
        Pane pane =new Pane();
        pane.setStyle("-fx-background-image: url('/lk/ijse/hardware/assets/logo/details.png');-fx-background-repeat: no-repeat;-fx-background-position: center");
        pane.setPrefWidth(30);
        pane.setMaxWidth(30);
        pane.setMaxHeight(30);
        pane.setPrefHeight(30);
        pane.setCursor(Cursor.HAND);
        return pane;

    }public static Pane paneEditBtn(){
        Pane pane =new Pane();
        pane.setStyle("-fx-background-image: url('/lk/ijse/hardware/assets/logo/pencil.png');-fx-background-repeat: no-repeat;-fx-background-position: center");
        pane.setPrefWidth(30);
        pane.setMaxWidth(30);
        pane.setMaxHeight(30);
        pane.setPrefHeight(30);
        pane.setCursor(Cursor.HAND);
        return pane;

    }
}
