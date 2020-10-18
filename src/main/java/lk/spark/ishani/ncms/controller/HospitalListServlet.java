package lk.spark.ishani.ncms.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lk.spark.ishani.ncms.database.DBConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "HospitalListServlet")
public class HospitalListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;

        try {
            connection = DBConnectionPool.getInstance().getConnection();
            ResultSet resultSet;
            JsonArray hospitals = new JsonArray();

            statement = connection.prepareStatement("SELECT hospital.*, (SELECT COUNT(*) FROM beds WHERE beds.hospital_id = hospital.hospital_id AND beds.bed_id IS NOT NULL) AS patient_count FROM hospital INNER JOIN doctor ON doctor.doctor_id = hospital.hospital_id");
            System.out.println(statement);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                JsonObject hospital = new JsonObject();
                hospital.addProperty("hospital_id", resultSet.getString("hospital_id"));
                hospital.addProperty("name", resultSet.getString("name"));
                hospital.addProperty("district", resultSet.getString("district"));
                hospital.addProperty("x_location", resultSet.getString("x_location"));
                hospital.addProperty("y_location", resultSet.getString("y_location"));
                hospital.addProperty("patient_count", resultSet.getInt("patient_count"));
                hospitals.add(hospital);
            }


            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



