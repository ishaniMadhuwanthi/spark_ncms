package lk.spark.ishani.dao;

import lk.spark.ishani.database.DBConnectionPool;
import lk.spark.ishani.model.Hospital;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HospitalDao {
    public String regHospital(Hospital hospital) {
       String INSERT_USERS_SQL = "INSERT INTO hospital (id, name, district, x_location, y_location, build_date) VALUES (?, ?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement stmt = null;
        int result = 0;

        try {
            con = DBConnectionPool.getInstance().getConnection();

            // Create a statement using connection object
            stmt = con.prepareStatement(INSERT_USERS_SQL);
            stmt.setString(1, hospital.getId());
            stmt.setString(2, hospital.getName());
            stmt.setString(3, hospital.getDistrict());
            stmt.setInt(4, hospital.getX_location());
            stmt.setInt(5, hospital.getY_location());
            stmt.setDate(6, (Date) hospital.getBuild_date());

            System.out.println(stmt);
            //Execute the query or update query
            result = stmt.executeUpdate();

            if (result != 0)
                return "success";//successfully inserted into databse

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return "failed"; // insertion failed into database
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
