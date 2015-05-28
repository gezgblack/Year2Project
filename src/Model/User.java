package Model;

import Operations.DBOperations;

public class User {

    //Variables
	private String username, phNum, addr, name, email;
	private int shopId, userLevel;
	protected String password;
	DBOperations db = new DBOperations();
	
	
	//Constructor 
	public User(){
		userLevel = -7;
	}
	public User(String username, String phNum, String addr, String name, String email, int shopId, int userLevel, String password){
		this.username = username; this.phNum = phNum; this.addr = addr; this.name = name; this.email = email; this.shopId = shopId; this.userLevel = userLevel; this.password=password;	
	}
	public User(String username, String phNum, String addr, String name, String email, int shopId, int userLevel){
		this.username = username; this.phNum = phNum; this.addr = addr; this.name = name; this.email = email; this.shopId = shopId; this.userLevel = userLevel;	
	}
	
	
	//Saves the user to the Database
	public void addUserToDB(){db.addUser(username, password, userLevel, phNum, addr, name, email, shopId);}
	
	//Start of getters
	public String 	getUsername(){	return this.username;}
	public String 	getphNum(){		return this.phNum;}
	public String 	getAddr(){		return this.addr;}
	public String 	getName(){		return this.name;}
	public String 	getEmail(){		return this.email;}
	public int 		getShopID(){	return this.shopId;}
	public int 		getuserLevel(){	return this.userLevel;}
    //End of getters
	
}
