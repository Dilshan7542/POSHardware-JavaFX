package lk.ijse.hardware.dto;


import java.sql.Date;
import java.sql.Time;

public class WastageDTO implements SuperDTO {
    private String wastageId;
    private String arthur;
    private String reason;
    private int qty;
    private double cost;
    private Date date;
    private Time time;
    private String itemCode;

    public WastageDTO() {
    }

    public WastageDTO(String wastageId, String arthur, String reason, int qty, double cost, Date date, Time time, String itemCode) {
        this.wastageId = wastageId;
        this.arthur = arthur;
        this.reason = reason;
        this.qty = qty;
        this.cost = cost;
        this.date = date;
        this.time = time;
        this.itemCode = itemCode;
    }

    public String getWastageId() {
        return wastageId;
    }

    public void setWastageId(String wastageId) {
        this.wastageId = wastageId;
    }

    public String getArthur() {
        return arthur;
    }

    public void setArthur(String arthur) {
        this.arthur = arthur;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Override
    public String toString() {
        return "WastageTM{" +
                "wastageId='" + wastageId + '\'' +
                ", arthur='" + arthur + '\'' +
                ", reason='" + reason + '\'' +
                ", qty=" + qty +
                ", cost=" + cost +
                ", date=" + date +
                ", time=" + time +
                ", itemCode='" + itemCode + '\'' +
                '}';
    }
}
