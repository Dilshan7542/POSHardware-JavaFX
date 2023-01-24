package lk.ijse.hardware.dao.custom;

import lk.ijse.hardware.dao.CrudDAO;
import lk.ijse.hardware.entity.Customer;

import java.sql.SQLException;
import java.util.Optional;

public interface CustomerDAO extends CrudDAO<Customer,String> {
        Optional<Customer> searchCustomerNIC(String id)throws SQLException;
        boolean searchCustomerIdOrNIC(String id)throws SQLException;
}
