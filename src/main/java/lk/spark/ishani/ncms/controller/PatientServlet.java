package lk.spark.ishani.ncms.controller;

import com.google.gson.JsonObject;
import lk.spark.ishani.ncms.database.DBConnectionPool;
import lk.spark.ishani.ncms.dao.PatientDao;
import lk.spark.ishani.ncms.dao.QueueDao;
import lk.spark.ishani.ncms.models.Bed;
import lk.spark.ishani.ncms.models.Hospital;
import lk.spark.ishani.ncms.models.Patient;

import javax.servlet.ServletException;
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

@WebServlet(name = "PatientServlet")
public class PatientServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patient_id = request.getParameter("patient_id");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String contact = request.getParameter("contact");
        String district = request.getParameter("district");
        String email = request.getParameter("email");
        String age = request.getParameter("age");
        String x_location = request.getParameter("x_location");
        String y_location =request.getParameter("y_location");


        Patient patient = new Patient();
        patient.setPatient_id(patient_id);
        patient.setFirst_name(first_name);
        patient.setLast_name(last_name);
        patient.setContact(contact);
        patient.setDistrict(district);
        patient.setEmail(email);
        patient.setAge(age);
        patient.setX_location(x_location);
        patient.setY_location(y_location);


        PatientDao patientDao = new PatientDao();
        String patientRegistered = patientDao.registerPatient(patient);

        if(!patientRegistered.equals("ERROR")) { //On success, you can display a message to user on Home page

            JsonObject json=new JsonObject();
            json.addProperty("patient_id",patient_id);

            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");

            PrintWriter writer = response.getWriter();
            writer.print(json.toString());
            System.out.println("Success");
        } else {  //On Failure, display a meaningful message to the User.
            System.out.println("Failed");
        }

        try {
            //patientDao.registerPatient(patient);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    ----------------view patient details
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patient_id = request.getParameter("patient_id");

        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        int result = 0;

        try {
            connection = DBConnectionPool.getInstance().getConnection();
            ResultSet resultSet;
            ResultSet resultSet2;

            statement = connection.prepareStatement("SELECT * FROM patient WHERE patient_id=? ");
            statement.setString(1, patient_id);
            System.out.println(statement);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                patient_id = resultSet.getString("patient_id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String contact = resultSet.getString("contact");
                String district = resultSet.getString("district");
                String email = resultSet.getString("email");
                String age = resultSet.getString("age");
                String x_location = resultSet.getString("x_location");
                String y_location = resultSet.getString("y_location");
                String admit_date= resultSet.getString("admit_date");
                String discharge_date= resultSet.getString("discharge_date");

                PrintWriter printWriter = response.getWriter();

//                printWriter.println(patient_id);
//                printWriter.println(first_name);
//                printWriter.println(last_name);
//                printWriter.println(contact);
//                printWriter.println(district);
//                printWriter.println(email);
//                printWriter.println(age);
//                printWriter.println(x_location);
//                printWriter.println(y_location);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("patient_id", patient_id);
                jsonObject.addProperty("first_name", first_name);
                jsonObject.addProperty("last_name", last_name);
                jsonObject.addProperty("contact", contact);
                jsonObject.addProperty("district", district);
                jsonObject.addProperty("email", email);
                jsonObject.addProperty("age", age);
                jsonObject.addProperty("x_location", x_location);
                jsonObject.addProperty("y_location", y_location);
                jsonObject.addProperty("admit_date", admit_date);
                jsonObject.addProperty("discharge_date", discharge_date);
               // printWriter.print(jsonObject.toString());

                System.out.println("doGet patient success");

                Hospital hospital = new Hospital();
                String nearestHospital = hospital.assignHospital(x_location, y_location);
                System.out.println("Nearest hospital: " + nearestHospital);

                jsonObject.addProperty("nearestHospital", nearestHospital);
                //printWriter.print(jsonObject.toString());


                Bed bed = new Bed();
                int bed_id = bed.allocateBed(nearestHospital, patient_id);
                System.out.println("Bed ID: " + bed_id);

                jsonObject.addProperty("bed_id", bed_id);
                printWriter.print(jsonObject.toString());

                int bedNo = 0;

                if(bed_id == 0){
                    statement2 = connection.prepareStatement("SELECT distinct hospital_id FROM hospital where hospital_id !='" + nearestHospital + "'");
                    System.out.println(statement2);
                    resultSet2 = statement2.executeQuery();

                    String hosId ="";
                    int queueLength;

                    /* Allocate a bed */
                    while(resultSet2.next()) {
                        if(bed_id==0) {
                            hosId = resultSet2.getString("hospital_id");
                            System.out.println(hosId);
                            bed_id = bed.allocateBed(hosId, patient_id);
                        }
                    }
                    /* If there is no available beds, add to queue */
                    bedNo = bed_id;
                    if(bedNo == 0){
                        QueueDao queue = new QueueDao();
                        queueLength = queue.addToQueue(patient_id);
                    }

                }
            }
            connection.close();

        } catch (Exception exception) {

        }
    }

    /*
    --------------update patient by entering admit details-------------
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String patient_id = request.getParameter("patient_id");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String contact = request.getParameter("contact");
        String district = request.getParameter("district");
        String email = request.getParameter("email");
        String age = request.getParameter("age");
        String x_location = request.getParameter("x_location");
        String y_location = request.getParameter("y_location");
        String admit_date = request.getParameter("admit_date");
        String discharge_date = request.getParameter("discharge_date");

        try {
            Connection connection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement=null;
            int result=0;

            statement = connection.prepareStatement("UPDATE patient SET  patient_id=?,first_name=?,last_name=?,contact=?, district=?,email=?,age=?, x_location=?,y_location=?,admit_date=?,discharge_date=? WHERE patient_id=?");
            ResultSet resultSet;

            statement.setString(1,patient_id);
            statement.setString(2,first_name);
            statement.setString(3,last_name);
            statement.setString(4,contact);
            statement.setString(5, district);
            statement.setString(6,email);
            statement.setString(7,age);
            statement.setString(8, x_location);
            statement.setString(9, y_location);
            statement.setString(10,admit_date);
            statement.setString(11,discharge_date);
            statement.setString(12,patient_id);
            System.out.println(statement);
            result = statement.executeUpdate();

            connection.close();
            PrintWriter printWriter = response.getWriter();

            JsonObject dataObject = new JsonObject();
            dataObject.addProperty("patient_id", patient_id);
            dataObject.addProperty("first_name", first_name);
            dataObject.addProperty("last_name", last_name);
            dataObject.addProperty("contact", contact);
            dataObject.addProperty("district", district);
            dataObject.addProperty("email", email);
            dataObject.addProperty("age", age);
            dataObject.addProperty("x_location", x_location);
            dataObject.addProperty("y_location", y_location);
            dataObject.addProperty("admit_date", admit_date);
            dataObject.addProperty("discharge_date",discharge_date);
            printWriter.print(dataObject.toString());

            printWriter.println(patient_id);
            printWriter.println(first_name);
            printWriter.println(last_name);
            printWriter.println(contact);
            printWriter.println(district);
            printWriter.println(email);
            printWriter.println(age);
            printWriter.println(x_location);
            printWriter.println(y_location);
            printWriter.println(admit_date);
            printWriter.println(discharge_date);


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
