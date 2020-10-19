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

@WebServlet(name = "PatientListServlet")
public class PatientListServlet extends HttpServlet {

    /*
    * ------------display patient list--------------
    */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;

        try {
            connection = DBConnectionPool.getInstance().getConnection();
            ResultSet resultSet;
            JsonArray patients = new JsonArray();

//            statement = connection.prepareStatement("SELECT hospital.*, (SELECT COUNT(*) FROM beds WHERE beds.hospital_id = hospital.hospital_id AND beds.bed_id IS NOT NULL) AS patient_count FROM hospital INNER JOIN doctor ON doctor.hospital_id = hospital.hospital_id");
            statement = connection.prepareStatement("SELECT * FROM patient" );
            System.out.println(statement);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                JsonObject patient = new JsonObject();
                patient.addProperty("patient_id", resultSet.getString("patient_id"));
                patient.addProperty("first_name", resultSet.getString("first_name"));
                patient.addProperty("last_name", resultSet.getString("last_name"));
                patient.addProperty("contact", resultSet.getString("contact"));
                patient.addProperty("district", resultSet.getString("district"));
                patient.addProperty("email", resultSet.getString("email"));
                patient.addProperty("age", resultSet.getString("age"));
                patient.addProperty("x_location", resultSet.getString("x_location"));
                patient.addProperty("y_location", resultSet.getString("y_location"));
//                hospital.addProperty("patient_count", resultSet.getInt("patient_count"));
                patients.add(patient);

            }

            System.out.println(patients) ;
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
