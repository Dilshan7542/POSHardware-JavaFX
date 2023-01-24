package lk.ijse.hardware.dto;

import java.sql.Date;

public class CustomerDTO implements SuperDTO {
    private String cid;
    private String customerId;
    private String name;
    private String email;
    private String address;
    private double salary;
    private String phone;
    Date date;

    public CustomerDTO() {
    }

    public CustomerDTO(String cid, String customerId, String name, String email, String address, double salary, String phone, Date date) {
        this.cid = cid;
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.salary = salary;
        this.phone = phone;
        this.date = date;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cid='" + cid + '\'' +
                ", customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", phone='" + phone + '\'' +
                ", date=" + date +
                '}';
    }
}
