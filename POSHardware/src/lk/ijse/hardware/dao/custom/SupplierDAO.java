package lk.ijse.hardware.dao.custom;

import lk.ijse.hardware.dao.CrudDAO;
import lk.ijse.hardware.entity.Supplier;

import java.sql.SQLException;

public interface SupplierDAO extends CrudDAO<Supplier,String> {
    boolean updateSupplierRecode(String id,int  recode) throws SQLException;
}
