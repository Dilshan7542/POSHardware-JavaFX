package lk.ijse.hardware.dao;

import lk.ijse.hardware.dao.exception.ConstraintViolationException;
import lk.ijse.hardware.entity.SuperEntity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudDAO <T extends SuperEntity,ID extends Serializable> extends SuperDAO{
    boolean save(T entity)throws SQLException;
    boolean update(T entity)throws SQLException;
    boolean delete(ID id)throws SQLException;
    List<T> getAll()throws SQLException;
    Optional<T> search(ID id)throws SQLException;
    String generateID()throws SQLException;
}

