package lk.ijse.hardware.entity;


import java.sql.Date;
import java.sql.Time;

public class Payment implements SuperEntity {
    private String payId;
    private double cash;
    private double card;
    private int cardIndex;
    private Date date;
    private Time time;
    private String orderId;
    private String cId;

    public Payment() {
    }

    public Payment(String payId, double cash, double card, int cardIndex, Date date, Time time, String orderId, String cId) {
        this.payId = payId;
        this.cash = cash;
        this.card = card;
        this.cardIndex = cardIndex;
        this.date = date;
        this.time = time;
        this.orderId = orderId;
        this.cId = cId;
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

    public int getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    @Override
    public String toString() {
        return "PaymentTM{" + "payId='" + payId + '\'' + ", cash=" + cash + ", card=" + card + ", cardIndex=" + cardIndex + ", date=" + date + ", time=" + time + ", orderId='" + orderId + '\'' + ", cId='" + cId + '\'' + '}';
    }
}
