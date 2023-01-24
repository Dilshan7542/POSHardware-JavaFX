package lk.ijse.hardware.controller.pos;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CustomerDetailsController {

    public AnchorPane customerPane;
    public AnchorPane customerListPane;


    public void initialize() throws IOException {
        customerPane.getChildren().add(FXMLLoader.load(getClass().getResource("/lk/ijse/hardware/view/dashboard/CustomerForm.fxml")));
        customerListPane.getChildren().add(FXMLLoader.load(getClass().getResource("/lk/ijse/hardware/view/dashboard/CustomerList.fxml")));
    }

}
