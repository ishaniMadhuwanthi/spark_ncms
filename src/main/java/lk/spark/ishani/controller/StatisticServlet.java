package lk.spark.ishani.controller;

import lk.spark.ishani.database.DBConnectionPool;

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

@WebServlet(name = "StatisticServlet")
public class StatisticServlet extends HttpServlet {

    //to view statistics separately
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Connection  con = DBConnectionPool.getInstance().getConnection();

            //to get hospital level statistics
            PreparedStatement stmt1 = con.prepareStatement("SELECT name FROM hospital");
            System.out.println(stmt1);
            ResultSet resultSet = stmt1.executeQuery();

            while (resultSet.next()) {
                String  hospital = resultSet.getString("name");
                PreparedStatement stmt2 = con.prepareStatement("SELECT COUNT(beds.bed_id) AS hospitalLevel FROM beds INNER JOIN hospital ON beds.hospital_id = hospital.hospital_id WHERE hospital.name ='"+hospital+"'");
                System.out.println(stmt2);
                ResultSet resultSet2 = stmt2.executeQuery();
                while (resultSet2.next()) {
                    int patientCount = resultSet2.getInt("hospitalLevel");
                    System.out.println(patientCount);
                    PrintWriter writer = response.getWriter();

                    writer.println("Hospital name: " + hospital);
                    writer.println("Hospital Level Statistics: " + patientCount);
                    request.setAttribute("hospitalLevel_PatientCount", patientCount);
                    writer.println("\n");
                }
            }

            //to get district level statistics
            PreparedStatement stmt3 = con.prepareStatement("SELECT district FROM hospital");
            System.out.println(stmt3);
            ResultSet resultSet3 = stmt3.executeQuery();
            while (resultSet3.next()) {
                String  district = resultSet3.getString("district");
                PreparedStatement stmt4 = con.prepareStatement("SELECT COUNT(beds.bed_id) AS districtLevel FROM beds INNER JOIN hospital ON beds.hospital_id = hospital.hospital_id WHERE hospital.district ='" + district + "'");
                PreparedStatement stmt5 = con.prepareStatement("SELECT COUNT(patient_queue.id) AS queueDistrictLevel FROM patient_queue INNER JOIN patient ON patient.patient_id = patient_queue.patient_id WHERE patient.district ='" + district + "'");
                System.out.println(stmt4);
                System.out.println(stmt5);
                ResultSet resultSet4 = stmt4.executeQuery();
                ResultSet resultSet5 = stmt5.executeQuery();

                while (resultSet4.next()) {
                    int disPatientCount = resultSet4.getInt("districtLevel");
                    while (resultSet5.next()) {
                        int queueDisPatient = resultSet5.getInt("queueDistrictLevel");
                        int districtPatientCount = disPatientCount + queueDisPatient;
                        System.out.println(districtPatientCount);
                        PrintWriter writer = response.getWriter();

                        writer.println("District: " + district);
                        writer.println("District Level Statistics: " + districtPatientCount);
                        request.setAttribute("districtLevel_PatientCount", districtPatientCount);
                        writer.println("\n");
                    }
                }
            }

            //to get country level statistics
            PreparedStatement stmt6 = con.prepareStatement("SELECT COUNT(bed_id) AS countryLevel FROM beds");
            PreparedStatement stmt7 = con.prepareStatement("SELECT COUNT(id) AS countryLevelQueue FROM patient_queue");
            System.out.println(stmt3);
            ResultSet resultSet6 = stmt6.executeQuery();
            ResultSet resultSet7 = stmt7.executeQuery();

            while (resultSet6.next()) {
                int hospitalPatientCount = resultSet3.getInt("countryLevel");
                while (resultSet7.next()) {
                    int queuePatientCount = resultSet7.getInt("countryLevelQueue");
                    int countryPatientCount = hospitalPatientCount + queuePatientCount;
                    System.out.println(countryPatientCount);

                    PrintWriter printWriter = response.getWriter();

                    printWriter.println("Country Level Statistics: " + countryPatientCount);
                    request.setAttribute("countryLevel_PatientCount", countryPatientCount);
                }
            }
            con.close();

        } catch (Exception exception) {

        }
    }

}
