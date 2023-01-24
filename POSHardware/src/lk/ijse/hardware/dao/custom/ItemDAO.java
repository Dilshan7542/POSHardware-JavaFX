package lk.ijse.hardware.dao.custom;

import lk.ijse.hardware.dao.CrudDAO;
import lk.ijse.hardware.entity.Item;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item,String> {
    boolean updateQtyAndRecodeGrn(String itemCode,int qty) throws SQLException;
    boolean removeQty(String itemCode,int qty) throws SQLException;
}
