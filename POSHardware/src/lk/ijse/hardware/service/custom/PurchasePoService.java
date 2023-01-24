package lk.ijse.hardware.service.custom;

import lk.ijse.hardware.dto.PoDTO;
import lk.ijse.hardware.dto.PoDetailDTO;
import lk.ijse.hardware.service.SuperService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PurchasePoService extends SuperService {
    boolean savePO(PoDTO poDTO)throws SQLException;
    boolean updatePO(PoDTO poDTO)throws SQLException;
    boolean deletePO(String id)throws SQLException;
    List<PoDTO> getAllPO()throws SQLException;
    PoDTO searchPO(String id)throws SQLException;
    String generatePOID()throws SQLException;
    List<PoDetailDTO> getAllPoListDetail() throws SQLException;
    boolean savePoList(PoDetailDTO list)throws SQLException;
    boolean saveAllPoList(List<PoDetailDTO> list)throws SQLException;
    List<PoDetailDTO> searchPoDetail(String ID)throws SQLException;
}
