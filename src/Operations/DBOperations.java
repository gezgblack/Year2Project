package Operations;
 
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import oracle.jdbc.pool.OracleDataSource;
 
public class DBOperations {
    private PreparedStatement pstmt;
    private ResultSet rset;
    private Connection conn;
    private Statement stmt;
 
    public DBOperations() {
        conn = openDB();
    }
 
    public Connection openDB() {
        try {
            OracleDataSource ods = new OracleDataSource();
 
            //Tallaght
            ods.setURL("jdbc:oracle:thin:@//10.10.2.7:1521/global1");
            ods.setUser("x00108663");
            ods.setPassword("db31Mar96");
 
            // Home Oracle XE
            //ods.setURL("jdbc:oracle:thin:HR/pmagee@localhost:1521:XE");
            //ods.setUser("hr");
            //ods.setPassword("passhr");
 
 
            conn = ods.getConnection();
        } catch (Exception e) {
            System.out.print("Unable to load driver " + e);
            System.exit(1);
        }
        return conn;
    }
 
    public void closeDB() {
        try {
            conn.close();
            System.out.print("Connection closed");
        } catch (SQLException e) {
            System.out.print("Could not close connection ");
            e.printStackTrace();
        }
    }
 
    public ResultSet Login(String user, String pass) {
        try{
            String query = "SELECT username, phoneNum, address, name, email, shopId, userLevel FROM Users WHERE username=" + " '" + user + "' AND password='"+pass+"'";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);
            
           return rset; 
 
        } catch (Exception ex) {
            System.out.println("Error here at login operation" + ex);
           return null;
        }
    }
    public int getQtybyID(String ID){
        int prodQty = -7;
        try{
            String sql ="SELECT qtyOnHand FROM Products WHERE productID = "+ID+"";
            pstmt =conn.prepareStatement(sql);
            rset= pstmt.executeQuery(sql);

            if(rset.next()){
                prodQty =rset.getInt(1);
            }
            return prodQty;
        }catch(Exception ex){
            System.out.println("Error here at getQtybyID " + ex);
            return -7;
        }
    }
 
    public ResultSet showStock(int shopID) {
        try {
 
            String query = "SELECT productId,productName,productPrice,sellCost,qtyOnHand FROM Products WHERE shopId='"+shopID+"'" ;
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);
 
 
            return rset;
 
        } catch (Exception ex) {
            System.out.println("Error here at showStock Operation " + ex);
            return null;
        }
 
    }

    public ResultSet showSales(int shopId) {
        try {
 
            String query = "SELECT salesId, sales.username, productId, amountSold, totalSale, customer, salesDate FROM Sales INNER JOIN Users ON Sales.Username=Users.Username WHERE ShopId ='"+shopId+"' ORDER BY salesdate DESC";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);
 
 
            return rset;
 
        } catch (Exception ex) {
            System.out.println("Error here at showStock Operation " + ex);
            return null;
        }
 
    }
 
    public ResultSet searchSales(String name, int shopId) { //Searching by Product Id
 
        try {
 
            String query = "SELECT salesId, sales.username, productId, amountSold, totalSale, customer, salesDate FROM Sales INNER JOIN Users ON Sales.username=Users.username WHERE salesId = '" + name + "' AND ShopId ='"+shopId+"'";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);
 
        } catch (Exception ex) {
            System.out.println("Error here at searchId Operation");
        }
        return rset;
    }
 
 
 
    public ResultSet searchId(String id, int shopId) { //Searching by Product Id
 
        try {
 
            String query = "SELECT productId,productName,productPrice,sellCost,qtyOnHand FROM Products WHERE productId = '" + id + "' AND shopId ='"+shopId+"'";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);
 
        } catch (Exception ex) {
            System.out.println("Error here at searchId Operation");
        }
        return rset;
    }
 
    public ResultSet searchName(String name, int shopID) { //Searching by Product Name
 
        try {
 
            String query = "SELECT productId,productName,productPrice,sellCost,qtyOnHand FROM Products WHERE UPPER(productName) = UPPER('" + name + "') AND shopId ='"+shopID+"'";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);

        } catch (Exception ex) {
            System.out.println("Error here at searchName Operation");
        }
        return rset;
    }

    public ResultSet searchOrderId(String id, int shopId) { //Searching by Order Id

        try {

            String query = "SELECT orderId,orderDate,orderCost FROM Delivery WHERE orderId = '" + id + "'" + "AND shopId ='"+shopId+"'";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);

        } catch (Exception ex) {
            System.out.println("Error here at searchOrderId Operation");
        }
        return rset;
    }

    //ADD PRODUCT CODE
    public void addProduct(String productName, Double productPrice, Double sellCost, int qty, int shopId) {
        try {

            String insertString = "INSERT INTO Products(productId,productName,productPrice,sellCost,qtyOnHand,shopId) values(productId_seq.nextVal,?,?,?,?,?)";

            pstmt = conn.prepareStatement(insertString);

            pstmt.setString(1, productName);
            pstmt.setDouble(2, productPrice);
            pstmt.setDouble(3, sellCost);
            pstmt.setInt(4, qty);
            pstmt.setInt(5, shopId);
            pstmt.executeUpdate();

            String query = "SELECT * FROM Products";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);

            while (rset.next()) {
                System.out.print(rset);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

 
    //REMOVE A PRODUCT
    public int deleteProduct(String name, int qty){
 
 
        int no=0;
        try{
            String productNames = getProductName(name);
            String sql = "DELETE FROM Products WHERE productName ="+"'"+ name +"'"+"AND qtyOnHand =" + qty;
            pstmt= conn.prepareStatement(sql);
            no = pstmt.executeUpdate(sql);


        }catch(Exception ex){
            System.out.println("Error here in deleteProduct Operations" + ex);
        }
        return no;
    }
 
 
    // GET PRODUCT NAME
    public String getProductName(String name){
        String prodNames ="";
        try{
            String sql ="SELECT productName FROM Products WHERE productName = "+"'"+name+"'";
            pstmt =conn.prepareStatement(sql);
            rset= pstmt.executeQuery(sql);
 
            if(rset.next()){
                prodNames =rset.getString(0);
            }
        }catch(Exception ex){
            System.out.println("Error here at getProductName " + ex);
        }
        return prodNames;
    }

    public int getShopIdofProduct(String productId){
        int shopId =0;
        try{
            String sql ="SELECT shopId FROM Products WHERE productId = "+"'"+productId+"'";
            pstmt =conn.prepareStatement(sql);
            rset= pstmt.executeQuery(sql);

            if(rset.next()){
                shopId =rset.getInt(1);
            }
        }catch(Exception ex){
            System.out.println("Error here at getProductName " + ex);
        }
        return shopId;
    }

    public void editStock(String prodID, String newName, double newPrice, double newSellCost, int newQty){
 
        try{
            String e =  "UPDATE Products SET  productName=" + "'" + newName + "'"+ "," + "productPrice=" + "'" + newPrice + "'" + ","+
                        "sellCost=" + "'" + newSellCost + "'" + "," + "qtyOnHand=" + "'" + newQty + "' WHERE PRODUCTID='"+prodID+"'";
 
 
            stmt = conn.createStatement();
            stmt.executeUpdate(e);
 
        }catch (Exception se) {
            System.out.println(se);
        }
    }
     
    public void removeProduct(String name, int shopId){
        try{
             
            String deleteString = "DELETE FROM PRODUCTS WHERE productName = '"+name+"' AND shopId ='"+shopId+"'";
             
             stmt = conn.createStatement();
             stmt.executeUpdate(deleteString);
 
        }catch(Exception se){
            System.out.println(se);
        }
    }

    //Set stock qty using productid
    public void changeStockQty(String productId, int diff, int shopId){
        try{
            String removeSQL =  "UPDATE Products SET  qtyOnHand='" + diff+"' WHERE productId='"+productId+"' AND shopId ='"+shopId+"'";
            stmt = conn.createStatement();
            stmt.executeUpdate(removeSQL);

        }catch (Exception se) {
            System.out.println(se);
        }
    }
 
    public ResultSet showDelivery(int shopId) {
        try {
            String s="";
            String query = "SELECT orderId,orderDate,orderCost FROM Delivery WHERE shopId='" + shopId + "' ORDER BY orderDate DESC" ;
            pstmt = conn.prepareStatement(query);
            rset=pstmt.executeQuery(query);
 
 
            return rset;
 
        } catch (Exception ex) {
            System.out.println("Error here at showDelivery Operation " + ex);
            return null;
        }
 
    }


    public ResultSet filterDate(String dateToBack, String table, int shopId){
        try{
            Calendar dt = Calendar.getInstance();
            Calendar backDate = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("YYYY-MMM-dd");

            Date d1 = new Date(); Date d2;
            d1 = dt.getTime();
            String query ="", datePhrase="";
            String s = dateFormat.format(d1).toString();
            if(table.equals("Delivery")){
                query ="select orderId,orderDate,orderCost from delivery where shopId ='"+shopId+"' AND "; datePhrase = "orderDate";
            }else if(table.equals("Sales")){
                query ="SELECT salesId, sales.username, productId, amountSold, totalSale, customer, salesDate FROM Sales INNER JOIN Users ON Sales.Username=Users.Username WHERE users.ShopId = '"+shopId+"' AND "; datePhrase="salesDate";
            }

            if(dateToBack.equals("Last Week")){

                backDate.add(Calendar.DAY_OF_YEAR, -7);
                d2 = backDate.getTime();
                String s2 = dateFormat.format(d2).toString();


                // System.out.println(s+"\n"+ s2);

                query += (datePhrase+" <=To_date('"+s+"','YYYY-mon-DD') AND "+datePhrase+" >=To_date('"+s2+"', 'YYYY-mon-DD')");

            }
            else if(dateToBack.equals("Last Month")){
                backDate.add(Calendar.DAY_OF_YEAR,-30);
                d2 = backDate.getTime();
                String s2 = dateFormat.format(d2).toString();

                query += (datePhrase+" <=To_date('"+s+"','YYYY-mon-DD') AND "+datePhrase+" >=To_date('"+s2+"', 'YYYY-mon-DD')");

            }

            else if(dateToBack.equals("Last Year")){
                backDate.add(Calendar.DAY_OF_YEAR,-365);
                d2 = backDate.getTime();
                String s2 = dateFormat.format(d2).toString();

                query += (datePhrase+" <=To_date('"+s+"','YYYY-mon-DD') AND "+datePhrase+" >=To_date('"+s2+"', 'YYYY-mon-DD')");

            }

            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);
            return rset;

        }catch(Exception ex){
            System.out.println("Error here at filterDate Operation "+ ex);
            return null;

        }

    }

    //ADD NEW DELIVERY
    public void addDelivery(String orderDate, String productId, int quantity, double cost,int shopId) {
        try {

            String insertString = "INSERT INTO Delivery(orderId, orderDate,productId,qty,orderCost,shopId) values(orderId_seq.nextVal, To_date(?,'YYYY-mon-DD'),?,?,?,?)";

            pstmt = conn.prepareStatement(insertString);
            //pstmt.setDate(1, settingDate());

            pstmt.setString(1, orderDate);
            pstmt.setString(2, productId);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4,cost);
            pstmt.setInt(5,shopId);
            pstmt.executeUpdate();

            String query = "SELECT * FROM Delivery";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);

            while (rset.next()) {
                System.out.print(rset);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }



    public void logSale(String user, String productID, int qty, double total, String custName, String date) {
        try {

            String insertString = "INSERT INTO Sales(salesId,username,productId,amountSold,totalSale,customer,salesDate) values(salesId_seq.nextVal,?,?,?,?,?,To_date(?,'YYYY/MM/DD HH24/MI/SS'))";

            pstmt = conn.prepareStatement(insertString);
            pstmt.setString(1, user);
            pstmt.setString(2, productID);
            pstmt.setDouble(3, qty);
            pstmt.setDouble(4, total);
            pstmt.setString(5, custName);
            pstmt.setString(6, date);
            pstmt.executeUpdate();

            String query = "SELECT * FROM Products";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);

            while (rset.next()) {
                System.out.print(rset);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }


    //GET SHOP MONEY
    public double getShopMoney(int shopID){
        try{

            double money =0;

            String sql ="SELECT shopBank FROM Shop WHERE shopID = "+shopID+"";
            pstmt =conn.prepareStatement(sql);
            rset= pstmt.executeQuery(sql);

            if(rset.next()){
                money =rset.getDouble(1);
            }
            return money;

        }catch(Exception e){
            System.out.println(e);
            return 0;
        }
    }

    public void updateBank(double amt,boolean positive, int shopID) {
        try {
            double newBal = 0;
            if (positive == true) {

                newBal = amt + getShopMoney(shopID);
            } else {
                newBal = getShopMoney(shopID)- amt;
            }
            String removeSQL = "UPDATE Shop SET  shopBank='" + newBal + "' WHERE shopID='" + shopID + "'";

            stmt = conn.createStatement();
            stmt.executeUpdate(removeSQL);

        }catch(Exception se){
            System.out.println(se);
        }
    }

    //Sell Stock
    public void sellStock(String prodName, double total, int diff, int shopID){
        try{
            updateBank(total,true,shopID); //Adds the total of the sale to the shops bank
            changeStockQty(prodName, diff, shopID); //Changes stock quantity

        }catch(Exception e){
            System.out.println(e);

        }

    }

    public ResultSet salesSearch(ArrayList<String> id, int shopId) { //Searching by Product Id

        try {
        	String idS = "";
        	
        	for(int loop1 =0; loop1 < id.size();loop1++){
        		if(loop1 == 0){
        			idS += "productID ='"+id.get(loop1)+"'";
        		}else if(loop1==id.size()){
        			idS += "OR productID='"+id.get(loop1)+"'";
        			
        		}else{
        			idS += "OR productID='"+id.get(loop1)+"'";
        		}
        	}
        

            String query = "SELECT productID, ProductName AS \"Product Name\", ProductPrice AS \"Price\", SellCost AS \"Cost\", qtyOnHand AS \"Amount Available\", 'Shannon Is Awesome'  AS \"Quantity\" FROM Products WHERE shopId='"+shopId+"' AND (" + idS + ")";
           
            
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);


        } catch (Exception ex) {
            System.out.println("Error here at sales Operation "+ex);
        }
        return rset;
    }
    
    public boolean inStock(String prodId, int shopId){
    	
    	boolean in = false;
    	try{
    		String sql ="SELECT productId FROM Products WHERE shopID = "+shopId+" AND productID ='"+prodId+"'";
            pstmt =conn.prepareStatement(sql);
            rset= pstmt.executeQuery(sql);
            
            while(rset.next()){
            	if(rset.getString(1).equals(prodId)){
            		in = true;
            	}
            }
    	}catch(Exception e){
    		System.out.println("Error here at InStock "+e);
    	}
    return in;
    }


    public String[] getShops(){
        try {

            String query1 = "SELECT shopName FROM Shop";
            String query2 = "Select Count(*) FROM Shop";
            pstmt = conn.prepareStatement(query1);
            ResultSet rset1 = pstmt.executeQuery(query1);

            pstmt = conn.prepareStatement(query2);
            ResultSet rset2 = pstmt.executeQuery(query2);
            int counter = 0;
            int length =0;
            while(rset2.next()){ length = rset2.getInt(1);}


            String[] shopIds = new String[length];
            while(rset1.next()){shopIds[counter]=rset1.getString(1); counter++;}

            return shopIds;


        } catch (Exception ex) {
            System.out.println("Error here at searchbyProdId Operation "+ex);
        }
        return null;
    }

    public void addShop(String shopName, String shopAddress, String phoneNum, double shopBank) {
        try {

            String insertString = "INSERT INTO Shop(shopId,shopName,shopAddress,phoneNum,shopBank) values(shopId_seq.nextVal,?,?,?,?)";

            pstmt = conn.prepareStatement(insertString);

            pstmt.setString(1, shopName);
            pstmt.setString(2, shopAddress);
            pstmt.setString(3, phoneNum);
            pstmt.setDouble(4, shopBank);
            pstmt.executeUpdate();

            String query = "SELECT * FROM Shop";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);

            while (rset.next()) {
                System.out.print(rset);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Remove Shop
    public void removeShop(String shopId){
        try{

            String deleteString = "DELETE FROM SHOP WHERE shopId = '"+shopId+"'";

            stmt = conn.createStatement();
            stmt.executeUpdate(deleteString);

        }catch(Exception se){
            System.out.println(se);
        }
    }

    //ADD User
    public void addUser(String username, String pw,int lvl, String phNum, String addr, String name, String email, int shopId) {
        try {

            String insertString = "INSERT INTO Users(username,password,userLevel,phoneNum,address,name,email,shopId) values(?,?,?,?,?,?,?,?) ";

            pstmt = conn.prepareStatement(insertString);

            pstmt.setString(1, username);
            pstmt.setString(2, pw);
            pstmt.setInt(3, lvl);
            pstmt.setString(4, phNum);
            pstmt.setString(5, addr);
            pstmt.setString(6,name);
            pstmt.setString(7, email);
            pstmt.setInt(8, shopId);
            pstmt.executeUpdate();

            String query = "SELECT * FROM Users";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);

            while (rset.next()) {
                System.out.print(rset);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int getShopIdOfShopName(String shopName){
        int shopId = -7;
        try{
            String query="SELECT shopId From Shop WHERE  shopName=  '"+ shopName+ "'";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery();



            while(rset.next()){
                shopId = rset.getInt(1);
            }
            return shopId;
        }catch(Exception ex){
            System.out.println("Error here at getShopId Operation " + ex);
            return shopId;
        }
    }
    
    public ResultSet getProductByName(String name, int shopId) { //Searching by Product name
    	 
        try {
 
            String query = "SELECT * FROM Products WHERE productName = '" + name + "' AND shopId ='"+shopId+"'";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);
 
        } catch (Exception ex) {
            System.out.println("Error here at searchId Operation");
        }
        return rset;
    }
    public ResultSet getShopDetails(String shopId){//Gets a shops information for the purposes of constructing a shop object
    	try { 
            String query = "SELECT * FROM Shop WHERE shopId ='"+shopId+"'";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);
 
        } catch (Exception ex) {
            System.out.println("Error here at getShopDetails Operation");
        }
        return rset;
    }

    public ResultSet showDb(String db){

        try {

            String query = "SELECT * FROM "+db+"";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);


            return rset;

        } catch (Exception ex) {
            System.out.println("Error here at Show DB Operation " + ex);
            return null;
        }
    }

    public ResultSet productDetails(String prodID){
        try {

            String query = "SELECT * FROM Products WHERE productId='"+prodID+"'";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);


            return rset;

        } catch (Exception ex) {
            System.out.println("Error here at Product Details Operation " + ex);
            return null;
        }
    }
    public boolean usernameExists(String username){
        try {

            String query = "SELECT * FROM Users WHERE username='"+username+"'";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);

            while(rset.next()){
                return true;
            }



        } catch (Exception ex) {
            System.out.println("Error here at Product Details Operation " + ex);
        }
        return false;
    }


}