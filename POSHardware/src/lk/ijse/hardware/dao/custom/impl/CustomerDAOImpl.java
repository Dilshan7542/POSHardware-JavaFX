package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.CustomerDAO;
import lk.ijse.hardware.dao.util.CreateNewID;
import lk.ijse.hardware.entity.Customer;
import lk.ijse.hardware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean save(Customer entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO customer VALUES(?,?,?,?,?,?,?,?)",
                entity.getCid(),
                entity.getCustomerId(),
                entity.getName(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getSalary(),
                entity.getPhone(),
                entity.getDate()

        );
    }

    @Override
    public boolean update(Customer entity) throws SQLException {
        return CrudUtil.execute("UPDATE customer SET customerId=?,name=?,email=?,address=?,phone=? WHERE cId=?",
                entity.getCustomerId(),
                entity.getName(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getCid()
        );
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public List<Customer> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM customer");
        ArrayList<Customer> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getString(7),
                    rst.getDate(8)

            ));
        }
        return customers;
    }

    @Override
    public Optional<Customer> search(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM customer WHERE cId=?", id);
        if (rst.next()) {
            return Optional.of(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getString(7),
                    rst.getDate(8)

            ));

        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> searchCustomerNIC(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM customer WHERE customerId=?", id);
        if (rst.next()) {
            return Optional.of(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getString(7),
                    rst.getDate(8)

            ));

        }
        return Optional.empty();
    }

    @Override
    public boolean searchCustomerIdOrNIC(String id) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM customer WHERE customerId=? OR customerId=?+'v'",id,id);
        return rst.next();
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT cId FROM customer ORDER BY cId DESC LIMIT 1");
        if (rst.next()) {
            return CreateNewID.generateID("C", rst.getString(1));
        } else {
            return CreateNewID.generateID("C", null);
        }
    }
}
