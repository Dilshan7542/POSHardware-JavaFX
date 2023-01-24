package lk.ijse.hardware.service.custom;

import lk.ijse.hardware.dto.CustomDTO;
import lk.ijse.hardware.entity.Custom;
import lk.ijse.hardware.service.SuperService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface QueryService extends SuperService {
    List<CustomDTO> getOrderAndPayment(Date start, Date end) throws SQLException;
}
