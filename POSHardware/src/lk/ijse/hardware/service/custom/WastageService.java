package lk.ijse.hardware.service.custom;

import lk.ijse.hardware.dto.WastageDTO;
import lk.ijse.hardware.service.SuperService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface WastageService  extends SuperService {
    boolean saveWastage(WastageDTO wastageDTO)throws SQLException;
    boolean updateWastage(WastageDTO wastageDTO)throws SQLException;
    boolean deleteWastage(String id)throws SQLException;
    List<WastageDTO> getAllWastage()throws SQLException;
    WastageDTO searchWastage(String id)throws SQLException;
    String generateWastageID()throws SQLException;
}
