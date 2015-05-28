package db;
import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;

/**
 * Created by x00108663 on 05/03/2015.
 */
public class CreateUsersTable {

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;
    private ResultSet rset;

    public CreateUsersTable(){

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
                stmt.execute("DROP TABLE Users");
                System.out.println("Users table dropped.");

            }catch(SQLException ex){
                System.out.println("Table did not exist" + ex);
            }

        }catch(SQLException ex){
            System.out.println("Error at dropping tables "+ ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void createUsers(){

        try{

            System.out.println("Inside Create Users");

            //Create a User Table

            String create ="CREATE TABLE Users"+
                    "(username varchar2(35) PRIMARY KEY, password varchar2(35),userLevel number, phoneNum Varchar(15),address varchar2(70),name varchar2(40),email varchar2(50))";
            pstmt = conn.prepareStatement(create);
            pstmt.executeUpdate(create);




            //Insert data into the Users Table

            String insertString = "INSERT INTO Users(username,password,userLevel,phoneNum,address,name,email) values(?,?,?,?,?,?,?) ";
            pstmt = conn.prepareStatement(insertString);

            pstmt.setString(1,"Admin");
            pstmt.setString(2,"NGgg5Vcch8lW");
            pstmt.setInt(3, 0);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"34a Green Street, Dublin");
            pstmt.setString(6,"John Redmond");
            pstmt.setString(7,"JohnRed@gmail.com");
            pstmt.executeUpdate();

            pstmt.setString(1,"Gavin");
            pstmt.setString(2,"pkFkYHUyZDB");
            pstmt.setInt(3, 1);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"34c Cork Street, Dublin");
            pstmt.setString(6,"Gavin Daly");
            pstmt.setString(7,"GavinDaly@gmail.com");
            pstmt.executeUpdate();

            pstmt.setString(1,"Paul");
            pstmt.setString(2,"zWF0qALc6tz");
            pstmt.setInt(3, 1);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"34 Woodford Hills, Dublin");
            pstmt.setString(6,"Paul Jones");
            pstmt.setString(7,"Paul.Jones@gmail.com");
            pstmt.executeUpdate();

            pstmt.setString(1,"Jim");
            pstmt.setString(2,"Dk0IsmdKfDA");
            pstmt.setInt(3, 1);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"75 Patrick's Street, Galway");
            pstmt.setString(6,"Jim Barry");
            pstmt.setString(7,"Jim.Barry@gmail.com");
            pstmt.executeUpdate();

            pstmt.setString(1,"Tim");
            pstmt.setString(2,"pk0O56xgRDA");
            pstmt.setInt(3, 1);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"Timmies's Street, Dublin");
            pstmt.setString(6,"Tim Murphy");
            pstmt.setString(7, "Tim.Murphy@gmail.com");
            pstmt.executeUpdate();


        }catch(SQLException ex){
            System.out.println("SQL Exception " + ex);
            System.exit(1);
        }
    }

    public void queryUsers(){

        try{
            String queryString = "Select * FROM Users";
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery(queryString);
            System.out.println("Users Table");

            while(rset.next()){
                System.out.println(rset.getString(1) + " " + rset.getString(2)+ " " + rset.getInt(3) + " " + rset.getString(4)+ " " + rset.getString(5) + " " + rset.getString(6)+ " "+ rset.getString(7));
            }

        }catch(Exception ex){
            System.out.println("Error here at displaying The info " + ex);
        }
    }

    public static void main(String args[]){
        CreateUsersTable ut = new CreateUsersTable();
        ut.dropTables();
        ut.createUsers();
        ut.queryUsers();
    }
}
