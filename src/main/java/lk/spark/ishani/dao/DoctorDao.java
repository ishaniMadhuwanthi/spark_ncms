package lk.spark.ishani.dao;

import lk.spark.ishani.database.DBConnectionPool;
import lk.spark.ishani.model.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorDao {

    public String regDoctor(Doctor doctor) {

        try {
            Connection  con = DBConnectionPool.getInstance().getConnection();

            PreparedStatement stmt = con.prepareStatement("INSERT INTO doctor (doctor_id, name, hospital_id, is_director) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, doctor.getDoctor_id());
            stmt.setString(2, doctor.getName());
            stmt.setString(3, doctor.getHospital_id());
            stmt.setBoolean(4, doctor.isIs_director());

            System.out.println(stmt);
            int result = stmt.executeUpdate();

            if (result != 0)
                return "success";//inserted successfully

        } catch (SQLException e) {
            // process sql exception

        }
        return "Failed";//insertion failed
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
