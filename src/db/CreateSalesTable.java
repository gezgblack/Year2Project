package db;
import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;
/**
 * Created by x00112704 on 02/04/2015.
 */
public class CreateSalesTable {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;
    private ResultSet rset;

    public CreateSalesTable(){

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
                stmt.execute("DROP SEQUENCE salesId_seq");

            }catch(SQLException ex){
                System.out.println("Sequence does not exist" + ex);
            }

            try{
                //Drop the Products Table
                stmt.execute("DROP TABLE Sales");
                System.out.println("Sales table dropped.");

            }catch(SQLException ex){
                System.out.println("Table did not exist" + ex);
            }

        }catch(SQLException ex){
            System.out.println("Error at dropping tables "+ ex.getMessage());
            ex.printStackTrace();
        }
    }


    public void createSales(){

        try{

            System.out.println("Inside Create Sales");

            //Create a Products Table

            String create ="CREATE TABLE Sales "+
                    "(salesId varchar2(15), username varchar2(15), productId varchar2(15), productNameSold varchar2(35), amountSold decimal, totalSale decimal(5,2),customer varchar2(50),salesDate Date,"+
                    "PRIMARY KEY (salesId), FOREIGN KEY(username) REFERENCES Users(username), FOREIGN KEY(productId) REFERENCES Products(productId))";

            pstmt = conn.prepareStatement(create);
            pstmt.executeUpdate(create);


            //Creating a sequence

            String createseq = "create sequence salesId_seq increment by 1 start with 1";
            pstmt = conn.prepareStatement(createseq);
            pstmt.executeUpdate(createseq);


            //Insert data into the Users Table
            String insertString = "INSERT INTO Sales(salesId,username,productId,productNameSold,amountSold,totalSale,customer,salesDate) values(salesId_seq.nextVal,?,?,?,?,?,?,To_date(?,'YYYY/MM/DD'))";
            pstmt = conn.prepareStatement(insertString);
            pstmt.setString(1, "Gavin");
            pstmt.setString(2, "1");
            pstmt.setString(3, "Cookies");
            pstmt.setDouble(4,3);
            pstmt.setDouble(5, 6.12);
            pstmt.setString(6, "Fixing Birch");
            pstmt.setString(7, "2014-03-02");
            pstmt.executeUpdate();


        }catch(SQLException ex){
            System.out.println("SQL Exception " + ex);

            System.exit(1);
        }
    }

    public void querySales(){

        try{
            String queryString = "Select * FROM Sales";
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery(queryString);
            System.out.println("Sales Table");

            while(rset.next()){
                System.out.println(rset.getString(1)+ " " + rset.getString(2)+ " " + rset.getString(3) +  " " + rset.getString(4)+ " " + rset.getDouble(5) + " " + rset.getDouble(6)+ " " + rset.getString(7)+ " "+ rset.getDate(8));
            }

        }catch(Exception ex){
            System.out.println("Error here at displaying The info " + ex);
        }
    }


    public static void main(String args[]){
        CreateSalesTable pt = new CreateSalesTable();
        pt.dropTables();
        pt.createSales();
        pt.querySales();
    }


}
