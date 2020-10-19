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
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "DoctorListServlet")
public class DoctorListServlet  extends HttpServlet {


    /*
     * ------------display doctor list--------------
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonArray errorArray;

        errorArray = new JsonArray();

        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;

        try {
            connection = DBConnectionPool.getInstance().getConnection();
            ResultSet resultSet;

            JsonArray doctors = new JsonArray();

            statement = connection.prepareStatement("SELECT * FROM doctor");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                JsonObject doctor = new JsonObject();
                doctor.addProperty("doctor_id", resultSet.getString("doctor_id"));
                doctor.addProperty("name", resultSet.getString("name"));
                doctor.addProperty("email", resultSet.getString("email"));
                doctor.addProperty("hospital_id", resultSet.getInt("hospital_id"));
                doctor.addProperty("is_director", resultSet.getInt("is_director"));
                doctors.add(doctor);

            }
            System.out.println(doctors) ;
            connection.close();
        } catch (Exception exception) {
            errorArray = new JsonArray();
            errorArray.add("Database connection failed");


        }
    }
}
