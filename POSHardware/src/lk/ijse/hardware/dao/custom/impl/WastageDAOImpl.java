package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.WastageDAO;
import lk.ijse.hardware.entity.Wastage;
import lk.ijse.hardware.util.CreateNewId;
import lk.ijse.hardware.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WastageDAOImpl implements WastageDAO {
    @Override
    public boolean save(Wastage entity) throws SQLException {
        return   CrudUtil.execute("INSERT  INTO wastage VALUES(?,?,?,?,?,?,?,?)",
                entity.getWastageId(),
                entity.getArthur(),
                entity.getReason(),
                entity.getQty(),
                entity.getCost(),
                entity.getDate(),
                entity.getTime(),
                entity.getItemCode()
        );
    }

    @Override
    public boolean update(Wastage entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public List<Wastage> getAll() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM wastage");
        ArrayList<Wastage> list=new ArrayList<>();
        while (rst.next()){
             list.add(new Wastage(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5),
                    rst.getDate(6),
                    rst.getTime(7),
                    rst.getString(8)


            ));
        }
        return list;
    }

    @Override
    public Optional<Wastage> search(String s) throws SQLException {
        return Optional.empty();
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT  wastageId FROM wastage ORDER BY  wastageId DESC LIMIT 1");
        if(rst.next()){
            return CreateNewId.generateId("WT",rst.getString(1));
        }else{
            return CreateNewId.generateId("WT",null);
        }
    }
}
