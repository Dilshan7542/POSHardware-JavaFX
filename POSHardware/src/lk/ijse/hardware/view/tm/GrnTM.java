package lk.ijse.hardware.view.tm;

import javafx.scene.control.Button;

import java.time.LocalDate;
import java.time.LocalTime;

public class GrnTM {

    public GrnTM(String grnId, String arthur, String invoiceId, LocalDate date, LocalTime time, String poId, String supplierId) {
        this.grnId = grnId;
        this.arthur = arthur;
        this.invoiceId = invoiceId;
        this.date = date;
        this.time = time;
        this.poId = poId;
        this.supplierId = supplierId;
    }

    private String grnId;
    private String arthur;
    private String invoiceId;
    private LocalDate date;
    private LocalTime time;
    private String poId;
    private String supplierId;
    private Button btnView;



    public GrnTM() {
    }

    public String getGrnId() {
        return grnId;
    }

    public void setGrnId(String grnId) {
        this.grnId = grnId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Button getBtnView() {
        return btnView;
    }

    public void setBtnView(Button btnView) {
        this.btnView = btnView;
    }
}
