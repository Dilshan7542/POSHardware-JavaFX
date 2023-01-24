package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.OrdersDAO;
import lk.ijse.hardware.entity.Orders;
import lk.ijse.hardware.util.CreateNewId;
import lk.ijse.hardware.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdersDAOImpl implements OrdersDAO {
    @Override
    public boolean save(Orders entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO orders VALUES (?,?,?,?,?,?,?,?)",
                entity.getOrderId(),
                entity.getCashier(),
                entity.getDate(),
                entity.getTime(),
                entity.getCId(),
                entity.getOrderType(),
                entity.isStatus(),
                entity.getNetDiscount()
        );
    }


    @Override
    public List<Orders> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM orders");
        ArrayList<Orders> ordersTMS=new ArrayList<>();
        while(rst.next()){
            ordersTMS.add(new Orders(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getTime(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getBoolean(7),
                    rst.getInt(8)
            ));
        }
        return ordersTMS;
    }
    @Override
    public Optional<Orders> search(String id) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM orders WHERE orderId=?",id);
        if(rst.next()){
            return Optional.of(new Orders(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getTime(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getBoolean(7),
                    rst.getInt(8)
            ));
        }
        return Optional.empty();
    }


    @Override
    public String generateID() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1");
        if (rst.next()) {
            return CreateNewId.generateId("D", rst.getString(1));
        } else {
            return CreateNewId.generateId("D", null);
        }
    }


    @Override
    public boolean updateOrderDiscount(String id, int discount, boolean status) throws SQLException {
        return CrudUtil.execute("UPDATE orders SET netDiscount=?,status=? WHERE orderId=?",discount,status,id);
    }

    @Override
    public List<Orders> searchCustomerOrder(String customerId) throws SQLException {
        ResultSet rst=CrudUtil.execute("SELECT * FROM orders WHERE cId=?",customerId);
        List<Orders> list=new ArrayList<>();
        while(rst.next()){
            list.add(new Orders(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getTime(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getBoolean(7),
                    rst.getInt(8)
            ));
        }
        return list;
    }

    @Override
    public boolean updateOrderDiscount(String id, int discount) throws SQLException {
        return CrudUtil.execute("UPDATE orders SET netDiscount=? WHERE orderId=?",discount,id);
    }

    @Override
    public List<Orders> searchOrdersByDate(Date date) throws SQLException {
        ResultSet rst=CrudUtil.execute("SELECT * FROM orders WHERE date=?",date);
        List<Orders> list=new ArrayList<>();
        while(rst.next()){
            list.add(new Orders(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getTime(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getBoolean(7),
                    rst.getInt(8)
            ));
        }
        return list;
    }
    @Override
    public boolean update(Orders entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }
}
