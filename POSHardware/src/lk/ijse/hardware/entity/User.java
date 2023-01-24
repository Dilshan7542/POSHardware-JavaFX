package lk.ijse.hardware.entity;


public class User implements SuperEntity {
    private String userId;
    private String name;
    private String pwd;
    private String empId;
    private String role;
    private boolean status;

    public User() {
    }

    public User(String userId, String name, String pwd, String empId, String role, boolean status) {
        this.userId = userId;
        this.name = name;
        this.pwd = pwd;
        this.empId = empId;
        this.role = role;
        this.status = status;
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

    @Override
    public String toString() {
        return "UserTM{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", empId='" + empId + '\'' +
                ", role='" + role + '\'' +
                ", status=" + status +
                '}';
    }
}
