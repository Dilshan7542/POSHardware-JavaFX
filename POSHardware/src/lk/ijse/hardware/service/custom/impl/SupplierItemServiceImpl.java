package lk.ijse.hardware.service.custom.impl;

import lk.ijse.hardware.dao.DAOType;
import lk.ijse.hardware.dao.DaoFactory;
import lk.ijse.hardware.dao.custom.SupplierItemDAO;
import lk.ijse.hardware.dao.custom.impl.SupplierItemDAOImpl;
import lk.ijse.hardware.dto.SupplierItemDTO;
import lk.ijse.hardware.entity.SupplierItem;
import lk.ijse.hardware.service.custom.SupplierItemService;
import lk.ijse.hardware.service.util.ServiceConverter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SupplierItemServiceImpl implements SupplierItemService {
    SupplierItemDAO supplierItemDAO=(SupplierItemDAOImpl) DaoFactory.getInstance().getDAO(DAOType.SUPPLIER_ITEM);
    ServiceConverter converter=new ServiceConverter();
    @Override
    public boolean updateCostPrice(SupplierItemDTO supplierItem) throws SQLException {
        return supplierItemDAO.updateCostPrice(converter.toSupplierItem(supplierItem));
    }

    @Override
    public boolean updateSupplierRecodeAndCost(List<SupplierItemDTO> list) throws SQLException {
        return supplierItemDAO.updateSupplierRecodeAndCost(list.stream().map(s -> converter.toSupplierItem(s)).collect(Collectors.toList()));
    }

    @Override
    public boolean saveSupplerItem(SupplierItemDTO supplierItem) throws SQLException {
        return supplierItemDAO.save(converter.toSupplierItem(supplierItem));
    }

    @Override
    public boolean updateSupplerItem(SupplierItemDTO supplierItemDTO) throws SQLException {
        return supplierItemDAO.update(converter.toSupplierItem(supplierItemDTO));
    }

    @Override
    public boolean deleteSupplerItem(String id) throws SQLException {
        return supplierItemDAO.delete(id);
    }

    @Override
    public List<SupplierItemDTO> getAllSupplerItem() throws SQLException {
        return supplierItemDAO.getAll().stream().map(s -> converter.formSupplierItem(s)).collect(Collectors.toList());
    }

    @Override
    public SupplierItemDTO searchSupplerItem(String id) throws SQLException {
        if (!supplierItemDAO.search(id).isPresent()) return null;
        return converter.formSupplierItem(supplierItemDAO.search(id).get());
    }

    @Override
    public String generateSupplerItemID() throws SQLException {
        return supplierItemDAO.generateID();
    }
}
