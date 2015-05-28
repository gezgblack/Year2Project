package db;
import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;

/**
 * Created by x00108663 on 05/03/2015.
 */
public class CreateProductsTable {

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;
    private ResultSet rset;

    public CreateProductsTable(){

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
                //drop the sequence
                stmt.execute("DROP SEQUENCE productId_seq");

            }catch(SQLException ex){
                System.out.println("Sequence does not exist" + ex);
            }

            try{
                //Drop the Products Table
                stmt.execute("DROP TABLE Products");
                System.out.println("Products table dropped.");

            }catch(SQLException ex){
                System.out.println("Table did not exist " + ex);
            }

        }catch(SQLException ex){
            System.out.println("Error at dropping tables "+ ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void createProducts(){

        try{

            System.out.println("Inside Create Products");

            //Create a Products Table

            String create ="CREATE TABLE Products"+
                    "(productId varchar2(15) PRIMARY KEY,productName varchar2(35),productPrice decimal(5,2),sellCost decimal(5,2),qtyOnHand number)";

            pstmt = conn.prepareStatement(create);
            pstmt.executeUpdate(create);


            //Creating a sequence

            String createseq = "create sequence productId_seq increment by 1 start with 1";
            pstmt = conn.prepareStatement(createseq);
            pstmt.executeUpdate(createseq);


            //Insert data into the Users Table

            String insertString = "INSERT INTO Products(productId,productName,productPrice,sellCost,qtyOnHand) values(productId_seq.nextVal,?,?,?,?)";
            pstmt = conn.prepareStatement(insertString);

            pstmt.setString(1,"Cookies");
            pstmt.setDouble(2,2);
            pstmt.setDouble(3,2.32);
            pstmt.setInt(4,24);
            pstmt.executeUpdate();

            pstmt.setString(1,"Milk");
            pstmt.setDouble(2,1.07);
            pstmt.setDouble(3,1.50);
            pstmt.setInt(4,20);
            pstmt.executeUpdate();

            pstmt.setString(1,"Pizza");
            pstmt.setDouble(2,4.09);
            pstmt.setDouble(3,4.35);
            pstmt.setInt(4,5);
            pstmt.executeUpdate();

            pstmt.setString(1,"Coke");
            pstmt.setDouble(2,1.06);
            pstmt.setDouble(3,1.90);
            pstmt.setInt(4,10);
            pstmt.executeUpdate();

            pstmt.setString(1,"Ketchup");
            pstmt.setDouble(2,.07);
            pstmt.setDouble(3,1.60);
            pstmt.setInt(4,7);
            pstmt.executeUpdate();


        }catch(SQLException ex){
            System.out.println("SQL Exception " + ex);
            System.exit(1);
        }
    }

    public void queryProducts(){

        try{
            String queryString = "Select * FROM Products";
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery(queryString);
            System.out.println("Products Table");

            while(rset.next()){
                System.out.println(rset.getString(1) + " " + rset.getString(2)+ " " + rset.getDouble(3) + " " + rset.getDouble(4)+ " " + rset.getInt(5));
            }

        }catch(Exception ex){
            System.out.println("Error here at displaying The info " + ex);
        }
    }

    public static void main(String args[]){
        CreateProductsTable pt = new CreateProductsTable();
        pt.dropTables();
        pt.createProducts();
        pt.queryProducts();
    }
}
