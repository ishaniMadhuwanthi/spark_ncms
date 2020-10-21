package lk.spark.ishani.ncms.models;

import com.google.gson.JsonObject;
import lk.spark.ishani.ncms.database.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
    private String email;
    private String moh_id;
    private String name;

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getMoh_id() { return moh_id; }

    public void setMoh_id(String moh_id) { this.moh_id = moh_id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public User(String moh_id) {
        this.moh_id = moh_id;
    }

    public User(){

    }

    public JsonObject serialize() {
        JsonObject data = new JsonObject();

        data.addProperty("email", this.email);
        data.addProperty("moh_id", this.moh_id);
        data.addProperty("name", this.name);

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
                this.moh_id = resultSet.getString("moh_id");
                this.name = resultSet.getString("name");
            }

            connection.close();

        } catch (Exception exception) {

        }
    }
}
