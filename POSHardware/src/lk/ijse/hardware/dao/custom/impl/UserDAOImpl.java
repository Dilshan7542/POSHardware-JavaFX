package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.UserDAO;
import lk.ijse.hardware.entity.User;
import lk.ijse.hardware.util.CreateNewId;
import lk.ijse.hardware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean save(User entity) throws SQLException {
        return   CrudUtil.execute("INSERT INTO user VALUES(?,?,?,?,?,?)",
                entity.getUserId(),
                entity.getName(),
                entity.getPwd(),
                entity.getEmpId(),
                entity.getRole(),
                entity.isStatus()
        );
    }

    @Override
    public boolean update(User entity) throws SQLException {
        return   CrudUtil.execute("UPDATE user SET name=?,pwd=?,empId=?,role=?,status=? WHERE userId=?",
                entity.getName(),
                entity.getPwd(),
                entity.getEmpId(),
                entity.getRole(),
                entity.isStatus(),
                entity.getUserId()
        );
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public List<User> getAll() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM user");
        ArrayList<User> userList=new ArrayList<>();
        while(rst.next()){
            userList.add(new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getBoolean(6)

            ));
        }
        return userList;
    }

    @Override
    public Optional<User> search(String name) throws SQLException {
        ResultSet rst=  CrudUtil.execute("SELECT  * FROM user WHERE name=?",name);
        if(rst.next()){
            return Optional.of(new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getBoolean(6)));
        }
        return Optional.empty();
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT  userId FROM user ORDER BY userId DESC LIMIT 1");
        if(rst.next()) return CreateNewId.generateId("EP",rst.getString(1));
        else return CreateNewId.generateId("EP",null);
    }
}
