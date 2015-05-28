package Model;

import Operations.DBOperations;


public class Shop {

    private String shopName, shopAddress, phoneNum,shopId;
    private double shopBank;

    DBOperations db = new DBOperations();

    //Existing Shop constructor
    public Shop(String shopId, String shopName, String shopAddress, String phoneNum, double shopBank){
        this.shopName = shopName; this.shopAddress = shopAddress; this.phoneNum = phoneNum; this.shopId = shopId; this.shopBank = shopBank;
    }
    //New Shop constructor 
    public Shop(String shopName, String shopAddress, String phoneNum, double shopBank){
        this.shopName = shopName; this.shopAddress = shopAddress; this.phoneNum = phoneNum; this.shopBank = shopBank;
    }

    public void addShopToDB(){
        db.addShop(shopName,shopAddress,phoneNum,shopBank);
    }
    public void removeShop(){db.removeShop(shopId);}
    public String getShopId(){return this.shopId;}





}
