package lk.ijse.hardware.dao.util;

import lk.ijse.hardware.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtil {
    public static <T> T execute(String sql,Object... arg) throws SQLException {
        final PreparedStatement st = DBConnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < arg.length; i++) {
                st.setObject(i+1,arg[i]);
        }
        if(sql.startsWith("SELECT") | sql.startsWith("select") | sql.startsWith("Select")){
            return (T)st.executeQuery();
        }
        return (T)(Boolean)(st.executeUpdate()>1);
    }
}
