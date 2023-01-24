package lk.ijse.hardware.service.custom;

import lk.ijse.hardware.dto.PaymentDTO;
import lk.ijse.hardware.entity.Payment;
import lk.ijse.hardware.service.SuperService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PaymentService extends SuperService {
    List<PaymentDTO> searchPaymentDate(Date start, Date end) throws SQLException;
    PaymentDTO getLastPayment() throws SQLException;
    boolean savePayment(PaymentDTO paymentDTO)throws SQLException;
    boolean updatePayment(PaymentDTO paymentDTO)throws SQLException;
    boolean deletePayment(String id)throws SQLException;
    List<PaymentDTO> getAllPayment()throws SQLException;
    PaymentDTO searchPayment(String id)throws SQLException;
    String generatePaymentID()throws SQLException;
}
