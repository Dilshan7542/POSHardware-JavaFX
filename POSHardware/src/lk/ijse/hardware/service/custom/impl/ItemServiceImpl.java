package lk.ijse.hardware.service.custom.impl;

import lk.ijse.hardware.dao.DAOType;
import lk.ijse.hardware.dao.DaoFactory;
import lk.ijse.hardware.dao.custom.ItemDAO;
import lk.ijse.hardware.dao.custom.impl.ItemDAOImpl;
import lk.ijse.hardware.dto.ItemDTO;
import lk.ijse.hardware.service.custom.ItemService;
import lk.ijse.hardware.service.util.ServiceConverter;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ItemServiceImpl implements ItemService {
    ItemDAO itemDAO=(ItemDAOImpl) DaoFactory.getInstance().getDAO(DAOType.ITEM);
    ServiceConverter converter=new ServiceConverter();
    @Override
    public boolean updateQtyAndRecodeGrn(String itemCode, int qty) throws SQLException {
        return itemDAO.updateQtyAndRecodeGrn(itemCode,qty);
    }

    @Override
    public boolean removeQty(String itemCode, int qty) throws SQLException {
        return itemDAO.removeQty(itemCode,qty);
    }

    @Override
    public boolean saveItem(ItemDTO itemDTO) throws SQLException {
        return itemDAO.save(converter.toItem(itemDTO));
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException {
        return  itemDAO.update(converter.toItem(itemDTO));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException {
        return itemDAO.delete(id);
    }

    @Override
    public List<ItemDTO> getAllItem() throws SQLException {
        return itemDAO.getAll().stream().map(i -> converter.formItem(i)).collect(Collectors.toList());
    }

    @Override
    public ItemDTO searchItem(String id) throws SQLException {
       if(!itemDAO.search(id).isPresent())return null;
        return converter.formItem(itemDAO.search(id).get());
    }

    @Override
    public String generateItemID() throws SQLException {
        return itemDAO.generateID();
    }
}
