package lk.ijse.hardware.view.tm;

import javafx.scene.layout.HBox;

public class PoListTM {
    private String poId;
    private String itemCode;
    private int qty;
    private String desc;
    private double costPrice;
    private int recode;
    private double totalCost;
    private HBox hbBox;


    public PoListTM(String poId, String itemCode, int qty, String desc, double costPrice, int recode, double totalCost) {
        this.poId = poId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.desc = desc;           // FOR GRN LIST
        this.costPrice = costPrice;
        this.recode = recode;
        this.totalCost = totalCost;
    }

    public PoListTM(String poId, String itemCode, int qty) {
        this.poId = poId;
        this.itemCode = itemCode;
        this.qty = qty;
    }

    public PoListTM() {
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


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public HBox getHbBox() {
        return hbBox;
    }

    public void setHbBox(HBox hbBox) {
        this.hbBox = hbBox;
    }
}
