package lk.spark.ishani.ncms.dao;

import lk.spark.ishani.ncms.database.DBConnectionPool;
import lk.spark.ishani.ncms.models.Doctor;
import lk.spark.ishani.ncms.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public String viewStatistics(User user) {
        String INSERT_USERS_SQL = "INSERT INTO user (email,moh_id,name) VALUES (?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            connection = DBConnectionPool.getInstance().getConnection();


            preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getMoh_id());
            preparedStatement.setString(3, user.getName());


            System.out.println(preparedStatement);

            result = preparedStatement.executeUpdate();

            if (result != 0)
                return "SUCCESS";

        } catch (SQLException e) {

            printSQLException(e);
        }
        return "Something wrong!";
    }

    /*
    ------------moh login------------
     */
    public String loginMoh(User user) {
        String INSERT_USERS_SQL = "SELECT Count(*) AS count FROM user WHERE moh_id ='" + user.getMoh_id() + "' and email ='" + user.getEmail() + "'";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            connection = DBConnectionPool.getInstance().getConnection();
            ResultSet resultSet;

            preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();

            int x=0;
            while (resultSet.next()) {
                int id_count=resultSet.getInt("count");
                System.out.println(id_count);

            }x=x+1;



            if (x == 1)
                return "SUCCESS";

        } catch (SQLException e) {
            printSQLException(e);
        }
        return "something wrong!";
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
