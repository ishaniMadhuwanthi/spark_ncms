package lk.spark.ishani.model;

import com.google.gson.JsonObject;
import lk.spark.ishani.database.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Bed {

    protected final int BED_COUNT = 10;
    private int bed_id;
    private int patient_id;
    private  String hospital_id;
    private String serial_no;

    public Bed(){

    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public int getBed_id() {
        return bed_id;
    }

    public void setBed_id(int bed_id) {
        this.bed_id = bed_id;
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


    public Bed(int bed_id, int patient_id, String hospital_d) {
        this.bed_id = bed_id;
        this.patient_id = patient_id;
        this.hospital_id = hospital_d;
    }

    public JsonObject serialize() {
        JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty("id", this.bed_id);
        jsonObj.addProperty("hospital_id", this.patient_id);
        jsonObj.addProperty("serial_no", this.hospital_id);

        return jsonObj;
    }

    public void loadBedData() {
        try {
            Connection con = DBConnectionPool.getInstance().getConnection();

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM beds WHERE bed_id=? LIMIT 1");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                this.bed_id = resultSet.getInt("id");
                this.patient_id = resultSet.getInt("patient_id");
                this.hospital_id = resultSet.getString("hospital_id");
            }
            con.close();
        } catch (Exception exception) {

        }
    }

    //assign bed for patient
    public int assignBed(String serial_no, String hospital_id){
        Hospital hospitalObj=new Hospital();
        int availableBeds=hospitalObj.getAvailableBedCount(hospital_id);
        setSerial_no(serial_no);
        setPatient_id(patient_id);
        int []bed= new int[availableBeds];

        try{
            for(int i=0;i<availableBeds;i++){

                Connection con= DBConnectionPool.getInstance().getConnection();

                PreparedStatement stmt = con.prepareStatement("INSERT INTO beds (patient_id,serial_no) VALUES (patient_id,serial_no)");
                ResultSet resultSet = stmt.executeQuery();
                while(resultSet.next()){
                    int bed_id = resultSet.getInt("bed_id");
                    bed[bed_id-1]=bed_id;
                }
                con.close();
            }

        }catch(Exception e){

        }
        return bed_id;
    }

    //when discharge a patient have to remove patient and delete bed+id that is assigned for the patient
    public void removePatient(int patient_id) {

        try {
            Connection con = DBConnectionPool.getInstance().getConnection();

            PreparedStatement stmt= con.prepareStatement("DELETE FROM beds WHERE patient_id='"+patient_id+"'");
            System.out.println(stmt);
            int result = stmt.executeUpdate();

            con.close();

        } catch (Exception exception) {

        }
    }


}
