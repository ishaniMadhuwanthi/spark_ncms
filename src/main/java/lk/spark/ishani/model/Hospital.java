package lk.spark.ishani.model;

import com.google.gson.JsonObject;
import lk.spark.ishani.database.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import static java.util.Comparator.comparingDouble;


public class Hospital {

    private String hospital_id;
    private String name;
    private String district;
    private int x_location;
    private int y_location;
    private Date build_date;

    public Hospital(String hospital_id){
        this.hospital_id=hospital_id;
    }
    public Hospital(){

    }

    public String getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getX_location() {
        return x_location;
    }

    public void setX_location(int x_location) {
        this.x_location = x_location;
    }

    public int getY_location() {
        return y_location;
    }

    public void setY_location(int y_location) {
        this.y_location = y_location;
    }

    public Date getBuild_date() {
        return build_date;
    }

    public void setBuild_date(Date build_date) {
        this.build_date = build_date;
    }

    //serialize hospital data into json object
    public JsonObject serialize() {
        JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty("id", this.hospital_id);
        jsonObj.addProperty("name", this.name);
        jsonObj.addProperty("district", this.district);
        jsonObj.addProperty("x_location", this.x_location);
        jsonObj.addProperty("y_location", this.y_location);
        jsonObj.addProperty("build_date", this.build_date != null ? this.build_date.toString() : null);

        return jsonObj;
    }

//load hospital details
public void loadHospitalData() {
    try {
        Connection con = DBConnectionPool.getInstance().getConnection();

        PreparedStatement stmt = con.prepareStatement("SELECT * FROM hospital WHERE hospital_id=? LIMIT 1");
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            this.hospital_id = resultSet.getString("id");
            this.name = resultSet.getString("name");
            this.district = resultSet.getString("district");
            this.x_location = resultSet.getInt("x_location");
            this.y_location = resultSet.getInt("y_location");
            this.build_date = resultSet.getDate("build_date");
        }
        con.close();
    } catch (Exception exception) {

    }
}

//get available bed count
//private ArrayList<Bed> beds;

    public int getAvailableBedCount(String hospital_id)  {
        try{
            Connection con = DBConnectionPool.getInstance().getConnection();

            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM beds WHERE hospital_id=? and serial_no IS NULL");
            ResultSet resultSet = stmt.executeQuery();

            stmt.setInt(1, Integer.parseInt(hospital_id));

            Hospital x=new Hospital();
            int count = x.getCount(resultSet);
            con.close();

            return count;

        }catch(Exception e){
            return 0;
        }
    }

    //to calculate count
    public int getCount(ResultSet resultSet){

        try {
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    //calculate shortest distance to the hospital from patient location coordinates and return nearest hospital
    public String getDistance(int x_location, int y_location) {

        Map<String, Double> distance = new HashMap<String, Double>();
        String nearestHospital="";

        try{
            Connection con = DBConnectionPool.getInstance().getConnection();
            ResultSet resultSet;

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM hospital");
            System.out.println(stmt);
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String hospital_id = resultSet.getString("id");
                int hospitalx_location = resultSet.getInt("x_location");
                int hospitaly_location = resultSet.getInt("y_location");
                int x_distance = Math.abs(hospitalx_location - x_location);
                int y_distance = Math.abs(hospitaly_location - y_location);

                double dist = Math.sqrt(Math.pow(x_distance, 2) + Math.pow(y_distance, 2));
                distance.put(hospital_id,dist);
            }
            nearestHospital = Collections.min(distance.entrySet(), comparingDouble(Map.Entry::getValue)).getKey();
            System.out.println(nearestHospital);
            con.close();
        }catch(Exception e){

        }
        return nearestHospital;
    }
}
