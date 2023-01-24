package lk.ijse.hardware.view.tm;

import javafx.scene.layout.Pane;

public class OrderDetailTM {
    private String orderId;
    private String itemCode;
    private String description;
    private int qty;
    private int discount;
    private double utilPrice;
    private double amount;
    private Pane removeBtn;

    public OrderDetailTM() {
    }

    public OrderDetailTM(String orderId, String itemCode, int qty, int discount, double utilPrice) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.discount = discount;
        this.utilPrice = utilPrice;
    }

    public OrderDetailTM(String itemCode, String description, double utilPrice, int discount, int qty, double amount) {
        this.itemCode = itemCode;
        this.description = description;
        this.utilPrice = utilPrice;
        this.discount = discount;
        this.qty = qty;
        this.amount = amount;
    }



    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUtilPrice() {
        return utilPrice;
    }

    public void setUtilPrice(double utilPrice) {
        this.utilPrice = utilPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Pane getRemoveBtn() {
        return removeBtn;
    }

    public void setRemoveBtn(Pane removeBtn) {
        this.removeBtn = removeBtn;
    }
}
