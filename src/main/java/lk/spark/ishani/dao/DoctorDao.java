package lk.spark.ishani.dao;

import lk.spark.ishani.database.DBConnectionPool;
import lk.spark.ishani.model.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorDao {

    public String regDoctor(Doctor doctor) {

        Connection con = null;
        PreparedStatement stmt = null;
        int result = 0;

        try {
            con = DBConnectionPool.getInstance().getConnection();

            stmt = con.prepareStatement("INSERT INTO doctor (id, name, hospital_id, is_director) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, doctor.getId());
            stmt.setString(2, doctor.getName());
            stmt.setString(3, doctor.getHospital_id());
            stmt.setBoolean(4, doctor.isIs_director());

            System.out.println(stmt);
            result = stmt.executeUpdate();

            if (result != 0)
                return "success";//inserted successfully

        } catch (SQLException e) {
            // process sql exception

        }
        return "Failed";//insertion failed
    }


}
