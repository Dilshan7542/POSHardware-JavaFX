package lk.ijse.hardware.view.tm;

import java.time.LocalDate;
import java.time.LocalTime;

public class PaymentTM {
    private String payId;
    private double cash;
    private double card;
    private int cardIndex;
    private LocalDate date;
    private LocalTime time;
    private String orderId;
    private String customerId;
    private String payment;
    private double amount;

    public PaymentTM() {
    }

    public PaymentTM(
                    String payId, double cash,
                   double card,int cardIndex, LocalDate date,
                   LocalTime time, String orderId,
                   String customerId
                ) {
        this.payId = payId;
        this.cash = cash;
        this.card = card;
        this.cardIndex=cardIndex;
        this.date = date;
        this.time = time;
        this.orderId = orderId;
        this.customerId = customerId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getCard() {
        return card;
    }

    public void setCard(double card) {
        this.card = card;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }
}
