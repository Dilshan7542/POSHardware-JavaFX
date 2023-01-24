package lk.ijse.hardware.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import lk.ijse.hardware.controller.dash.CustomerController;
import lk.ijse.hardware.dto.OrdersDTO;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.*;
import lk.ijse.hardware.service.custom.impl.CategoryServiceImpl;
import lk.ijse.hardware.service.custom.impl.CustomerServiceImpl;
import lk.ijse.hardware.service.custom.impl.ItemServiceImpl;
import lk.ijse.hardware.service.custom.impl.PurchaseOrderServiceImpl;
import lk.ijse.hardware.util.*;
import lk.ijse.hardware.util.enm.Route;
import lk.ijse.hardware.util.enm.TextFields;
import lk.ijse.hardware.view.tm.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class POSMainFormController {
    static String backProduct;
    static CustomerTM customer;
    static POSMainFormController posMain;
    static OrdersTM unpaidOrder;
    public Pane btnBackCategory;
    public Pane btnHomeCategory;
    public TableView mainTable;
    public TableColumn<OrderDetailTM, String> colItem;
    public TableColumn<OrderDetailTM, String> colDesc;
    public TableColumn<OrderDetailTM, Double> colPrice;
    public TableColumn<OrderDetailTM, Integer> colDis;
    public TableColumn<OrderDetailTM, Integer> colQty;
    public TableColumn colAmount;
    public Label lblTotal;
    public GridPane posMainGridPane;
    public Label lblAllDiscount;
    public JFXTextField txtAnimationDiscount;
    public Label lblSubTotal;
    public Label lblDiscountDisplay;
    public StackPane discountNumPadPane;
    public StackPane orderTypePane;
    public Label lblOrderType;
    public Label lblCustomer;
    public Label lblDate;
    public Label lblTime;
    public StackPane customerFadePane;
    public JFXTextField txtSearchCustomer;
    public TableColumn colAddBtn;
    public TableColumn colPhone;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colName;
    public TableColumn colCustomerId;
    public TableView<CustomerTM> mainCustomerTable;
    public Label lblCashier;
    public Label lblOrderCount;
    public Label lblOrderId;
    public AnchorPane btnRest;
    public AnchorPane btnHold;
    public AnchorPane pane;
    public GridPane categoryGridPane;
    public AnchorPane panePayment;
    public TableColumn colDelete;
    int totalDiscount;
    ArrayList<AnchorPane> categoryPane = new ArrayList<>();
    ArrayList<AnchorPane> paneItem = new ArrayList<>();
    List<CategoryTM> allCategory;
    ObservableList<OrderDetailTM> obOrderList = FXCollections.observableArrayList();
    PurchaseOrderService purchaseOrderBO=(PurchaseOrderServiceImpl) ServiceFactory.getInstance().getService(ServiceType.PURCHASE_ORDER);
    CustomerService customerBO=(CustomerServiceImpl) ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
    ItemService itemBO=(ItemServiceImpl) ServiceFactory.getInstance().getService(ServiceType.ITEM);
    CategoryService categoryBO=(CategoryServiceImpl) ServiceFactory.getInstance().getService(ServiceType.CATEGORY);
    Converter converterTM = new Converter();
    ConverterList converterList = new ConverterList();


    ObservableList<CustomerTM> obCustomersList = FXCollections.observableArrayList();

    public static void getAllFields(Route route, Object field) {
        switch (route) {
            case CUSTOMER:
                POSMainFormController.customer = (CustomerTM) field;
                posMain.addFields();
                break;
            case ORDER:
                POSMainFormController.unpaidOrder = (OrdersTM) field;
                posMain.addFields();

                break;


        }
    }

    public void initialize() {
        lblCashier.setText(LoginFormController.arthur);
        addTime();
        posMain = this;
        generateNewId();
        addCategory();
        categoryAction();
        addCustomerTable();
        countFields();


    }

    private void addTime() {
        lblDate.setText(LocalDate.now() + "");
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime localTime = LocalTime.now();
            lblTime.setText(localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond());
        }), new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void generateNewId() {
        try {
            unpaidOrder = new OrdersTM();
            unpaidOrder.setOrderId(purchaseOrderBO.generateOrderID());
            lblOrderId.setText(unpaidOrder.getOrderId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void countFields() {
        int count = 0;
        try {
            List<OrdersTM> allOrder = converterList.formOrderTMList(purchaseOrderBO.getAllOrder());
            for (OrdersTM o : allOrder) {
                if (!o.isStatus()) {
                    count++;
                }
            }
            lblOrderCount.setText(count + "");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addFields() {
        if (customer != null) {
            lblCustomer.setText(customer.getName());
        }
        if (unpaidOrder != null) {
            btnHold.setDisable(true);
            clearCategory();
            addCategory();
            categoryAction();
            lblOrderId.setText(unpaidOrder.getOrderId());
            if (obOrderList.size() > 0) {
                obOrderList.clear();
            }

            try {
                List<OrderDetailTM> orderDetails = purchaseOrderBO.searchOrderDetail(unpaidOrder.getOrderId()).stream().map(or ->
                        converterTM.formOrderDetailTM(or)
                ).collect(Collectors.toList());
                for (OrderDetailTM o : orderDetails) {
                    final ProductTM product = converterTM.formProductTM(itemBO.searchItem(o.getItemCode()));
                    o.setDescription(product.getDescription());
                    o.setAmount((o.getQty() * o.getUtilPrice()));
                    o.setAmount(o.getAmount() - o.getAmount() * o.getDiscount() / 100);
                    o.setRemoveBtn(Design.btnRemove());
                    o.getRemoveBtn().setOpacity(0.1);

                    obOrderList.add(o);
                }
                POSMainFormController.customer = converterTM.formCustomerTM(customerBO.searchCustomer(unpaidOrder.getCustomerId()));
                lblCustomer.setText(POSMainFormController.customer.getName());
                totalDiscount = unpaidOrder.getDiscount();
                setDiscountAllFields(totalDiscount);
                addTable();
                panePayment.setDisable(false);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void addCustomerTable() {
        try {

            clearObOrderList();

            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            colAddBtn.setCellValueFactory(new PropertyValueFactory<>("addBtn"));
            List<CustomerTM> allCustomer = converterList.formCustomerTMList(customerBO.getAllCustomer());
            for (CustomerTM c : allCustomer) {
                c.setAddBtn(Design.getSelectButton());
                c.getAddBtn().setOnMouseClicked(event -> {
                    customerSelectField();

                });
                obCustomersList.add(c);
            }

            mainCustomerTable.setItems(obCustomersList);
            mainCustomerTable.refresh();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void backgroundIconDisable() {
        btnBackCategory.setDisable(true);
        btnBackCategory.setOpacity(0.20);
        btnHomeCategory.setDisable(true);
        btnHomeCategory.setOpacity(0.20);

    }

    public void addTableValue() {
        List<OrderDetailTM> orderDetails = null;
        try {
            orderDetails = purchaseOrderBO.searchOrderDetail(unpaidOrder.getOrderId()).stream().map(or ->
                    converterTM.formOrderDetailTM(or)
            ).collect(Collectors.toList());

            for (OrderDetailTM p : obOrderList) {
                ProductTM product = converterTM.formProductTM(itemBO.searchItem(p.getItemCode()));
                p.setDescription(product.getDescription());
                p.setDiscount(product.getDiscount());
                p.setOrderId(lblOrderId.getText());
                p.setAmount((p.getQty() * p.getUtilPrice()));
                p.setAmount(p.getAmount() - p.getAmount() * p.getDiscount() / 100);
                p.setRemoveBtn(Design.btnRemove());
                p.getRemoveBtn().setOnMouseClicked(event -> {
                    try {
                        final List<OrderDetailTM> orderDetails1 = purchaseOrderBO.searchOrderDetail(unpaidOrder.getOrderId()).stream().map(or ->
                                converterTM.formOrderDetailTM(or)
                        ).collect(Collectors.toList());
                        for (OrderDetailTM o : orderDetails1) {
                            if (o.getItemCode().equals(p.getItemCode())) {
                                if (o.getQty() < p.getQty()) {
                                    p.setQty(p.getQty() - 1);
                                    p.setAmount((p.getQty() * p.getUtilPrice()));
                                    p.setAmount(p.getAmount() - p.getAmount() * p.getDiscount() / 100);

                                    mainTable.refresh();
                                    return;
                                }
                                p.getRemoveBtn().setDisable(true);
                                p.getRemoveBtn().setOpacity(0.1);
                                return;
                            }
                        }
                        obOrderList.remove(p);
                        if (obOrderList.size() <= 0) {
                            btnHold.setDisable(true);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                });
                for (OrderDetailTM o : orderDetails) {
                    if (o.getItemCode().equals(p.getItemCode())) {
                        if (o.getQty() < p.getQty()) {
                            p.getRemoveBtn().setDisable(false);
                            p.getRemoveBtn().setOpacity(1);
                        } else {
                            p.getRemoveBtn().setDisable(true);
                            p.getRemoveBtn().setOpacity(0.1);

                        }


                    }
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addTable() throws SQLException, ClassNotFoundException {
        colItem.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("utilPrice"));
        colDis.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));


        colDis.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colDis.setOnEditCommit(event -> {
            btnHold.setDisable(false);
            panePayment.setDisable(true);
            OrderDetailTM orderDetail = event.getRowValue();
            orderDetail.setDiscount(event.getNewValue());


            if (orderDetail.getDiscount() < 0 | orderDetail.getDiscount() >= 101) {
                orderDetail.setAmount(orderDetail.getQty() * orderDetail.getUtilPrice());
                orderDetail.setDiscount(0);
            } else {
                double total = orderDetail.getQty() * orderDetail.getUtilPrice();
                orderDetail.setAmount(total - (total * event.getNewValue() / 100));
            }

            getTotalPrice();

        });
        colQty.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colQty.setOnEditCommit(event -> {
            panePayment.setDisable(true);
            btnHold.setDisable(false);
            OrderDetailTM orderDetail = event.getRowValue();
            orderDetail.setQty(event.getNewValue());
            if (orderDetail.getQty() < 1) {
                orderDetail.setQty(1);
            }
            double total = orderDetail.getQty() * orderDetail.getUtilPrice();
            orderDetail.setAmount(total - (total * orderDetail.getDiscount() / 100));
            getTotalPrice();
            addTableValue();

        });

        getTotalPrice();
        mainTable.getFocusModel().focus(obOrderList.size() - 1, colQty);
        mainTable.requestFocus();
        mainTable.setItems(obOrderList);
        mainTable.refresh();
    }

    private double getTotalPrice() {
        double totalPrice = 0.0;
        for (OrderDetailTM ord : obOrderList) {
            totalPrice += ord.getAmount();
        }
        mainTable.refresh();
        lblTotal.setText(String.format("%.2f", totalPrice));
        lblSubTotal.setText(String.format("%.2f", totalPrice));
        return totalPrice;
    }

    private void categoryAction() {
        for (AnchorPane p : categoryPane) {
            p.setOnMouseClicked(event -> {

                ObservableList<Node> children = p.getChildren();
                Node node = children.get(0);
                Label lbl = (Label) node;
                String text = lbl.getText();
                backProduct = text;
                findProduct(text);
                btnBackCategory.setDisable(false);
                btnBackCategory.setOpacity(1);
                btnHomeCategory.setDisable(false);
                btnHomeCategory.setOpacity(1);

            });
        }
        for (AnchorPane p : paneItem) {
            p.setOnMouseClicked(event -> {
                ObservableList<Node> children = p.getChildren();
                Node node = children.get(0);
                Label lbl = (Label) node;
                btnHold.setDisable(false);
                try {
                    ProductTM product = converterTM.formProductTM(itemBO.searchItem(lbl.getText()));
                    OrderDetailTM orderDetail = new OrderDetailTM(
                            product.getItemCode(),
                            product.getDescription(),
                            product.getUtilPrice(),
                            product.getDiscount(), 1, 0);

                    ObservableList<OrderDetailTM> obTempList = tempObProductList();
                    for (OrderDetailTM item : obTempList) {
                        if (item.getItemCode().equals(product.getItemCode())) {
                            item.setQty(item.getQty() + 1);
                            addTableValue();
                            addTable();
                            return;
                        }
                    }
                    obOrderList.add(orderDetail);
                    addTableValue();
                    addTable();
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    panePayment.setDisable(true);
                }
            });
        }
    }

    private ObservableList<OrderDetailTM> tempObProductList() {
        ObservableList<OrderDetailTM> obTempList = FXCollections.observableArrayList();
        for (OrderDetailTM ord : obOrderList) {
            obTempList.add(ord);
        }
        return obTempList;
    }

    private void findProduct(String field) {
        ArrayList<AnchorPane> tempPane = new ArrayList<>();

        try {
            List<ProductTM> allProduct = converterList.formProductTMList(itemBO.getAllItem());

            int count = 0;
            for (CategoryTM c : allCategory) {
                if (c.getParent() != null && c.getParent().equals(field)) {
                    AnchorPane newPane = createNewPane(c.getName());
                    newPane.setStyle("-fx-background-color :#95a5a6;-fx-background-radius: 15px;-fx-border-radius: 15px;-fx-border-width: 2px;-fx-border-color: white");
                    tempPane.add(newPane);
                    categoryPane.add(newPane);
                    count++;
                }
            }
            for (ProductTM p : allProduct) {
                if (categoryBO.searchCategory(p.getCategoryId()).getName().equals(field)) {
                    AnchorPane newPane = createNewPane(p.getDescription());
                    //    newPane.setStyle("-fx-background-color : linear-gradient(from 25% 25% tm 100% 100%,dodgerblue, lightgray);-fx-background-radius: 15px;-fx-border-radius: 15px;-fx-border-width: 2px;-fx-border-color: white");
                    newPane.setStyle("-fx-background-color :  MediumSeaGreen;-fx-background-radius: 15px;-fx-border-radius: 15px;-fx-border-width: 2px;-fx-border-color: white");
                    paneItem.add(newPane);
                    tempPane.add(newPane);
                    count++;
                }
            }
            addCategoryGridPane(tempPane, count);
            categoryAction();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCategory() {
        backgroundIconDisable();

        try {

            this.allCategory = converterList.formCategoryTMList(categoryBO.getAllCategory());
            allCategory.remove(0);// REMOVE NO PARENT
            int count = 0;
            for (CategoryTM c : allCategory) {
                if (c.getParent() == null || c.getParent().equals("No Parent")) {
                    AnchorPane newPane = createNewPane(c.getName());
                    //   newPane.setStyle("-fx-background-color : linear-gradient(from 25% 25% tm 100% 100%,dodgerblue, #88f388);-fx-background-radius: 15px;-fx-border-radius: 15px;-fx-border-width: 2px;-fx-border-color: white");
                    newPane.setStyle("-fx-background-color :#576574;-fx-background-radius: 15px;-fx-border-radius: 15px;-fx-border-width: 2px;-fx-border-color: white");
                    final Node node = newPane.getChildren().get(0);
                    Label lbl = (Label) node;
                    lbl.setStyle("-fx-font-size: 22px;-fx-font-weight: bold;-fx-text-fill: white");
                    categoryPane.add(newPane);

                    count++;
                }

            }
            addCategoryGridPane(categoryPane, count);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private AnchorPane createNewPane(String name) {
        Label lbl = new Label(name);
        lbl.setPrefSize(200, 200);
        lbl.setPadding(new Insets(10));
        lbl.setWrapText(true);
        AnchorPane.setLeftAnchor(lbl, 0.0);
        AnchorPane.setRightAnchor(lbl, 0.0);
        AnchorPane.setTopAnchor(lbl, 0.0);
        AnchorPane.setBottomAnchor(lbl, 0.0);
        lbl.setAlignment(Pos.TOP_CENTER);
        lbl.setStyle("-fx-font-size: 22px;-fx-font-weight: bold;-fx-text-fill: #020202");
        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(lbl);
        pane.setMinWidth(150);
        pane.setMaxWidth(200);
        pane.setMinHeight(100);
        pane.setMaxHeight(120);
        pane.setCursor(Cursor.HAND);
        return pane;
    }

    private void addCategoryGridPane(ArrayList<AnchorPane> categoryPane, int length) {
        categoryGridPane.getChildren().clear();
        categoryGridPane.getRowConstraints().removeAll();
        int number = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < 4; j++) {
                if (categoryPane.size() > number)
                    categoryGridPane.add(categoryPane.get(number++), j, i);
            }
            if (i >= 3) {
                categoryGridPane.addRow(1);
            }
        }
    }

    public void btnSearch(ActionEvent actionEvent) { // NOT READY======================================================

    }

    private String findParent(String backProduct) {
        for (CategoryTM c : allCategory) {
            if (c.getName().equals(backProduct)) {
                return c.getParent();

            }
        }
        return backProduct;
    }

    private ArrayList<String> getCategoryMainList() {
        ArrayList<String> mainCategory = new ArrayList<>();
        for (CategoryTM c : allCategory) {
            if (c.getParent() == null || c.getParent().equals("No Parent")) {
                mainCategory.add(c.getName());
            }
        }
        return mainCategory;
    }

    public void btnDashboard(MouseEvent mouseEvent) throws IOException, InterruptedException {
        Navigation.navigate(Route.DASHBOARD, pane);
    }

    public void btnBackCategory(MouseEvent mouseEvent) {
        categoryGridPane.getChildren().clear();
        categoryGridPane.getRowConstraints().removeAll();
        for (String s : getCategoryMainList()) {
            if (s.equals(backProduct)) {
                categoryPane.clear();
                paneItem.clear();
                addCategory();
                categoryAction();
                return;
            }
        }
        backProduct = findParent(backProduct);
        findProduct(backProduct);
        categoryAction();
    }

    public void btnHomeCategory(MouseEvent mouseEvent) {
        clearCategory();
        addCategory();
        categoryAction();

    }

    public void clearCategory() {
        categoryGridPane.getChildren().clear();
        categoryGridPane.getRowConstraints().removeAll();
        categoryPane.clear();
        paneItem.clear();
    }

    public void btnOrders(MouseEvent mouseEvent) throws IOException {
        Navigation.alertWindow(Route.ORDER);

    }

    public void btnOrderType(MouseEvent mouseEvent) {
        onOfTempPane(orderTypePane);
    }

    public void btnCustomer(MouseEvent mouseEvent) throws IOException {
        posMain = this;
        CustomerController.route = Route.POS;
        Navigation.alertWindow(Route.CUSTOMER);

    }

    public void btnPayment(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Route.PAYMENT, pane);
    }

    public void btnAllDiscount(MouseEvent mouseEvent) {
        if (obOrderList.size() > 0) {
            txtAnimationDiscount.setText(null);
            onOfTempPane(discountNumPadPane);

        }
    }

    public void btnBackFlush(MouseEvent mouseEvent) {
        StringBuffer text = new StringBuffer(getTxtAnimation());
        txtAnimationDiscount.setText(text.deleteCharAt(getTxtAnimation().length() - 1) + "");
    }

    public void btn9(MouseEvent mouseEvent) {
        txtAnimationDiscount.setText(getTxtAnimation() + "9");
    }

    public void btn8(MouseEvent mouseEvent) {
        txtAnimationDiscount.setText(getTxtAnimation() + "8");
    }

    public void btn7(MouseEvent mouseEvent) {
        txtAnimationDiscount.setText(getTxtAnimation() + "7");
    }

    public void btn6(MouseEvent mouseEvent) {
        txtAnimationDiscount.setText(getTxtAnimation() + "6");
    }

    public void btn5(MouseEvent mouseEvent) {
        txtAnimationDiscount.setText(getTxtAnimation() + "5");
    }

    public void btn4(MouseEvent mouseEvent) {
        txtAnimationDiscount.setText(getTxtAnimation() + "4");
    }

    public void btn3(MouseEvent mouseEvent) {
        txtAnimationDiscount.setText(getTxtAnimation() + "3");
    }

    public void btn2(MouseEvent mouseEvent) {
        txtAnimationDiscount.setText(getTxtAnimation() + "2");
    }

    public void btn1(MouseEvent mouseEvent) {
        txtAnimationDiscount.setText(getTxtAnimation() + "1");
    }

    public void btn0(MouseEvent mouseEvent) {
        txtAnimationDiscount.setText(getTxtAnimation() + "0");
    }

    public void btnOk(MouseEvent mouseEvent) {
        onOfTempPane(discountNumPadPane);
        if (Regex.isTextFieldValid(TextFields.INTEGER, txtAnimationDiscount.getText())) {
            int discount = Integer.parseInt(txtAnimationDiscount.getText());
            setDiscountAllFields(discount);
            btnHold.setDisable(false);
        }
    }

    private void setDiscountAllFields(int discount) {
        if (discount > 0 && discount <= 100) {
            double finalPrice = (getTotalPrice() * discount / 100);
            lblDiscountDisplay.setText(discount + "%");
            lblAllDiscount.setText(String.format("-%.2f", finalPrice));
            totalDiscount = discount;


            lblTotal.setText(String.format("%.2f", (getTotalPrice() - finalPrice)));
            txtAnimationDiscount.setText("0");
        } else {
            lblDiscountDisplay.setText(null);
            lblAllDiscount.setText("0.0");
            lblTotal.setText(getTotalPrice() + "");
            lblSubTotal.setText(getTotalPrice() + "");
        }
    }

    public String getTxtAnimation() {
        if (txtAnimationDiscount.getText() == null) {
            return "";
        }
        return txtAnimationDiscount.getText();
    }

    public void onOfTempPane(StackPane pane) {
        if (pane.isVisible()) {
            Animations.fadeOut(pane);
            pane.setVisible(false);
            posMainGridPane.setDisable(false);
        } else {
            Animations.fadeInUp(pane);
            pane.setVisible(true);
            posMainGridPane.setDisable(true);
        }
    }

    public void clearFields() {
        lblDiscountDisplay.setText("0");
        lblAllDiscount.setText("0.0");
        lblTotal.setText("0.0");
        lblSubTotal.setText("0.0");
    }

    public void btnVoid(MouseEvent mouseEvent) {


    }

    public void btnHold(MouseEvent mouseEvent) throws SQLException {
        removeInvalidValues();
        if (customer == null) {
            customer = new CustomerTM();
            customer.setId("C00000");
        }
        try {

            final OrdersDTO ordersDTO = new OrdersDTO(
                    unpaidOrder.getOrderId(),
                    lblCashier.getText(),
                    Date.valueOf(LocalDate.now()),
                    Time.valueOf(LocalTime.now()),
                    customer.getId(),
                    lblOrderType.getText(),
                    false,
                    totalDiscount
            );
            if (obOrderList.size() > 0 && isExistOrder()) {
                List<OrderDetailTM> orderDetails = new ArrayList<>();
                for (OrderDetailTM o : obOrderList) {
                    orderDetails.add(o);
                }
                ordersDTO.setOrderDetailDTOList(orderDetails.stream().map(o -> converterTM.toOrderDetail(o)).collect(Collectors.toList()));
                if (purchaseOrderBO.saveHoldOrder(ordersDTO)) {
                    clearCategory();
                    initialize();
                    obOrderList.clear();

                    new Alert(Alert.AlertType.INFORMATION, "OrdersTM Has Hold 1").show();
                }
            } else {

                List<OrderDetailTM> orderDetail = new ArrayList<>();
                for (OrderDetailTM o : obOrderList) {
                    o.setOrderId(unpaidOrder.getOrderId());
                    orderDetail.add(o);
                }
                ordersDTO.setOrderDetailDTOList(orderDetail.stream().map(o -> converterTM.toOrderDetail(o)).collect(Collectors.toList()));
                if (purchaseOrderBO.existsHoldOrder(ordersDTO)) {
                    clearCategory();
                    initialize();
                    obOrderList.clear();
                    new Alert(Alert.AlertType.INFORMATION, "OrdersTM Has Hold 2").show();

                } else {

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            btnHold.setDisable(true);
            panePayment.setDisable(true);
            clearFields();
        }

    }

    private boolean isExistOrder() throws SQLException {

       if(purchaseOrderBO.searchOrder(unpaidOrder.getOrderId())==null){
            return true;
       }
        return false;


    }

    private void removeInvalidValues() {
        ObservableList<OrderDetailTM> tempOb = FXCollections.observableArrayList();
        for (OrderDetailTM o : obOrderList) {
            tempOb.add(o);
        }
        for (OrderDetailTM o : tempOb) {
            if (o.getQty() <= 0) {
                obOrderList.remove(o);
            }

        }
    }

    public void btnTakeWay(MouseEvent mouseEvent) {
        onOfTempPane(orderTypePane);
        lblOrderType.setText("TAKEAWAY");
    }

    public void btnDelivery(MouseEvent mouseEvent) {
        onOfTempPane(orderTypePane);
        lblOrderType.setText("DELIVERY");
    }

    public void btnCustomerFade(MouseEvent mouseEvent) {
        onOfTempPane(customerFadePane);
    }

    public void btnAddCustomerTable(ActionEvent actionEvent) {
        customerSelectField();
    }

    public void customerSelectField() {
        CustomerTM customerSelect = mainCustomerTable.getSelectionModel().getSelectedItem();
        if (customerSelect != null) {
            lblCustomer.setText(customerSelect.getName());
            customer = customerSelect;
            onOfTempPane(customerFadePane);
        }

    }

    public void btnRest(MouseEvent mouseEvent) {
        totalDiscount = 0;
        generateNewId();
        clearFields();
        clearObOrderList();
        clearCategory();
        initialize();

    }

    public void clearObOrderList() {
        if (obOrderList.size() > 0) {
            obOrderList.clear();
        }
    }
}
