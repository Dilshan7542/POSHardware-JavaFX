package lk.ijse.hardware.entity;

public class GrnDetail implements SuperEntity {
    private String grnId;
    private double costPrice;
    private int qty;
    private String itemCode;

    public GrnDetail() {
    }

    public GrnDetail(String grnId, double costPrice, int qty, String itemCode) {
        this.grnId = grnId;
        this.costPrice = costPrice;
        this.qty = qty;
        this.itemCode = itemCode;
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

    @Override
    public String toString() {
        return "GrmDetail{" +
                "grnId='" + grnId + '\'' +
                ", costPrice=" + costPrice +
                ", qty=" + qty +
                ", itemCode='" + itemCode + '\'' +
                '}';
    }
}
