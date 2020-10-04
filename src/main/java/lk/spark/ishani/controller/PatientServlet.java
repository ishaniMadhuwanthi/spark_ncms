package lk.spark.ishani.controller;

import lk.spark.ishani.dao.PatientDao;
import lk.spark.ishani.model.Patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "PatientServlet")
public class PatientServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
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

        Patient patient = new Patient();
        patient.setId(id);
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //view patient details
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Patient patient = new Patient(Integer.parseInt(request.getParameter("id")));
        patient.loadPatientData();
        System.out.println("Loading Success");
    }

}