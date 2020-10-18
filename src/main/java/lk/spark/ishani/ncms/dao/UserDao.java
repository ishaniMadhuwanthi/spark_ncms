package lk.spark.ishani.ncms.dao;

import lk.spark.ishani.ncms.database.DBConnectionPool;
import lk.spark.ishani.ncms.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
    public String viewStatistics(User user) {
        String INSERT_USERS_SQL = "INSERT INTO user (email, password, moh, hospital) VALUES (?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            connection = DBConnectionPool.getInstance().getConnection();


            preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(4, user.isMoh());
            preparedStatement.setBoolean(5, user.isHospital());

            System.out.println(preparedStatement);

            result = preparedStatement.executeUpdate();

            if (result != 0)
                return "SUCCESS";

        } catch (SQLException e) {

            printSQLException(e);
        }
        return "Oops.. Something went wrong there..!";
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
