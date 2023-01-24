package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.OrderDetailDAO;
import lk.ijse.hardware.entity.GrnDetail;
import lk.ijse.hardware.entity.OrderDetail;
import lk.ijse.hardware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public boolean save(OrderDetail entity) throws SQLException {
        return   CrudUtil.execute("INSERT INTO orderdetail VALUES (?,?,?,?,?)",
                entity.getOrderId(),
                entity.getItemCode(),
                entity.getQty(),
                entity.getDiscount(),
                entity.getUtilPrice()
        );
    }

    @Override
    public boolean saveAll(List<OrderDetail> list) throws SQLException {
        for(OrderDetail or:list){
            if(!save(or)){
                return false;
            }

        }
        return true;
    }

    @Override
    public List<OrderDetail> searchDetail(String ID) throws SQLException {
        ResultSet rst=  CrudUtil.execute("SELECT * FROM orderdetail WHERE orderId=?",ID);
        ArrayList<OrderDetail> list=new ArrayList<>();
        while(rst.next()){
            list.add(new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getInt(4),
                    rst.getDouble(5)
            ));
        }
        return  list;
    }

    @Override
    public boolean removeOrderDetail(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM orderdetail WHERE orderId=?",id);
    }
}
