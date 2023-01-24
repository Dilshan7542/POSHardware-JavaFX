package lk.ijse.hardware.dao.custom;

import lk.ijse.hardware.dao.DetailDAO;
import lk.ijse.hardware.entity.OrderDetail;

import java.sql.SQLException;

public interface OrderDetailDAO extends DetailDAO<OrderDetail,String> {
    boolean removeOrderDetail(String id) throws SQLException;
}
