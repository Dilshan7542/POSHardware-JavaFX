package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.custom.GrnDetailDAO;
import lk.ijse.hardware.dao.custom.ItemDAO;
import lk.ijse.hardware.entity.GrnDetail;
import lk.ijse.hardware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrnDetailDAOImpl implements GrnDetailDAO {
    ItemDAO itemDAO = new ItemDAOImpl();

    @Override
    public boolean save(GrnDetail entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO grndetail VALUES (?,?,?,?)",
                entity.getGrnId(),
                entity.getCostPrice(),
                entity.getQty(),
                entity.getItemCode());
    }

    @Override
    public boolean saveAll(List<GrnDetail> list) throws SQLException {
        for (GrnDetail g : list) {
            if (!save(g)) {
                return false;
            } else {
                if (!itemDAO.updateQtyAndRecodeGrn(g.getItemCode(), g.getQty())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<GrnDetail> searchDetail(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM grndetail WHERE grnId=?", id);
        ArrayList<GrnDetail> grnDetails = new ArrayList<>();
        while (rst.next()) {
            grnDetails.add(new GrnDetail(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getInt(3),
                    rst.getString(4)));
        }
        return grnDetails;
    }


}
