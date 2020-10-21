package lk.spark.ishani.ncms.models;

import com.google.gson.JsonObject;
import lk.spark.ishani.ncms.database.DBConnectionPool;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Comparator.comparingDouble;


public class Hospital {
    private String hospital_id;
    private String name;
    private String district;
    private String x_location;
    private String y_location;

    public Hospital() {

    }

    public Hospital(String hospital_id){
        this.hospital_id = hospital_id;
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

    public String getX_location() { return x_location; }

    public void setX_location(String x_location) { this.x_location = x_location; }

    public String getY_location() {
        return y_location;
    }

    public void setY_location(String y_location) { this.y_location = y_location; }

    public JsonObject serialize() {
        JsonObject data = new JsonObject();

        data.addProperty("hospital_id", this.hospital_id);
        data.addProperty("name", this.name);
        data.addProperty("district", this.district);
        data.addProperty("x_location", this.x_location);
        data.addProperty("y_location", this.y_location);

        return data;
    }

    public String assignHospital(String patientLocationX, String patientLocationY) {
        Connection connection = null;
        PreparedStatement statement = null;
        Map<String, Double> distance = new HashMap<String, Double>();

        double dist;
        String nearestHospital = "";

        try {
            connection = DBConnectionPool.getInstance().getConnection();
            ResultSet resultSet;

            statement = connection.prepareStatement("SELECT * FROM hospital");
            System.out.println(statement);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String hospital_id = resultSet.getString("hospital_id");
                String hos_x = resultSet.getString("x_location");;
                double x_location = Double.parseDouble(hos_x);
                //System.out.println(pi);
                //double x_location = resultSet.getInt("x_location");
                String hos_y= resultSet.getString("y_location");
                double y_location = Double.parseDouble(hos_y);
                double pat_x=Double.parseDouble(String.valueOf(patientLocationX));
                double pat_y=Double.parseDouble(String.valueOf(patientLocationY));
                double distanceX = Math.abs(x_location - pat_x);
                double distanceY = Math.abs(y_location - pat_y);

                dist = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
                distance.put(hospital_id,dist);
            }

            System.out.println(distance);
            System.out.println(Collections.min(distance.values()));
            nearestHospital = Collections.min(distance.entrySet(), comparingDouble(Map.Entry::getValue)).getKey();
            System.out.println(nearestHospital);
            connection.close();

        } catch (Exception exception) {

        }
        return nearestHospital;
    }


}
