package lk.ijse.hardware.service.custom.impl;

import lk.ijse.hardware.dao.DAOType;
import lk.ijse.hardware.dao.DaoFactory;
import lk.ijse.hardware.dao.custom.CategoryDAO;
import lk.ijse.hardware.dao.custom.impl.CategoryDAOImpl;
import lk.ijse.hardware.dto.CategoryDTO;
import lk.ijse.hardware.service.custom.CategoryService;
import lk.ijse.hardware.service.util.ServiceConverter;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryServiceImpl implements CategoryService {
    CategoryDAO categoryDAO=(CategoryDAOImpl) DaoFactory.getInstance().getDAO(DAOType.CATEGORY);
    ServiceConverter converter=new ServiceConverter();
    @Override
    public boolean updateCategoryName(String id, String name) throws SQLException {
        return categoryDAO.updateName(id,name);
    }

    @Override
    public List<CategoryDTO> getAllCategory() throws SQLException {
        return categoryDAO.getAll().stream().map(c -> converter.formCategory(c)).collect(Collectors.toList());
    }

    @Override
    public boolean saveCategory(CategoryDTO categoryDTO) throws SQLException {
        return categoryDAO.save(converter.toCategory(categoryDTO));
    }

    @Override
    public boolean updateCategory(CategoryDTO categoryDTO) throws SQLException {
        return categoryDAO.update(converter.toCategory(categoryDTO));
    }

    @Override
    public boolean deleteCategory(String id) throws SQLException {
        return categoryDAO.delete(id);
    }

    @Override
    public CategoryDTO searchCategory(String id) throws SQLException {
                if(!categoryDAO.search(id).isPresent())return null;
        return converter.formCategory(categoryDAO.search(id).get());
    }

    @Override
    public String generateCategoryID() throws SQLException {
        return categoryDAO.generateID();
    }
}
