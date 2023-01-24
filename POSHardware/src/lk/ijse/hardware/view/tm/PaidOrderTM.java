package lk.ijse.hardware.view.tm;

import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.time.LocalTime;

public class PaidOrderTM {
    private String orderId;
    private String cashier;
    private LocalDate date;
    private LocalTime time;
    private double card;
    private String customer;
    private double cash;
    private double amount;
    private Pane viewDetailBtn;

    public PaidOrderTM(String orderId, String customer, LocalDate date, LocalTime time, double card, double cash, double amount) {
        this.orderId = orderId;
        this.customer=customer;
        this.date = date;
        this.time = time;
        this.card = card;
        this.cash = cash;
        this.amount = amount;
    }


    public PaidOrderTM(String orderId, String cashier, String customer,
                       LocalDate date, LocalTime time, double card, double cash, double amount) {
        this.orderId = orderId;
        this.cashier = cashier;
        this.customer = customer;
        this.date = date;
        this.time = time;
        this.card = card;
        this.cash = cash;
        this.amount = amount;
    }

    public PaidOrderTM() {
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Pane getViewDetailBtn() {
        return viewDetailBtn;
    }

    public void setViewDetailBtn(Pane viewDetailBtn) {
        this.viewDetailBtn = viewDetailBtn;
    }

    public double getCard() {
        return card;
    }

    public void setCard(double card) {
        this.card = card;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }
}
