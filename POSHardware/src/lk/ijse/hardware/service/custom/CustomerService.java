package lk.ijse.hardware.service.custom;

import lk.ijse.hardware.dto.CustomerDTO;
import lk.ijse.hardware.entity.Customer;
import lk.ijse.hardware.service.SuperService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CustomerService extends SuperService {
    boolean saveCustomer(CustomerDTO customerDTO)throws SQLException;
    boolean updateCustomer(CustomerDTO customerDTO)throws SQLException;
    boolean deleteCustomer(String id)throws SQLException;
    List<CustomerDTO> getAllCustomer()throws SQLException;
    CustomerDTO searchCustomer(String id)throws SQLException;
    String generateCustomerID()throws SQLException;
    CustomerDTO searchCustomerNIC(String id)throws SQLException;
    boolean searchCustomerIdOrNIC(String id)throws SQLException;
}
