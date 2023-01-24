package lk.ijse.hardware.service.custom;

import lk.ijse.hardware.dto.OrderDetailDTO;
import lk.ijse.hardware.dto.OrdersDTO;
import lk.ijse.hardware.dto.PaymentDTO;
import lk.ijse.hardware.entity.Orders;
import lk.ijse.hardware.service.SuperService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


public interface PurchaseOrderService extends SuperService {
    boolean saveHoldOrder(OrdersDTO ordersDTO)throws SQLException;
    boolean existsHoldOrder(OrdersDTO ordersDTO)throws SQLException;
    boolean updateOrder(OrdersDTO entity)throws SQLException;
    boolean deleteOrder(String id)throws SQLException;
    List<OrdersDTO> getAllOrder()throws SQLException;
    OrdersDTO searchOrder(String id)throws SQLException,RuntimeException;
    String generateOrderID()throws SQLException;

    List<OrdersDTO> searchCustomerOrder(String customerId) throws SQLException;
    boolean updateOrderDiscount(String id,int discount) throws SQLException;
    List<OrdersDTO> searchOrdersByDate(Date date) throws SQLException;
    boolean saveOrderDetail(OrderDetailDTO orderDetailDTO)throws SQLException;
    boolean saveAllOrderDetail(List<OrderDetailDTO> list)throws SQLException;
    List<OrderDetailDTO> searchOrderDetail(String ID)throws SQLException;
    boolean removeOrderDetail(String id) throws SQLException;


}
