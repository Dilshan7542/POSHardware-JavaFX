package lk.ijse.hardware.service.custom.impl;

import lk.ijse.hardware.dao.DAOType;
import lk.ijse.hardware.dao.DaoFactory;
import lk.ijse.hardware.dao.custom.UserDAO;
import lk.ijse.hardware.dao.custom.impl.UserDAOImpl;
import lk.ijse.hardware.dto.UserDTO;
import lk.ijse.hardware.service.custom.UserService;
import lk.ijse.hardware.service.util.ServiceConverter;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    UserDAO userDAO=(UserDAOImpl) DaoFactory.getInstance().getDAO(DAOType.USER);
    ServiceConverter converter=new ServiceConverter();
    @Override
    public boolean saveUser(UserDTO userDTO) throws SQLException {
        return userDAO.save(converter.toUser(userDTO));
    }

    @Override
    public boolean updateUser(UserDTO userDTO) throws SQLException {
        return userDAO.update(converter.toUser(userDTO));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException {
        return userDAO.delete(id);
    }

    @Override
    public List<UserDTO> getAllUser() throws SQLException {
        return userDAO.getAll().stream().map(u -> converter.formUser(u)).collect(Collectors.toList());
    }

    @Override
    public UserDTO searchUser(String name) throws SQLException {
        if (!userDAO.search(name).isPresent())return null;
        return converter.formUser(userDAO.search(name).get());
    }

    @Override
    public String generateUserID() throws SQLException {
        return userDAO.generateID();
    }
}
