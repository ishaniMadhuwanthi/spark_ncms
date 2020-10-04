package lk.spark.ishani.controller;

import lk.spark.ishani.dao.HospitalDao;
import lk.spark.ishani.database.DBConnectionPool;
import lk.spark.ishani.model.Hospital;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

@WebServlet(name = "HospitalServlet")
public class HospitalServlet extends HttpServlet {

    //view hospital details
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Hospital hospital = new Hospital(request.getParameter("id"));
        hospital.loadHospitalData();
        System.out.println("Loading success");
    }

    //insert(build) new hospital
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String district = request.getParameter("district");
        int x_location = Integer.parseInt(request.getParameter("x_location"));
        int y_location = Integer.parseInt(request.getParameter("y_location"));
        java.util.Date date_build = new java.util.Date();
        Date build_date = new Date(date_build.getTime());

        Hospital hospital = new Hospital();
        hospital.setId(id);
        hospital.setName(name);
        hospital.setDistrict(district);
        hospital.setX_location(x_location);
        hospital.setY_location(y_location);
        hospital.setBuild_date(build_date);

        HospitalDao hospitalDao = new HospitalDao();
        String hospitalRegistered = hospitalDao.regHospital(hospital);

        if(hospitalRegistered.equals("success"))
        {
            System.out.println("Successfully registered");//registered successfully
        }
        else
        {
            System.out.println("Registraion failed");//registration failed
        }

        try {
            hospitalDao.regHospital(hospital);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //update hospital details
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String district = request.getParameter("district");
        int x_location = Integer.parseInt(request.getParameter("x_location"));
        int y_location = Integer.parseInt(request.getParameter("y_location"));
        Date date_build = new Date();
        Date build_date = new Date(date_build.getTime());

        try {
            Connection con = DBConnectionPool.getInstance().getConnection();
            PreparedStatement pstmt=null;
            int result=0;

            pstmt = con.prepareStatement("UPDATE hospital SET  id=?,name=?, district=?, x_location=?,y_location=?,build_date=? WHERE id=?");

            pstmt.setString(1,id);
            pstmt.setString(2,name);
            pstmt.setString(3, district);
            pstmt.setInt(4, x_location);
            pstmt.setInt(5, y_location);
            pstmt.setDate(6, (java.sql.Date) build_date);

            con.close();

            result = pstmt.executeUpdate();
            if (result != 0){
                System.out.println("Successfully updated");//updated successfully
            }else{
                System.out.println("Update failed");//update process failed
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    //delete hospital
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            Connection con = DBConnectionPool.getInstance().getConnection();
            PreparedStatement pstmt=null;

            String id = request.getParameter("id");

            pstmt = con.prepareStatement("DELETE FROM hospital WHERE id=?");
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
