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

        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;

        try {
            connection = DBConnectionPool.getInstance().getConnection();
            ResultSet resultSet;

            JsonArray doctors = new JsonArray();

            statement = connection.prepareStatement("SELECT * FROM doctor");
            System.out.println(statement);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                JsonObject doctor = new JsonObject();
                doctor.addProperty("doctor_id", resultSet.getString("doctor_id"));
                doctor.addProperty("name", resultSet.getString("name"));
                doctor.addProperty("email", resultSet.getString("email"));
                doctor.addProperty("hospital_id", resultSet.getString("hospital_id"));
                doctor.addProperty("is_director", resultSet.getBoolean("is_director"));
                doctors.add(doctor);

            }
            System.out.println(doctors) ;

            PrintWriter printWriter = resp.getWriter();
            printWriter.println(doctors.toString());

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
