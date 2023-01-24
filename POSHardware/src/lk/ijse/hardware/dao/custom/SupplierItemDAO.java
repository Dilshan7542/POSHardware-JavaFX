package lk.ijse.hardware.dao.custom;

import lk.ijse.hardware.dao.CrudDAO;

import lk.ijse.hardware.entity.SupplierItem;

import java.sql.SQLException;
import java.util.List;

public interface SupplierItemDAO extends CrudDAO<SupplierItem, String> {
    boolean updateCostPrice(SupplierItem supplierItem) throws SQLException;
    boolean updateSupplierRecodeAndCost(List<SupplierItem> list) throws SQLException;
}
