package lk.spark.ishani.model;

import com.google.gson.JsonObject;
import lk.spark.ishani.database.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class Patient {

    //patient data
    private int patient_id;
    private String first_name;
    private String last_name;
    private String contact;
    private String address;
    private String district;
    private String gender;
    private String email;
    private String age;
    private int x_location;
    private int y_location;
    private String severity_level;
    private Date admit_date;
    private int admitted_by;
    private Date discharge_date;
    private int discharged_by;
    private String serial_no;

    public int getPatient_id() { return patient_id; }

    public void setPatient_id(int patient_id) { this.patient_id = patient_id; }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getSeverity_level() {
        return severity_level;
    }

    public void setSeverity_level(String severity_level) {
        this.severity_level = severity_level;
    }

    public Date getAdmit_date() {
        return admit_date;
    }

    public void setAdmit_date(Date admit_date) {
        this.admit_date = admit_date;
    }

    public int getAdmitted_by() {
        return admitted_by;
    }

    public void setAdmitted_by(int admitted_by) {
        this.admitted_by = admitted_by;
    }

    public Date getDischarge_date() {
        return discharge_date;
    }

    public void setDischarge_date(Date discharge_date) {
        this.discharge_date = discharge_date;
    }

    public int getDischarged_by() {
        return discharged_by;
    }

    public void setDischarged_by(int discharged_by) {
        this.discharged_by = discharged_by;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public Patient(int patient_id) {
        this.patient_id = patient_id;
    }
    public Patient(){

    }

    //Serialise patient data to Json Object
    public JsonObject serialize () {
        JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty("patient_id", this.patient_id);
        jsonObj.addProperty("first_name", this.first_name);
        jsonObj.addProperty("last_name", this.last_name);
        jsonObj.addProperty("contact", this.contact);
        jsonObj.addProperty("address", this.address);
        jsonObj.addProperty("district", this.district);
        jsonObj.addProperty("gender", this.gender);
        jsonObj.addProperty("email", this.email);
        jsonObj.addProperty("age", this.age);
        jsonObj.addProperty("x_location", this.x_location);
        jsonObj.addProperty("y_location", this.y_location);
        jsonObj.addProperty("severity_level", this.severity_level);
        jsonObj.addProperty("admit_date", this.admit_date != null ? this.admit_date.toString() : null);
        jsonObj.addProperty("admitted_by", this.admitted_by);
        jsonObj.addProperty("discharge_date", this.discharge_date != null ? this.discharge_date.toString() : null);
        jsonObj.addProperty("discharged_by", this.discharged_by);
        jsonObj.addProperty("serial_no",this.serial_no);

        return jsonObj;
    }

    //load data from db
    public void loadPatientData() {
        try {
            Connection connection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement;
            ResultSet resultSet;

            statement = connection.prepareStatement("SELECT * FROM patient WHERE patient_id=? LIMIT 1");
            //statement.setInt(1, this.id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                this.patient_id = resultSet.getInt("patient_id");
                this.first_name = resultSet.getString("first_name");
                this.last_name = resultSet.getString("last_name");
                this.contact = resultSet.getString("contact");
                this.address = resultSet.getString("address");
                this.district = resultSet.getString("district");
                this.gender = resultSet.getString("gender");
                this.email = resultSet.getString("email");
                this.age = resultSet.getString("age");
                this.x_location = resultSet.getInt("x_location");
                this.y_location = resultSet.getInt("y_location");
                this.severity_level = resultSet.getString("severity_level");
                this.admit_date = resultSet.getDate("admit_date");
                this.admitted_by = resultSet.getInt("admitted_by");
                this.discharge_date = resultSet.getDate("discharge_date");
                this.discharged_by = resultSet.getInt("discharged_by");
                this.serial_no = resultSet.getString("serial_no");
            }
            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
