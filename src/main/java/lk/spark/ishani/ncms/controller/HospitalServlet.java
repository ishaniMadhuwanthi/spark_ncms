package lk.spark.ishani.ncms.controller;

import com.google.gson.JsonObject;
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


    /*
    ------------insert hospital-------------
     */
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
            e.printStackTrace();
        }
    }


     /*
    ------------view hospital-------------
     */

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

//                printWriter.println( hospital_id);
//                printWriter.println( name);
//                printWriter.println( district);
//                printWriter.println(x_location);
//                printWriter.println( y_location);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("hospital_id", hospital_id);
                jsonObject.addProperty("name", name);
                jsonObject.addProperty("district", district);
                jsonObject.addProperty("x_location", x_location);
                jsonObject.addProperty("y_location", y_location);
                printWriter.print(jsonObject.toString());

                System.out.println("doGet hospital success");

            }
            connection.close();

        } catch (Exception exception) {

        }
    }

    /* Discharge patient by director and make the bed available for other patients
    * ---------delete hospital--------------
    * */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;

        try{
            connection = DBConnectionPool.getInstance().getConnection();

            String hospital_id = request.getParameter("hospital_id");
            int result1=0;
            int result2=0;

            statement1 = connection.prepareStatement("DELETE FROM beds WHERE hospital_id=?");
            statement1.setString(1, hospital_id);
            System.out.println(statement1);
            statement1.executeUpdate();

            statement2 = connection.prepareStatement("DELETE FROM hospital WHERE hospital_id=?");
            statement2.setString(1, hospital_id);
            System.out.println(statement2);
            statement2.executeUpdate();

            //PrintWriter writer=response.getWriter();
            if ((result1 != 0) || (result2 !=0)){
                System.out.println("Successfully deleted");//updated successfully
            }else{
                System.out.println("deletion failed");//update process failed
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        String patient_id = request.getParameter("patient_id");
        String hospital_id = request.getParameter("hospital_id");

        Hospital hospital=new Hospital();

        Doctor doctor = new Doctor();
        doctor.dischargePatients(patient_id, hospital_id);

        Bed bed = new Bed();
        bed.makeAvailable(patient_id, hospital_id);
    }


    /*
    --------------update hospital-------------
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String hospital_id = request.getParameter("hospital_id");
        String name = request.getParameter("name");
        String district = request.getParameter("district");
        String x_location = request.getParameter("x_location");
        String y_location = request.getParameter("y_location");

        try {
            Connection connection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement=null;
            int result=0;

            statement = connection.prepareStatement("UPDATE hospital SET  hospital_id=?,name=?, district=?, x_location=?,y_location=? WHERE hospital_id=?");
            ResultSet resultSet;

            statement.setString(1,hospital_id);
            statement.setString(2,name);
            statement.setString(3, district);
            statement.setString(4, x_location);
            statement.setString(5, y_location);
            statement.setString(6,hospital_id);
            System.out.println(statement);
            result = statement.executeUpdate();

            connection.close();
            PrintWriter printWriter = response.getWriter();

            JsonObject dataObject = new JsonObject();
            dataObject.addProperty("hospital_id", hospital_id);
            dataObject.addProperty("name", name);
            dataObject.addProperty("district", district);
            dataObject.addProperty("x_location", x_location);
            dataObject.addProperty("y_location", y_location);
            printWriter.print(dataObject.toString());

            printWriter.println(hospital_id);
            printWriter.println(name);
            printWriter.println(district);
            printWriter.println(x_location);
            printWriter.println(y_location);


            //System.out.println("update success");


//            result = statement.executeUpdate();
            if (result != 0){
                System.out.println("Successfully updated");//updated successfully
            }else{
                System.out.println("Update failed");//update process failed
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
