package lk.ijse.hardware.service.custom;

import lk.ijse.hardware.dto.OrdersDTO;
import lk.ijse.hardware.dto.PaymentDTO;
import lk.ijse.hardware.service.SuperService;

import java.sql.SQLException;

public interface PurchasePaymentService extends SuperService {

    boolean saveLastOrderAndPayment(OrdersDTO ordersDTO)throws SQLException;
    boolean updatePayment(PaymentDTO paymentDTO)throws SQLException;
    boolean deletePayment(String id)throws SQLException;
    String generatePaymentID()throws SQLException;
    boolean removeQty(String itemCode,int qty) throws SQLException;
    boolean updateOrderDiscount(String id,int discount,boolean status) throws SQLException;
    OrdersDTO searchOrder(String id)throws SQLException;
}
