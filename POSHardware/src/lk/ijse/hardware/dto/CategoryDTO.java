package lk.ijse.hardware.dto;


import java.sql.Date;

public class CategoryDTO implements SuperDTO {
    private String categoryId;
    private String name;
    private String parent;
    private int recode;
    private boolean status;
    private Date date;

    public CategoryDTO() {
    }

    public CategoryDTO(String categoryId, String name, String parent, int recode, boolean status, Date date) {
        this.categoryId = categoryId;
        this.name = name;
        this.parent = parent;
        this.recode = recode;
        this.status = status;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId='" + categoryId + '\'' +
                ", name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", recode=" + recode +
                ", status=" + status +
                ", date=" + date +
                '}';
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getRecode() {
        return recode;
    }

    public void setRecode(int recode) {
        this.recode = recode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
