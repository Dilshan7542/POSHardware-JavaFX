package lk.ijse.hardware.view.tm;

public class AddItemTM {
    private String itemCode;
    private String Description;
    private int qty;
    private double price;
    private int recode;

    public AddItemTM(String itemCode, String description, int qty, double price) {
        this.itemCode = itemCode;
        Description = description;
        this.qty = qty;
        this.price = price;
    }

    public AddItemTM() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRecode() {
        return recode;
    }

    public void setRecode(int recode) {
        this.recode = recode;
    }
}
