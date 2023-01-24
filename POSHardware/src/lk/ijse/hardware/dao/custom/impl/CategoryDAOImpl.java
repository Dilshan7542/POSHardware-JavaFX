package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.CategoryDAO;
import lk.ijse.hardware.dao.util.CreateNewID;
import lk.ijse.hardware.entity.Category;
import lk.ijse.hardware.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDAOImpl implements CategoryDAO {
    @Override
    public boolean save(Category entity) throws SQLException {
        return  CrudUtil.execute("INSERT INTO category VALUES(?,?,?,?,?,?)",
                entity.getCategoryId(),
                entity.getName(),
                entity.getParent(),
                entity.getRecode(),
                entity.isStatus(),
                entity.getDate());
    }

    @Override
    public boolean update(Category entity) throws SQLException {
        return  CrudUtil.execute("UPDATE category SET name=?,parent=?,recode=?,status=? WHERE categoryId=?",
                entity.getName(),
                entity.getParent(),
                entity.getRecode(),
                entity.isStatus(),
                entity.getCategoryId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
       return CrudUtil.execute("DELETE FROM category WHERE categoryId=?",id);
    }

    @Override
    public List<Category> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM category");
        List<Category> categories=new ArrayList<>();
        while(rst.next()){
            categories.add(new Category(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getBoolean(5),
                    rst.getDate(6)));
        }
        return categories;
    }

    @Override
    public Optional<Category> search(String field) throws SQLException {
        if(field.startsWith("C0")){
            ResultSet rst= CrudUtil.execute("SELECT * FROM category WHERE categoryId=?",field);
            if(rst.next()){
                return Optional.of(new Category(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getInt(4),
                        rst.getBoolean(5),
                        rst.getDate(6)));
            }
        }
        ResultSet rst= CrudUtil.execute("SELECT * FROM category WHERE name=?",field);
        if(rst.next()){
            return Optional.of(new Category(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getBoolean(5),
                    rst.getDate(6)));
        }
        return Optional.empty();
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet rst=CrudUtil.execute("SELECT categoryId FROM category ORDER BY categoryId DESC LIMIT 1");
        if(rst.next()){
            return CreateNewID.generateID("C",rst.getString(1));
        }
        return CreateNewID.generateID("C",null);
    }

    @Override
    public boolean updateName(String id, String name) throws SQLException {
        return CrudUtil.execute("UPDATE category SET name=? WHERE categoryId=?",name,id);
    }
}
