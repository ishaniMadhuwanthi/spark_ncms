package lk.spark.ishani.ncms.controller;

import lk.spark.ishani.ncms.database.DBConnectionPool;
import lk.spark.ishani.ncms.dao.DoctorDao;
import lk.spark.ishani.ncms.models.Doctor;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "DoctorServlet")
public class DoctorServlet extends HttpServlet {

    /*
    -----------insert doctor---------------
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String doctor_id = request.getParameter("doctor_id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String hospital_id = request.getParameter("hospital_id");
        Boolean is_director = Boolean.valueOf(request.getParameter("is_director"));

        Doctor doctor = new Doctor();
        doctor.setDoctor_id(doctor_id);
        doctor.setName(name);
        doctor.setEmail(email);
        doctor.setHospital_id(hospital_id);
        doctor.setIs_director(is_director);

        DoctorDao doctorDao = new DoctorDao();
        String doctorRegistered = doctorDao.registerDoctor(doctor);

        if(doctorRegistered.equals("SUCCESS"))   //On success, you can display a message to user on Home page
        {
            System.out.println("Success");
        }
        else   //On Failure, display a meaningful message to the User.
        {
            System.out.println("Failed");
        }

        try {
            doctorDao.registerDoctor(doctor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    --------------view doctor details--------------
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String doctor_id = request.getParameter("doctor_id");

        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;

        try {
            connection = DBConnectionPool.getInstance().getConnection();
            ResultSet resultSet;

            statement = connection.prepareStatement("SELECT * FROM doctor WHERE doctor_id=?");
            statement.setString(1, doctor_id);
            System.out.println(statement);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String hospital_id = resultSet.getString("hospital_id");
                Boolean is_director = resultSet.getBoolean("is_director");

                System.out.println("Id: " + doctor_id);
                System.out.println("Name: " + name);
                System.out.println("Email: "+email);
                System.out.println("HospitalId: " + hospital_id);
                System.out.println("Is Director: " + is_director);
                System.out.println("doGet doctor success");

                PrintWriter printWriter = response.getWriter();

                printWriter.println("Id: " + doctor_id);
                printWriter.println("Name: " + name);
                printWriter.println("Email: "+email);
                printWriter.println("HospitalId: " + hospital_id);
                printWriter.println("Is Director: " + is_director);
                System.out.println("doGet doctor success");

                JSONObject obj = new JSONObject();

                obj.put("doctor_id",doctor_id);
                obj.put("name",name);
                obj.put("email",email);
                obj.put("hospital_id",hospital_id);
                obj.put("is_director",is_director);

                StringWriter out = new StringWriter();
                obj.writeJSONString(out);

                String jsonText = out.toString();
                System.out.print(jsonText);
            }
            connection.close();

        } catch (Exception exception) {

        }
    }

    /*
    ------------delete doctor-------
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Connection con = DBConnectionPool.getInstance().getConnection();

            String doctor_id = req.getParameter("doctor_id");

            PreparedStatement pstmt = con.prepareStatement("DELETE FROM doctor WHERE doctor_id=?");
            pstmt.setString(1, doctor_id);
            pstmt.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    /*
    ------------update doctor details--------------
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String doctor_id = req.getParameter("doctor_id");
            String name = req.getParameter("name");
            String hospital_id = req.getParameter("hospital_id");
            Boolean is_director = Boolean.valueOf(req.getParameter("is_director"));

            Connection con = DBConnectionPool.getInstance().getConnection();

            PreparedStatement pstmt = con.prepareStatement("UPDATE doctor SET  doctor_id=?,name=?, hospital_id=?, is_director=? WHERE doctor_id=?");

            pstmt.setString(1,doctor_id);
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
        } catch( SQLException ex){
            ex.printStackTrace();
        }
    }

}
