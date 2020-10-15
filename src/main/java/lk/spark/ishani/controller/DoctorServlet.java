package lk.spark.ishani.controller;

import lk.spark.ishani.dao.DoctorDao;
import lk.spark.ishani.database.DBConnectionPool;
import lk.spark.ishani.model.Doctor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(name = "DoctorServlet")
public class DoctorServlet extends HttpServlet {

//insert doctor
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String hospital_id = req.getParameter("hospital_id");
        Boolean is_director = Boolean.valueOf(req.getParameter("is_director"));

        Doctor doctor = new Doctor();
        doctor.setDoctor_id(id);
        doctor.setName(name);
        doctor.setHospital_id(hospital_id);
        doctor.setIs_director(is_director);

        DoctorDao doctorDao = new DoctorDao();
        String doctorRegistered = doctorDao.regDoctor(doctor);

        if(doctorRegistered.equals("success"))  {
            System.out.println("Registered successfully"); //registered successfully
        }
        else
        {
            System.out.println("Registration Failed");//registration failed
        }

        try {
            doctorDao.regDoctor(doctor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// view doctor details
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            Doctor doctor = new Doctor(Integer.parseInt(req.getParameter("doctor_id")));
            doctor.loadDoctorData();

            System.out.println("doctor loading success");

    }

// update doctor
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int doctor_id = Integer.parseInt(req.getParameter("doctor_id"));
        String name = req.getParameter("name");
        String hospital_id = req.getParameter("hospital_id");
        Boolean is_director = Boolean.valueOf(req.getParameter("is_director"));

        try {
            Connection con = DBConnectionPool.getInstance().getConnection();

            PreparedStatement pstmt = con.prepareStatement("UPDATE doctor SET  doctor_id=?,name=?, hospital_id=?, is_director=? WHERE doctor_id=?");

            pstmt.setInt(1,doctor_id);
            pstmt.setString(2,name);
            pstmt.setString(3, hospital_id);
            pstmt.setBoolean(4, is_director);

            con.close();

            int result = pstmt.executeUpdate();
            if (result != 0){
                System.out.println("Successfully updated");//updated successfully
            }else{
                System.out.println("Update failed");//update process failed
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// delete doctor
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Connection con = DBConnectionPool.getInstance().getConnection();

            int doctor_id = Integer.parseInt(req.getParameter("id"));

            PreparedStatement pstmt = con.prepareStatement("DELETE FROM doctor WHERE doctor_id=?");
            pstmt.setInt(1, doctor_id);
            pstmt.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
