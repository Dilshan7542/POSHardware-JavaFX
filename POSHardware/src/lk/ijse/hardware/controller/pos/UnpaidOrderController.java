package lk.ijse.hardware.controller.pos;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hardware.controller.POSMainFormController;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.PaymentService;
import lk.ijse.hardware.service.custom.PurchaseOrderService;
import lk.ijse.hardware.service.custom.impl.ItemServiceImpl;
import lk.ijse.hardware.service.custom.impl.PaymentServiceImpl;
import lk.ijse.hardware.util.Converter;
import lk.ijse.hardware.util.ConverterList;
import lk.ijse.hardware.util.Design;
import lk.ijse.hardware.util.Navigation;
import lk.ijse.hardware.util.enm.Route;
import lk.ijse.hardware.view.tm.OrderDetailTM;
import lk.ijse.hardware.view.tm.OrdersTM;
import lk.ijse.hardware.view.tm.PaymentTM;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class UnpaidOrderController {
    public TableColumn colId;
    public TableColumn colCustomer;
    public TableColumn colCashier;
    public TableColumn colDate;
    public TableColumn colTIme;
    public TableColumn colTotalPrice;
    public TableColumn colAddOrder;
    public TableView mainTable;
    public JFXTextField txtSearch;
    PurchaseOrderService purchaseOrderBO=(PurchaseOrderService) ServiceFactory.getInstance().getService(ServiceType.PURCHASE_ORDER);
    PaymentService paymentBO=(PaymentServiceImpl) ServiceFactory.getInstance().getService(ServiceType.PAYMENT);
    ConverterList converterList = new ConverterList();
    Converter converterTM = new Converter();
    ObservableList<OrdersTM> obOrdersTMList = FXCollections.observableArrayList();

    public void initialize() {
        addTable();
    }

    private void addTable() {
        if (obOrdersTMList.size() > 0) {
            obOrdersTMList.clear();
        }
        try {


            colId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            colCustomer.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colCashier.setCellValueFactory(new PropertyValueFactory<>("cashier"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colTIme.setCellValueFactory(new PropertyValueFactory<>("time"));
            colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
            colAddOrder.setCellValueFactory(new PropertyValueFactory<>("addBtn"));


            List<OrdersTM> allOrdersTM = converterList.formOrderTMList(purchaseOrderBO.getAllOrder());
            for (OrdersTM o : allOrdersTM) {
                final List<OrderDetailTM> orderDetailTMS = purchaseOrderBO.searchOrderDetail(o.getOrderId()).stream().map(l ->
                        converterTM.formOrderDetailTM(l)
                ).collect(Collectors.toList());
                double lastAmount = 0.0;
                for (OrderDetailTM list : orderDetailTMS) {
                    double total = list.getQty() * list.getUtilPrice();
                    lastAmount += total - total * list.getDiscount() / 100;

                }
                o.setTotalPrice(lastAmount);
                o.setAddBtn(Design.getSelectButton());
                o.getAddBtn().setOnMouseClicked(event -> {
                    POSMainFormController.getAllFields(Route.ORDER, o);
                    Navigation.navigateClose();

                });
                obOrdersTMList.add(o);
            }
            isPayOrNot();
            mainTable.setItems(obOrdersTMList);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void isPayOrNot() throws SQLException, ClassNotFoundException {
        List<PaymentTM> allPaymentTM = paymentBO.getAllPayment().stream().map(p ->
                converterTM.formPaymentTM(p)
        ).collect(Collectors.toList());
        ObservableList<OrdersTM> tempOb = FXCollections.observableArrayList();
        for (OrdersTM or : obOrdersTMList) {
            tempOb.add(or);
        }
        for (OrdersTM or : tempOb) {
            for (PaymentTM p : allPaymentTM) {
                if (or.getOrderId().equals(p.getOrderId())) {
                    obOrdersTMList.remove(or);
                    break;
                }

            }
        }
        tempOb = null;
        allPaymentTM = null;
        System.gc();
    }


}
