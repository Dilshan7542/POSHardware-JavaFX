package lk.ijse.hardware.dto;

public class SupplierDTO implements SuperDTO {
    private String spId;
    private String name;
    private String email;
    private String address;
    private String phone;
    private int recode;
    private boolean status;

    public SupplierDTO() {
    }

    public SupplierDTO(String spId, String name, String email, String address, String phone, int recode, boolean status) {
        this.spId = spId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.recode = recode;
        this.status = status;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
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

    @Override
    public String toString() {
        return "SupplierTM{" +
                "spId='" + spId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", recode=" + recode +
                ", status=" + status +
                '}';
    }
}
