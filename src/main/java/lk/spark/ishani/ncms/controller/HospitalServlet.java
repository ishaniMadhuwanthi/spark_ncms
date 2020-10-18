package lk.spark.ishani.ncms.controller;

import lk.spark.ishani.ncms.models.Hospital;
import lk.spark.ishani.ncms.database.DBConnectionPool;
import lk.spark.ishani.ncms.dao.HospitalDao;
import lk.spark.ishani.ncms.models.Bed;
import lk.spark.ishani.ncms.models.Doctor;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "HospitalServlet")
public class HospitalServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String hospital_id = request.getParameter("hospital_id");
        String name = request.getParameter("name");
        String district = request.getParameter("district");
        String x_location = request.getParameter("x_location");
        String y_location = request.getParameter("y_location");

        Hospital hospital = new Hospital();
        hospital.setHospital_id(hospital_id);
        hospital.setName(name);
        hospital.setDistrict(district);
        hospital.setX_location(x_location);
        hospital.setY_location(y_location);

        HospitalDao hospitalDao = new HospitalDao();
        String hospitalRegistered = hospitalDao.registerHospital(hospital);

        if(hospitalRegistered.equals("SUCCESS"))   //On success, you can display a message to user on Home page
        {
            System.out.println("Success");
        }
        else   //On Failure, display a meaningful message to the User.
        {
            System.out.println("Failed");
        }

        String patient_id = request.getParameter("patient_id");
        hospital_id = request.getParameter("hospital_id");

        Doctor doctor = new Doctor();
        doctor.dischargePatients(patient_id, hospital_id);

        Bed bed = new Bed();
        bed.makeAvailable(patient_id, hospital_id);

        try {
            hospitalDao.registerHospital(hospital);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hospital_id = request.getParameter("hospital_id");

        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;

        try {
            connection = DBConnectionPool.getInstance().getConnection();
            ResultSet resultSet;

            statement = connection.prepareStatement("SELECT * FROM hospital WHERE hospital_id=?");
            statement.setString(1, hospital_id);
            System.out.println(statement);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                hospital_id = resultSet.getString("hospital_id");
                String name = resultSet.getString("name");
                String district=resultSet.getString("district");
                String x_location = resultSet.getString("x_location");
                String y_location = resultSet.getString("y_location");

                PrintWriter printWriter = response.getWriter();

                printWriter.println("Id: " + hospital_id);
                printWriter.println("Name: " + name);
                printWriter.println("District: " + district);
                printWriter.println("Location_X: " + x_location);
                printWriter.println("Location_Y: " + y_location);

                System.out.println("doGet hospital success");

            }
            connection.close();

        } catch (Exception exception) {

        }
    }

    /* Discharge patient by director and make the bed available for other patients */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        PreparedStatement statement = null;

        String patient_id = request.getParameter("patient_id");
        String hospital_id = request.getParameter("hospital_id");

        Doctor doctor = new Doctor();
        doctor.dischargePatients(patient_id, hospital_id);

        Bed bed = new Bed();
        bed.makeAvailable(patient_id, hospital_id);
    }



}
