package lk.ijse.hardware.view.tm;

import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.time.LocalTime;

public class OrdersTM {
    private String orderId;
    private String cashier;
    private LocalDate date;
    private LocalTime time;
    private String customerId;
    private String orderType;
    private boolean status;
    private int discount;
    private double totalPrice;
    private Pane addBtn;

    public OrdersTM(String orderId, String cashier, LocalDate date, LocalTime time, String customerId, String orderType, boolean status, int discount) {
        this.orderId = orderId;
        this.cashier = cashier;
        this.date = date;
        this.time = time;
        this.customerId = customerId;
        this.orderType = orderType;
        this.status = status;
        this.discount = discount;
    }

    public OrdersTM() {
    }



    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Pane getAddBtn() {
        return addBtn;
    }

    public void setAddBtn(Pane addBtn) {
        this.addBtn = addBtn;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
