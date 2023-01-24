package lk.ijse.hardware.dto;


import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class PoDTO implements SuperDTO {
    private String poId;
    private String arthur;
    private Date date;
    private Time time;
    private String spId;
    Date requestDate;

    public PoDTO(String poId, String arthur, Date date, Time time, String spId, Date requestDate) {
        this.poId = poId;
        this.arthur = arthur;
        this.date = date;
        this.time = time;
        this.spId = spId;
        this.requestDate = requestDate;
    }

    List<PoDetailDTO> poDetailDTOList;

    public List<PoDetailDTO> getPoDetailDTOList() {
        return poDetailDTOList;
    }

    public void setPoDetailDTOList(List<PoDetailDTO> poDetailDTOList) {
        this.poDetailDTOList = poDetailDTOList;
    }

    public PoDTO(String poId, String arthur, Date date, Time time, String spId, Date requestDate, List<PoDetailDTO> poDetailDTOList) {
        this.poId = poId;
        this.arthur = arthur;
        this.date = date;
        this.time = time;
        this.spId = spId;
        this.requestDate = requestDate;
        this.poDetailDTOList=poDetailDTOList;
    }

    public PoDTO() {
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
