package lk.ijse.hardware.entity;


import java.sql.Date;
import java.sql.Time;

public class Orders implements SuperEntity{
    private String orderId;
    private String cashier;
    private Date date;
    private Time time;
    private String cId;
    private String orderType;
    private boolean status;
    private int netDiscount;

    public Orders() {
    }

    public Orders(String orderId, String cashier, Date date, Time time, String cId, String orderType, boolean status, int netDiscount) {
        this.orderId = orderId;
        this.cashier = cashier;
        this.date = date;
        this.time = time;
        this.cId = cId;
        this.orderType = orderType;
        this.status = status;
        this.netDiscount = netDiscount;
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

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getNetDiscount() {
        return netDiscount;
    }

    public void setNetDiscount(int netDiscount) {
        this.netDiscount = netDiscount;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId='" + orderId + '\'' +
                ", cashier='" + cashier + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", cId='" + cId + '\'' +
                ", orderType='" + orderType + '\'' +
                ", status=" + status +
                ", netDiscount=" + netDiscount +
                '}';
    }
}
