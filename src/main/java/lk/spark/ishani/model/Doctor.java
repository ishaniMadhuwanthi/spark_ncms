package lk.spark.ishani.model;

import com.google.gson.JsonObject;
import lk.spark.ishani.database.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {

    private int id;
    private String name;
    private String hospital_id;
    private boolean is_director;

    public Doctor(){

    }
    public Doctor(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        jsonObj.addProperty("id", this.id);
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

            statement = con.prepareStatement("SELECT * FROM doctor WHERE id=?");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                this.id = resultSet.getInt("id");
                this.name = resultSet.getString("name");
                this.hospital_id = resultSet.getString("hospital_id");
                this.is_director = resultSet.getBoolean("is_director");
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
