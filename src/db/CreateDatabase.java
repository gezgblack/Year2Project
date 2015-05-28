package db;

import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;

/**
 * Created by x00108663 on 05/03/2015.
 */
public class CreateDatabase {

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;
    private ResultSet rset;

    public CreateDatabase(){

        conn=openDB();
    }

    //CREATING A CONNECTION WITH THE DATABASE
    public Connection openDB(){
        try{
            OracleDataSource ods = new OracleDataSource();

            // Tallaght
            ods.setURL("jdbc:oracle:thin:@//10.10.2.7:1521/global1");
            ods.setUser("x00108663");
            ods.setPassword("db31Mar96");

            // Home Oracle XE
            //ods.setURL("jdbc:oracle:thin:HR/pmagee@localhost:1521:XE");
            //ods.setUser("hr");
            //ods.setPassword("passhr");

            conn= ods.getConnection();
            System.out.println("Connected");

        }catch(Exception ex){
            System.out.println("Error here at openDB, Unable to load driver " + ex);
            System.exit(1);
        }
        return conn;
    }

    //DROPPING THE TABLES IN THE RIGHT ORDER
    public void dropTables(){

        System.out.println("Checking for existing tables ");

        try{
            // Statement Object
            stmt = conn.createStatement();

            try{
                //drop the sequence
                stmt.execute("DROP SEQUENCE salesId_seq");
                stmt.execute("DROP SEQUENCE orderId_seq");
                stmt.execute("DROP SEQUENCE productId_seq");
                stmt.execute("DROP SEQUENCE shopId_seq");

            }catch(SQLException ex){
                System.out.println("Sequence does not exist" + ex);
            }

            try{
                //Drop the Products Table
                stmt.execute("DROP TABLE Delivery");
                stmt.execute("DROP TABLE Sales");
                stmt.execute("DROP TABLE Users");
                stmt.execute("DROP TABLE Products");
                stmt.execute("DROP TABLE Shop");
                System.out.println("All tables dropped.");

            }catch(SQLException ex){
                System.out.println("Table did not exist" + ex);
            }

        }catch(SQLException ex){
            System.out.println("Error at dropping tables "+ ex.getMessage());
            ex.printStackTrace();
        }
    }

    //CREATING THE SHOP TABLE
    public void createShop() {

        try {

            System.out.println("Inside Create Shop");

            //Create a table Shop

            String create = "CREATE TABLE Shop" +
                    "(shopId number PRIMARY KEY, shopName varchar2(35),shopAddress varchar2(45), phoneNum Varchar(15),shopBank decimal(9,2))";
            pstmt = conn.prepareStatement(create);
            pstmt.executeUpdate(create);

            //Creating a sequence

            System.out.println("Here");
            String createseq = "create sequence shopId_seq increment by 1 start with 1";
            pstmt = conn.prepareStatement(createseq);
            pstmt.executeUpdate(createseq);

            //Insert data into the Shop Table

            String insertString = "INSERT INTO Shop(shopId,shopName,shopAddress,phoneNum,shopBank) values(shopId_seq.nextVal,?,?,?,?)";
            pstmt = conn.prepareStatement(insertString);

            pstmt.setString(1, "Centra Clondalkin");
            pstmt.setString(2, "23 Monastery road, Clondalkin");
            pstmt.setString(3, "0857643562");
            pstmt.setDouble(4, 100.00);
            pstmt.executeUpdate();

            pstmt.setString(1, "Centra Tallaght");
            pstmt.setString(2, "Belgard Road, Tallaght");
            pstmt.setString(3, "0857645326");
            pstmt.setDouble(4, 530.00);
            pstmt.executeUpdate();
            
            pstmt.setString(1, "Centra Lucan");
            pstmt.setString(2, "Main Road, Lucan");
            pstmt.setString(3, "017864312");
            pstmt.setDouble(4, 800.00);
            pstmt.executeUpdate();
            
            pstmt.setString(1, "Centra Dun Laoghaire");
            pstmt.setString(2, "Coast Place, Tallaght");
            pstmt.setString(3, "35378945610");
            pstmt.setDouble(4, 9503.07);
            pstmt.executeUpdate();

            pstmt.setString(1, "Centra Clane");
            pstmt.setString(2, "Esso Service Station, Clane");
            pstmt.setString(3, "0875462245");
            pstmt.setDouble(4, 450.10);
            pstmt.executeUpdate();


        } catch (SQLException ex) {
            System.out.println("SQL Exception " + ex);
            System.exit(1);
        }
    }

