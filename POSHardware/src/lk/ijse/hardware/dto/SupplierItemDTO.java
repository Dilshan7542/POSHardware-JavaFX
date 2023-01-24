package lk.ijse.hardware.dto;

public class SupplierItemDTO implements SuperDTO {
    private String spId;
    private String itemCode;
    private double costPrice;
    private int recode;

    public SupplierItemDTO() {
    }

    public SupplierItemDTO(String spId, String itemCode, double costPrice, int recode) {
        this.spId = spId;
        this.itemCode = itemCode;
        this.costPrice = costPrice;
        this.recode = recode;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public int getRecode() {
        return recode;
    }

    public void setRecode(int recode) {
        this.recode = recode;
    }

    @Override
    public String toString() {
        return "SupplierItem{" +
                "spId='" + spId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", costPrice=" + costPrice +
                ", recode=" + recode +
                '}';
    }
}
