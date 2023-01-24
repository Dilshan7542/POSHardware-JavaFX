package lk.ijse.hardware.service.custom.impl;

import lk.ijse.hardware.dao.DAOType;
import lk.ijse.hardware.dao.DaoFactory;
import lk.ijse.hardware.dao.custom.WastageDAO;
import lk.ijse.hardware.dao.custom.impl.WastageDAOImpl;
import lk.ijse.hardware.dto.WastageDTO;
import lk.ijse.hardware.entity.Wastage;
import lk.ijse.hardware.service.custom.WastageService;
import lk.ijse.hardware.service.util.ServiceConverter;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WastageServiceImpl implements WastageService {
    WastageDAO wastageDAO = (WastageDAOImpl) DaoFactory.getInstance().getDAO(DAOType.WASTAGE);
    ServiceConverter converter = new ServiceConverter();

    @Override
    public boolean saveWastage(WastageDTO wastageDTO) throws SQLException {
        return wastageDAO.save(converter.toWastage(wastageDTO));
    }

    @Override
    public boolean updateWastage(WastageDTO wastageDTO) throws SQLException {
        return wastageDAO.update(converter.toWastage(wastageDTO));
    }

    @Override
    public boolean deleteWastage(String id) throws SQLException {
        return wastageDAO.delete(id);
    }

    @Override
    public List<WastageDTO> getAllWastage() throws SQLException {
        return wastageDAO.getAll().stream().map(w -> converter.fromWastage(w)).collect(Collectors.toList());
    }

    @Override
    public WastageDTO searchWastage(String id) throws SQLException {
        final Optional<Wastage> search = wastageDAO.search(id);
        if(!search.isPresent())return null;
        return converter.fromWastage(search.get());
    }

    @Override
    public String generateWastageID() throws SQLException {
        return wastageDAO.generateID();
    }
}
