package lk.ijse.hardware.service.custom;

import lk.ijse.hardware.dto.CategoryDTO;
import lk.ijse.hardware.service.SuperService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CategoryService extends SuperService {
    boolean updateCategoryName(String id,String name)throws SQLException;
    List<CategoryDTO> getAllCategory()throws SQLException;
    boolean saveCategory(CategoryDTO entity)throws SQLException;
    boolean updateCategory(CategoryDTO categoryDTO)throws SQLException;
    boolean deleteCategory(String id)throws SQLException;
    CategoryDTO searchCategory(String id)throws SQLException;
    String generateCategoryID()throws SQLException;
}
