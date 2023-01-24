package lk.ijse.hardware.controller.dash;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.*;
import lk.ijse.hardware.service.custom.impl.CustomerServiceImpl;
import lk.ijse.hardware.service.custom.impl.PaymentServiceImpl;
import lk.ijse.hardware.service.custom.impl.PurchaseOrderServiceImpl;
import lk.ijse.hardware.service.custom.impl.QueryServiceImpl;
import lk.ijse.hardware.util.Converter;
import lk.ijse.hardware.util.ConverterList;
import lk.ijse.hardware.util.DateFormat;
import lk.ijse.hardware.util.Design;
import lk.ijse.hardware.view.tm.CustomerTM;
import lk.ijse.hardware.view.tm.OrdersTM;
import lk.ijse.hardware.view.tm.PaidOrderTM;
import lk.ijse.hardware.view.tm.PaymentTM;
import org.controlsfx.control.textfield.TextFields;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerListController {

    public TableView mainTable;
    public JFXButton btnSearch;
    CustomerTM customer;
    ObservableList<PaidOrderTM> obCustomerList = FXCollections.observableArrayList();
    CustomerService customerBO=(CustomerServiceImpl) ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
    QueryService queryBO=(QueryServiceImpl) ServiceFactory.getInstance().getService(ServiceType.QUERY);
    PurchaseOrderService purchaseOrderBO=(PurchaseOrderServiceImpl) ServiceFactory.getInstance().getService(ServiceType.PURCHASE_ORDER);
    PaymentService paymentBO=(PaymentServiceImpl) ServiceFactory.getInstance().getService(ServiceType.PAYMENT);
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private Label lblCustomerId;
    @FXML
    private Label lblName;
    @FXML
    private Label lblAddress;
    @FXML
    private JFXDatePicker dateStart;
    @FXML
    private JFXDatePicker dateEnd;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblPhone;
    @FXML
    private Label lblRegDate;
    @FXML
    private Label lblMonthlyTotal;
    @FXML
    private Label lblTopSaleDate;
    @FXML
    private Label lblTopSaleAmount;
    @FXML
    private Label lblLastBillDate;
    @FXML
    private Label lblLastBilAmount;
    @FXML
    private TableColumn<?, ?> colOrderId;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colTime;
    @FXML
    private TableColumn<?, ?> colCard;
    @FXML
    private TableColumn<?, ?> colCash;
    @FXML
    private TableColumn<?, ?> colTotal;
    @FXML
    private TableColumn<?, ?> colView;
    ConverterList converterList=new ConverterList();
    Converter converterTM=new Converter();
    public void initialize() {
        addFields();
        addTable();

    }

    private void addTable() {

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colCard.setCellValueFactory(new PropertyValueFactory<>("card"));
        colCash.setCellValueFactory(new PropertyValueFactory<>("cash"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colView.setCellValueFactory(new PropertyValueFactory<>("viewDetailBtn"));
        for (PaidOrderTM p : obCustomerList) {
            p.setViewDetailBtn(Design.viewDetailBtn());
            p.getViewDetailBtn().setOnMouseClicked(event -> {
                new Alert(Alert.AlertType.INFORMATION, "Not Find").show();

            });

        }
        mainTable.setItems(obCustomerList);


    }

    private void addFields() {
        try {
            final List<CustomerTM> allCustomer = converterList.formCustomerTMList(customerBO.getAllCustomer());
            String[] names = new String[allCustomer.size()];
            int count = 0;
            for (CustomerTM c : allCustomer) {
                names[count++] = c.getCustomerId();
            }

            TextFields.bindAutoCompletion(txtSearch, names);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        dateEnd = DateFormat.hideNextDate(dateEnd);
        dateStart = DateFormat.hideNextDate(dateStart);

    }


    public void btnSearch(javafx.event.ActionEvent actionEvent) {
        if (obCustomerList.size() > 0) {
            obCustomerList.clear();
        }
        if (customer == null) return;

        if (dateStart.getValue() != null && dateEnd.getValue() != null) {
            try {
                final List<PaidOrderTM> orderAndPayment = queryBO.getOrderAndPayment(Date.valueOf(dateStart.getValue()), Date.valueOf(dateEnd.getValue())).stream().map(query ->
                        new PaidOrderTM(
                                query.getOrderId(),
                                query.getCid(),
                                query.getDate().toLocalDate(),
                                query.getTime().toLocalTime(),
                                query.getCard(),
                                query.getCash(),
                                query.getCard() + query.getCash()
                        )
                ).collect(Collectors.toList());
                for (PaidOrderTM p : orderAndPayment) {
                    if (p.getCustomer().equals(customer.getId())) {
                        obCustomerList.add(p);
                    }
                }

                addTable();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnAdd(javafx.event.ActionEvent actionEvent) {
    }

    public void txtSearchBtn(javafx.event.ActionEvent actionEvent) {

        try {


            final CustomerTM customer = converterTM.formCustomerTM(customerBO.searchCustomerNIC(txtSearch.getText()));
            this.customer = customer;
            if (customer != null) {
                btnSearch.setDisable(false);
                lblCustomerId.setText(customer.getCustomerId());
                lblName.setText(customer.getName());
                lblEmail.setText(customer.getEmail());
                lblAddress.setText(customer.getAddress());
                lblPhone.setText(customer.getPhone());
                lblRegDate.setText(customer.getDate() + "");

                final List<OrdersTM> ordersTMS = converterList.formOrderTMList(purchaseOrderBO.searchCustomerOrder(customer.getId()));
                if (ordersTMS.size() <= 0) {
                    lblMonthlyTotal.setText("N/A");
                    lblTopSaleDate.setText("N/A");
                    lblTopSaleAmount.setText("N/A");
                    lblLastBillDate.setText("N/A");
                    lblLastBilAmount.setText("N/A");
                    return;
                }
                double monthlyTotal = 0.0;
                double topSale = 0.0;
                double lastBill = 0.0;
                LocalDate date = LocalDate.now();
                String topSaleDate = "";
                String lastBillDate = "";

                for (OrdersTM o : ordersTMS) {

                    final PaymentTM p = converterTM.formPaymentTM(paymentBO.searchPayment(o.getOrderId()));
                    if (p != null) {
                        if (date.getMonthValue() == o.getDate().getMonthValue()) {
                            monthlyTotal += p.getCard() + p.getCash();
                        }
                        double total = p.getCard() + p.getCash();
                        if (topSale < total) {
                            topSale = total;
                            topSaleDate = p.getDate() + "";
                        }

                    }

                }
                if (ordersTMS.size() != -1) {
                    String id = ordersTMS.get(ordersTMS.size() - 1).getOrderId();
                    final PaymentTM payment = converterTM.formPaymentTM(paymentBO.searchPayment(id));
                    if (payment != null) {
                        lastBill = payment.getCard() + payment.getCash();
                        lastBillDate = payment.getDate() + "";
                    }

                }
                lblMonthlyTotal.setText(monthlyTotal + "0");
                lblTopSaleDate.setText(topSaleDate);
                lblTopSaleAmount.setText(topSale + "");
                lblLastBillDate.setText(lastBillDate);
                lblLastBilAmount.setText(lastBill + "");


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
