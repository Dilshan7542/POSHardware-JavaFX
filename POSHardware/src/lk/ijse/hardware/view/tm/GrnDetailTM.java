package lk.ijse.hardware.view.tm;

public class GrnDetailTM {
private String grnId;
private double costPrice;
private int qty;
private String itemCode;


    public GrnDetailTM(String grnId, double costPrice, int qty, String itemCode) {
        this.grnId = grnId;
        this.costPrice = costPrice;
        this.qty = qty;
        this.itemCode = itemCode;

    }

    public GrnDetailTM() {
    }

    public String getGrnId() {
        return grnId;
    }

    public void setGrnId(String grnId) {
        this.grnId = grnId;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

}
