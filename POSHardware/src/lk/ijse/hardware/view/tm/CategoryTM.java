package lk.ijse.hardware.view.tm;

import javafx.scene.layout.HBox;


import java.time.LocalDate;


public class CategoryTM {

    private String id;


    private String name;
    private String parent;
    private int totalProduct;
    private boolean displayOnPos;
    private LocalDate createdAt;
   private HBox hbBOxEdit;
   private HBox hbBOxDelete;
    public CategoryTM(String id, String name, String parent, int totalProduct, boolean displayOnPos, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.totalProduct = totalProduct;
        this.displayOnPos = displayOnPos;
        this.createdAt = createdAt;
    }


    public CategoryTM() {

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public boolean isDisplayOnPos() {
        return displayOnPos;
    }

    public void setDisplayOnPos(boolean displayOnPos) {
        this.displayOnPos = displayOnPos;
    }





    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    public String toString(){
        return getName();
    }


    public HBox getHbBOxEdit() {
        return hbBOxEdit;
    }

    public void setHbBOxEdit(HBox hbBOxEdit) {
        this.hbBOxEdit = hbBOxEdit;
    }

    public HBox getHbBOxDelete() {
        return hbBOxDelete;
    }

    public void setHbBOxDelete(HBox hbBOxDelete) {
        this.hbBOxDelete = hbBOxDelete;
    }
}
