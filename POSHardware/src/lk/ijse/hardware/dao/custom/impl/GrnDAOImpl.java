package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.GrnDAO;
import lk.ijse.hardware.entity.Grn;
import lk.ijse.hardware.util.CreateNewId;
import lk.ijse.hardware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GrnDAOImpl implements GrnDAO {
    @Override
    public boolean save(Grn entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO grn VALUES (?,?,?,?,?,?,?)",
                entity.getGrnId(),
                entity.getArthur(),
                entity.getInvoice(),
                entity.getDate(),
                entity.getTime(),
                entity.getPoId(),
                entity.getSpId());
    }


    @Override
    public List<Grn> getAll() throws SQLException {
        ResultSet rest = CrudUtil.execute("SELECT * FROM grn");
        ArrayList<Grn> grnList = new ArrayList<>();
        while (rest.next()) {
            grnList.add(new Grn(
                    rest.getString(1),
                    rest.getString(2),
                    rest.getString(3),
                    rest.getDate(4),
                    rest.getTime(5),
                    rest.getString(6),
                    rest.getString(7)));
        }
        return grnList;
    }



    @Override
    public String generateID() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT grnId FROM grn ORDER BY grnId DESC LIMIT 1");
        if (rst.next()) {
            return CreateNewId.generateId("GR", rst.getString(1));
        } else {
            return CreateNewId.generateId("GR", null);
        }
    }

    @Override
    public boolean update(Grn entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }
    @Override
    public Optional<Grn> search(String s) throws SQLException {
        return Optional.empty();
    }
}
