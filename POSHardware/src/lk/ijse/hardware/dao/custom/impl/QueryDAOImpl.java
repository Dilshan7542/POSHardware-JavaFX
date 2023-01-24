package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.QueryDAO;
import lk.ijse.hardware.entity.Custom;
import lk.ijse.hardware.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public List<Custom> getOrderAndPayment(Date start, Date end) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT o.orderId,o.cId,o.date,o.time,o.cashier,p.card,p.cash FROM ordersTMS AS o " +
                "INNER JOIN payment AS p ON o.orderId=p.orderId WHERE o.date BETWEEN ? And ?",start,end);
        ArrayList<Custom> list=new ArrayList<>();
        while(rst.next()){
            list.add(new Custom(
                    rst.getString(1),
                    rst.getDate(3),
                    rst.getTime(4),
                    rst.getString(2),
                    rst.getDouble(5),
                    rst.getDouble(6),
                    rst.getString(2)
            ));
        }
        return list;
    }
}
