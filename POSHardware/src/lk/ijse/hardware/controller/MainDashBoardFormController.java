package lk.ijse.hardware.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.hardware.controller.dash.CustomerController;
import lk.ijse.hardware.dto.PaymentDTO;
import lk.ijse.hardware.service.ServiceFactory;
import lk.ijse.hardware.service.ServiceType;
import lk.ijse.hardware.service.custom.PaymentService;
import lk.ijse.hardware.service.custom.PurchaseOrderService;
import lk.ijse.hardware.service.custom.impl.ItemServiceImpl;
import lk.ijse.hardware.service.custom.impl.PaymentServiceImpl;
import lk.ijse.hardware.service.custom.impl.PurchaseOrderServiceImpl;
import lk.ijse.hardware.util.Converter;
import lk.ijse.hardware.util.ConverterList;
import lk.ijse.hardware.util.Navigation;
import lk.ijse.hardware.util.enm.Route;
import lk.ijse.hardware.view.tm.OrderDetailTM;
import lk.ijse.hardware.view.tm.OrdersTM;
import lk.ijse.hardware.view.tm.PaymentTM;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class MainDashBoardFormController {

    public Pane loginPaneMini;

    public AnchorPane paneCenter;

    public AnchorPane paneList2;
    public AnchorPane paneList3;


    public ScrollPane leftScrollPane;
    public VBox vboxUser;
    public Label lblBtnProfile;
    public Label lblBtnLogout;
    public VBox vboxSideOrder;
    public VBox vBoxSideMain;
    public VBox vboxSideInventory;
    public VBox vboxReport;
    public VBox vboxSideUser;
    public AnchorPane pane;
    public GridPane gridPane;
    public VBox vboxSideCustomer;
    public VBox vboxSideProvider;
    public AnchorPane btnUser;
    public AnchorPane btnInventory;
    public AnchorPane btnReport;
    public AnchorPane btnCustomer;
    public AnchorPane btnProvider;
    public AnchorPane btnOrder;
    public Label lblTitle;
    public ContextMenu userLogoutContextMenu;
    public AnchorPane upSidePane;
    public AreaChart chartPane;
    public CategoryAxis saleChart;
    public Label lblArthur;
    public Label lblDate;
    public Label lblTime;
    public Label lblLastBill;
    public Label lblTotalSale;
    public Pane dashPaneBlue112;
    public Label lblTopBill;
    Converter converterTM = new Converter();
    ConverterList converterList = new ConverterList();
    PurchaseOrderService purchaseOrderBO=(PurchaseOrderServiceImpl) ServiceFactory.getInstance().getService(ServiceType.PURCHASE_ORDER);
    PaymentService paymentBO=(PaymentServiceImpl) ServiceFactory.getInstance().getService(ServiceType.PAYMENT);

    public void initialize() {
        lblArthur.setText(LoginFormController.arthur);
        removeAllSideVbox();
        removeUserBtn();
        cartPane();
        addTime();
        paymentLast();

    }

    private void paymentLast() {
        try {
            final PaymentDTO lastPayment =paymentBO.getLastPayment();
            System.out.println((lastPayment.getCard() + lastPayment.getCash())+"\t|"+lastPayment.getPayId());
            lblLastBill.setText((lastPayment.getCard() + lastPayment.getCash()) + "");
            final List<OrdersTM> ordersTMS = converterList.formOrderTMList(purchaseOrderBO.searchOrdersByDate(Date.valueOf(LocalDate.now())));
            double total = 0.0;
            double topBill = 0.0;
            double max = 0.0;
            for (OrdersTM ordersTM : ordersTMS) {
                final PaymentDTO paymentDTO = paymentBO.searchPayment(ordersTM.getOrderId());
                if (paymentDTO != null) {
                final PaymentTM paymentTM = converterTM.formPaymentTM(paymentDTO);

                    total += paymentTM.getCard() + paymentTM.getCash();
                    max = paymentTM.getCard() + paymentTM.getCash();
                }
                if (topBill < max) {
                    topBill = max;
                }
            }
            lblTotalSale.setText(String.format("%.2f", total));
            lblTopBill.setText(String.format("%.2f", topBill));


        } catch (SQLException e) {
            e.printStackTrace();
        }

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

    private void cartPane() {
        XYChart.Series s1 = new XYChart.Series();
        try {
            final List<OrdersTM> allOrdersTM = converterList.formOrderTMList(purchaseOrderBO.searchOrdersByDate(Date.valueOf(LocalDate.now())));
            for (OrdersTM o : allOrdersTM) {
                final List<OrderDetailTM> orderDetailTMS = purchaseOrderBO.searchOrderDetail(o.getOrderId()).stream().map(l ->
                        converterTM.formOrderDetailTM(l)
                ).collect(Collectors.toList());
                double total = 0.0;
                for (OrderDetailTM orderDetailTM : orderDetailTMS) {
                    total += orderDetailTM.getQty() * orderDetailTM.getUtilPrice();
                }
                s1.getData().add(new XYChart.Data(o.getDate().getDayOfMonth() + "", total));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        chartPane.getData().addAll(s1);

    }


    private void removeUserBtn() {
        vboxUser.getChildren().remove(lblBtnLogout);
        vboxUser.getChildren().remove(lblBtnProfile);
    }


    public void dropMenuBtn(MouseEvent mouseEvent) {
        if (leftScrollPane.isVisible()) {
            upSidePane.setVisible(false);
            gridPane.getColumnConstraints().get(0).setMinWidth(0);
            gridPane.getColumnConstraints().get(0).setMaxWidth(0);
            gridPane.getColumnConstraints().get(0).setPrefWidth(0);
            leftScrollPane.setVisible(false);
        } else {
            gridPane.getColumnConstraints().get(0).setMinWidth(300);
            gridPane.getColumnConstraints().get(0).setPrefWidth(300);
            gridPane.getColumnConstraints().get(0).setMaxWidth(300);
            upSidePane.setVisible(true);
            leftScrollPane.setVisible(true);
        }
    }

    public void removeAllSideVbox() {
        vBoxSideMain.getChildren().remove(vboxSideOrder);
        vBoxSideMain.getChildren().remove(vboxSideProvider);
        vBoxSideMain.getChildren().remove(vboxSideCustomer);
        vBoxSideMain.getChildren().remove(vboxSideInventory);
        vBoxSideMain.getChildren().remove(vboxSideUser);
        vBoxSideMain.getChildren().remove(vboxReport);
    }

    private int indexSet(AnchorPane sidePane) { // add pane tm VBox
        return vBoxSideMain.getChildren().indexOf(sidePane) + 1;
    }

    public void userClicked(MouseEvent mouseEvent) throws InterruptedException {

        userLogoutContextMenu.show(loginPaneMini, mouseEvent.getScreenX(), mouseEvent.getScreenY());


    }

    public void userIconExited(MouseEvent mouseEvent) {
        removeUserBtn();
    }

    public void btnOrder(MouseEvent mouseEvent) {
        controllerVbox(vboxSideOrder, btnOrder);
    }

    public void btnProvider(MouseEvent mouseEvent) {
        controllerVbox(vboxSideProvider, btnProvider);
    }

    public void btnCustomer(MouseEvent mouseEvent) {
        controllerVbox(vboxSideCustomer, btnCustomer);
    }

    public void btnInventory(MouseEvent mouseEvent) {
        controllerVbox(vboxSideInventory, btnInventory);
    }

    public void btnUserSide(MouseEvent mouseEvent) {

        controllerVbox(vboxSideUser, btnUser);
    }

    public void btnReport(MouseEvent mouseEvent) {

        controllerVbox(vboxReport, btnReport);
    }

    private void controllerVbox(VBox vbox, AnchorPane btnPane) {
        boolean isAdd = vBoxSideMain.getChildren().indexOf(vbox) < 0;
        removeAllSideVbox();
        if (isAdd) {
            vBoxSideMain.getChildren().add(indexSet(btnPane), vbox);
        } else {
            removeAllSideVbox();
        }
    }

    public void btnDashboard(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Route.DASHBOARD_CENTER, pane);

    }

    public void btnPOS(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Route.POS, pane);
    }

    public void btnProduct(MouseEvent mouseEvent) throws IOException {
        lblTitle.setText("MANAGE PRODUCT");
        Navigation.navigate(Route.PRODUCT, paneCenter);
    }

    public void btnCategory(MouseEvent mouseEvent) throws IOException {
        lblTitle.setText("MANAGE CATEGORY");
        Navigation.navigate(Route.CATEGORY, paneCenter);
    }


    public void btnSupplierList(MouseEvent mouseEvent) throws IOException {
        lblTitle.setText("MANAGE SUPPLIER LIST");
        Navigation.navigate(Route.SUPPLIER_LIST, paneCenter);
    }

    public void btnSupplier(MouseEvent mouseEvent) throws IOException {
        lblTitle.setText("MANAGE SUPPLIER");
        Navigation.navigate(Route.SUPPLIER, paneCenter);
    }

    public void btnWastage(MouseEvent mouseEvent) throws IOException {
        lblTitle.setText("MANAGE WASTAGE");
        Navigation.navigate(Route.WASTAGE, paneCenter);
    }

    public void btnGRN(MouseEvent mouseEvent) throws IOException {
        lblTitle.setText("MANAGE GRN");
        Navigation.navigate(Route.GRN, paneCenter);
    }

    public void btnPo(MouseEvent mouseEvent) throws IOException {
        lblTitle.setText("MANAGE PO");
        Navigation.navigate(Route.PO, paneCenter);
    }


    public void btnCustomerList(MouseEvent mouseEvent) throws IOException {
        lblTitle.setText("CUSTOMER LIST");
        Navigation.navigate(Route.CUSTOMER_LIST, paneCenter);
    }

    public void btnCustomerManage(MouseEvent mouseEvent) throws IOException {
        lblTitle.setText("MANAGE CUSTOMER");
        CustomerController.route = Route.CUSTOMER;
        Navigation.navigate(Route.CUSTOMER, paneCenter);

    }

    public void userPaneBtn(MouseEvent mouseEvent) {


    }


    public void btnOrderDetail(MouseEvent mouseEvent) throws IOException {
        lblTitle.setText("ORDERS");
        Navigation.navigate(Route.PAID_ORDER, paneCenter);
    }

    public void btnPayment(MouseEvent mouseEvent) {

    }

    public void btnAddUser(MouseEvent mouseEvent) throws IOException {
        lblTitle.setText("MANAGE USER");
        Navigation.navigate(Route.USER, paneCenter);
    }

    public void btnLogout(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) pane.getScene().getWindow();
        window.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/hardware/view/LoginForm.fxml")));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.setTitle("Login From");
        stage.show();

    }
}
