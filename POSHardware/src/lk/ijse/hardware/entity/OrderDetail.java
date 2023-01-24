package lk.ijse.hardware.entity;

public class OrderDetail implements SuperEntity{
    private String orderId;
    private String itemCode;
    private int qty;
    private int discount;
    private double utilPrice;

    public OrderDetail() {
    }

    public OrderDetail(String orderId, String itemCode, int qty, int discount, double utilPrice) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.discount = discount;
        this.utilPrice = utilPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getUtilPrice() {
        return utilPrice;
    }

    public void setUtilPrice(double utilPrice) {
        this.utilPrice = utilPrice;
    }

    @Override
    public String toString() {
        return "OrderDetailTM{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", discount=" + discount +
                ", utilPrice=" + utilPrice +
                '}';
    }
}
