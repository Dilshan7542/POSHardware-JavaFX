package lk.ijse.hardware.dto;


import java.sql.Date;

public class ItemDTO implements SuperDTO {
    private String itemCode;
    private String description;
    private int remind;
    private double utilPrice;
    private boolean status;
    private String categoryId;
    private Date date;
    private int recode;
    private int qty;

    public ItemDTO(String itemCode, String description, int remind, double utilPrice, boolean status, String categoryId, Date date, int recode, int qty) {
        this.itemCode = itemCode;
        this.description = description;
        this.remind = remind;
        this.utilPrice = utilPrice;
        this.status = status;
        this.categoryId = categoryId;
        this.date = date;
        this.recode = recode;
        this.qty = qty;
    }

    private int discount;

    public ItemDTO() {
    }

    public ItemDTO(String itemCode, String description, int remind, double utilPrice, boolean status, String categoryId, Date date, int recode, int qty, int discount) {
        this.itemCode = itemCode;
        this.description = description;
        this.remind = remind;
        this.utilPrice = utilPrice;
        this.status = status;
        this.categoryId = categoryId;
        this.date = date;
        this.recode = recode;
        this.qty = qty;
        this.discount = discount;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }



}
