package lk.ijse.hardware.dao.custom;

import lk.ijse.hardware.dao.SuperDAO;
import lk.ijse.hardware.entity.Custom;
import lk.ijse.hardware.entity.Orders;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface QueryDAO extends SuperDAO {
    List<Custom> getOrderAndPayment(Date start, Date end) throws SQLException;
}
