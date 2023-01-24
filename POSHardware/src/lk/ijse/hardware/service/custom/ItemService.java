package lk.ijse.hardware.service.custom;

import lk.ijse.hardware.dto.ItemDTO;
import lk.ijse.hardware.service.SuperService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ItemService extends SuperService {
    boolean updateQtyAndRecodeGrn(String itemCode,int qty) throws SQLException;
    boolean removeQty(String itemCode,int qty) throws SQLException;
    boolean saveItem(ItemDTO itemDTO)throws SQLException;
    boolean updateItem(ItemDTO itemDTO)throws SQLException;
    boolean deleteItem(String id)throws SQLException;
    List<ItemDTO> getAllItem()throws SQLException;
    ItemDTO searchItem(String id)throws SQLException;
    String generateItemID()throws SQLException;
}
