package lk.ijse.hardware.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lk.ijse.hardware.dto.UserDTO;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.UserService;
import lk.ijse.hardware.service.custom.impl.UserServiceImpl;
import lk.ijse.hardware.util.Sound;
import lk.ijse.hardware.view.tm.UserTM;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {


    public static String arthur;
    public BorderPane pane;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPwd;
    public Label lblUserError;
    public Label lblPwdError;
    UserService userBO=(UserServiceImpl) ServiceFactory.getInstance().getService(ServiceType.USER);

    public void btnLogin(ActionEvent actionEvent) {
        addSound();
        login();
    }

    public void userBtn(ActionEvent actionEvent) {
        txtPwd.requestFocus();
    }

    public void pwdBtn(ActionEvent actionEvent) {
        login();
    }

    public void login() {
        addSound();
// v
        try {
            final UserDTO user = userBO.searchUser(txtUserName.getText());

            final UserTM userTM = new UserTM(
                    user.getUserId(),
                    user.getName(),
                    user.getPwd(),
                    user.getEmpId(),
                    user.getRole(),
                    user.isStatus()
            );
            if (userTM != null) {
                if (userTM.getPwd().equals(txtPwd.getText())) {
                    Stage window = (Stage) pane.getScene().getWindow();
                    window.close();
                    Scene scene = null;
                    if (userTM.isStatus()) {
                        arthur = userTM.getName();
                        scene = new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hardware/view/MainDashBoardForm.fxml")));

                    } else {
                        arthur = userTM.getName();
                        scene = new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hardware/view/POSMainForm.fxml")));
                    }

                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Main Dashboard");
                    stage.getIcons().add(new Image("lk/ijse/hardware/assets/logo/store.png"));
                    stage.setMaximized(true);
                    stage.show();


                } else {
                    lblPwdError.setText("Invalid Password");
                }

            } else {
                lblUserError.setText("Invalid UserTM Name");
            }


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }

    private void addSound() {
        Sound.infoSound();
    }
}
