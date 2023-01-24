package lk.ijse.hardware.entity;

public class PoDetail implements SuperEntity{
    private String poId;
    private String itemCode;
    private int qty;

    public PoDetail() {
    }

    public PoDetail(String poId, String itemCode, int qty) {
        this.poId = poId;
        this.itemCode = itemCode;
        this.qty = qty;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
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

    @Override
    public String toString() {
        return "PoDetailDTO{" +
                "poId='" + poId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                '}';
    }
}
