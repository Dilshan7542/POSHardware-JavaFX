package lk.ijse.hardware.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.hardware.dto.OrdersDTO;
import lk.ijse.hardware.dto.PaymentDTO;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.CustomerService;
import lk.ijse.hardware.service.custom.ItemService;
import lk.ijse.hardware.service.custom.PurchaseOrderService;
import lk.ijse.hardware.service.custom.PurchasePaymentService;
import lk.ijse.hardware.service.custom.impl.CustomerServiceImpl;
import lk.ijse.hardware.service.custom.impl.ItemServiceImpl;
import lk.ijse.hardware.service.custom.impl.PurchaseOrderServiceImpl;
import lk.ijse.hardware.service.custom.impl.PurchasePaymentServiceImpl;
import lk.ijse.hardware.util.Converter;
import lk.ijse.hardware.util.ConverterList;
import lk.ijse.hardware.util.Navigation;
import lk.ijse.hardware.util.Regex;
import lk.ijse.hardware.util.enm.PaymentType;
import lk.ijse.hardware.util.enm.Route;
import lk.ijse.hardware.util.enm.TextFields;
import lk.ijse.hardware.view.tm.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentController {

    public AnchorPane pane;
    public Label lblSubtotal;
    public Label lblTax;
    public Label lblNetAmount;
    public JFXTextField txtNetDiscount;
    public Label lblNetDiscount;
    public Label lblLineDisc;
    public Label lblDiscountAmount;
    public Label lblFinalNetAmount;
    public Label lblDueAmount;
    public Label lblChange;
    public Label lblPaymentType;
    public JFXTextField txtLastIndex;
    public JFXTextField txtAmount;
    public TableView mainTable;
    public TableColumn colPayment;
    public TableColumn colAmount;
    public Label lblTime;
    public Label lblDate;
    public Label lblQtySold;
    public Label lblCashier;
    public Label lblCustomer;
    PaymentType paymentType = PaymentType.CASH;
    double netDiscount = 0.0;
    double lineDisc = 0.0;
    double subtotal = 0.0;
    double mainNetAmount = 0.0;
    double netAmount;
    double dueAmount;
    int qtySold = 0;
    ItemService itemBO=(ItemServiceImpl) ServiceFactory.getInstance().getService(ServiceType.ITEM);
    PurchaseOrderService purchaseOrderBO=(PurchaseOrderServiceImpl) ServiceFactory.getInstance().getService(ServiceType.PURCHASE_ORDER);
    PurchasePaymentService purchasePaymentBO=(PurchasePaymentServiceImpl) ServiceFactory.getInstance().getService(ServiceType.PURCHASE_PAYMENT);
    CustomerService customerBO=(CustomerServiceImpl) ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
    Converter converterTM = new Converter();
    ConverterList converterList = new ConverterList();
    ObservableList<PaymentTM> obMiniPaymentList = FXCollections.observableArrayList();


    public void initialize() {
        lblCashier.setText(LoginFormController.arthur);
        dateAndTime();
        addFields();
        addTable();

    }

    private void addTable() {
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        String[] names = {"Cash", "Card", "Online PaymentTM", "M-Cash"};
        PaymentTM[] payments = new PaymentTM[names.length];
        for (int i = 0; i < payments.length; i++) {
            payments[i] = new PaymentTM();
            payments[i].setPayment(names[i]);
            payments[i].setAmount(0.0);
            obMiniPaymentList.add(payments[i]);
        }
        mainTable.setItems(obMiniPaymentList);
        mainTable.refresh();
    }

    private void dateAndTime() {
        lblDate.setText(LocalDate.now() + "");
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime localTime = LocalTime.now();
            lblTime.setText(localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond());
        }), new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void addFields() {
        OrdersTM unpaidOrder = POSMainFormController.unpaidOrder;

        if (txtNetDiscount.getText().trim().isEmpty()) {
            txtNetDiscount.setText(unpaidOrder.getDiscount() + "");
        }

        try {
            final CustomerTM customer = converterTM.formCustomerTM(customerBO.searchCustomer(unpaidOrder.getCustomerId()));
            lblCustomer.setText(customer.getName());


            final List<OrderDetailTM> orderDetails = purchaseOrderBO.searchOrderDetail(unpaidOrder.getOrderId()).stream().map(o ->
                    converterTM.formOrderDetailTM(o)
            ).collect(Collectors.toList());
            for (OrderDetailTM o : orderDetails) {
                double total = o.getQty() * o.getUtilPrice();
                subtotal += total;
                lineDisc += total * o.getDiscount() / 100;
                qtySold += o.getQty();
            }
            mainNetAmount = subtotal;
            int discount = Integer.parseInt(txtNetDiscount.getText());
            lblNetDiscount.setText((subtotal * discount / 100) + "0");
            netDiscount = lineDisc + (subtotal * discount / 100);
            lblDiscountAmount.setText("-" + netDiscount + "0");
            netAmount = mainNetAmount - netDiscount;

            lblLineDisc.setText(lineDisc + "0");
            lblQtySold.setText("" + qtySold);
            lblSubtotal.setText(subtotal + "0");
            lblNetAmount.setText(mainNetAmount + "0");
            lblFinalNetAmount.setText(netAmount + "0");
            lblDueAmount.setText(String.format("%.2f", netAmount));
            lblChange.setText("0.0");


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void btnBackToPos(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Route.POS, pane);
    }

    public void btnCreditCard(MouseEvent mouseEvent) {
        paymentType = PaymentType.CARD;
        txtAmount.setText(null);
        lblPaymentType.setText("Card");
        txtLastIndex.requestFocus();
    }

    public void btnCash(MouseEvent mouseEvent) {
        paymentType = PaymentType.CASH;
        txtAmount.setText(null);
        lblPaymentType.setText("Cash");
        txtAmount.requestFocus();
    }

    public void btnOnlinePayment(MouseEvent mouseEvent) {
        paymentType = PaymentType.ONLINE_PAYMENT;
        lblPaymentType.setText("Online PaymentTM");
        txtAmount.requestFocus();
    }

    public void btnEasyCash(MouseEvent mouseEvent) {
        txtAmount.setText(null);
        lblPaymentType.setText("Easy Cash");
        txtAmount.requestFocus();
    }

    public void btnIPay(MouseEvent mouseEvent) {
        txtAmount.setText(null);
        lblPaymentType.setText("I-Pay");
        txtAmount.requestFocus();
    }

    public void btnMCash(MouseEvent mouseEvent) {
        paymentType = PaymentType.M_CASH;
        txtAmount.setText(null);
        lblPaymentType.setText("M-Cash");
        txtAmount.requestFocus();
    }

    public void keyTxtAmount(ActionEvent actionEvent) {
        if (!Regex.isTextFieldValid(TextFields.INTEGER_DECIMAL, txtAmount.getText())) {
            new Alert(Alert.AlertType.WARNING, "Please Enter Decimal Values").show();
            txtAmount.setText(null);

        } else {
            double amount = Double.parseDouble(txtAmount.getText());
            checkValidAmount(amount);
            txtAmount.setText(null);
        }

    }

    public void checkValidAmount(double amount) {
        switch (paymentType) {
            case CASH:
                obMiniPaymentList.get(0).setAmount(amount);
                break;
            case CARD:
                obMiniPaymentList.get(1).setAmount(amount);
                break;
            case ONLINE_PAYMENT:
                obMiniPaymentList.get(2).setAmount(amount);
                break;
            case M_CASH:
                obMiniPaymentList.get(3).setAmount(amount);
                break;

        }
        double allTotalCount = getTotalAmount();

        mainTable.refresh();
        double total = netAmount - allTotalCount;
        if (total <= 0) {
            dueAmount = 0.0;
            lblDueAmount.setText(dueAmount + "");
            lblChange.setText(String.format("%.2f", total));

        } else {
            dueAmount = total;
            lblDueAmount.setText(String.format("%.2f", total));
            lblChange.setText("0");
        }


    }

    private double getTotalAmount() {
        double allTotalCount = 0.0;
        for (PaymentTM p : obMiniPaymentList) {
            allTotalCount += p.getAmount();
        }
        return allTotalCount;
    }

    public void keyTxtDiscount(ActionEvent actionEvent) {
        if (Regex.setTextColor(TextFields.INTEGER, txtNetDiscount)) {
            int discount = Integer.parseInt(txtNetDiscount.getText());
            lblNetDiscount.setText((subtotal * discount / 100) + "0");
            netDiscount = lineDisc + (subtotal * discount / 100);
            lblDiscountAmount.setText(String.format("%.2f", netDiscount));
            netAmount = subtotal - netDiscount;
            lblFinalNetAmount.setText(netAmount + "0");
            dueAmount = netAmount - getTotalAmount();
            lblDueAmount.setText(String.format("%.2f", dueAmount));


        }
    }

    public void btnExact(MouseEvent mouseEvent) throws SQLException {
        double total = getTotalAmount() - netAmount;
        if (total >= 0) {
            try {
                int discount = Integer.parseInt(txtNetDiscount.getText());
                int lastIndex = 0;
                if (!txtLastIndex.getText().trim().isEmpty()) {
                    lastIndex = Integer.parseInt(txtLastIndex.getText());
                }
                final OrdersDTO ordersDTO = new OrdersDTO();
                ordersDTO.setPaymentDTO(
                        new PaymentDTO(
                                purchaseOrderBO.generateOrderID(),
                                obMiniPaymentList.get(0).getAmount(),
                                obMiniPaymentList.get(1).getAmount(),
                                lastIndex,
                                Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()),
                                POSMainFormController.unpaidOrder.getOrderId(),
                                POSMainFormController.unpaidOrder.getCustomerId()));

                ordersDTO.setOrderId(POSMainFormController.unpaidOrder.getOrderId());
                ordersDTO.setNetDiscount(discount);
                ordersDTO.setStatus(true);
                purchasePaymentBO.saveLastOrderAndPayment(ordersDTO);
                printBill();
                try {
                    Navigation.navigate(Route.POS, pane);
                    new Alert(Alert.AlertType.INFORMATION, "Change :" + total).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void printBill() {
        JasperDesign load = null;
        try {
            double totalSale = 0.0;
            double totalDisc = 0.0;
            double finalAmount = 0.0;
            double card = 0.0;
            double cash = 0.0;
            OrdersTM order = POSMainFormController.unpaidOrder;
            List<OrderDetailTM> orderDetails = purchaseOrderBO.searchOrderDetail(order.getOrderId()).stream().map(o ->
                    converterTM.formOrderDetailTM(o)).collect(Collectors.toList());
            for (OrderDetailTM o : orderDetails) {
                final ProductTM product = converterTM.formProductTM(itemBO.searchItem(o.getItemCode()));
                o.setDescription(product.getDescription());
                double total = o.getQty() * o.getUtilPrice();

                o.setAmount(total - total * o.getDiscount() / 100);
                totalSale += total;
            }
            cash = obMiniPaymentList.get(0).getAmount();
            card = obMiniPaymentList.get(1).getAmount();


            HashMap<String, Object> map = new HashMap<String, Object>();
            final CustomerTM customer = converterTM.formCustomerTM(customerBO.searchCustomer(order.getCustomerId()));
            int lastIndex = 0;
            if (Regex.isTextFieldValid(TextFields.INTEGER, txtLastIndex.getText())) {
                lastIndex = Integer.parseInt(txtLastIndex.getText());
            }

            map.put("customer", customer.getName());
            map.put("orderId", order.getOrderId());
            map.put("cashier", order.getCashier());
            map.put("tableNo", 1);
            map.put("subTotal", subtotal);
            map.put("discount", netDiscount);
            map.put("discountString", txtNetDiscount.getText() + "%");
            map.put("netAmount", netAmount - netDiscount);
            map.put("balance", getTotalAmount() - netAmount);
            map.put("card", card);
            map.put("cardIndex", lastIndex);
            map.put("cash", cash);


            load = JRXmlLoader.load(this.getClass().getResourceAsStream("/lk/ijse/hardware/report/bill.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(load);
            //    JasperReport compileReport =(JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/hardware/report/bill.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanCollectionDataSource(orderDetails));
            JasperViewer.viewReport(jasperPrint, false);
            //


//
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void keyTxtLastIndex(ActionEvent actionEvent) {

    }

    @FXML
    void btn100(MouseEvent event) {
        checkValidAmount(100);
    }

    @FXML
    void btn500(MouseEvent event) {
        checkValidAmount(500);

    }

    @FXML
    void btn1000(MouseEvent event) {
        checkValidAmount(1000);

    }

    @FXML
    void btn1500(MouseEvent event) {
        checkValidAmount(1500);

    }

    @FXML
    void btn2000(MouseEvent event) {
        checkValidAmount(2000);

    }


    @FXML
    void btn5000(MouseEvent event) {
        checkValidAmount(5000);

    }

    public void btnPrint(MouseEvent mouseEvent) {
        printBill();
    }
}
