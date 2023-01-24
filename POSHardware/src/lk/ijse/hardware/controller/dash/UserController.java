package lk.ijse.hardware.controller.dash;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import lk.ijse.hardware.dto.UserDTO;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.UserService;
import lk.ijse.hardware.service.custom.impl.ItemServiceImpl;
import lk.ijse.hardware.service.custom.impl.UserServiceImpl;
import lk.ijse.hardware.util.Design;
import lk.ijse.hardware.util.Regex;
import lk.ijse.hardware.util.enm.TextFields;
import lk.ijse.hardware.view.tm.UserTM;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class UserController {
    public JFXTextField txtId;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPwd;
    public JFXTextField txtEmpId;
    public JFXTextField txtRole;
    public JFXComboBox<String> cmbStatus;
    public TableView mainTable;
    public TableColumn colId;
    public TableColumn colUserName;
    public TableColumn colPwd;
    public TableColumn colEmpId;
    public TableColumn colRole;
    public TableColumn colStatus;
    public TableColumn colEdit;
    boolean isUpdate;
    UserService userBO=(UserServiceImpl) ServiceFactory.getInstance().getService(ServiceType.USER);
    ObservableList<UserTM> obUserListTM = FXCollections.observableArrayList();

    public void initialize() {
        addTable();
        addCmbBox();
    }

    private void addCmbBox() {
        cmbStatus.getItems().add("Show");
        cmbStatus.getItems().add("Hide");
    }

    private void addTable() {
        try {
            txtId.setText(userBO.generateUserID());
            txtId.setStyle("-fx-fill: #5454e3;");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (obUserListTM.size() > 0) {
            obUserListTM.clear();
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPwd.setCellValueFactory(new PropertyValueFactory<>("pwd"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEdit.setCellValueFactory(new PropertyValueFactory<>("editBtn"));
        try {
            final List<UserTM> allUserTM = userBO.getAllUser().stream().map(u -> new UserTM(
                    u.getUserId(),
                    u.getName(),
                    u.getPwd(),
                    u.getEmpId(),
                    u.getRole(),
                    u.isStatus()
            )).collect(Collectors.toList());
            for (UserTM u : allUserTM) {
                u.setEditBtn(Design.editBtn());
                u.getEditBtn().setOnMouseClicked(event -> {
                    for (UserTM btn : allUserTM) {
                        btn.getEditBtn().setDisable(true);
                        btn.getEditBtn().setOpacity(0.1);
                        u.getEditBtn().setOpacity(1);
                    }
                    txtId.setText(u.getUserId());
                    txtId.setStyle("-fx-fill: #5454e3;");
                    txtUserName.setText(u.getName());
                    txtPwd.setText(u.getPwd());
                    txtEmpId.setText(u.getEmpId());
                    txtRole.setText(u.getRole());
                    isUpdate = true;

                });
                obUserListTM.add(u);
            }
            mainTable.setItems(obUserListTM);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void onKeyName(KeyEvent keyEvent) {
        if (Regex.setTextColor(TextFields.NAME, txtUserName)) {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                txtPwd.requestFocus();

            }
        }
    }


    public void onKeyPwd(KeyEvent keyEvent) {
        if (Regex.isTextFieldValid(TextFields.PWD, txtPwd.getText())) {
            txtPwd.setFocusColor(Paint.valueOf("Green"));
            if (keyEvent.getCode() == KeyCode.ENTER) {
                txtEmpId.requestFocus();

            }
        } else {
            txtPwd.setFocusColor(Paint.valueOf("Red"));
        }
    }

    public void onKeyEmp(KeyEvent keyEvent) {
        if (Regex.setTextColor(TextFields.EMP_ID, txtEmpId)) {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                txtRole.requestFocus();

            }
        }

    }

    public void onKeyRole(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME, txtRole);

    }


    public void btnAdd(ActionEvent actionEvent) {
        if (validAll()) {
            boolean status = false;
            if (cmbStatus.getValue() != null) {
                if (cmbStatus.getValue().startsWith("Hide")) {
                    status = true;
                }

            }
            if (!isUpdate) {
                try {
                    final boolean save = userBO.saveUser(new UserDTO(
                            txtId.getText(),
                            txtUserName.getText(),
                            txtPwd.getText(),
                            txtEmpId.getText(),
                            txtRole.getText(),
                            status
                    ));
                    if (save) {
                        new Alert(Alert.AlertType.INFORMATION, "Done").show();
                        addTable();
                        clearTextField();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    boolean save = userBO.updateUser(new UserDTO(
                            txtId.getText(),
                            txtUserName.getText(),
                            txtPwd.getText(),
                            txtEmpId.getText(),
                            txtRole.getText(),
                            status
                    ));
                    if (save) {
                        new Alert(Alert.AlertType.INFORMATION, "Updated").show();
                        mainTable.refresh();
                        addTable();
                        clearTextField();
                        for (UserTM btn : obUserListTM) {
                            btn.getEditBtn().setDisable(false);
                            btn.getEditBtn().setOpacity(1);
                            isUpdate = false;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        } else {
            new Alert(Alert.AlertType.WARNING, "Please Fill The Valid Values").show();
        }

    }

    private void clearTextField() {
        txtUserName.setText("");
        txtPwd.setText("");
        txtEmpId.setText("");
        txtRole.setText("");
    }

    private boolean validAll() {
        if (!Regex.isTextFieldValid(TextFields.NAME, txtUserName.getText())) {
            return false;
        } else {
            if (!Regex.isTextFieldValid(TextFields.PWD, txtPwd.getText())) {
                return false;
            } else {
                if (!Regex.isTextFieldValid(TextFields.EMP_ID, txtEmpId.getText())) {
                    return false;
                } else {
                    return Regex.isTextFieldValid(TextFields.NAME, txtRole.getText());
                }
            }
        }
    }

}

