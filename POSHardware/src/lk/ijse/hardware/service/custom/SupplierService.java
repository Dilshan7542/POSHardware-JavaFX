package lk.ijse.hardware.service.custom;

import lk.ijse.hardware.dto.SupplierDTO;
import lk.ijse.hardware.service.SuperService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SupplierService extends SuperService {
    boolean saveSupplier(SupplierDTO supplierDTO)throws SQLException;
    boolean updateSuppler(SupplierDTO supplierDTO)throws SQLException;
    boolean deleteSupplier(String id)throws SQLException;
    List<SupplierDTO> getAllSuppler()throws SQLException;
    SupplierDTO searchSuppler(String id)throws SQLException;
    String generateSupplierID()throws SQLException;
    boolean updateSupplierRecode(String id,int  recode) throws SQLException;
}
