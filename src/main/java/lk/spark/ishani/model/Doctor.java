package lk.spark.ishani.model;

import com.google.gson.JsonObject;
import lk.spark.ishani.database.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {

    private int doctor_id;
    private String name;
    private String hospital_id;
    private boolean is_director;

    public Doctor(){

    }
    public Doctor(int doctor_id){
        this.doctor_id=doctor_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public boolean isIs_director() {
        return is_director;
    }

    public void setIs_director(boolean is_director) {
        this.is_director = is_director;
    }

    //serialize doctor details to json objects
    public JsonObject serialize() {
        JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty("doctor_id", this.doctor_id);
        jsonObj.addProperty("name", this.name);
        jsonObj.addProperty("hospital_id", this.hospital_id);
        jsonObj.addProperty("is_director", this.is_director);

        return jsonObj;
    }

    //load data into database
    public void loadDoctorData(){
        try{
            Connection con = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement;
            ResultSet resultSet;

            statement = con.prepareStatement("SELECT * FROM doctor WHERE doctor_id=?");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                this.doctor_id = resultSet.getInt("doctor_id");
                this.name = resultSet.getString("name");
                this.hospital_id = resultSet.getString("hospital_id");
                this.is_director = resultSet.getBoolean("is_director");
            }

            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //to discharge a patient
    public void dischargePatient(int patient_id, String hospital_id) {

        try {
            Connection con = DBConnectionPool.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM doctor WHERE hospital_id='" +hospital_id + "' AND is_director=1");
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int director = resultSet.getInt("doctor_id");
                System.out.println(director);
                PreparedStatement stmt = con.prepareStatement("UPDATE patient SET discharge_date=? , discharged_by= '" + director + "' WHERE patient_id = '" + patient_id + "'");

                stmt.setDate(1, new java.sql.Date(new java.util.Date().getTime()));

                int result = stmt.executeUpdate();

                if(result !=0){
                    System.out.println("success");
                }else
                    System.out.println("Failed");
            }

            System.out.println("doctor id: " + doctor_id);
            System.out.println("name: " + name);
            System.out.println("hospital id: " + hospital_id);
            System.out.println("is director: " + is_director);
            System.out.println("doGet doctor success");
            con.close();

        } catch (Exception exception) {

        }
    }
}