    //CREATING THE PRODUCTS TABLE
    public void createProducts(){

        try{

            System.out.println("Inside Create Products");

            //Create a Products Table

            String create ="CREATE TABLE Products"+
                    "(productId varchar2(15) PRIMARY KEY,productName varchar2(35),productPrice decimal(5,2),sellCost decimal(5,2),qtyOnHand number,shopId number, FOREIGN KEY (shopId) REFERENCES Shop(shopId))";

            pstmt = conn.prepareStatement(create);
            pstmt.executeUpdate(create);


            //Creating a sequence

            String createseq = "create sequence productId_seq increment by 1 start with 1";
            pstmt = conn.prepareStatement(createseq);
            pstmt.executeUpdate(createseq);


            //Insert data into the Products Table

            String insertString = "INSERT INTO Products(productId,productName,productPrice,sellCost,qtyOnHand,shopId) values(productId_seq.nextVal,?,?,?,?,?)";
            pstmt = conn.prepareStatement(insertString);


            //Shop 1: The products below belong to shop 1
            pstmt.setString(1,"Cookies");
            pstmt.setDouble(2, 2);
            pstmt.setDouble(3,2.32);
            pstmt.setInt(4, 69);
            pstmt.setInt(5,1);
            pstmt.executeUpdate();

            pstmt.setString(1,"Milk");
            pstmt.setDouble(2,1.07);
            pstmt.setDouble(3,1.50);
            pstmt.setInt(4,45);
            pstmt.setInt(5,1);
            pstmt.executeUpdate();

            pstmt.setString(1,"Pizza");
            pstmt.setDouble(2,4.09);
            pstmt.setDouble(3,4.35);
            pstmt.setInt(4,5);
            pstmt.setInt(5,1);
            pstmt.executeUpdate();

            pstmt.setString(1,"Coke");
            pstmt.setDouble(2,1.06);
            pstmt.setDouble(3,1.90);
            pstmt.setInt(4,54);
            pstmt.setInt(5,1);
            pstmt.executeUpdate();

            pstmt.setString(1,"Ketchup");
            pstmt.setDouble(2,.07);
            pstmt.setDouble(3,1.60);
            pstmt.setInt(4,78);
            pstmt.setInt(5,1);
            pstmt.executeUpdate();

            //Shop 2: The products below belong to shop 2
            pstmt.setString(1,"Bread");
            pstmt.setDouble(2,0.90);
            pstmt.setDouble(3,1.40);
            pstmt.setInt(4,64);
            pstmt.setInt(5,2);
            pstmt.executeUpdate();

            pstmt.setString(1,"Pepsi");
            pstmt.setDouble(2,1.30);
            pstmt.setDouble(3,2.10);
            pstmt.setInt(4,62);
            pstmt.setInt(5,2);
            pstmt.executeUpdate();

            pstmt.setString(1,"Large Pizza");
            pstmt.setDouble(2,5.10);
            pstmt.setDouble(3,6.30);
            pstmt.setInt(4,75);
            pstmt.setInt(5,2);
            pstmt.executeUpdate();

            pstmt.setString(1,"Mustard");
            pstmt.setDouble(2,2.00);
            pstmt.setDouble(3,2.60);
            pstmt.setInt(4,63);
            pstmt.setInt(5,2);
            pstmt.executeUpdate();

            pstmt.setString(1,"Milk");
            pstmt.setDouble(2,1.30);
            pstmt.setDouble(3,1.90);
            pstmt.setInt(4,58);
            pstmt.setInt(5,2);
            pstmt.executeUpdate();

            //Shop 3: The products below belong to shop 3
            pstmt.setString(1,"Cake");
            pstmt.setDouble(2,3.60);
            pstmt.setDouble(3,3.99);
            pstmt.setInt(4,50);
            pstmt.setInt(5,3);
            pstmt.executeUpdate();

            pstmt.setString(1,"Sprite");
            pstmt.setDouble(2,1.60);
            pstmt.setDouble(3,1.99);
            pstmt.setInt(4,80);
            pstmt.setInt(5,3);
            pstmt.executeUpdate();

            pstmt.setString(1,"Pork");
            pstmt.setDouble(2,4.60);
            pstmt.setDouble(3,4.99);
            pstmt.setInt(4,96);
            pstmt.setInt(5,3);
            pstmt.executeUpdate();

            pstmt.setString(1,"Mayonnaise");
            pstmt.setDouble(2,1.20);
            pstmt.setDouble(3,2.00);
            pstmt.setInt(4,51);
            pstmt.setInt(5,3);
            pstmt.executeUpdate();

            pstmt.setString(1,"Strawberry Milk");
            pstmt.setDouble(2,2.60);
            pstmt.setDouble(3,2.99);
            pstmt.setInt(4,55);
            pstmt.setInt(5,3);
            pstmt.executeUpdate();

            //Shop 4: The products below belong to shop 4
            pstmt.setString(1,"Green Apples");
            pstmt.setDouble(2,1.45);
            pstmt.setDouble(3,1.99);
            pstmt.setInt(4,56);
            pstmt.setInt(5,4);
            pstmt.executeUpdate();

            pstmt.setString(1,"Lettuce");
            pstmt.setDouble(2,0.75);
            pstmt.setDouble(3,1.10);
            pstmt.setInt(4,84);
            pstmt.setInt(5,4);
            pstmt.executeUpdate();

            pstmt.setString(1,"Sausages");
            pstmt.setDouble(2,3.45);
            pstmt.setDouble(3,4.99);
            pstmt.setInt(4,73);
            pstmt.setInt(5,4);
            pstmt.executeUpdate();

            pstmt.setString(1,"Lemonade");
            pstmt.setDouble(2,1.45);
            pstmt.setDouble(3,2.05);
            pstmt.setInt(4,50);
            pstmt.setInt(5,4);
            pstmt.executeUpdate();

            pstmt.setString(1,"Bread Sticks");
            pstmt.setDouble(2,0.45);
            pstmt.setDouble(3,1.20);
            pstmt.setInt(4,61);
            pstmt.setInt(5,4);
            pstmt.executeUpdate();

            //Shop 5: The products below belong to shop 5
            pstmt.setString(1,"Beef");
            pstmt.setDouble(2,3.69);
            pstmt.setDouble(3,4.20);
            pstmt.setInt(4,69);
            pstmt.setInt(5,5);
            pstmt.executeUpdate();

            pstmt.setString(1,"Lilt");
            pstmt.setDouble(2,1.10);
            pstmt.setDouble(3,1.40);
            pstmt.setInt(4,57);
            pstmt.setInt(5,5);
            pstmt.executeUpdate();

            pstmt.setString(1,"Oranges");
            pstmt.setDouble(2,1.10);
            pstmt.setDouble(3,1.60);
            pstmt.setInt(4,43);
            pstmt.setInt(5,5);
            pstmt.executeUpdate();

            pstmt.setString(1,"Low Fat Milk");
            pstmt.setDouble(2,0.90);
            pstmt.setDouble(3,1.50);
            pstmt.setInt(4,36);
            pstmt.setInt(5,5);
            pstmt.executeUpdate();

            pstmt.setString(1,"Chips");
            pstmt.setDouble(2,2.10);
            pstmt.setDouble(3,3.20);
            pstmt.setInt(4,72);
            pstmt.setInt(5,5);
            pstmt.executeUpdate();



        }catch(SQLException ex){
            System.out.println("SQL Exception " + ex);
            System.exit(1);
        }
    }

