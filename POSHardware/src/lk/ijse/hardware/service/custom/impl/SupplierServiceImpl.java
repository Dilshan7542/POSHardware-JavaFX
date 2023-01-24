package lk.ijse.hardware.service.custom.impl;

import lk.ijse.hardware.dao.DAOType;
import lk.ijse.hardware.dao.DaoFactory;
import lk.ijse.hardware.dao.custom.SupplierDAO;
import lk.ijse.hardware.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.hardware.dto.SupplierDTO;
import lk.ijse.hardware.entity.Supplier;
import lk.ijse.hardware.service.custom.SupplierItemService;
import lk.ijse.hardware.service.custom.SupplierService;
import lk.ijse.hardware.service.util.ServiceConverter;
import rex.utils.S;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SupplierServiceImpl implements SupplierService {
    SupplierDAO supplierDAO=(SupplierDAOImpl) DaoFactory.getInstance().getDAO(DAOType.SUPPLIER);
    ServiceConverter converter=new ServiceConverter();
    @Override
    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException {
    return supplierDAO.save(converter.toSupplier(supplierDTO));
    }

    @Override
    public boolean updateSuppler(SupplierDTO supplierDTO) throws SQLException {
        return supplierDAO.update(converter.toSupplier(supplierDTO));
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException {
        return supplierDAO.delete(id);
    }

    @Override
    public List<SupplierDTO> getAllSuppler() throws SQLException {
        return supplierDAO.getAll().stream().map(s -> converter.formSupplier(s)).collect(Collectors.toList());
    }

    @Override
    public SupplierDTO searchSuppler(String id) throws SQLException {
        final Optional<Supplier> search = supplierDAO.search(id);
        if(!search.isPresent())return null;
        return converter.formSupplier(search.get());
    }

    @Override
    public String generateSupplierID() throws SQLException {
        return supplierDAO.generateID();
    }

    @Override
    public boolean updateSupplierRecode(String id, int recode) throws SQLException {
        return supplierDAO.updateSupplierRecode(id,recode);
    }
}
