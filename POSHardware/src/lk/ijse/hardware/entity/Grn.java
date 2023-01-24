package lk.ijse.hardware.entity;


import java.sql.Date;
import java.sql.Time;

public class Grn implements SuperEntity{
    private String grnId;
    private String arthur;
    private String invoice;
    private Date date;
    private Time time;
    private String poId;
    private String spId;

    public Grn() {
    }

    public Grn(String grnId, String arthur, String invoice, Date date, Time time, String poId, String spId) {
        this.grnId = grnId;
        this.arthur = arthur;
        this.invoice = invoice;
        this.date = date;
        this.time = time;
        this.poId = poId;
        this.spId = spId;
    }

    public String getGrnId() {
        return grnId;
    }

    public void setGrnId(String grnId) {
        this.grnId = grnId;
    }

    public String getArthur() {
        return arthur;
    }

    public void setArthur(String arthur) {
        this.arthur = arthur;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
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

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    @Override
    public String toString() {
        return "GrnTM{" +
                "grnId='" + grnId + '\'' +
                ", arthur='" + arthur + '\'' +
                ", invoice='" + invoice + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", poId='" + poId + '\'' +
                ", spId='" + spId + '\'' +
                '}';
    }

}
