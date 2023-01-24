package lk.ijse.hardware.service.custom;

import lk.ijse.hardware.dto.GrnDTO;
import lk.ijse.hardware.dto.GrnDetailDTO;
import lk.ijse.hardware.service.SuperService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PurchaseGrnService extends SuperService {
    boolean saveGrn(GrnDTO grnDTO)throws SQLException;
    boolean updateGrn(GrnDTO grnDTO)throws SQLException;
    boolean deleteGrn(String id)throws SQLException;
    List<GrnDTO> getAllGrn()throws SQLException;
    GrnDTO searchGrn(String id)throws SQLException;
    String generateGrnID()throws SQLException;
    List<GrnDetailDTO> searchGrnDetail(String ID)throws SQLException;
    boolean saveGrnDetail(GrnDetailDTO entity)throws SQLException;
    boolean saveAllGrnDetail(List<GrnDetailDTO> list)throws SQLException;
    List<GrnDetailDTO> searchDetailGrnDetail(String ID)throws SQLException;
}
