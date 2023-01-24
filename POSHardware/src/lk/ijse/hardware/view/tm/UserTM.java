package lk.ijse.hardware.view.tm;

import javafx.scene.layout.HBox;

public class UserTM {
    private String userId;
    private String name;
    private String pwd;
    private String empId;
    private String role;
    private boolean status;
    private HBox editBtn;

    public UserTM(String name, String pwd, boolean status) {
        this.name = name;
        this.pwd = pwd;
        this.status = status;
    }

    public UserTM(String userId, String name, String pwd, String empId, String role, boolean status) {
        this.userId = userId;
        this.name = name;
        this.pwd = pwd;
        this.empId = empId;
        this.role = role;
        this.status = status;
    }

    public UserTM() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public HBox getEditBtn() {
        return editBtn;
    }

    public void setEditBtn(HBox editBtn) {
        this.editBtn = editBtn;
    }
}
