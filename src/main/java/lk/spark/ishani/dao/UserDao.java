package lk.spark.ishani.dao;

import lk.spark.ishani.database.DBConnectionPool;
import lk.spark.ishani.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

    public String regUser(User user) {

        try {
            Connection con = DBConnectionPool.getInstance().getConnection();

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO user (email, password, moh, hospital) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setBoolean(3, user.isIsmoh());
            pstmt.setBoolean(4, user.isIsdoctor());

            System.out.println(pstmt);
            int result = pstmt.executeUpdate();

            if (result != 0)
                return "success";

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "failed";
    }


}
