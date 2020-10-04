package lk.spark.ishani.database;

import java.sql.*;
import java.util.UUID;

public class DBTest
{
    private void simpleInsertQuery()
    {
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement stmt = null;
        try
        {
            UUID uuid = UUID.randomUUID();

            con = DBConnectionPool.getInstance().getConnection();
            stmt = con.prepareStatement("INSERT INTO hospital (id,name, district, x_location, y_location, build_date) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, uuid.toString());
            stmt.setString(2, "Teaching Hospital, Jaffna");
            stmt.setString(3, "Jaffna");
            stmt.setInt(4, 301);
            stmt.setInt(5, 250);
            stmt.setDate(6, new Date(new java.util.Date().getTime()));
            int changedRows = stmt.executeUpdate();
            System.out.println( changedRows == 1 ? "Successfully inserted" : "Insertion failed");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            DBConnectionPool.getInstance().close(rs);
            DBConnectionPool.getInstance().close(stmt);
            DBConnectionPool.getInstance().close(con);
        }
    }

    private void simpleSelectQuery()
    {
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement stmt = null;
        try
        {
            con = DBConnectionPool.getInstance().getConnection();
            stmt = con.prepareStatement("SELECT * FROM hospital WHERE district = ?");
            stmt.setString(1, "Colombo");
            rs = stmt.executeQuery();
            while(rs.next())
            {
                System.out.println(rs.getString("id"));
                System.out.println(rs.getString("name"));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            DBConnectionPool.getInstance().close(rs);
            DBConnectionPool.getInstance().close(stmt);
            DBConnectionPool.getInstance().close(con);
        }
    }

    public static void main(String[] args)
    {
        DBTest dbTest = new DBTest();
       // dbTest.simpleSelectQuery();
        dbTest.simpleInsertQuery();
        //dbTest.complexSelectQuery();
    }
}
