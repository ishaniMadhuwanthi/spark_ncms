package lk.spark.ishani.model;

import com.google.gson.JsonObject;
import lk.spark.ishani.database.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Bed {

    private int id;
    private int patient_id;
    private  String hospital_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }


    public Bed(int id, int patient_id, String hospital_d) {
        this.id = id;
        this.patient_id = patient_id;
        this.hospital_id = hospital_d;
    }

    public JsonObject serialize() {
        JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty("id", this.id);
        jsonObj.addProperty("hospital_id", this.patient_id);
        jsonObj.addProperty("serial_no", this.hospital_id);

        return jsonObj;
    }

    public void bedData() {
        try {
            Connection con = DBConnectionPool.getInstance().getConnection();

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM beds WHERE id=? LIMIT 1");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                this.id = resultSet.getInt("id");
                this.patient_id = resultSet.getInt("patient_id");
                this.hospital_id = resultSet.getString("hospital_id");
            }
            con.close();
        } catch (Exception exception) {

        }
    }

    //allocate bed for patient

}
