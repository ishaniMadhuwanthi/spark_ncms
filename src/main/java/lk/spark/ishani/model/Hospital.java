package lk.spark.ishani.model;

import com.google.gson.JsonObject;
import lk.spark.ishani.database.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class Hospital {

    private String id;
    private String name;
    private String district;
    private int x_location;
    private int y_location;
    private Date build_date;

    public Hospital(String id){
        this.id=id;
    }
    public Hospital(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

        jsonObj.addProperty("id", this.id);
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
        PreparedStatement stmt;
        ResultSet resultSet;

        stmt = con.prepareStatement("SELECT * FROM patient WHERE id=? LIMIT 1");
        resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            this.id = resultSet.getString("id");
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
}
