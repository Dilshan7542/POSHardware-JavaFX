package lk.ijse.hardware.controller.pos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.CustomerService;
import lk.ijse.hardware.service.custom.PaymentService;
import lk.ijse.hardware.service.custom.PurchaseOrderService;
import lk.ijse.hardware.service.custom.impl.CustomerServiceImpl;
import lk.ijse.hardware.service.custom.impl.ItemServiceImpl;
import lk.ijse.hardware.service.custom.impl.PurchaseOrderServiceImpl;
import lk.ijse.hardware.util.Converter;
import lk.ijse.hardware.util.ConverterList;
import lk.ijse.hardware.util.Design;
import lk.ijse.hardware.view.tm.CustomerTM;
import lk.ijse.hardware.view.tm.OrdersTM;
import lk.ijse.hardware.view.tm.PaidOrderTM;
import lk.ijse.hardware.view.tm.PaymentTM;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PaidOrderController {

    public Label lblCard;
    public Label lblCash;
    public Label lblTotalSale;
    public TableColumn colOrId;
    public TableColumn colCustomer;
    public TableColumn colCashier;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colCard;
    public TableColumn colCash;

    public TableColumn colAmount;
    public TableColumn colViewDetail;
    public TableView mainTable;
    PaymentService paymentBO=(PaymentService) ServiceFactory.getInstance().getService(ServiceType.PAYMENT);
    PurchaseOrderService purchaseOrderBO=(PurchaseOrderServiceImpl) ServiceFactory.getInstance().getService(ServiceType.PURCHASE_ORDER);
    CustomerService customerBO=(CustomerServiceImpl) ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
    Converter converterTM = new Converter();
    ConverterList converterList = new ConverterList();

    ObservableList<PaidOrderTM> obPaidOrderTMList = FXCollections.observableArrayList();

    public void initialize() {
        addTable();
        addFields();
    }

    private void addFields() {
        try {
            final List<PaymentTM> allPaymentTM = paymentBO.getAllPayment().stream().map(p -> converterTM.formPaymentTM(p)).collect(Collectors.toList());
            double cash = 0.0d;
            double card = 0.0d;
            double total = 0.0d;
            for (PaymentTM p : allPaymentTM) {
                if (p.getDate().equals(LocalDate.now())) {
                    card += p.getCard();
                    cash += p.getCash();
                }
            }
            total = card + cash;
            lblCard.setText(String.format("%.2f", card));
            lblCash.setText(String.format("%.2f", cash));
            lblTotalSale.setText(String.format("%.2f", total));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addTable() {

        colOrId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colCashier.setCellValueFactory(new PropertyValueFactory<>("cashier"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colCard.setCellValueFactory(new PropertyValueFactory<>("card"));
        colCash.setCellValueFactory(new PropertyValueFactory<>("cash"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colViewDetail.setCellValueFactory(new PropertyValueFactory<>("viewDetailBtn"));


        try {
            List<OrdersTM> allOrdersTM = converterList.formOrderTMList(purchaseOrderBO.getAllOrder());

            final List<PaymentTM> allPaymentTM = paymentBO.getAllPayment().stream().map(p -> converterTM.formPaymentTM(p)).collect(Collectors.toList());
            for (OrdersTM o : allOrdersTM) {
                for (PaymentTM p : allPaymentTM) {
                    if (p.getOrderId().equals(o.getOrderId())) {
                        PaidOrderTM paid = new PaidOrderTM();
                        paid.setOrderId(o.getOrderId());
                        CustomerTM customer = converterTM.formCustomerTM(customerBO.searchCustomer(o.getCustomerId()));
                        paid.setCustomer(customer.getName());
                        paid.setCashier(o.getCashier());
                        paid.setDate(o.getDate());
                        paid.setTime(o.getTime());
                        paid.setCard(p.getCard());
                        paid.setCash(p.getCash());
                        paid.setAmount(p.getCash() + p.getCard());
                        paid.setViewDetailBtn(Design.viewDetailBtn());
                        paid.getViewDetailBtn().setOnMouseClicked(event -> {

                        });
                        obPaidOrderTMList.add(paid);
                    }
                }

            }
            mainTable.setItems(obPaidOrderTMList);
            mainTable.refresh();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
