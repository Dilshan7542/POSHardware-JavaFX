package lk.ijse.hardware.dao.custom;

import lk.ijse.hardware.dao.CrudDAO;
import lk.ijse.hardware.entity.Payment;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PaymentDAO extends CrudDAO<Payment, String> {
    List<Payment> searchDate(Date start, Date end) throws SQLException;
    Optional<Payment> getLastPayment() throws SQLException;
}