    //CREATING THE USERS TABLE
    public void createUsers(){

        try{

            System.out.println("Inside Create Users");

            //Create a User Table

            String create ="CREATE TABLE Users"+
                    "(username varchar2(35) PRIMARY KEY, password varchar2(35),userLevel number, phoneNum Varchar(15),address varchar2(70),name varchar2(40),email varchar2(50),shopId number, FOREIGN KEY(shopId) REFERENCES Shop(shopId))";
            pstmt = conn.prepareStatement(create);
            pstmt.executeUpdate(create);

            //Insert data into the Users Table

            String insertString = "INSERT INTO Users(username,password,userLevel,phoneNum,address,name,email,shopId) values(?,?,?,?,?,?,?,?) ";
            pstmt = conn.prepareStatement(insertString);

            pstmt.setString(1,"Admin");
            pstmt.setString(2,"NGgg5Vcch8lW");
            pstmt.setInt(3, 0);
            pstmt.setString(4, "123456789");
            pstmt.setString(5,"34a Green Street, Dublin");
            pstmt.setString(6,"John Redmond");
            pstmt.setString(7,"JohnRed@gmail.com");
            pstmt.setInt(8,1);
            pstmt.executeUpdate();

            //Shop 1 : the users below belong to shop 1
            pstmt.setString(1,"Gavin");
            pstmt.setString(2,"pkFkYHUyZDB");
            pstmt.setInt(3, 1);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"34c Cork Street, Dublin");
            pstmt.setString(6,"Gavin Daly");
            pstmt.setString(7,"GavinDaly@gmail.com");
            pstmt.setInt(8,1);
            pstmt.executeUpdate();

            pstmt.setString(1,"Paul");
            pstmt.setString(2,"zWF0qALc6tz");
            pstmt.setInt(3, 2);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"34 Woodford Hills, Dublin");
            pstmt.setString(6,"Paul Jones");
            pstmt.setString(7,"Paul.Jones@gmail.com");
            pstmt.setInt(8,1);
            pstmt.executeUpdate();

