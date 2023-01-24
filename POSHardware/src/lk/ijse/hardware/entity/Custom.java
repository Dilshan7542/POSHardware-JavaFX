package lk.ijse.hardware.entity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Custom implements SuperEntity{
    private String orderId;
    private Date date;
    private Time time;
    private String cashier;

    private double card;
    private double cash;
    private String cid;

    public Custom() {
    }

    public Custom(String orderId, Date date, Time time, String cashier, double card, double cash, String cid) {
        this.orderId = orderId;
        this.date = date;
        this.time = time;
        this.cashier = cashier;
        this.card = card;
        this.cash = cash;
        this.cid = cid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "Custom{" +
                "orderId='" + orderId + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", cashier='" + cashier + '\'' +
                ", card=" + card +
                ", cash=" + cash +
                ", cid='" + cid + '\'' +
                '}';
    }
}
