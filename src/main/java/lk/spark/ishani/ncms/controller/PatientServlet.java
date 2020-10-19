package lk.spark.ishani.ncms.controller;

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

        if(patientRegistered.equals("SUCCESS")) { //On success, you can display a message to user on Home page

            System.out.println("Success");
        } else {  //On Failure, display a meaningful message to the User.
            System.out.println("Failed");
        }

        try {
            patientDao.registerPatient(patient);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String contact = resultSet.getString("contact");
                String district = resultSet.getString("district");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");
                int x_location = resultSet.getInt("location_x");
                int y_location = resultSet.getInt("location_y");

                PrintWriter printWriter = response.getWriter();

                printWriter.println("Id: " + patient_id);
                printWriter.println("First name: " + first_name);
                printWriter.println("Last name: " + last_name);
                printWriter.println("Contact: " + contact);
                printWriter.println("District: " + district);
                printWriter.println("Email: " + email);
                printWriter.println("Age: " + age);
                printWriter.println("Location_X: " + x_location);
                printWriter.println("Location_Y: " + y_location);

                System.out.println("doGet patient success");

                Hospital hospital = new Hospital();
                String nearestHospital = hospital.assignHospital(x_location, y_location);
                System.out.println("Nearest hospital: " + nearestHospital);

                Bed bed = new Bed();
                int bed_id = bed.allocateBed(nearestHospital, patient_id);
                System.out.println("Bed ID: " + bed_id);
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

}
