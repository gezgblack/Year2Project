package db;
import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;

/**
 * Created by x00108663 on 05/03/2015.
 */
public class CreateShopTable {

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;
    private ResultSet rset;

    public CreateShopTable(){

        conn=openDB();
    }

    public Connection openDB(){
        try{
            OracleDataSource ods = new OracleDataSource();

            // Tallaght
            //ods.setURL("jdbc:oracle:thin:@//10.10.2.7:1521/global1");
           // ods.setUser("x00108663");
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
                stmt.execute("DROP SEQUENCE shopId_seq");

            }catch(SQLException ex){
                System.out.println("Sequence does not exist" + ex);
            }

            try{
                //Drop the Users Shop
                stmt.execute("DROP TABLE Shop");
                System.out.println("Shop table dropped.");

            }catch(SQLException ex){
                System.out.println("Table did not exist" + ex);
            }

        }catch(SQLException ex){
            System.out.println("Error at dropping tables "+ ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void createShop(){

        try{

            System.out.println("Inside Create Shop");

            //Create a table Shop

            String create ="CREATE TABLE Shop"+
                    "(shopId number PRIMARY KEY, shopName varchar2(35),shopAddress varchar2(45), phoneNum Varchar(15),shopBank decimal(9,2))";
            pstmt = conn.prepareStatement(create);
            pstmt.executeUpdate(create);

            //Creating a sequence

            String createseq = "create sequence shopId_seq increment by 1 start with 1";
            pstmt = conn.prepareStatement(createseq);
            pstmt.executeUpdate(createseq);

            //Insert data into the Shop Table

            String insertString = "INSERT INTO Shop(shopId,shopName,shopAddress,phoneNum,shopBank) values(shopId_seq.nextVal,?,?,?,?)";
            pstmt = conn.prepareStatement(insertString);

            pstmt.setString(1,"Centra Clondalkin");
            pstmt.setString(2,"23 Monastery road, Clondalkin");
            pstmt.setString(3, "0857643562");
            pstmt.setDouble(4,100.00);
            pstmt.executeUpdate();


        }catch(SQLException ex){
            System.out.println("SQL Exception " + ex);
            System.exit(1);
        }
    }

    public void queryShop(){

        try{
            String queryString = "Select * FROM Shop";
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery(queryString);
            System.out.println("Shop Table");

            while(rset.next()){
                System.out.println(rset.getInt(1) + " " + rset.getString(2)+ " " + rset.getString(3) + " " + rset.getString(4)+ " " + rset.getDouble(5));
            }

        }catch(Exception ex){
            System.out.println("Error here at displaying The info " + ex);
        }
    }

    public static void main(String args[]){
        CreateShopTable st = new CreateShopTable();
        st.dropTables();
        st.createShop();
        st.queryShop();
    }
}
