package lk.ijse.hardware.dao.custom;

import lk.ijse.hardware.dao.CrudDAO;
import lk.ijse.hardware.entity.Category;

import java.sql.SQLException;

public interface CategoryDAO extends CrudDAO<Category,String> {
       boolean updateName(String id,String name)throws SQLException;
}
