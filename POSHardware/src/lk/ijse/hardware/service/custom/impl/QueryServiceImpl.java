package lk.ijse.hardware.service.custom.impl;

import lk.ijse.hardware.dao.DAOType;
import lk.ijse.hardware.dao.DaoFactory;
import lk.ijse.hardware.dao.custom.QueryDAO;
import lk.ijse.hardware.dao.custom.impl.QueryDAOImpl;
import lk.ijse.hardware.dto.CustomDTO;
import lk.ijse.hardware.entity.Custom;
import lk.ijse.hardware.service.custom.QueryService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class QueryServiceImpl implements QueryService {
    QueryDAO queryDAO=(QueryDAOImpl) DaoFactory.getInstance().getDAO(DAOType.QUERY);

    @Override
    public List<CustomDTO> getOrderAndPayment(Date start, Date end) throws SQLException {
        return queryDAO.getOrderAndPayment(start,end).stream().map(q -> new CustomDTO(
                q.getOrderId(),
                q.getDate(),
                q.getTime(),
                q.getCashier(),
                q.getCard(),
                q.getCash(),
                q.getCid())).collect(Collectors.toList());
    }
}
