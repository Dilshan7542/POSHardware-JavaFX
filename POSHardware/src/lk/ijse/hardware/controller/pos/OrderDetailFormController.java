package lk.ijse.hardware.controller.pos;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OrderDetailFormController {
    public AnchorPane onHoldPane;
    public AnchorPane paidPane;
    public AnchorPane orderHistoryPane;
    public void initialize() throws IOException {
        onHoldPane.getChildren().add(FXMLLoader.load(getClass().getResource("/lk/ijse/hardware/view/pos/UnpaidOrder.fxml")));
        paidPane.getChildren().add(FXMLLoader.load(getClass().getResource("/lk/ijse/hardware/view/pos/PaidOrder.fxml")));

    }
}
