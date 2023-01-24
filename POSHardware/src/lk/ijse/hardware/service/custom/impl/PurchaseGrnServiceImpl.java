package lk.ijse.hardware.service.custom.impl;

import lk.ijse.hardware.dao.DAOType;
import lk.ijse.hardware.dao.DaoFactory;
import lk.ijse.hardware.dao.custom.GrnDAO;
import lk.ijse.hardware.dao.custom.GrnDetailDAO;
import lk.ijse.hardware.dao.custom.impl.GrnDAOImpl;
import lk.ijse.hardware.dao.custom.impl.GrnDetailDAOImpl;
import lk.ijse.hardware.db.DBConnection;
import lk.ijse.hardware.dto.GrnDTO;
import lk.ijse.hardware.dto.GrnDetailDTO;
import lk.ijse.hardware.service.custom.PurchaseGrnService;
import lk.ijse.hardware.service.util.ServiceConverter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseGrnServiceImpl implements PurchaseGrnService {
    GrnDAO grnDAO = (GrnDAOImpl) DaoFactory.getInstance().getDAO(DAOType.GRN);
    GrnDetailDAO grnDetailDAO = (GrnDetailDAOImpl) DaoFactory.getInstance().getDAO(DAOType.GRN_DETAIL);
    ServiceConverter converter = new ServiceConverter();

    @Override
    public boolean saveGrn(GrnDTO grnDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        final boolean save = grnDAO.save(converter.toGrn(grnDTO));
        if (!save) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        } else {
            if (!grnDetailDAO.saveAll(grnDTO.getGrnDetailDTOList().stream().map(g -> converter.toGrnDetail(g)).collect(Collectors.toList()))) {
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
    public boolean updateGrn(GrnDTO grnDTO) throws SQLException {
        return grnDAO.update(converter.toGrn(grnDTO));
    }

    @Override
    public boolean deleteGrn(String id) throws SQLException {
        return grnDAO.delete(id);
    }

    @Override
    public List<GrnDTO> getAllGrn() throws SQLException {
        return grnDAO.getAll().stream().map(g -> converter.formGrnDTO(g)).collect(Collectors.toList());
    }

    @Override
    public GrnDTO searchGrn(String id) throws SQLException {
        if(!grnDAO.search(id).isPresent())return null;
        return converter.formGrnDTO(grnDAO.search(id).get());
    }

    @Override
    public String generateGrnID() throws SQLException {
        return grnDAO.generateID();
    }

    @Override
    public List<GrnDetailDTO> searchGrnDetail(String ID) throws SQLException {
        return grnDetailDAO.searchDetail(ID).stream().map(g -> converter.formGrnDetailDTO(g)).collect(Collectors.toList());
    }

    @Override
    public boolean saveGrnDetail(GrnDetailDTO grnDetailDTO) throws SQLException {
        return grnDetailDAO.save(converter.toGrnDetail(grnDetailDTO));
    }

    @Override
    public boolean saveAllGrnDetail(List<GrnDetailDTO> list) throws SQLException {
        return grnDetailDAO.saveAll(list.stream().map(g -> converter.toGrnDetail(g)).collect(Collectors.toList()));
    }

    @Override
    public List<GrnDetailDTO> searchDetailGrnDetail(String ID) throws SQLException {
        return grnDetailDAO.searchDetail(ID).stream().map(g -> converter.formGrnDetailDTO(g)).collect(Collectors.toList());
    }
}
