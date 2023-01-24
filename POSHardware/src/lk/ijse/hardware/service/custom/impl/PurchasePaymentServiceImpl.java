package lk.ijse.hardware.service.custom.impl;

import lk.ijse.hardware.dao.DAOType;
import lk.ijse.hardware.dao.DaoFactory;
import lk.ijse.hardware.dao.custom.ItemDAO;
import lk.ijse.hardware.dao.custom.OrderDetailDAO;
import lk.ijse.hardware.dao.custom.OrdersDAO;
import lk.ijse.hardware.dao.custom.PaymentDAO;
import lk.ijse.hardware.dao.custom.impl.ItemDAOImpl;
import lk.ijse.hardware.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.hardware.dao.custom.impl.OrdersDAOImpl;
import lk.ijse.hardware.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.hardware.db.DBConnection;
import lk.ijse.hardware.dto.OrdersDTO;
import lk.ijse.hardware.dto.PaymentDTO;
import lk.ijse.hardware.entity.OrderDetail;
import lk.ijse.hardware.service.custom.PurchasePaymentService;
import lk.ijse.hardware.service.util.ServiceConverter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PurchasePaymentServiceImpl implements PurchasePaymentService {
    OrdersDAO ordersDAO = (OrdersDAOImpl) DaoFactory.getInstance().getDAO(DAOType.ORDER);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAOImpl) DaoFactory.getInstance().getDAO(DAOType.ORDER_DETAIL);
    ItemDAO itemDAO = (ItemDAOImpl) DaoFactory.getInstance().getDAO(DAOType.ITEM);
    PaymentDAO paymentDAO = (PaymentDAOImpl) DaoFactory.getInstance().getDAO(DAOType.PAYMENT);
    ServiceConverter converter = new ServiceConverter();

    @Override
    public boolean saveLastOrderAndPayment(OrdersDTO ordersDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
            if(!paymentDAO.save(converter.toPayment(ordersDTO.getPaymentDTO()))){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }else{
                if (!updateOrderDiscount(ordersDTO.getOrderId(), ordersDTO.getNetDiscount(), ordersDTO.isStatus())) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                } else {
                    final List<OrderDetail> orderDetails = orderDetailDAO.searchDetail(ordersDTO.getOrderId());
                    for (OrderDetail list : orderDetails) {
                        if (!itemDAO.removeQty(list.getItemCode(), list.getQty())) {
                            connection.rollback();
                            connection.setAutoCommit(true);
                            return false;
                        }
                    }

                }
            }

        connection.commit();
        connection.setAutoCommit(true);
        return true;
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
    public String generatePaymentID() throws SQLException {
        return paymentDAO.generateID();
    }

    @Override
    public boolean removeQty(String itemCode, int qty) throws SQLException {
        return itemDAO.removeQty(itemCode, qty);
    }

    @Override
    public boolean updateOrderDiscount(String id, int discount, boolean status) throws SQLException {
        return ordersDAO.updateOrderDiscount(id, discount, true);
    }

    @Override
    public OrdersDTO searchOrder(String id) throws SQLException {
        if(!ordersDAO.search(id).isPresent())return null;
        return converter.formOrders(ordersDAO.search(id).get());
    }
}
