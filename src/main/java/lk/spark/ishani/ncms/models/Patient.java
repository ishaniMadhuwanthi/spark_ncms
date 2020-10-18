package lk.spark.ishani.ncms.models;

import com.google.gson.JsonObject;

import java.sql.Date;

public class Patient {
    private String patient_id;
    private String first_name;
    private String last_name;
    private String contact;
    private String district;
    private String email;
    private String age;
    private String x_location;
    private String y_location;

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public String getX_location() {
        return x_location;
    }

    public void setX_location(String x_location) {
        this.x_location = x_location;
    }

    public String getY_location() {
        return y_location;
    }

    public void setY_location(String y_location) {
        this.y_location = y_location;
    }

    public JsonObject serialize() {
        JsonObject data = new JsonObject();

        data.addProperty("patient_id", this.patient_id);
        data.addProperty("first_name", this.first_name);
        data.addProperty("last_name", this.last_name);
        data.addProperty("contact", this.contact);
        data.addProperty("district", this.district);
        data.addProperty("email", this.email);
        data.addProperty("age", this.age);
        data.addProperty("x_location", this.x_location);
        data.addProperty("y_location", this.y_location);
        data.addProperty("district", this.district);


        return data;
    }

}
