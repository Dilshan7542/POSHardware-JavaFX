package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.PoDetailDAO;
import lk.ijse.hardware.entity.PoDetail;
import lk.ijse.hardware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PoDetailDAOImpl implements PoDetailDAO {
    @Override
    public boolean save(PoDetail entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO poDetail VALUES (?,?,?)",
                entity.getPoId(),
                entity.getItemCode(),
                entity.getQty());
    }

    @Override
    public boolean saveAll(List<PoDetail> list) throws SQLException {
        for (PoDetail p : list) {
            if (!save(p)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<PoDetail> searchDetail(String ID) throws SQLException {
        return null;
    }

    @Override
    public List<PoDetail> getAllPoListDetail() throws SQLException {
        ResultSet rest = CrudUtil.execute("SELECT * FROM podetail");
        ArrayList<PoDetail> list = new ArrayList<>();
        while (rest.next()) {
            list.add(new PoDetail(
                    rest.getString(1),
                    rest.getString(2),
                    rest.getInt(3)));
        }
        return list;
    }
}
