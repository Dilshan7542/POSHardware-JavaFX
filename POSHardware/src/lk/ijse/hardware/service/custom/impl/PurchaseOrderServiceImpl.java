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
import lk.ijse.hardware.dto.OrderDetailDTO;
import lk.ijse.hardware.dto.OrdersDTO;
import lk.ijse.hardware.entity.Orders;
import lk.ijse.hardware.service.custom.PurchaseOrderService;
import lk.ijse.hardware.service.util.ServiceConverter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    OrdersDAO ordersDAO = (OrdersDAOImpl) DaoFactory.getInstance().getDAO(DAOType.ORDER);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAOImpl) DaoFactory.getInstance().getDAO(DAOType.ORDER_DETAIL);
    ItemDAO itemDAO = (ItemDAOImpl) DaoFactory.getInstance().getDAO(DAOType.ITEM);
    PaymentDAO paymentDAO = (PaymentDAOImpl) DaoFactory.getInstance().getDAO(DAOType.PAYMENT);
    ServiceConverter converter = new ServiceConverter();
    Connection connection = DBConnection.getInstance().getConnection();

    @Override
    public boolean saveHoldOrder(OrdersDTO ordersDTO) throws SQLException {
        connection.setAutoCommit(false);
        if (!ordersDAO.save(converter.toOrders(ordersDTO))) {
            connection.rollback();
            connection.setAutoCommit(false);
            return false;
        } else {
            final boolean isSave = orderDetailDAO.saveAll(ordersDTO.getOrderDetailDTOList().stream().map(o ->
                    converter.toOrderDetail(o)).collect(Collectors.toList()));
            if (!isSave) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public boolean existsHoldOrder(OrdersDTO ordersDTO) throws SQLException {
        connection.setAutoCommit(false);
        System.out.println("this \t"+ordersDTO.getOrderId());
        if (!removeOrderDetail(ordersDTO.getOrderId())) {
                System.out.println("R");
            connection.setAutoCommit(true);
            return false;
        } else {

            if (!updateOrderDiscount(ordersDTO.getOrderId(), ordersDTO.getNetDiscount())) {
                System.out.println("R1");
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            } else {
                final boolean isSave = orderDetailDAO.saveAll(ordersDTO.getOrderDetailDTOList().stream().map(o ->
                        converter.toOrderDetail(o)).collect(Collectors.toList()));
                if (!isSave) {
                    System.out.println("R2");
                    connection.rollback();
                    connection.setAutoCommit(false);
                    return false;
                }

            }
        }
        System.out.println("R3");
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public boolean updateOrder(OrdersDTO orderDTO) throws SQLException {
        return ordersDAO.save(converter.toOrders(orderDTO));
    }

    @Override
    public boolean deleteOrder(String id) throws SQLException {
        return ordersDAO.delete(id);
    }

    @Override
    public List<OrdersDTO> getAllOrder() throws SQLException {
        return ordersDAO.getAll().stream().map(o -> converter.formOrders(o)).collect(Collectors.toList());
    }

    @Override
    public OrdersDTO searchOrder(String id) throws SQLException,RuntimeException {
           if(!ordersDAO.search(id).isPresent())return null;
        return converter.formOrders(ordersDAO.search(id).get());
    }

    @Override
    public String generateOrderID() throws SQLException {
        return ordersDAO.generateID();
    }

    @Override
    public List<OrdersDTO> searchCustomerOrder(String customerId) throws SQLException {
        return ordersDAO.searchCustomerOrder(customerId).stream().map(o -> converter.formOrders(o)).collect(Collectors.toList());
    }

    @Override
    public boolean updateOrderDiscount(String id, int discount) throws SQLException {
        return ordersDAO.updateOrderDiscount(id, discount);
    }

    @Override
    public List<OrdersDTO> searchOrdersByDate(Date date) throws SQLException {
        return ordersDAO.searchOrdersByDate(date).stream().map(o -> converter.formOrders(o)).collect(Collectors.toList());
    }

    @Override
    public boolean saveOrderDetail(OrderDetailDTO orderDetailDTO) throws SQLException {
        return orderDetailDAO.save(converter.toOrderDetail(orderDetailDTO));
    }

    @Override
    public boolean saveAllOrderDetail(List<OrderDetailDTO> list) throws SQLException {
        return orderDetailDAO.saveAll(list.stream().map(o -> converter.toOrderDetail(o)).collect(Collectors.toList()));
    }

    @Override
    public List<OrderDetailDTO> searchOrderDetail(String ID) throws SQLException {
        return orderDetailDAO.searchDetail(ID).stream().map(o -> converter.formOrderDetail(o)).collect(Collectors.toList());
    }

    @Override
    public boolean removeOrderDetail(String id) throws SQLException {
        return orderDetailDAO.removeOrderDetail(id);
    }
}
