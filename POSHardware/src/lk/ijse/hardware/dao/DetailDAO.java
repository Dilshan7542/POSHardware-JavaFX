package lk.ijse.hardware.dao;

import lk.ijse.hardware.entity.GrnDetail;
import lk.ijse.hardware.entity.SuperEntity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface DetailDAO<T extends SuperEntity,ID extends Serializable> extends SuperDAO{
    boolean save(T entity)throws SQLException;
    boolean saveAll(List<T> list)throws SQLException;
    List<T> searchDetail(String ID)throws SQLException;

}
