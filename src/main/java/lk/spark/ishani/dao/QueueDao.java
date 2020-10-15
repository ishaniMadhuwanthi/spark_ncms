package lk.spark.ishani.dao;

import lk.spark.ishani.controller.HospitalServlet;
import lk.spark.ishani.database.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QueueDao {

    public int addToQueue(int patient_id) {

        int queue_length = 4;
        int queue_id = 0;
        int [] queue = new int[4];

        try {
            Connection con = DBConnectionPool.getInstance().getConnection();
            PreparedStatement stmt1 = con.prepareStatement("SELECT * FROM patient_queue");
            System.out.println(stmt1);
            ResultSet resultSet = stmt1.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                 patient_id= Integer.parseInt(resultSet.getString("patient_id"));
                queue[id-1]=id;
            }
            for(int i=0; i<queue_length; i++){
                if(queue[i]==0){
                    queue_id = i+1;
                    System.out.println("Current queue length: " + queue_id);
                    break;
                }
            }
            //insert patient into queue
            if(queue_id!=0) {
                PreparedStatement stmt2 = con.prepareStatement("INSERT INTO patient_queue (id, patient_id) VALUES (" + queue_id + ",'" + patient_id + "')");
                System.out.println(stmt2);
                int result = stmt2.executeUpdate();
            } else {
                HospitalServlet hospital = new HospitalServlet();
                //insert new hospital
            }
            con.close();
        } catch (Exception exception) {

        }
        return  queue_id;
    }
}
