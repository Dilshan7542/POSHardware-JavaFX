package lk.ijse.hardware.service.custom.impl;

import lk.ijse.hardware.dao.DAOType;
import lk.ijse.hardware.dao.DaoFactory;
import lk.ijse.hardware.dao.custom.CustomerDAO;
import lk.ijse.hardware.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.hardware.dto.CustomerDTO;
import lk.ijse.hardware.service.custom.CustomerService;
import lk.ijse.hardware.service.util.ServiceConverter;
import rex.utils.S;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements CustomerService {
    CustomerDAO customerDAO=(CustomerDAOImpl) DaoFactory.getInstance().getDAO(DAOType.CUSTOMER);
    ServiceConverter converter=new ServiceConverter();
    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.save(converter.toCustomer(customerDTO));
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.update(converter.toCustomer(customerDTO));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return customerDAO.delete(id);
    }

    @Override
    public List<CustomerDTO> getAllCustomer() throws SQLException {
        return customerDAO.getAll().stream().map(c -> converter.formCustomer(c)).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException {
        if (!customerDAO.search(id).isPresent())return null;
        return converter.formCustomer(customerDAO.search(id).get());
    }

    @Override
    public String generateCustomerID() throws SQLException {
        return customerDAO.generateID();
    }

    @Override
    public CustomerDTO searchCustomerNIC(String id) throws SQLException {
        return converter.formCustomer(customerDAO.searchCustomerNIC(id).get());
    }

    @Override
    public boolean searchCustomerIdOrNIC(String id) throws SQLException {
        return customerDAO.searchCustomerIdOrNIC(id);
    }
}
