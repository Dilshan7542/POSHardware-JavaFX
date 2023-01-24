package lk.ijse.hardware.view.tm;

import java.time.LocalDate;
import java.time.LocalTime;

public class WastageTM {
        private String wastageId;
        private String arthur;
        private String reason;
        private int qty;
        private double cost;
        private LocalDate date;
        private LocalTime time;
        private String itemCode;
        private double totalCost;
        private String desc;


        public WastageTM(String wastageId, String arthur, String reason, int qty, double cost, LocalDate date, LocalTime time, String itemCode) {
                this.wastageId = wastageId;
                this.arthur = arthur;
                this.reason = reason;
                this.qty = qty;
                this.cost = cost;
                this.date = date;
                this.time = time;
                this.itemCode = itemCode;
        }

        public WastageTM() {
        }

        public String getWastageId() {
                return wastageId;
        }

        public void setWastageId(String wastageId) {
                this.wastageId = wastageId;
        }

        public String getArthur() {
                return arthur;
        }

        public void setArthur(String arthur) {
                this.arthur = arthur;
        }

        public String getReason() {
                return reason;
        }

        public void setReason(String reason) {
                this.reason = reason;
        }

        public int getQty() {
                return qty;
        }

        public void setQty(int qty) {
                this.qty = qty;
        }

        public double getCost() {
                return cost;
        }

        public void setCost(double cost) {
                this.cost = cost;
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

        public String getItemCode() {
                return itemCode;
        }

        public void setItemCode(String itemCode) {
                this.itemCode = itemCode;
        }

        public double getTotalCost() {
                return totalCost;
        }

        public void setTotalCost(double totalCost) {
                this.totalCost = totalCost;
        }

        public String getDesc() {
                return desc;
        }

        public void setDesc(String desc) {
                this.desc = desc;
        }
}
