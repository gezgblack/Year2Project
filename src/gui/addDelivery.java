package gui;

import Model.Product;
import Model.User;
import Operations.DBOperations;
import com.michaelbaranov.microba.calendar.DatePicker;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class addDelivery extends JFrame implements ActionListener {

    //DECLARING ALL THE NECESSARY VARIABLE
    private DatePicker  datepicker = new DatePicker(new Date());
    private JTextField productIdText,orderCostText,qtyText;
    private JButton cancelButton,addDeliveryButton;
    JLabel deliveryDateLabel,productIdLabel,productQtyLabel,orderCostLabel,errorLabel;
    DBOperations db = new DBOperations();
    private Date dt = new Date();
    private User user;

    //CONFIGURING THE WINDOW
	public addDelivery(User inUser) { //shoppeId : Each shop has its own delivery

        user = inUser;
        setBounds(100, 100, 367, 247);
        setResizable(false);
        setTitle("Add New Delivery");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Close single window.
        getContentPane().setLayout(null);

        deliveryDateLabel = new JLabel("Order Date");
        deliveryDateLabel.setFont(new Font("Georgia", Font.PLAIN, 18));
        deliveryDateLabel.setBounds(29, 21, 133, 21);
        getContentPane().add(deliveryDateLabel);

        productIdLabel = new JLabel("Product Id");
        productIdLabel.setFont(new Font("Georgia", Font.PLAIN, 18));
        productIdLabel.setBounds(29, 56, 122, 21);
        getContentPane().add(productIdLabel);

        productIdText = new JTextField();
        productIdText.setBounds(179, 59, 146, 20);
        getContentPane().add(productIdText);
        productIdText.setColumns(10);

        //CONFIGURING THE CALENDAR/DATE PICKER
        datepicker = new DatePicker(new Date());
        datepicker.setBounds(179, 21, 146, 20);
        datepicker.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dt = datepicker.getDate(); //CHOOSE A DATE ON THE CALENDAR
            }
        });
        getContentPane().add(datepicker);


        addDeliveryButton = new JButton("Add Delivery");
        addDeliveryButton.setBounds(59, 174, 104, 23);
        addDeliveryButton.addActionListener(this); // ADD DELIVERY ACTION
        getContentPane().add(addDeliveryButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(197, 174, 89, 23);
        cancelButton.addActionListener(this); //CANCEL ACTION
        getContentPane().add(cancelButton);

        productQtyLabel = new JLabel("Product Quantity");
        productQtyLabel.setFont(new Font("Georgia", Font.PLAIN, 18));
        productQtyLabel.setBounds(29, 88, 150, 21);
        getContentPane().add(productQtyLabel);

        qtyText = new JTextField();
        qtyText.setBounds(179, 91, 146, 20);
        getContentPane().add(qtyText);
        qtyText.setColumns(10);

        errorLabel = new JLabel("Product Id does not exist");
        errorLabel.setFont(new Font("Georgia", Font.PLAIN, 18));
        errorLabel.setBounds(85, 140, 190, 29);
        errorLabel.setForeground(Color.red);
        getContentPane().add(errorLabel);
        errorLabel.setVisible(false);

	}

    public void actionPerformed(ActionEvent e) {
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MMM-dd"); //FORMATTING THE DATE
        if(e.getSource().equals(addDeliveryButton)){

            String deliveryDate = dateFormat.format(dt),
            deliveryInfo = productIdText.getText(); //RETRIEVING THE PRODUCT ID CHOSEN
            int qty = Integer.parseInt(qtyText.getText()); //RETRIEVING THE QUANTITY CHOSEN

            double money = db.getShopMoney(user.getShopID());
            { //if costs are sufficient do the following
                if(db.getShopIdofProduct(deliveryInfo) == user.getShopID()) { //if the product id belongs to the shop id then do the following
                	
                	ResultSet rset = db.productDetails(productIdText.getText());

                	try {
        				while(rset.next()){
        					Product pd = new Product(rset.getString(1), rset.getString(2),rset.getDouble(3),rset.getDouble(4),rset.getInt(5),rset.getInt(6));
        					pd.setChangeQty(qty);
                            if(pd.getDeliveryCost()>money){  //If the money of the shop is not enough , throw an error
                                errorLabel.setText("Not enough money");
                                errorLabel.setVisible(true);

                            }else {
                                pd.newDelivery(deliveryDate);
                                errorLabel.setText("");
                            }
        				}
        			} catch (SQLException e1) {
        				e1.printStackTrace();
        			}

                 }else{
                    errorLabel.setText("Invalid product ID");
                    errorLabel.setVisible(true);

                }

            }
            //SETTING THE TEXTFIELDS TO DEFAULT EMPTY STATE
            productIdText.setText("");
            qtyText.setText("");
        }

        else if(e.getSource().equals(cancelButton)){
            dispose();
        }
    }
}
