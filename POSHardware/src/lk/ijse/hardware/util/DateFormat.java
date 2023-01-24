package lk.ijse.hardware.util;

import com.jfoenix.controls.JFXDatePicker;
import javafx.scene.control.DateCell;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormat {

    public static JFXDatePicker formatDate(JFXDatePicker datePicker) {
        datePicker.setDayCellFactory(param -> new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.compareTo(LocalDate.now())<0);
            }
        }); //HIDE PRE DATE
        datePicker.setConverter(new StringConverter<LocalDate>() { //FORMAT DATE
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("YYY/MM/dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        return datePicker;
    }public static JFXDatePicker formatDate(JFXDatePicker datePicker,String format) {
        datePicker.setConverter(new StringConverter<LocalDate>() { //FORMAT DATE
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        return datePicker;

    }
    public static JFXDatePicker hidePreviousDate(JFXDatePicker datePicker){
        datePicker.setDayCellFactory(param -> new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.compareTo(LocalDate.now())<0);
            }
        }); //HIDE PRE DATE
        return datePicker;
    }public static JFXDatePicker hideNextDate(JFXDatePicker datePicker){
        datePicker.setDayCellFactory(param -> new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.compareTo(LocalDate.now())>0);
            }
        }); //HIDE PRE DATE
        return datePicker;
    }
}
