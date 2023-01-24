package lk.ijse.hardware.view.tm;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.time.LocalDate;

public class CustomerTM {
    private String id;
    private String customerId;
    private String name;
    private String email;
    private String address;
    private double salary;
    private String phone;
    private LocalDate date;
    private Pane addBtn;
    private HBox editBtn;
    private Pane viewDetailBtn;


    public CustomerTM(String id, String customerId, String name, String email, String address, double salary, String phone, LocalDate date) {
        this.id = id;
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.salary = salary;
        this.phone = phone;
        this.date = date;
    }

    public CustomerTM() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Pane getAddBtn() {
        return addBtn;
    }

    public void setAddBtn(Pane addBtn) {
        this.addBtn = addBtn;
    }





    public Pane getViewDetailBtn() {
        return viewDetailBtn;
    }

    public void setViewDetailBtn(Pane viewDetailBtn) {
        this.viewDetailBtn = viewDetailBtn;
    }

    public HBox getEditBtn() {
        return editBtn;
    }

    public void setEditBtn(HBox editBtn) {
        this.editBtn = editBtn;
    }
}
