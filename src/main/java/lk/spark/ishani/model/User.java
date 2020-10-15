package lk.spark.ishani.model;

import com.google.gson.JsonObject;
import lk.spark.ishani.database.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {

    private String email;
    private String password;
    private boolean ismoh;
    private boolean isdoctor;
    private boolean ispatient;

    public User(){

    }
    public User(String email){
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIsmoh() {
        return ismoh;
    }

    public void setIsmoh(boolean ismoh) {
        this.ismoh = ismoh;
    }

    public boolean isIsdoctor() {
        return isdoctor;
    }

    public void setIsdoctor(boolean isdoctor) {
        this.isdoctor = isdoctor;
    }

    public boolean isIspatient() {
        return ispatient;
    }

    public void setIspatient(boolean ispatient) {
        this.ispatient = ispatient;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //serialize data into json object
    public JsonObject serialize() {
        JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty("email", this.email);
        jsonObj.addProperty("password", this.password);
        jsonObj.addProperty("ismoh", this.ismoh);
        jsonObj.addProperty("isdoctor", this.isdoctor);
        jsonObj.addProperty("ispatient",this.ispatient);

        return jsonObj;
    }



    //load user data
    public void loadUserData() {
        try {
            Connection con = DBConnectionPool.getInstance().getConnection();
            PreparedStatement stmt;
            ResultSet resultSet;

            stmt = con.prepareStatement("SELECT * FROM user WHERE email=?");
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                this.email = resultSet.getString("email");
                this.password = resultSet.getString("password");
                this.ismoh = resultSet.getBoolean("ismoh");
                this.isdoctor = resultSet.getBoolean("isdoctor");
                this.ispatient = resultSet.getBoolean("ispatient");
            }
            con.close();
        } catch (Exception exception) {

        }
    }
}
