package lk.ijse.hardware.dao.custom;


import lk.ijse.hardware.dao.DetailDAO;
import lk.ijse.hardware.entity.PoDetail;

import java.sql.SQLException;
import java.util.List;

public interface PoDetailDAO extends DetailDAO<PoDetail, String> {
    List<PoDetail> getAllPoListDetail() throws SQLException;
}
