package lk.ijse.hardware.controller.find;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hardware.controller.dash.GRNController;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.PurchaseGrnService;
import lk.ijse.hardware.service.custom.impl.ItemServiceImpl;
import lk.ijse.hardware.service.custom.impl.PurchaseGrnServiceImpl;
import lk.ijse.hardware.util.Converter;
import lk.ijse.hardware.view.tm.GrnTM;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class OldGRNController {

    ObservableList<GrnTM> obOrderList = FXCollections.observableArrayList();
    Converter converterTM = new Converter();
    PurchaseGrnService purchaseGrnBO=(PurchaseGrnServiceImpl) ServiceFactory.getInstance().getService(ServiceType.PURCHASE_GRN);
    @FXML
    private TableView<GrnTM> mainTable;
    @FXML
    private TableColumn<?, ?> colGRN;
    @FXML
    private TableColumn<?, ?> colPOid;
    @FXML
    private TableColumn<?, ?> coArthur;
    @FXML
    private TableColumn<?, ?> colInvoice;
    @FXML
    private TableColumn<?, ?> colSuppler;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colTime;
    @FXML
    private TableColumn<?, ?> colView;

    public void initialize() {

        addTable();


    }

    private void addTable() {

        colGRN.setCellValueFactory(new PropertyValueFactory<>("grnId"));
        colPOid.setCellValueFactory(new PropertyValueFactory<>("poId"));
        coArthur.setCellValueFactory(new PropertyValueFactory<>("arthur"));
        colInvoice.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        colSuppler.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colView.setCellValueFactory(new PropertyValueFactory<>("btnView"));

        try {
            final List<GrnTM> allGrnListTM = purchaseGrnBO.getAllGrn().stream().map(g -> converterTM.formGrnTM(g)).collect(Collectors.toList());
            for (GrnTM grnTM : allGrnListTM) {
                Button btnView = new Button("View");
                btnView.setStyle("-fx-background-color: green;-fx-fill: white;-fx-font-weight: bold");
                btnView.setCursor(Cursor.HAND);
                grnTM.setBtnView(btnView);
                grnTM.getBtnView().setOnAction(event -> {
                    GRNController.getOrderValue(grnTM);

                });
                obOrderList.add(grnTM);
            }
            mainTable.setItems(obOrderList);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
