package lk.ijse.hardware.entity;


import java.sql.Date;
import java.sql.Time;

public class Po implements SuperEntity{
    private String poId;
    private String arthur;
    private Date date;
    private Time time;
    private String spId;
    Date requestDate;

    public Po(String poId, String arthur, Date date, Time time, String spId, Date requestDate) {
        this.poId = poId;
        this.arthur = arthur;
        this.date = date;
        this.time = time;
        this.spId = spId;
        this.requestDate = requestDate;
    }

    public Po() {
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getArthur() {
        return arthur;
    }

    public void setArthur(String arthur) {
        this.arthur = arthur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public String toString() {
        return "PoTM{" +
                "poId='" + poId + '\'' +
                ", arthur='" + arthur + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", spId='" + spId + '\'' +
                ", requestDate=" + requestDate +
                '}';
    }
}