            pstmt.setString(1,"Jim");
            pstmt.setString(2,"Dk0IsmdKfDA");
            pstmt.setInt(3, 2);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"75 Patrick's Street, Galway");
            pstmt.setString(6,"Jim Barry");
            pstmt.setString(7,"Jim.Barry@gmail.com");
            pstmt.setInt(8,1);
            pstmt.executeUpdate();

            pstmt.setString(1,"Tim");
            pstmt.setString(2,"pk0O56xgRDA");
            pstmt.setInt(3, 2);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"Timmies's Street, Dublin");
            pstmt.setString(6,"Tim Murphy");
            pstmt.setString(7, "Tim.Murphy@gmail.com");
            pstmt.setInt(8,1);
            pstmt.executeUpdate();


            //Shop 2 : the users below belong to shop 2
            pstmt.setString(1,"Preston");
            pstmt.setString(2,"7ole9F7W"); //1        1NoWork4Eva
            pstmt.setInt(3, 1);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"Killers Street, Kill");
            pstmt.setString(6,"Shane Preston");
            pstmt.setString(7, "Shane.Preston@killers.com");
            pstmt.setInt(8,2);
            pstmt.executeUpdate();
            
            pstmt.setString(1,"Emily");
            pstmt.setString(2,"5qdelW"); //1 
            pstmt.setInt(3, 2);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"Killers Street, Kill");
            pstmt.setString(6,"Emily Lastname");
            pstmt.setString(7, "Emily.Lname@uncreative.com");
            pstmt.setInt(8,2);
            pstmt.executeUpdate();

            pstmt.setString(1,"Bob");
            pstmt.setString(2,"Ug186l7EH5"); //1
            pstmt.setInt(3, 2);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"Prestons Road, Naas");
            pstmt.setString(6,"Robert Jones");
            pstmt.setString(7, "bob.jo@hotmail.com");
            pstmt.setInt(8,2);
            pstmt.executeUpdate();

            //Shop 3 : the users below belong to shop 3
            pstmt.setString(1,"Sam");
            pstmt.setString(2,"pwDAch5YZk"); //1
            pstmt.setInt(3, 1);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"Alexandra Street, Clane");
            pstmt.setString(6,"Samuel Winchester");
            pstmt.setString(7, "sam666@gmail.com");
            pstmt.setInt(8,3);
            pstmt.executeUpdate();

            pstmt.setString(1,"Willy");
            pstmt.setString(2,"RaiCuTI4nlM"); //1
            pstmt.setInt(3, 2);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"Abbeylands, Straffan");
            pstmt.setString(6,"William Brennan");
            pstmt.setString(7, "willBr@gmail.com");
            pstmt.setInt(8,3);
            pstmt.executeUpdate();

            pstmt.setString(1,"Chester");
            pstmt.setString(2,"xAz0m47u2xKILAT"); //1
            pstmt.setInt(3, 2);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"Cloisters, Celbridge");
            pstmt.setString(6,"Chester Raheem");
            pstmt.setString(7, "chester.raheem@hotmail.com");
            pstmt.setInt(8,3);
            pstmt.executeUpdate();

            //Shop 4 : the user below belong to shop 4
            pstmt.setString(1,"Sergei");
            pstmt.setString(2,"a8x0YgLO1yKEcAS"); //1
            pstmt.setInt(3, 1);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"Stalingrad Street, Dublin");
            pstmt.setString(6,"Sergei Putin");
            pstmt.setString(7, "ser.put@gmail.com");
            pstmt.setInt(8,4);
            pstmt.executeUpdate();

            pstmt.setString(1,"Shannon");
            pstmt.setString(2,"RUcM5Izgqxio7Ur"); //1
            pstmt.setInt(3, 2);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"Cork Street, Clane");
            pstmt.setString(6,"Shanon Ataev");
            pstmt.setString(7, "shannon.at@gmail.com");
            pstmt.setInt(8,4);
            pstmt.executeUpdate();

            pstmt.setString(1,"Lisa");
            pstmt.setString(2,"X6FoR9hwVsDw"); //1
            pstmt.setInt(3, 2);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"Adams Street, Leixlip");
            pstmt.setString(6,"Lisa Baelish");
            pstmt.setString(7, "lisa.baelish@gmail.com");
            pstmt.setInt(8,4);
            pstmt.executeUpdate();

            //Shop 5 : the users below belong to shop 5
            pstmt.setString(1,"Sophie");
            pstmt.setString(2,"L8bysllucmTGlC"); //1
            pstmt.setInt(3, 1);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"Manor Road, Newbridge");
            pstmt.setString(6,"Sophie Sheridan");
            pstmt.setString(7, "sophie.sheri@hotmail.com");
            pstmt.setInt(8,5);
            pstmt.executeUpdate();

            pstmt.setString(1,"Stephen");
            pstmt.setString(2,"tov8Z1hsNWLeTK1W"); //1
            pstmt.setInt(3, 2);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"Belgard Road, Tallaght");
            pstmt.setString(6,"Stephen Green");
            pstmt.setString(7, "steve.gr@hotmail.com");
            pstmt.setInt(8,5);
            pstmt.executeUpdate();

            pstmt.setString(1,"Kelly");
            pstmt.setString(2,"74hyXgGsPdJi"); //1
            pstmt.setInt(3, 2);
            pstmt.setString(4,"123456789");
            pstmt.setString(5,"Killers Street, Tallaght");
            pstmt.setString(6,"Kelly Murphy");
            pstmt.setString(7, "kelly.murph@hotmail.com");
            pstmt.setInt(8,5);
            pstmt.executeUpdate();




        }catch(SQLException ex){
            System.out.println("SQL Exception " + ex);
            System.exit(1);
        }
    }

    //Converting java.util.Date to java.sql.date
    private static java.sql.Date settingDate(){
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }

    //CREATING THE DELIVERY TABLE
    public void createDelivery(){

        try{

            System.out.println("Inside Create Delivery");

            //Create a Delivery Table

            String create ="CREATE TABLE Delivery"+
                    "(orderId Varchar2(15) PRIMARY KEY, orderDate Date,orderCost decimal(5,2), productId varchar2(15),shopId number,qty number,Foreign Key(productId) References Products(productId),Foreign Key(shopId) References Shop(shopId))";
            pstmt = conn.prepareStatement(create);
            pstmt.executeUpdate(create);


            //Creating a sequence

            String createseq = "create sequence orderId_seq increment by 1 start with 1";
            pstmt = conn.prepareStatement(createseq);
            pstmt.executeUpdate(createseq);


            //Insert data into the Delivery Table

            String insertString = "INSERT INTO Delivery(orderId,orderDate,orderCost,productId,shopId,qty) values(orderId_seq.nextVal,To_date(?,'YYYY/mon/DD'),?,?,?,?) ";
            pstmt = conn.prepareStatement(insertString);
            pstmt.setDate(1, settingDate());


            pstmt.setString(1, "2014/May/02");
            pstmt.setDouble(2, 23.32);
            pstmt.setInt(3, 1);
            pstmt.setInt(4,1);
            pstmt.setInt(5,10);
            pstmt.executeUpdate();


            pstmt.setString(1,"2014/Apr/04");
            pstmt.setDouble(2, 54.12);
            pstmt.setInt(3,1);
            pstmt.setInt(4,1);
            pstmt.setInt(5,16);
            pstmt.executeUpdate();


            pstmt.setString(1, "2015/Jun/02");
            pstmt.setDouble(2, 83.23);
            pstmt.setInt(3,1);
            pstmt.setInt(4,2);
            pstmt.setInt(5,30);
            pstmt.executeUpdate();


            pstmt.setString(1, "2015/Aug/01");
            pstmt.setDouble(2, 64.54);
            pstmt.setInt(3,1);
            pstmt.setInt(4,2);
            pstmt.setInt(5,26);
            pstmt.executeUpdate();


            pstmt.setString(1, "2014/Mar/04");
            pstmt.setDouble(2, 43.34);
            pstmt.setInt(3,6);
            pstmt.setInt(4,3);
            pstmt.setInt(5,15);
            pstmt.executeUpdate();

            pstmt.setString(1, "2014/Apr/12");
            pstmt.setDouble(2, 68.87);
            pstmt.setInt(3,2);
            pstmt.setInt(4,3);
            pstmt.setInt(5,62);
            pstmt.executeUpdate();

            pstmt.setString(1, "2014/Sep/27");
            pstmt.setDouble(2, 65.87);
            pstmt.setInt(3,3);
            pstmt.setInt(4,4);
            pstmt.setInt(5,36);
            pstmt.executeUpdate();

            pstmt.setString(1, "2014/Jan/30");
            pstmt.setDouble(2, 42.87);
            pstmt.setInt(3,4);
            pstmt.setInt(4,4);
            pstmt.setInt(5,43);
            pstmt.executeUpdate();

            pstmt.setString(1, "2014/Jun/16");
            pstmt.setDouble(2, 36.87);
            pstmt.setInt(3,1);
            pstmt.setInt(4,5);
            pstmt.setInt(5,23);
            pstmt.executeUpdate();

            pstmt.setString(1, "2014/Jul/04");
            pstmt.setDouble(2, 67.87);
            pstmt.setInt(3,4);
            pstmt.setInt(4,5);
            pstmt.setInt(5,45);
            pstmt.executeUpdate();

        }catch(SQLException ex){
            System.out.println("SQL Exception " + ex);
            System.exit(1);
        }
    }

    //CREATING THE SALES TABLE
    public void createSales(){

        try{

            System.out.println("Inside Create Sales");

            //Create a Sales Table

            String create ="CREATE TABLE Sales "+
                    "(salesId varchar2(15), username varchar2(15), productId varchar2(15), amountSold number, totalSale decimal(5,2),customer varchar2(50),salesDate Date,"+
                    "PRIMARY KEY (salesId), FOREIGN KEY(username) REFERENCES Users(username), FOREIGN KEY(productId) REFERENCES Products(productId))";

            pstmt = conn.prepareStatement(create);
            pstmt.executeUpdate(create);


            //Creating a sequence

            String createseq = "create sequence salesId_seq increment by 1 start with 1";
            pstmt = conn.prepareStatement(createseq);
            pstmt.executeUpdate(createseq);


            //Insert data into the Sales Table
            String insertString = "INSERT INTO Sales(salesId,username,productId,amountSold,totalSale,customer,salesDate) values(salesId_seq.nextVal,?,?,?,?,?,To_date(?,'YYYY/MM/DD'))";
            pstmt = conn.prepareStatement(insertString);
            
            pstmt.setString(1, "Gavin");
            pstmt.setString(2, "1");
            pstmt.setInt(3,3);
            pstmt.setDouble(4, 6.12);
            pstmt.setString(5, "Fixing Birch");
            pstmt.setString(6, "2015-04-10");
            pstmt.executeUpdate();
            
            pstmt.setString(1, "Emily");
            pstmt.setString(2, "6");
            pstmt.setInt(3, 23);
            pstmt.setDouble(4, 27.60);
            pstmt.setString(5, "Bob Bones");
            pstmt.setString(6, "2014-08-11");
            pstmt.executeUpdate();

            pstmt.setString(1, "Bob");
            pstmt.setString(2, "3");
            pstmt.setInt(3, 13);
            pstmt.setDouble(4, 18.25);
            pstmt.setString(5, "Alan Himlin");
            pstmt.setString(6, "2014-07-30");
            pstmt.executeUpdate();

            pstmt.setString(1, "Sam");
            pstmt.setString(2, "8");
            pstmt.setInt(3, 42);
            pstmt.setDouble(4, 65.10);
            pstmt.setString(5, "Jack Peterson");
            pstmt.setString(6, "2014-11-30");
            pstmt.executeUpdate();

            pstmt.setString(1, "Kelly");
            pstmt.setString(2, "5");
            pstmt.setInt(3, 13);
            pstmt.setDouble(4, 23.10);
            pstmt.setString(5, "Ciara McDonnagh");
            pstmt.setString(6, "2014-09-16");
            pstmt.executeUpdate();


            pstmt.setString(1, "Shannon");
            pstmt.setString(2, "4");
            pstmt.setInt(3, 16);
            pstmt.setDouble(4, 43.10);
            pstmt.setString(5, "Misha Poliak");
            pstmt.setString(6, "2014-06-24");
            pstmt.executeUpdate();


        }catch(SQLException ex){
            System.out.println("SQL Exception " + ex);

            System.exit(1);
        }
    }

    //DISPLAY ALL THE USERS
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

    //DISPLAY ALL THE PRODUCTS
    public void queryProducts(){

        try{
            String queryString = "Select * FROM Products";
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery(queryString);
            System.out.println("Products Table");

            while(rset.next()){
                System.out.println(rset.getString(1) + " " + rset.getString(2)+ " " + " " + rset.getDouble(3)+ " " + rset.getDouble(4) + " "+ rset.getInt(5));
            }

        }catch(Exception ex){
            System.out.println("Error here at displaying The info " + ex);
        }
    }

    //DISPLAY ALL THE DELIVERIES
    public void queryDelivery(){

        try{
            String queryString = "Select * FROM Delivery";
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

    //DISPLAY ALL THE SALES
    public void querySales(){

        try{
            String queryString = "Select * FROM Sales";
            pstmt = conn.prepareStatement(queryString);
            rset = pstmt.executeQuery(queryString);
            System.out.println("Sales Table");

            while(rset.next()){
                System.out.println(rset.getString(1)+ " " + rset.getString(2)+  " " + rset.getString(3)+ " " + rset.getDouble(4) + " " + rset.getDouble(5)+ " " + rset.getString(6)+ " "+ rset.getDate(7));
            }

        }catch(Exception ex){
            System.out.println("Error here at displaying The info " + ex);
        }
    }

    //DISPLAY ALL THE SHOPS
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

    //RUN
    public static void main(String args[]){
        CreateDatabase cd = new CreateDatabase();
        cd.dropTables();
        cd.createShop();
        cd.createUsers();
        cd.createProducts();
        cd.createDelivery();
        cd.createSales();
        cd.queryUsers();
        cd.queryProducts();
        cd.queryDelivery();
        cd.queryShop();
        cd.querySales();

    }
}