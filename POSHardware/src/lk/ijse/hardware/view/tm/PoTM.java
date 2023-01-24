package lk.ijse.hardware.view.tm;

import java.time.LocalDate;
import java.time.LocalTime;


public class PoTM {
    private String poId;
    private String arthur;
    private LocalDate date;
    private LocalTime time;
    private String spId;
    private LocalDate requestDate;

    public PoTM(String poId, String arthur, LocalDate date, LocalTime time, String spId, LocalDate requestDate) {
        this.poId = poId;
        this.arthur = arthur;
        this.date = date;
        this.time = time;
        this.spId = spId;
        this.requestDate = requestDate;
    }

    public PoTM() {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }public String toString(){
        return arthur+"\t\t"+poId;
    }
}
