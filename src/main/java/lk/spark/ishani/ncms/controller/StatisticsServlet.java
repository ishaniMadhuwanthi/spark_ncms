package lk.spark.ishani.ncms.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lk.spark.ishani.ncms.database.DBConnectionPool;

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

@WebServlet(name = "StatisticsServlet")
public class StatisticsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    //to load statistics
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        PreparedStatement statement4 = null;
        PreparedStatement statement5 = null;
        PreparedStatement statement6 = null;
        PreparedStatement statement7 = null;

        try {
            connection = DBConnectionPool.getInstance().getConnection();
            ResultSet resultSet1;
            ResultSet resultSet2;
            ResultSet resultSet3;
            ResultSet resultSet4;
            ResultSet resultSet5;
            ResultSet resultSet6;
            ResultSet resultSet7;

            JsonObject data = new JsonObject();
            JsonArray jsonArray;

            /*
            ------------hospital level--------------
            */

            statement = connection.prepareStatement("SELECT name FROM hospital");
            resultSet1 = statement.executeQuery();
            jsonArray = new JsonArray();

            while (resultSet1.next()) {
                String  hospital_name = resultSet1.getString("name");
                statement6 = connection.prepareStatement("SELECT COUNT(beds.bed_id) AS hospitalLevel FROM beds INNER JOIN hospital ON beds.hospital_id = hospital.hospital_id WHERE hospital.name ='"+hospital_name+"'");
                resultSet6 = statement6.executeQuery();

                while (resultSet6.next()) {
                    int patientCount = resultSet6.getInt("hospitalLevel");
                    System.out.println(patientCount);



                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("name", hospital_name);
                    jsonObject.addProperty("statistics", patientCount);

                    jsonArray.add(jsonObject);

//                    PrintWriter printWriter = response.getWriter();
//
//                    printWriter.println("Hospital name: " + hospital_name);
//                    printWriter.println("Hospital Level Statistics: " + patientCount);
//                    request.setAttribute("hosPatientCount", patientCount);
//                    printWriter.println("\n");
                }

                data.add("hospitalPatients", jsonArray);
            }


             /*
            ------------district level --------------
            */

            statement7 = connection.prepareStatement("SELECT district FROM hospital");
            resultSet7 = statement7.executeQuery();
            jsonArray = new JsonArray();
            while (resultSet7.next()) {
                String  district = resultSet7.getString("district");
                statement2 = connection.prepareStatement("SELECT COUNT(beds.bed_id) AS districtLevel FROM beds INNER JOIN hospital ON beds.hospital_id = hospital.hospital_id WHERE hospital.district ='" + district + "'");
                statement5 = connection.prepareStatement("SELECT COUNT(patient_queue.id) AS queueDistrictLevel FROM patient_queue INNER JOIN patient ON patient.patient_id = patient_queue.patient_id WHERE patient.district ='" + district + "'");
                System.out.println(statement2);
                System.out.println(statement5);
                resultSet2 = statement2.executeQuery();
                resultSet5 = statement5.executeQuery();


                while (resultSet2.next()) {
                    int disPatientCount = resultSet2.getInt("districtLevel");
                    while (resultSet5.next()) {
                        int queueDisPatient = resultSet5.getInt("queueDistrictLevel");
                        int districtPatientCount = disPatientCount + queueDisPatient;
                        System.out.println(districtPatientCount);
//                        PrintWriter printWriter = response.getWriter();


                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("district", district);
                        jsonObject.addProperty("statistics", districtPatientCount);

                        jsonArray.add(jsonObject);

//                        printWriter.println("District: " + district);
//                        printWriter.println("District Level Statistics: " + districtPatientCount);
//                        request.setAttribute("disPatientCount", districtPatientCount);
//                        printWriter.println("\n");
                    }
                    data.add("districtPatients", jsonArray);
                }
            }


             /*
            ------------country level--------------
            */

            statement3 = connection.prepareStatement("SELECT COUNT(bed_id) AS countryLevel FROM beds");
            statement4 = connection.prepareStatement("SELECT COUNT(id) AS countryLevelQueue FROM patient_queue");
            resultSet3 = statement3.executeQuery();
            resultSet4 = statement4.executeQuery();
            jsonArray = new JsonArray();
            while (resultSet3.next()) {
                int hospitalPatientCount = resultSet3.getInt("countryLevel");
                while (resultSet4.next()) {
                    int queuePatientCount = resultSet4.getInt("countryLevelQueue");
                    int countryPatientCount = hospitalPatientCount + queuePatientCount;
                    System.out.println(countryPatientCount);

//                    PrintWriter printWriter = response.getWriter();

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("statistics", countryPatientCount);

                    jsonArray.add(jsonObject);
//                    printWriter.println("Country Level Statistics: " + countryPatientCount);
//                    request.setAttribute("couPatientCount", "50");
                }

                data.add("countryPatients", jsonArray);
            }

            PrintWriter printWriter = response.getWriter();
            printWriter.print(data.toString());
            connection.close();

        } catch (Exception exception) {

        }
    }
}
