package lk.ijse.hardware.dto;


import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class OrdersDTO implements SuperDTO {
    private String orderId;
    private String cashier;
    private Date date;
    private Time time;
    private String cid;
    private String orderType;
    private boolean status;
    private int netDiscount;
    List<OrderDetailDTO> orderDetailDTOList;
    PaymentDTO paymentDTO;

    public OrdersDTO(String orderId, String cashier, Date date, Time time, String cid, String orderType, boolean status, int netDiscount, List<OrderDetailDTO> orderDetailDTOList, PaymentDTO paymentDTO) {
        this.orderId = orderId;
        this.cashier = cashier;
        this.date = date;
        this.time = time;
        this.cid = cid;
        this.orderType = orderType;
        this.status = status;
        this.netDiscount = netDiscount;
        this.orderDetailDTOList = orderDetailDTOList;
        this.paymentDTO = paymentDTO;
    }

    public OrdersDTO() {
    }

    public OrdersDTO(String orderId, String cashier, Date date, Time time, String cid, String orderType, boolean status, int netDiscount) {
        this.orderId = orderId;
        this.cashier = cashier;
        this.date = date;
        this.time = time;
        this.cid = cid;
        this.orderType = orderType;
        this.status = status;
        this.netDiscount = netDiscount;
    }
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<OrderDetailDTO> getOrderDetailDTOList() {
        return orderDetailDTOList;
    }

    public void setOrderDetailDTOList(List<OrderDetailDTO> orderDetailDTOList) {
        this.orderDetailDTOList = orderDetailDTOList;
    }

    public PaymentDTO getPaymentDTO() {
        return paymentDTO;
    }

    public void setPaymentDTO(PaymentDTO paymentDTO) {
        this.paymentDTO = paymentDTO;
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
                ", cId='" + cid + '\'' +
                ", orderType='" + orderType + '\'' +
                ", status=" + status +
                ", netDiscount=" + netDiscount +
                '}';
    }
}
