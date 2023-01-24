package lk.ijse.hardware.service.custom;

import lk.ijse.hardware.dto.SupplierItemDTO;
import lk.ijse.hardware.entity.SupplierItem;
import lk.ijse.hardware.service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SupplierItemService extends SuperService {
    boolean updateCostPrice(SupplierItemDTO supplierItem) throws SQLException;
    boolean updateSupplierRecodeAndCost(List<SupplierItemDTO> list) throws SQLException;
    boolean saveSupplerItem(SupplierItemDTO entity)throws SQLException;
    boolean updateSupplerItem(SupplierItemDTO entity)throws SQLException;
    boolean deleteSupplerItem(String id)throws SQLException;
    List<SupplierItemDTO> getAllSupplerItem()throws SQLException;
    SupplierItemDTO searchSupplerItem(String id)throws SQLException;
    String generateSupplerItemID()throws SQLException;
}
