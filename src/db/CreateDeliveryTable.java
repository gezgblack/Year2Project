package db;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;

/**
 * Created by x00108663 on 21/03/2015.
 */
public class CreateDeliveryTable {

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;
    private ResultSet rset;

    public CreateDeliveryTable(){

        conn=openDB();
    }

    public Connection openDB(){
        try{
            OracleDataSource ods = new OracleDataSource();

            // Tallaght
            //ods.setURL("jdbc:oracle:thin:@//10.10.2.7:1521/global1");
            //ods.setUser("x00108663");
            //ods.setPassword("db31Mar96");

            // Home Oracle XE
            ods.setURL("jdbc:oracle:thin:HR/pmagee@localhost:1521:XE");
            ods.setUser("hr");
            ods.setPassword("passhr");

            conn= ods.getConnection();
            System.out.println("Connected");

        }catch(Exception ex){
            System.out.println("Error here at openDB, Unable to load driver " + ex);
            System.exit(1);
        }
        return conn;
    }

    public void dropTables(){

        System.out.println("Checking for existing tables ");

        try{
            // Statement Object
            stmt = conn.createStatement();

            try{
                //Drop the Users Table
                stmt.execute("DROP TABLE Delivery");
                System.out.println("Delivery table dropped.");

            }catch(SQLException ex){
                System.out.println("Table did not exist" + ex);
            }

        }catch(SQLException ex){
            System.out.println("Error at dropping tables "+ ex.getMessage());
            ex.printStackTrace();
        }
    }

    //Converting java.util.Date to java.sql.date
    private static java.sql.Date settingDate(){
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }

    public void createDelivery(){

        try{

            System.out.println("Inside Create Delivery");

            //Create a User Table

            String create ="CREATE TABLE Delivery"+
                    "(orderId Varchar2(15) PRIMARY KEY, orderDate Date,orderCost decimal(5,2), productId varchar2(15),Foreign Key(productId) References Products(productId))";
            pstmt = conn.prepareStatement(create);
            pstmt.executeUpdate(create);


            //Creating a sequence

            String createseq = "create sequence orderId_seq increment by 1 start with 1";
            pstmt = conn.prepareStatement(createseq);
            pstmt.executeUpdate(createseq);


            //Insert data into the Delivery Table

            String insertString = "INSERT INTO Delivery(orderId,orderDate,orderCost) values(orderId_seq.nextVal,To_date(?,'YYYY/mon/DD'),?) ";
            pstmt = conn.prepareStatement(insertString);
            pstmt.setDate(1, settingDate());


            pstmt.setString(1, "2014/May/02");
            pstmt.setDouble(2, 23.32);
            pstmt.executeUpdate();


            pstmt.setString(1,"2014/Apr/04");
            pstmt.setDouble(2, 54.12);
            pstmt.executeUpdate();


            pstmt.setString(1, "2015/Jun/02");
            pstmt.setDouble(2, 83.23);
            pstmt.executeUpdate();


            pstmt.setString(1, "2015/Aug/01");
            pstmt.setDouble(2, 64.54);
            pstmt.executeUpdate();


            pstmt.setString(1, "2014/Mar/04");
            pstmt.setDouble(2, 43.34);
            pstmt.executeUpdate();


        }catch(SQLException ex){
            System.out.println("SQL Exception " + ex);
            System.exit(1);
        }
    }

    public void queryDelivery(){

        try{
            String queryString = "Select* FROM Delivery";
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery(queryString);
            System.out.println("Delivery Table");

            while(rset.next()){
                System.out.println(rset.getString(1)+ " "+ rset.getDate(2)+ " " + rset.getDouble(3));
            }

        }catch(Exception ex){
            System.out.println("Error here at displaying The info " + ex);
        }
    }

    public static void main(String args[]){
        CreateDeliveryTable dt = new CreateDeliveryTable();
        dt.dropTables();
        dt.createDelivery();
        dt.queryDelivery();
    }
}
