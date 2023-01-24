package lk.ijse.hardware.service.custom.impl;

import lk.ijse.hardware.dao.DAOType;
import lk.ijse.hardware.dao.DaoFactory;
import lk.ijse.hardware.dao.custom.PoDAO;
import lk.ijse.hardware.dao.custom.PoDetailDAO;
import lk.ijse.hardware.dao.custom.impl.PoDAOImpl;
import lk.ijse.hardware.dao.custom.impl.PoDetailDAOImpl;
import lk.ijse.hardware.db.DBConnection;
import lk.ijse.hardware.dto.PoDTO;
import lk.ijse.hardware.dto.PoDetailDTO;
import lk.ijse.hardware.entity.Po;
import lk.ijse.hardware.service.custom.PurchasePoService;
import lk.ijse.hardware.service.util.ServiceConverter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PurchasePoServiceImpl implements PurchasePoService {
    PoDAO poDAO = (PoDAOImpl) DaoFactory.getInstance().getDAO(DAOType.PO);
    PoDetailDAO poDetailDAO = (PoDetailDAOImpl) DaoFactory.getInstance().getDAO(DAOType.PO_DETAIL);
    ServiceConverter converter = new ServiceConverter();

    @Override
    public boolean savePO(PoDTO poDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        if (!poDAO.save(converter.toPo(poDTO))) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        } else {
            boolean isSave = poDetailDAO.saveAll(poDTO.getPoDetailDTOList().stream().map(p -> converter.toPoDetail(p)).collect(Collectors.toList()));
            if (!isSave) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public boolean updatePO(PoDTO poDTO) throws SQLException {
        return poDAO.update(converter.toPo(poDTO));

    }

    @Override
    public boolean deletePO(String id) throws SQLException {
        return poDAO.delete(id);
    }

    @Override
    public List<PoDTO> getAllPO() throws SQLException {
        return poDAO.getAll().stream().map(p -> converter.formPoDTO(p)).collect(Collectors.toList());
    }

    @Override
    public PoDTO searchPO(String id) throws SQLException {
        final Optional<Po> search = poDAO.search(id);
        if(!search.isPresent())return null;
        return converter.formPoDTO(search.get());
    }

    @Override
    public String generatePOID() throws SQLException {
        return poDAO.generateID();
    }

    @Override
    public List<PoDetailDTO> getAllPoListDetail() throws SQLException {
        return poDetailDAO.getAllPoListDetail().stream().map(list -> converter.formPoDetailDTO(list)).collect(Collectors.toList());
    }

    @Override
    public boolean savePoList(PoDetailDTO list) throws SQLException {
        return poDetailDAO.save(converter.toPoDetail(list));
    }

    @Override
    public boolean saveAllPoList(List<PoDetailDTO> list) throws SQLException {
        return poDetailDAO.saveAll(list.stream().map(l -> converter.toPoDetail(l)).collect(Collectors.toList()));
    }

    @Override
    public List<PoDetailDTO> searchPoDetail(String ID) throws SQLException {
        return poDetailDAO.searchDetail(ID).stream().map(l -> converter.formPoDetailDTO(l)).collect(Collectors.toList());
    }
}
