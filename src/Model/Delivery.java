package Model;

import Operations.DBOperations;

public class Delivery {

    String orderDate,productId;
    int quantity, shopId;
    double cost;
    DBOperations db = new DBOperations();

    //Constructor
    public Delivery(String orderDate, String productId, int quantity, int shopId, double cost){
        this.orderDate = orderDate; this.productId = productId; this.quantity = quantity; this.cost = cost; this.shopId = shopId;
    }

    //Saves the delivery to the Database
    public void addDeliveryToDB(){db.addDelivery(orderDate, productId, quantity, cost, shopId);}

}
