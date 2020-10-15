package lk.spark.ishani.controller;

import lk.spark.ishani.dao.PatientDao;
import lk.spark.ishani.dao.QueueDao;
import lk.spark.ishani.database.DBConnectionPool;
import lk.spark.ishani.model.Bed;
import lk.spark.ishani.model.Hospital;
import lk.spark.ishani.model.Patient;

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
import java.util.Date;

@WebServlet(name = "PatientServlet")
public class PatientServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int patient_id = Integer.parseInt(request.getParameter("patient_"));
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String contact = request.getParameter("contact");
        String address = request.getParameter("address");
        String district = request.getParameter("district");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String age = request.getParameter("age");
        int x_location = Integer.parseInt(request.getParameter("x_location"));
        int y_location = Integer.parseInt(request.getParameter("y_location"));
        String severity_level = request.getParameter("severity_level");

        java.util.Date date_admit = new java.util.Date();
        Date admit_date =  new Date(date_admit.getTime());

        int admitted_by = Integer.parseInt(request.getParameter("admitted_by"));

        java.util.Date date_discharged = new java.util.Date();
        Date discharge_date = new Date(date_discharged.getTime());

        int discharged_by = Integer.parseInt(request.getParameter("discharged_by"));
        String serial_no = request.getParameter("serial_no");

        Patient patient = new Patient();
        patient.setPatient_id(patient_id);
        patient.setFirst_name(first_name);
        patient.setLast_name(last_name);
        patient.setContact(contact);
        patient.setAddress(address);
        patient.setDistrict(district);
        patient.setGender(gender);
        patient.setEmail(email);
        patient.setAge(age);
        patient.setX_location(x_location);
        patient.setY_location(y_location);
        patient.setSeverity_level(severity_level);
        patient.setAdmit_date(admit_date);
        patient.setAdmitted_by(admitted_by);
        patient.setDischarge_date(discharge_date);
        patient.setDischarged_by(discharged_by);
        patient.setSerial_no(serial_no);

        PatientDao patientDao = new PatientDao();
        String patientReg = patientDao.regPatient(patient);

        if(patientReg.equals("success"))   //On success, you can display a message to user on Home page
        {
            System.out.println("Successfully Registered");
        }
        else   //On Failure
        {
            System.out.println("Registration Failed");
        }

        try {
            patientDao.regPatient(patient);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //view patient details
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        Patient patient = new Patient(Integer.parseInt(request.getParameter("patient_id")));
        patient.loadPatientData();
        System.out.println("patient loading Success");

        Hospital hospital = new Hospital();
        String nearestHospital = hospital.getDistance(patient.getX_location(),patient.getY_location());
        //System.out.println("Nearest hospital: " + nearestHospital);
        PrintWriter writer = new PrintWriter(System.out);
        writer.write(nearestHospital);
        writer.flush();

        Bed bed =new Bed();
        int selectedBed=bed.assignBed(nearestHospital, bed.getBed_id());
//        writer.write(selectedBed);
//        writer.flush();

        try {
            Connection con = DBConnectionPool.getInstance().getConnection();

            if (selectedBed == 0) {
                PreparedStatement stmt = con.prepareStatement("SELECT distinct hospital_id FROM hospital where hospital_id !='" + nearestHospital + "'");
                ResultSet resultSet= stmt.executeQuery();
                String h_id="";

                //to assign a bed for patient
                while(resultSet.next()){
                    if(selectedBed ==0){
                        h_id = resultSet.getString("hospital_id");
                        selectedBed = bed.assignBed(h_id, patient.getPatient_id());
                    }
                }

                //add patient to queue
                Hospital hospitalObj=new Hospital();
                int freeBeds=hospitalObj.getAvailableBedCount(h_id);
                if(freeBeds ==0){
                    QueueDao queue= new QueueDao();
                   int length=queue.addToQueue(patient.getPatient_id());
                }
            }
        }catch(Exception e){
           e.printStackTrace();
        }
    }

}