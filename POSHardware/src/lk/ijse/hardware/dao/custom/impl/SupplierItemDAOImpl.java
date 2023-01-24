package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.SupplierItemDAO;
import lk.ijse.hardware.entity.SupplierItem;
import lk.ijse.hardware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SupplierItemDAOImpl implements SupplierItemDAO {

    @Override
    public boolean save(SupplierItem entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO supplieritem VALUES (?,?,?,?)",
                entity.getSpId(),
                entity.getItemCode(),
                entity.getCostPrice(),
                entity.getRecode());
    }

    @Override
    public boolean update(SupplierItem entity) throws SQLException {
        return CrudUtil.execute("UPDATE supplieritem SET costPrice=?,recode=recode+? WHERE itemCode=? AND spId=?",
                entity.getCostPrice(),
                entity.getRecode(),
                entity.getItemCode(),
                entity.getSpId());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM supplieritem WHERE itemCode=?",id);
    }

    @Override
    public List<SupplierItem> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM supplieritem");
        ArrayList<SupplierItem> supplierLists=new ArrayList<>();
        while(rst.next()){
            supplierLists.add(new SupplierItem(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4) ));
        }
        return supplierLists;
    }

    @Override
    public Optional<SupplierItem> search(String id) throws SQLException {
        ResultSet rest= CrudUtil.execute("SELECT * FROM supplieritem WHERE itemCode=?",id);
        if(rest.next()){
            return Optional.of(new SupplierItem(
                    rest.getString(1),
                    rest.getString(2),
                    rest.getDouble(3),
                    rest.getInt(4)));
        }
        return Optional.empty();
    }

    @Override
    public String generateID() throws SQLException {
        return null;
    }

    @Override
    public boolean updateCostPrice(SupplierItem supplierItem) throws SQLException {
        return CrudUtil.execute("UPDATE supplieritem SET costPrice=? WHERE itemCode=?",
                supplierItem.getCostPrice(),
                supplierItem.getItemCode());
    }

    @Override
    public boolean updateSupplierRecodeAndCost(List<SupplierItem> list) throws SQLException {
        for (SupplierItem s:list){
            if(!update(s)){
                return false;
            }
        }
        return true;
    }
}
