package gui;

import Model.Product;
import Model.User;
import Operations.DBOperations;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EditGUI extends JFrame implements ActionListener{

    //DECLARING ALL THE NECESSARY VARIABLES
    private JTextField productNameText,productIdText;
    private JLabel prodNameLabel,prodIdLabel,instructions,instructions2,help;
    private JButton editButton,exitButton,searchIdButton,searchNameButton,showAll;
    DBOperations db=new DBOperations();
    private ResultSet rset;
    private User user;
    private ArrayList<Product> products = new ArrayList<Product>();
    
    private JTable table = new JTable() {
        @Override
        public boolean isCellEditable(int row, int column) {

            return column !=0;
        }
    };


    //CONFIGURING THE WINDOW
    public EditGUI(User inUser) throws SQLException { //shoppeId : Each shop can only edit the items in its own shop
    	user = inUser;
    	
        setTitle("Edit");
        setSize(781, 543);
        setResizable(false);
        getContentPane().setLayout(null);

        Image imgIcon = new ImageIcon(this.getClass().getResource("/gui/images/Icon.png")).getImage(); //Path of the Image
        setIconImage(imgIcon);

        prodNameLabel = new JLabel("Product Name"); //Configuring the Label
        prodNameLabel.setBounds(30, 33, 91, 17);
        prodNameLabel.setFont(new Font("Georgia", Font.PLAIN, 14));
        getContentPane().add(prodNameLabel);

        productNameText = new JTextField(); //Configuring the Text Field
        productNameText.setBounds(160, 30, 126, 23);
        productNameText.setFont(new Font("Georgia", Font.PLAIN, 14));
        getContentPane().add(productNameText);
        productNameText.setColumns(10);

        instructions = new JLabel("1: Double click information you wish to change and hit Enter");
        instructions.setBounds(2, 450, 485, 17);
        getContentPane().add(instructions);

        instructions2 = new JLabel("2: Hit the Save Changes button to Save Changes");
        instructions2.setBounds(2, 465, 485, 17);
        getContentPane().add(instructions2);

        prodIdLabel = new JLabel("Product Id"); //Configuring the Label
        prodIdLabel.setBounds(484, 36, 68, 17);
        prodIdLabel.setFont(new Font("Georgia", Font.PLAIN, 14));
        getContentPane().add(prodIdLabel);

        productIdText = new JTextField(); //Configuring the Text Field
        productIdText.setBounds(587, 33, 126, 23);
        productIdText.setFont(new Font("Georgia", Font.PLAIN, 14));
        getContentPane().add(productIdText);
        productIdText.setColumns(10);

        //Configuring the Button
        searchNameButton = new JButton("Search");
        searchNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        searchNameButton.setBounds(195, 67, 91, 23);
        searchNameButton.setFont(new Font("Georgia", Font.PLAIN, 11));
        searchNameButton.addActionListener(this);
        getContentPane().add(searchNameButton);

        searchIdButton = new JButton("Search");
        searchIdButton.setBounds(624, 67, 89, 23);//Configuring the Button
        searchIdButton.setFont(new Font("Georgia", Font.PLAIN, 11));
        searchIdButton.addActionListener(this);
        getContentPane().add(searchIdButton);

        JScrollPane listOfProducts = new JScrollPane(); ////Configuring the Table full of Content
        listOfProducts.setBounds(30, 106, 702, 347);
        getContentPane().add(listOfProducts);

        listOfProducts.setViewportView(table);

        editButton = new JButton("Save Changes");
        editButton.setFont(new Font("Georgia", Font.PLAIN, 11)); //Configuring the Button
        editButton.setBounds(420, 464, 115, 31);
        editButton.addActionListener(this);
        getContentPane().add(editButton);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Georgia", Font.PLAIN, 11)); //Configuring the Button
        exitButton.setBounds(550, 464, 59, 31);
        exitButton.addActionListener(this);
        getContentPane().add(exitButton);

        showAll = new JButton("Show All");
        showAll.setFont(new Font("Georgia", Font.PLAIN, 11)); //Configuring the Button
        showAll.setBounds(620, 464, 100, 31);
        showAll.addActionListener(this);
        getContentPane().add(showAll);

        help = new JLabel("Help");
        Image img4 = new ImageIcon(this.getClass().getResource("images/helpIcon.png")).getImage(); //Path of the Image
        help.setIcon(new ImageIcon(img4));
        help.setFont(new Font("Georgia", Font.PLAIN, 11));
        help.setBounds(20, 485, 89, 23);
        help.setToolTipText("If you are having difficulty accessing the system contact the IT support at itsupport@topstock.ie or 0876376526");
        getContentPane().add(help);

        rset = db.showStock(user.getShopID()); // DISPLAY ALL THE DELIVERY INFO WHEN THE WINDOW IS OPENED
        table.setModel(DbUtils.resultSetToTableModel(rset));
        
        rset = db.showStock(user.getShopID());
        while(rset.next()){
        	products.add(new Product(rset.getString(1),rset.getString(2),rset.getDouble(3),rset.getDouble(4),rset.getInt(5),user.getShopID()));
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==exitButton){ //CLOSE WINDOW
            dispose();
        }

        //Search by Id Button Action

        if(e.getSource()==searchIdButton){
            rset=db.searchId(productIdText.getText(), user.getShopID()); //SEARCH FOR A PRODUCT BY PRODUCT ID
            table.setModel(DbUtils.resultSetToTableModel(rset));

        }
        //Search by name Button Action
        if(e.getSource()==searchNameButton ){
            rset =db.searchName(productNameText.getText(), user.getShopID()); //SEARCH FOR A PRODUCT BY PRODUCT NAME
            table.setModel(DbUtils.resultSetToTableModel(rset));
        }

        if(e.getSource() == editButton){
        	//Saves the changes for all the products.
        	for(int rowLoop =0;rowLoop<table.getRowCount();rowLoop++){
        		for(int pLoop=0;pLoop<products.size();pLoop++){
        			if(table.getValueAt(rowLoop, 0).toString().equals(products.get(pLoop).getProdId())){
        				products.get(pLoop).setproductName(table.getModel().getValueAt(rowLoop, 1).toString());
        				products.get(pLoop).setproductPrice(Double.parseDouble((table.getModel().getValueAt(rowLoop, 2)).toString()));
        				products.get(pLoop).setSellCost(Double.parseDouble((table.getModel().getValueAt(rowLoop, 3)).toString()));
        				products.get(pLoop).setOnHand(Integer.parseInt((table.getModel().getValueAt(rowLoop, 4)).toString()));
        				
        				products.get(pLoop).edit();	
        			}
        		}
        	}

            rset = db.showStock(user.getShopID());
            table.setModel(DbUtils.resultSetToTableModel(rset));
        }

        if(e.getSource().equals(showAll)){
            rset = db.showStock(user.getShopID()); // DISPLAY ALL THE DELIVERY INFO WHEN THE WINDOW IS OPENED
            table.setModel(DbUtils.resultSetToTableModel(rset));
        }


    }
}
