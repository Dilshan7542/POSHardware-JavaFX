package lk.ijse.hardware.view.tm;

import javafx.scene.layout.HBox;

public class SupplierTM {
    private String id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private int recode;
    private boolean status;
    private HBox editBtn;

    public SupplierTM(String id, String name, String email, String address, String phone, int recode, boolean status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.recode = recode;
        this.status = status;
    }

    public SupplierTM() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }



    public int getRecode() {
        return recode;
    }

    public void setRecode(int recode) {
        this.recode = recode;
    }


    public HBox getEditBtn() {
        return editBtn;
    }

    public void setEditBtn(HBox editBtn) {
        this.editBtn = editBtn;
    }public String toString(){
        return id+"\t\t"+name;
    }
}
