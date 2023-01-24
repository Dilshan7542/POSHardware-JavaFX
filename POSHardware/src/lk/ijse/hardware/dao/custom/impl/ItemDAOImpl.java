package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.ItemDAO;
import lk.ijse.hardware.entity.Item;
import lk.ijse.hardware.util.CreateNewId;
import lk.ijse.hardware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public boolean save(Item entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO item VALUES(?,?,?,?,?,?,?,?,?,?)",
                entity.getItemCode(),
                entity.getDescription(),
                entity.getRemind(),
                entity.getUtilPrice(),
                entity.isStatus(),
                entity.getCategoryId(),
                entity.getDate(),
                entity.getRecode(),
                entity.getQty(),
                entity.getDiscount());
    }

    @Override
    public boolean update(Item entity) throws SQLException {
        return CrudUtil.execute("UPDATE item SET categoryId=?,description=?,remind=?,utilPrice=?,status=? WHERE itemCode=?",
                entity.getCategoryId(),
                entity.getDescription(),
                entity.getRemind(),
                entity.getUtilPrice(),
                entity.isStatus(),
                entity.getItemCode()

        );
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public List<Item> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM item");
        ArrayList<Item> list = new ArrayList<>();
        while (rst.next()) {
            list.add(new Item(
                    rst.getString("itemCode"),
                    rst.getString("description"),
                    rst.getInt("remind"),
                    rst.getDouble("utilPrice"),
                    rst.getBoolean("status"),
                    rst.getString("categoryId"),
                    rst.getDate("date"),
                    rst.getInt("recode"),
                    rst.getInt("qty"),
                    rst.getInt("discount")
            ));
        }
        return list;
    }

    @Override
    public Optional<Item> search(String field) throws SQLException {

        if (field.startsWith("PR-")) {
            ResultSet rst = CrudUtil.execute("SELECT * FROM item WHERE itemCode=?", field);
            if (rst.next())
                return Optional.of(new Item(
                        rst.getString("itemCode"),
                        rst.getString("description"),
                        rst.getInt("remind"),
                        rst.getDouble("utilPrice"),
                        rst.getBoolean("status"),
                        rst.getString("categoryId"),
                        rst.getDate("date"),
                        rst.getInt("recode"),
                        rst.getInt("qty"),
                        rst.getInt("discount")
                ));
        }
        ResultSet rst = CrudUtil.execute("SELECT * FROM item WHERE description=?", field);
        if (rst.next())
            return Optional.of(new Item(
                    rst.getString("itemCode"),
                    rst.getString("description"),
                    rst.getInt("remind"),
                    rst.getDouble("utilPrice"),
                    rst.getBoolean("status"),
                    rst.getString("categoryId"),
                    rst.getDate("date"),
                    rst.getInt("recode"),
                    rst.getInt("qty"),
                    rst.getInt("discount")
            ));
        return Optional.empty();
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT itemCode FROM item ORDER BY itemCode DESC LIMIT 1");
        if (resultSet.next())
            return CreateNewId.generateId("PR-", resultSet.getString(1));

        return CreateNewId.generateId("PR-", null);
    }

    @Override
    public boolean updateQtyAndRecodeGrn(String itemCode, int qty) throws SQLException {
        return CrudUtil.execute("UPDATE item SET qty=qty+?,recode=recode+? WHERE itemCode=?", qty, qty, itemCode);
    }

    @Override
    public boolean removeQty(String itemCode, int qty) throws SQLException {
        return CrudUtil.execute("UPDATE item SET qty=qty-? WHERE itemCode=?", qty, itemCode);
    }
}
