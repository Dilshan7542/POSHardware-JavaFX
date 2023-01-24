package lk.ijse.hardware.controller.dash;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import lk.ijse.hardware.dto.SupplierDTO;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.SupplierService;
import lk.ijse.hardware.service.custom.impl.ItemServiceImpl;
import lk.ijse.hardware.service.custom.impl.SupplierServiceImpl;
import lk.ijse.hardware.util.Converter;
import lk.ijse.hardware.util.ConverterList;
import lk.ijse.hardware.util.Design;
import lk.ijse.hardware.util.Regex;
import lk.ijse.hardware.util.enm.TextFields;
import lk.ijse.hardware.view.tm.SupplierTM;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SupplierController {

    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public JFXTextField txtAddress;
    public JFXTextField txtPhone;
    public JFXComboBox<String> cmbStatus;
    public TableColumn<SupplierTM, String> colId;
    public TableColumn<SupplierTM, String> colName;
    public TableColumn<SupplierTM, String> colEmail;
    public TableColumn<SupplierTM, String> colAddress;
    public TableColumn<SupplierTM, String> colPhone;
    public TableColumn<SupplierTM, String> colStatus;
    public TableColumn<SupplierTM, String> colEdit;
    public TableView mainTable;
    boolean isUpdate;
    ConverterList converterList = new ConverterList();
    SupplierService supplierBO=(SupplierServiceImpl) ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
    ObservableList<SupplierTM> obSupplerList = FXCollections.observableArrayList();

    public void initialize() {
        txtName.requestFocus();
        addTable();
        addComboBox();
        addId();
    }

    private void addId() {
        try {
            txtId.setStyle("-fx-text-fill: black");
            txtId.setText(supplierBO.generateSupplierID());
            txtId.setEditable(false);
            txtId.requestFocus();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addComboBox() {
        cmbStatus.getItems().add("Show");
        cmbStatus.getItems().add("Hide");
    }

    private void addTable() {
        if (obSupplerList != null) {
            obSupplerList.clear();
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEdit.setCellValueFactory(new PropertyValueFactory<>("editBtn"));

        try {
            List<SupplierTM> allSupplierTM = converterList.formSupplierTMList(supplierBO.getAllSuppler());
            for (SupplierTM s : allSupplierTM) {
                obSupplerList.add(s);
            }
            for (SupplierTM s : obSupplerList) {
                s.setEditBtn(Design.editBtn());
                s.getEditBtn().setOnMouseClicked(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are Sure Edit For Data ?");
                    alert.getButtonTypes().clear();
                    alert.getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.APPLY) {
                        disableAllBtn();
                        s.getEditBtn().setDisable(false);
                        s.getEditBtn().setOpacity(1);
                        isUpdate = true;
                        setTextFieldToValue(s);

                    }
                });
            }


            mainTable.setItems(obSupplerList);
            mainTable.refresh();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void disableAllBtn() {
        for (SupplierTM s : obSupplerList) {
            s.getEditBtn().setDisable(true);
            s.getEditBtn().setOpacity(0.1);

        }

    }

    private void setTextFieldToValue(SupplierTM supplierTM) {

        txtId.setText(supplierTM.getId());
        txtId.setStyle("-fx-text-fill: red");
        txtName.setText(supplierTM.getName());
        txtAddress.setText(supplierTM.getAddress());
        txtEmail.setText(supplierTM.getEmail());
        txtPhone.setText(supplierTM.getPhone());


    }

    public void btnAdd(ActionEvent actionEvent) {


        if (validText() instanceof Boolean) {
            try {
                if (!isUpdate) {
                    if (supplierBO.saveSupplier(new SupplierDTO(txtId.getText(), txtName.getText(), txtEmail.getText(), txtAddress.getText(), txtPhone.getText(), 0, isCheckStatus()))) {
                        new Alert(Alert.AlertType.INFORMATION, "SupplierTM add Successfully").show();

                    }
                } else {
                    updateSupplier();

                }
                clearFields();
                addTable();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {

            new Alert(Alert.AlertType.WARNING, "Please Fill The Form").show();

            return;
        }

    }

    public void updateSupplier() throws SQLException, ClassNotFoundException {
        boolean isUpdate = supplierBO.updateSuppler(new SupplierDTO(txtId.getText(), txtName.getText(), txtEmail.getText(), txtAddress.getText(), txtPhone.getText(), 0, isCheckStatus()));
        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "Update Successfully").show();
        }
    }

    private boolean isCheckStatus() {
        if (!cmbStatus.getSelectionModel().isEmpty()) {
            return !cmbStatus.getValue().equals("Hide");
        }
        return true;
    }


    public void clearFields() {
        addId();
        txtName.clear();
        txtEmail.clear();
        txtAddress.clear();
        txtPhone.clear();
        cmbStatus.getItems().clear();
        addComboBox();
    }

    public void isKeyEnter(KeyEvent keyEvent, JFXTextField textField) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            textField.requestFocus();
        }
    }

    private Object validText() {

        if (!Regex.setTextColor(TextFields.NAME, txtName)) {
            txtName.setUnFocusColor(Paint.valueOf("Red"));
            return txtName;
        } else {

            if (!Regex.setTextColor(TextFields.EMAIL, txtEmail)) {
                txtEmail.setUnFocusColor(Paint.valueOf("Red"));
                return txtEmail;
            } else {
                if (!Regex.setTextColor(TextFields.ADDRESS, txtAddress)) {
                    txtAddress.setUnFocusColor(Paint.valueOf("Red"));
                    return txtAddress;
                } else {
                    if (!Regex.setTextColor(TextFields.PHONE, txtPhone)) {
                        txtPhone.setUnFocusColor(Paint.valueOf("Red"));

                        return txtPhone;
                    }
                }

            }
        }
        txtName.setUnFocusColor(Paint.valueOf("black"));
        txtEmail.setUnFocusColor(Paint.valueOf("Black"));
        txtAddress.setUnFocusColor(Paint.valueOf("Black"));
        txtPhone.setUnFocusColor(Paint.valueOf("Black"));
        return true;

    }


    public void onKeyName(KeyEvent keyEvent) {
        if (Regex.setTextColor(TextFields.NAME, txtName)) {
            isKeyEnter(keyEvent, txtEmail);
        }

    }

    public void onKeyEmail(KeyEvent keyEvent) {
        if (Regex.setTextColor(TextFields.EMAIL, txtEmail)) {
            isKeyEnter(keyEvent, txtAddress);
        }
    }

    public void onKeyAddress(KeyEvent keyEvent) {
        if (Regex.setTextColor(TextFields.ADDRESS, txtAddress)) {
            isKeyEnter(keyEvent, txtPhone);
        }
    }

    public void onKeyPhone(KeyEvent keyEvent) {
        if (Regex.setTextColor(TextFields.PHONE, txtPhone)) {
            isKeyEnter(keyEvent, txtName);
        }
    }
}




















