package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.PaymentDAO;
import lk.ijse.hardware.entity.Payment;
import lk.ijse.hardware.util.CreateNewId;
import lk.ijse.hardware.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public boolean save(Payment entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO payment VALUES (?,?,?,?,?,?,?,?)",
                entity.getPayId(),
                entity.getCash(),
                entity.getCard(),
                entity.getCardIndex(),
                entity.getDate(),
                entity.getTime(),
                entity.getOrderId(),
                entity.getcId()
        );
    }

    @Override
    public List<Payment> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM payment");
        ArrayList<Payment> payments = new ArrayList<>();
        while (rst.next()) {
            payments.add(new Payment(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getDate(5),
                    rst.getTime(6),
                    rst.getString(7),
                    rst.getString(8)

            ));
        }
        return payments;
    }

    @Override
    public Optional<Payment> search(String id) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM payment WHERE orderId=?",id);

        if(rst.next()) {
            return Optional.of(new Payment(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getDate(5),
                    rst.getTime(6),
                    rst.getString(7),
                    rst.getString(8)

            ));
        }
        return Optional.empty();
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT payId FROM payment ORDER BY payId DESC  LIMIT 1");
        if(rst.next()){
            return CreateNewId.generateId("PY",rst.getString(1));
        }
        return CreateNewId.generateId("PY",null);
    }

    @Override
    public List<Payment> searchDate(Date start, Date end) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM payment WHERE date BETWEEN ? AND ?",start,end);
        ArrayList<Payment> payments = new ArrayList<>();
        while (rst.next()) {
            payments.add(new Payment(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getDate(5),
                    rst.getTime(6),
                    rst.getString(7),
                    rst.getString(8)

            ));
        }
        return payments;
    }

    @Override
    public Optional<Payment> getLastPayment() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM payment ORDER BY payId DESC  LIMIT 1");
        if (rst.next()) {
            return Optional.of(new Payment(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getDate(5),
                    rst.getTime(6),
                    rst.getString(7),
                    rst.getString(8)

            ));
        }
        return Optional.empty();
    }
    @Override
    public boolean update(Payment entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }
}
