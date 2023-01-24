package lk.ijse.hardware.controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurve;

public class MainFormController {
    public AnchorPane pane;
    public CubicCurve carve;

    public void exits(MouseEvent mouseEvent) {
        carve.setControlX2(89.0);
        carve.setControlY2(183.0);
    }

    public void moved(MouseEvent mouseEvent) {
        carve.setControlX2(mouseEvent.getX());
    }
}
