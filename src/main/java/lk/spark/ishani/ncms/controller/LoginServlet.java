package lk.spark.ishani.ncms.controller;

import com.google.gson.JsonArray;
import lk.spark.ishani.ncms.database.DBConnectionPool;
import lk.spark.ishani.ncms.models.Doctor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {

    /*
    ------------login control----------------
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonArray errorArray;

        String password=req.getParameter("doctor_id");
        String email = req.getParameter("email");

        //String encoded_password = Base64.getEncoder().encodeToString(req.getParameter("doctor_id").getBytes());

        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;

        try {
            connection = DBConnectionPool.getInstance().getConnection();
            ResultSet resultSet;

            statement = connection.prepareStatement("SELECT Count(*) AS count FROM doctor WHERE doctor_id ='" + password + "' and email ='" + email + "'");
            System.out.println(statement);

            statement.setString(1, password);
            statement.setString(2, email);

            resultSet = statement.executeQuery();


                int count=0;
                while (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
                System.out.println(count);
            if (count == 1) {

                Doctor doctor = new Doctor(password);
                doctor.getModel();


            } else {
                errorArray = new JsonArray();
                errorArray.add("Email or Password is wrong");


            }

            connection.close();
        } catch (Exception e) {
            errorArray = new JsonArray();
            errorArray.add("Database connection failed");

           }
    }
}


