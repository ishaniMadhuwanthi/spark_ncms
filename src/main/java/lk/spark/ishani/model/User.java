package lk.spark.ishani.model;

import com.google.gson.JsonObject;
import lk.spark.ishani.database.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {

    private String username;
    private String password;
    private String name;
    private boolean moh;
    private boolean doctor;
    private boolean patient;

    public boolean isPatient() {
        return patient;
    }

    public void setPatient(boolean patient) {
        this.patient = patient;
    }

    public User(String username){
        this.username=username;
    }

    public User(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMoh() {
        return moh;
    }

    public void setMoh(boolean moh) {
        this.moh = moh;
    }

    public boolean isDoctor() {
        return doctor;
    }

    public void setDoctor(boolean doctor) {
        this.doctor = doctor;
    }

    //serialize data into json object
    public JsonObject serialize() {
        JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty("username", this.username);
        jsonObj.addProperty("password", this.password);
        jsonObj.addProperty("name", this.name);
        jsonObj.addProperty("moh", this.moh);
        jsonObj.addProperty("doctor", this.doctor);
        jsonObj.addProperty("patient",this.patient);

        return jsonObj;
    }

    public void loadUserData() {
        try {
            Connection con = DBConnectionPool.getInstance().getConnection();
            PreparedStatement stmt;
            ResultSet resultSet;

            stmt = con.prepareStatement("SELECT * FROM user WHERE username=?");
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                this.username = resultSet.getString("username");
                this.password = resultSet.getString("password");
                this.name = resultSet.getString("name");
                this.moh = resultSet.getBoolean("moh");
                this.doctor = resultSet.getBoolean("doctor");
                this.patient = resultSet.getBoolean("patient");
            }
            con.close();
        } catch (Exception exception) {

        }
    }
}
