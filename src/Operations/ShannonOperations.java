package Operations;

import java.sql.*;

import oracle.jdbc.pool.OracleDataSource;

public class ShannonOperations {
    private PreparedStatement pstmt;
    private ResultSet rset;
    private Connection conn;
    private Statement stmt;

    public ShannonOperations() {
        conn = openDB();
    }

    public Connection openDB() {
        try {
            OracleDataSource ods = new OracleDataSource();

         //Tallaght
              //ods.setURL("jdbc:oracle:thin:@//10.10.2.7:1521/global1");
              //ods.setUser("x00108663");
              //ods.setPassword("db31Mar96");
            
            // Home Oracle XE
            ods.setURL("jdbc:oracle:thin:HR/pmagee@localhost:1521:XE");
            ods.setUser("hr");
            ods.setPassword("passhr");

        


            conn = ods.getConnection();
            System.out.println("connected.");
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

    public int Login(String user, String pass){
        try{
            String query = "SELECT userLevel FROM Users WHERE username="+" '"+user+"' " + "AND " +"password= "+"'"+pass+"'";
            pstmt =conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);

            int level=-7;
            while(rset.next()){
                level = rset.getInt(1);
            }
            System.out.print(level);
            return level;

        }catch(Exception ex){
            System.out.println("Error here at login operation" + ex);
            return -7;
        }
    }

    public ResultSet showStock(){
        try{

            String query ="SELECT * FROM Products";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);


            return rset;

        }catch(Exception ex){
            System.out.println("Error here at showStock Operation "+ ex);
            return null;
        }

    }
    //ADD PRODUCT CODE
    public void addProduct(String productName, Double productPrice, Double sellCost, int qty, int shopId){
        try{

        	String insertString = "INSERT INTO Products(productId,productName,productPrice,sellCost,qtyOnHand,shopId) values(productId_seq.nextVal,?,?,?,?,?)";

            pstmt = conn.prepareStatement(insertString);

            pstmt.setString(1, productName);
            pstmt.setDouble(2, productPrice);
            pstmt.setDouble(3, sellCost);
            pstmt.setInt(4, qty);
            pstmt.setInt(5, shopId);
            pstmt.executeUpdate();
            
            String query ="SELECT * FROM Products";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);
            
            while(rset.next()){
            	System.out.print(rset);
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }
 // GET PRODUCT NAME
    public String getProductName(String prodID){
        String prodNames ="";
        try{
            String sql ="SELECT productName FROM Products WHERE productID = "+prodID+"";
            pstmt =conn.prepareStatement(sql);
            rset= pstmt.executeQuery(sql);

            if(rset.next()){
                prodNames =rset.getString(1);
            }
        }catch(Exception ex){
            System.out.println("Error here at getProductName " + ex);
        }
        return prodNames;
    }
    
    public double getProductProfit(String prodID){
        double price=0, cost=0, profit=0;
        try{
            String sql ="SELECT productprice, sellcost FROM Products WHERE productID = "+prodID+"";
            pstmt =conn.prepareStatement(sql);
            rset= pstmt.executeQuery(sql);

            if(rset.next()){
                price =rset.getDouble(1);
                cost = rset.getDouble(2);
            }
            profit = cost-price;
            return profit;
        }catch(Exception ex){
            System.out.println("Error here at getProductProfit " + ex);
            return 0;
        }
       
    }
    
    public void removeStock(String name, int diff){
    	try{
            String removeSQL =  "UPDATE Products SET  qtyOnHand='" + diff+"' WHERE productName='"+name+"'";


            stmt = conn.createStatement();
            stmt.executeUpdate(removeSQL);

        }catch (Exception se) {
            System.out.println(se);
        }
    }
  //Get Quantity
    public int getQtybyID(String ID){
        int prodQty = -7;
        try{
            String sql ="SELECT qtyOnHand FROM Products WHERE productId='"+ID+"'";
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
    
    public void updateBank(double amt, int shopID){
    	try{
    		double newBal = amt + getShopMoney(shopID);
    		
            String removeSQL =  "UPDATE Shop SET  shopBank='" + newBal+"' WHERE shopID='"+shopID+"'";


            stmt = conn.createStatement();
            stmt.executeUpdate(removeSQL);

        }catch (Exception se) {
            System.out.println(se);
        }
    }
    
   
    
    public double sellStock(String prodID, int qty, int diff, int shopID){
    	try{
    		double profit = (getProductProfit(prodID)*qty);
    		
    		updateBank(profit, shopID);
    		removeStock(getProductName(prodID), diff);
    		
    		
    		
    		return profit;
    	}catch(Exception e){
    		System.out.println(e);
    		return 0;
    	}
    	
    }
    
    public ResultSet salesSearch(String id, int qty, int shopId) { //Searching by Product Id

        try {

            String query = "SELECT productID, ProductName AS \"Product Name\", ProductPrice AS \"Price\", SellCost AS \"Cost\", qtyOnHand AS \"Amount Available\", "+qty+" AS \"Quantity\"  FROM Products WHERE productId = '" + id + "'AND shopId='"+shopId+"'";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);
           

        } catch (Exception ex) {
            System.out.println("Error here at searchbyProdId Operation "+ex);
        }
        return rset;
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
    	
    

}
