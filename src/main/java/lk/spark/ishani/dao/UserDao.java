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

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO user (username, password, name, moh, hospital) VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setBoolean(4, user.isMoh());
            pstmt.setBoolean(5, user.isDoctor());

            System.out.println(pstmt);
            int result = pstmt.executeUpdate();

            if (result != 0)
                return "success";

        } catch (SQLException e) {
            // process sql exception

        }
        return "failed";
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable tmsg = ex.getCause();
                while (tmsg != null) {
                    System.out.println("Cause: " + tmsg);
                    tmsg = tmsg.getCause();
                }
            }
        }
    }
}
