package lk.ijse.hardware.view.tm;

import javafx.scene.layout.HBox;

public class SupplierListTM {

    private String spId;
    private String description;
    private String itemCode;
    private double costPrice;
    private int recode;
    private double totalCost;
    private int qty;
    private HBox hbBox;
  //  T costPrice=?,recode=recode+? WHERE itemCode=? AND spId=?"
    public SupplierListTM(String spId, String description, String itemCode, double costPrice, int recode, double totalCost, int qty) { // PoTM Form
        this.spId = spId;
        this.description = description;
        this.itemCode = itemCode;
        this.costPrice = costPrice;
        this.recode = recode;
        this.totalCost = totalCost;
        this.qty = qty;
    }

    public SupplierListTM(String spId, String itemCode, double costPrice, int recode) { // SupplierTM list Form
        this.spId = spId;
        this.itemCode = itemCode;
        this.costPrice = costPrice;
        this.recode = recode;
    }




    public SupplierListTM() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
