package lk.ijse.hardware.service.custom;

import lk.ijse.hardware.dto.UserDTO;
import lk.ijse.hardware.service.SuperService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserService extends SuperService {
    boolean saveUser(UserDTO userDTO)throws SQLException;
    boolean updateUser(UserDTO userDTO)throws SQLException;
    boolean deleteUser(String id)throws SQLException;
    List<UserDTO> getAllUser()throws SQLException;
    UserDTO searchUser(String name)throws SQLException;
    String generateUserID()throws SQLException;
}
