package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.SupplierDAO;
import lk.ijse.hardware.entity.Supplier;
import lk.ijse.hardware.util.CreateNewId;
import lk.ijse.hardware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public boolean save(Supplier entity) throws SQLException {
        return   CrudUtil.execute("INSERT INTO supplier VALUES(?,?,?,?,?,?,?)",
                entity.getSpId(),
                entity.getName(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getRecode(),
                entity.isStatus());
    }

    @Override
    public boolean update(Supplier entity) throws SQLException {
        return CrudUtil.execute("UPDATE supplier SET name=?,email=?,address=?,phone=?,status=? WHERE spId=?",
                entity.getName(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getPhone(),
                entity.isStatus(),
                entity.getSpId()
        );
    }

    @Override
    public List<Supplier> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM supplier");
        ArrayList<Supplier> suppliers=new ArrayList<>();
        while (rst.next()){
            suppliers.add(new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6),
                    rst.getBoolean(7)));
        }
        return suppliers;
    }

    @Override
    public Optional<Supplier> search(String field) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM supplier WHERE spId=?",field);
        if(rst.next()){
            return Optional.of(new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6),
                    rst.getBoolean(7)));
        }
        return Optional.empty();
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet rst=CrudUtil.execute("SELECT spId FROM supplier ORDER BY spId DESC LIMIT 1");
        if (rst.next()) {
            return CreateNewId.generateId("SP", rst.getString(1));
        } else {
            return CreateNewId.generateId("SP", null);
        }
    }

    @Override
    public boolean updateSupplierRecode(String id, int recode) throws SQLException {
        return CrudUtil.execute("UPDATE supplier SET recode=recode+? WHERE spId=?",recode,id);
    }
    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }
}
