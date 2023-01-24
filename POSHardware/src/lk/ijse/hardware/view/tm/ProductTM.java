package lk.ijse.hardware.view.tm;

import javafx.scene.layout.HBox;

import java.time.LocalDate;

public class ProductTM {
    private String itemCode;
    private String description;
    private String categoryId;
    private int remind;
    private double utilPrice;
    private boolean status;
    private LocalDate date;
    private int recode;
    private int qty;
    private int discount=0;
    private CategoryTM categoryTM;
    private HBox btnEdit;
    private HBox btnRemove;



    public ProductTM(String itemCode, String description, String categoryId, int remind, double utilPrice, boolean status, LocalDate date, int recode, int qty) {
        this.itemCode = itemCode;
        this.description = description;
        this.categoryId = categoryId;
        this.remind = remind;
        this.utilPrice = utilPrice;
        this.status = status;
        this.date = date;
        this.recode = recode;
        this.qty = qty;
    }

    public ProductTM(String itemCode, String description, String categoryId, int remind, double utilPrice, boolean status, LocalDate date) {
        this.itemCode = itemCode;
        this.description = description;
        this.categoryId = categoryId;
        this.remind = remind;
        this.utilPrice = utilPrice;
        this.status = status;
        this.date = date;
    }


    public ProductTM() {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getRemind() {
        return remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public double getUtilPrice() {
        return utilPrice;
    }

    public void setUtilPrice(double utilPrice) {
        this.utilPrice = utilPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    public CategoryTM getCategory() {
        return categoryTM;
    }

    public void setCategory(CategoryTM categoryTM) {
        this.categoryTM = categoryTM;
    }public String toString(){
        return itemCode+"\t"+description+"\t"+categoryId+"\t"+utilPrice;
    }

    public int getRecode() {
        return recode;
    }

    public void setRecode(int recode) {
        this.recode = recode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public HBox getBtnEdit() {
        return btnEdit;
    }

    public void setBtnEdit(HBox btnEdit) {
        this.btnEdit = btnEdit;
    }

    public HBox getBtnRemove() {
        return btnRemove;
    }

    public void setBtnRemove(HBox btnRemove) {
        this.btnRemove = btnRemove;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
