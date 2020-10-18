package lk.spark.ishani.ncms.models;

import com.google.gson.JsonObject;
import lk.spark.ishani.ncms.database.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
    private String email;
    private String password;
    private boolean isMoh;
    private boolean isHospital;

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isMoh() {
        return isMoh;
    }

    public void setMoh(boolean moh) {
        isMoh = moh;
    }

    public boolean isHospital() {
        return isHospital;
    }

    public void setHospital(boolean hospital) {
        isHospital = hospital;
    }

    public JsonObject serialize() {
        JsonObject data = new JsonObject();

        data.addProperty("email", this.email);
        data.addProperty("password", this.password);
        data.addProperty("isMoh", this.isMoh);
        data.addProperty("isHospital", this.isHospital);

        return data;
    }

    public void getModel() {
        try {
            Connection connection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement;
            ResultSet resultSet;

            statement = connection.prepareStatement("SELECT * FROM user WHERE email=?");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                this.email = resultSet.getString("email");
                this.password = resultSet.getString("password");
                this.isMoh = resultSet.getBoolean("isMoh");
                this.isHospital = resultSet.getBoolean("isHospital");
            }

            connection.close();

        } catch (Exception exception) {

        }
    }
}
