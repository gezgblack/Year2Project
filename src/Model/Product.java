package Model;
import Operations.DBOperations;

public class Product {
    //Variables
    private String productName, productID;
    private double productPrice,sellCost;
    private int qtyOnHand,shopId,qtyChange;
    DBOperations db = new DBOperations();

    //Start of constructors
    //Pre-existing object
    public Product(String productID,String productName,double productPrice,double sellCost,int qty,int shopId){
        this.productID = productID; this.productName=productName; this.productPrice=productPrice; this.sellCost=sellCost; this.qtyOnHand=qty; this.shopId=shopId;
    }
    //Brand new object
    public Product(String productName,double productPrice,double sellCost,int qty,int shopId){
        this.productName=productName; this.productPrice=productPrice; this.sellCost=sellCost; this.qtyOnHand=qty; this.shopId=shopId;
    }
    //end of constructors

    //Saves the product to the Database
    public void addProductToDB(){db.addProduct(productName, productPrice, sellCost, qtyOnHand, shopId);}

    //Start of Getters
    public String 	getProdId(){return this.productID;}
    public String 	getProdName(){return this.productName;}
    public double 	getProdPrice(){return this.productPrice;}
    public double 	getCost(){return this.sellCost;}
    public int 		getOnHand(){return this.qtyOnHand;}
    public int 		getShopID(){return this.shopId;}
    public int 		getSellQty(){return this.qtyChange;}
    public double   getDeliveryCost(){return (productPrice*qtyChange);}
    //End of getters

    //Start of Setters
    public void setproductName(String name){this.productName=name;}
    public void setproductPrice(double price){this.productPrice=price;}
    public void setSellCost(double cost){this.sellCost = cost;}
    public void setChangeQty(int inQty){this.qtyChange = inQty;}
    public void setOnHand(int onHand){this.qtyOnHand = onHand;}
    //end of setters

    //start of Methods
    public void sell(String username, String custName, String date){
    	qtyOnHand -= qtyChange;
    	double total = (qtyChange*sellCost);
    	db.sellStock(productName, total, qtyOnHand, shopId);
    	db.logSale(username, productID, qtyChange, total, custName, date);
    }

    //Removing products
    public void remove(int amt){
    	if(amt<qtyOnHand){
    		qtyOnHand -= amt;
    		db.changeStockQty(productID,amt,shopId);
    	}else{
    		db.removeProduct(productName, shopId);
    	}
    }
    public void edit(){
    	db.editStock(productID, productName, productPrice, sellCost, qtyOnHand);
    }

    //Adding stock and recording the delivery.
    public void newDelivery(String orderDate){
        double cost = productPrice*qtyChange;
    	qtyOnHand += qtyChange;
    	db.updateBank(cost, false, shopId);
    	db.changeStockQty(productID, qtyOnHand, shopId);
    	db.addDelivery(orderDate, productID, qtyChange, cost, shopId);
    }
    //End of Methods
    

}
