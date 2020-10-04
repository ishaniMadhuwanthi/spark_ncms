package lk.spark.ishani.dao;

import lk.spark.ishani.database.DBConnectionPool;
import lk.spark.ishani.model.Patient;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientDao {
    //patient registration
    public String regPatient(Patient patient) {
        String INSERT_USERS_SQL = "INSERT INTO patient (id, first_name, last_name, contact,addrress,district,gender,email,age, x_location, y_location, severity_level, admit_date, admitted_by, discharge_date, discharged_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        Connection con = null;
        PreparedStatement stmt = null;
        int result = 0;

        try {
           // UUID uuid = UUID.randomUUID();
            con = DBConnectionPool.getInstance().getConnection();

            // Step 2:Create a statement using connection object
            stmt = con.prepareStatement(INSERT_USERS_SQL);
            stmt.setInt(1, patient.getId());
           // stmt.setString(1, uuid.toString());
            stmt.setString(2, patient.getFirst_name());
            stmt.setString(3, patient.getLast_name());
            stmt.setString(4, patient.getContact());
            stmt.setString(5, patient.getAddress());
            stmt.setString(6, patient.getDistrict());
            stmt.setString(7, patient.getGender());
            stmt.setString(8, patient.getEmail());
            stmt.setString(9, patient.getAge());
            stmt.setInt(10, patient.getX_location());
            stmt.setInt(11, patient.getY_location());
            stmt.setString(12, patient.getSeverity_level());
            stmt.setDate(13, (Date) patient.getAdmit_date());
            stmt.setInt(14, patient.getAdmitted_by());
            stmt.setDate(15, (Date) patient.getDischarge_date());
            stmt.setInt(16, patient.getDischarged_by());

            System.out.println(stmt);
            result = stmt.executeUpdate();//execute updatings

            if (result!=0)
                return "success";//successfully inserted into databse

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return "failed"; // display error msg on failure.
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