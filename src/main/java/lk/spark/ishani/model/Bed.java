package lk.spark.ishani.model;

import com.google.gson.JsonObject;
import lk.spark.ishani.database.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Bed {

    protected final int BED_COUNT = 10;
    private int bed_id;
    private int patient_id;
    private  String hospital_id;


    public Bed(){

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

        jsonObj.addProperty("bed_id", this.bed_id);
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
    public int assignBed(String hospital_id, int patient_id){
        Hospital hospitalObj=new Hospital();
        int availableBeds=hospitalObj.getAvailableBedCount(hospital_id);
        setPatient_id(patient_id);
        setHospital_id(hospital_id);
        int []bed= new int[availableBeds];
        int bed_id=0;
        int bedCount=0;

        try{
            Connection con = DBConnectionPool.getInstance().getConnection();
            ResultSet resultSet;
            PreparedStatement stmt1 = con.prepareStatement("SELECT * FROM beds where hospital_id= '" + getHospital_id() + "'");
            System.out.println(stmt1);
            resultSet = stmt1.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                bed[id-1]=id;
            }
            for(int i=0; i< availableBeds; i++){
                if(bed[i]==0){
                    bed_id = i+1;
                    bedCount = bed_id;
                    break;
                }
            }
            if(bed_id!=0) {
                PreparedStatement stmt2 = con.prepareStatement("INSERT INTO beds (bed_id, hospital_id, patient_id) VALUES (" + bed_id + ",'" + hospital_id + "','" + patient_id + "')");
                System.out.println(stmt2);
                int result = stmt2.executeUpdate();
            }
            con.close();
            } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return bed_id;
    }

    //when discharge a patient have to remove patient and delete bed_id that is assigned for the patient
    public void removePatient(int patient_id) {

        try {
            Connection con = DBConnectionPool.getInstance().getConnection();

            Map<Integer,String> queue = new HashMap<Integer,String>();

            //view patients who has beds
            PreparedStatement stmt1 = con.prepareStatement("DELETE FROM beds WHERE patient_id='"+patient_id+"'");
            System.out.println(stmt1);
            int result1 = stmt1.executeUpdate();

            //view patient_queue details
            PreparedStatement stmt2 = con.prepareStatement("SELECT * FROM patient_queue ORDER BY id ASC");
            System.out.println(stmt2);
            ResultSet resultSet1 = stmt2.executeQuery();

            if (resultSet1.next()) {
                int id = resultSet1.getInt("id");
                int patientid = resultSet1.getInt("patient_id");
                String hospitalid = resultSet1.getString("hospital_id");
                assignBed(hospitalid, patientid);

                //delete patient from queue
                PreparedStatement stmt3 = con.prepareStatement("DELETE FROM patient_queue where patient_id = '"+patientid+"'");
                System.out.println(stmt3);
                int result2 = stmt3.executeUpdate();

                //again view remain patient details in the queue
                PreparedStatement stmt4 = con.prepareStatement("SELECT * FROM patient_queue ORDER BY id ASC");
                System.out.println(stmt4);
                ResultSet resultSet2 = stmt4.executeQuery();

                //update queue according to patient adding and removing
                while(resultSet2.next()) {
                    int current_id = resultSet2.getInt("id");
                    int next_id = current_id-1;
                    System.out.println(next_id);
                    PreparedStatement stmt5 = con.prepareStatement("UPDATE patient_queue SET id=" + next_id + " Where id="+ current_id);
                    System.out.println(stmt5);
                    stmt5.executeUpdate();
                }
            }

            con.close();

        } catch (Exception exception) {

        }
    }


}
