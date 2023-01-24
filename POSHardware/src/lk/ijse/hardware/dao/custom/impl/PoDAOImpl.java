package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.PoDAO;
import lk.ijse.hardware.entity.Po;
import lk.ijse.hardware.util.CreateNewId;
import lk.ijse.hardware.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PoDAOImpl implements PoDAO {
    @Override
    public boolean save(Po entity) throws SQLException {
        return   CrudUtil.execute("INSERT INTO po VALUES (?,?,?,?,?,?)",
                entity.getPoId(),
                entity.getArthur(),
                entity.getDate(),
                entity.getTime(),
                entity.getSpId(),
                entity.getRequestDate()
        );

    }

    @Override
    public boolean update(Po entity) throws SQLException {
        return CrudUtil.execute("UPDATE po SET arthur=?,date=?,time=?,spId=?,requestDate=? WHERE poId=?",
                entity.getArthur(),
                entity.getDate(),
                entity.getTime(),
                entity.getSpId(),
                entity.getRequestDate(),
                entity.getPoId());
    }



    @Override
    public List<Po> getAll() throws SQLException {
        ResultSet rest = CrudUtil.execute("SELECT * FROM po");
        ArrayList<Po> poList=new ArrayList<>();
        while(rest.next()){
            poList.add(new Po(rest.getString(1),
                    rest.getString(2),
                    rest.getDate(3),
                    rest.getTime(4),
                    rest.getString(5),
                    rest.getDate(6)));
        }
        return poList;
    }

    @Override
    public Optional<Po> search(String id) throws SQLException {
        ResultSet rest= CrudUtil.execute("SELECT * FROM po WHERE poId=?",id);
        if(rest.next()){
            return Optional.of( new Po(
                    rest.getString(1),
                    rest.getString(2),
                    rest.getDate(3),
                    rest.getTime(4),
                    rest.getString(5),
                    rest.getDate(6)));
        }

        return Optional.empty();
    }

    @Override
    public String generateID() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT poId FROM po ORDER BY poId DESC LIMIT 1");
        if (rst.next()) {
            return CreateNewId.generateId("P", rst.getString(1));
        } else {
            return CreateNewId.generateId("P", null);
        }
    }
    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }
}
