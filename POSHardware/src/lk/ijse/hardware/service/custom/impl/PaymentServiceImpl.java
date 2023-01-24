package lk.ijse.hardware.service.custom.impl;

import lk.ijse.hardware.dao.DAOType;
import lk.ijse.hardware.dao.DaoFactory;
import lk.ijse.hardware.dao.custom.PaymentDAO;
import lk.ijse.hardware.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.hardware.dto.PaymentDTO;
import lk.ijse.hardware.entity.Payment;
import lk.ijse.hardware.service.custom.PaymentService;
import lk.ijse.hardware.service.util.ServiceConverter;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaymentServiceImpl implements PaymentService {
    PaymentDAO paymentDAO=(PaymentDAOImpl) DaoFactory.getInstance().getDAO(DAOType.PAYMENT);
    ServiceConverter converter=new ServiceConverter();
    @Override
    public List<PaymentDTO> searchPaymentDate(Date start, Date end) throws SQLException {
        return paymentDAO.searchDate(start,end).stream().map(p -> converter.formPayment(p)).collect(Collectors.toList());
    }

    @Override
    public PaymentDTO getLastPayment() throws SQLException {
        final Optional<Payment> lastPayment = paymentDAO.getLastPayment();
        if(!lastPayment.isPresent())return null;
        return converter.formPayment(lastPayment.get());
    }

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException {
        return paymentDAO.save(converter.toPayment(paymentDTO));
    }

    @Override
    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException {
        return paymentDAO.update(converter.toPayment(paymentDTO));
    }

    @Override
    public boolean deletePayment(String id) throws SQLException {
        return paymentDAO.delete(id);
    }

    @Override
    public List<PaymentDTO> getAllPayment() throws SQLException {
        return paymentDAO.getAll().stream().map(p -> converter.formPayment(p)).collect(Collectors.toList());
    }

    @Override
    public PaymentDTO searchPayment(String id) throws SQLException {
        if (!paymentDAO.search(id).isPresent())return null;
        return converter.formPayment(paymentDAO.search(id).get());
    }

    @Override
    public String generatePaymentID() throws SQLException {
        return paymentDAO.generateID();
    }
}
