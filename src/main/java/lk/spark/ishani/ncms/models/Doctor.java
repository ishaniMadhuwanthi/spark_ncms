package lk.spark.ishani.ncms.models;

import lk.spark.ishani.ncms.database.DBConnectionPool;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Doctor implements Serializable {
    private String doctor_id;
    private String name;
    private String email;
    private String hospital_id;
    private boolean is_director;

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

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

    public Doctor(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public Doctor() {

    }

    public void getModel() {
        try {
            Connection connection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement;
            ResultSet resultSet;

            statement = connection.prepareStatement("SELECT * FROM doctor WHERE doctor_id=?");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                this.doctor_id = resultSet.getString("doctor_id");
                this.name = resultSet.getString("name");
                this.email = resultSet.getString("email");
                this.hospital_id = resultSet.getString("hospital_id");
                this.is_director= resultSet.getBoolean("is_director");
            }

            connection.close();

        } catch (Exception exception) {

        }
    }

    public void dischargePatients(String patient_id, String hospital_id) {
        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        int result = 0;

        try {
            connection = DBConnectionPool.getInstance().getConnection();
            ResultSet resultSet;

            statement2 = connection.prepareStatement("SELECT * FROM doctor WHERE hospital_id='" +hospital_id + "' AND is_director=1");
            resultSet = statement2.executeQuery();
            System.out.println(statement2);
            while (resultSet.next()) {
                String director = resultSet.getString("doctor_id");
                System.out.println(director);
//                statement = connection.prepareStatement("UPDATE patient SET discharge_date=? , discharged_by= '" + director + "' WHERE patient_id = '" + patient_id + "'");
//                statement.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
                System.out.println(statement);
                result = statement.executeUpdate();

                if(result!=0){
                    System.out.println("success");
                }else
                    System.out.println("Failed");
            }

            System.out.println("Id: " + doctor_id);
            System.out.println("Name: " + name);
            System.out.println("Email: "+email);
            System.out.println("Hospital id: " + hospital_id);
            System.out.println("Is Director: " + is_director);
            System.out.println("doGet doctor success");

            connection.close();
        } catch (Exception exception) {

        }


    }

}
