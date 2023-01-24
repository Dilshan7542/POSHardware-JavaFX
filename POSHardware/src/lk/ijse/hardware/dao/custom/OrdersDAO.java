package lk.ijse.hardware.dao.custom;

import lk.ijse.hardware.dao.CrudDAO;
import lk.ijse.hardware.entity.Orders;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface OrdersDAO extends CrudDAO<Orders,String> {
    boolean updateOrderDiscount(String id,int discount,boolean status) throws SQLException;
    List<Orders> searchCustomerOrder(String customerId) throws SQLException;
    boolean updateOrderDiscount(String id,int discount) throws SQLException;
    List<Orders> searchOrdersByDate(Date date) throws SQLException;

}
