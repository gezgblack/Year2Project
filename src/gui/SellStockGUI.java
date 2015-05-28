package gui;

import javax.swing.*;

import Model.Product;
import Model.User;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class SellStockGUI extends JFrame implements ActionListener{
	
	//Variables initialized 
	private JTextField quantityText, productIdText;
	private JTable table;
	private JTextArea errorBox;
    private JLabel help;
	private final JButton finishButton = new JButton("Finish & Pay"), exitButton = new JButton("Exit"), enterButton = new JButton("Enter");
	Operations.DBOperations db = new Operations.DBOperations();
	private TableModel tm;
	private DefaultTableModel def;
    private User user;
	private ArrayList<String> prodId; 
	private ArrayList<Integer> qty;
	private ArrayList<Product> products = new ArrayList<Product>();
	
	
	
	//Configuring the Window
	public SellStockGUI(User inUser) {

        user = inUser;
		
		prodId = new ArrayList<String>(); qty = new ArrayList<Integer>();

		setResizable(false);
		setTitle("Sell Item");
		setSize(781,543);

        Image imgIcon = new ImageIcon(this.getClass().getResource("images/Icon.png")).getImage(); //Path of the Image
        setIconImage(imgIcon);
		
		getContentPane().setLayout(null);
		
		
	
		
		JScrollPane listOfProducts = new JScrollPane(); ////Configuring the Table full of Content
		listOfProducts.setBounds(30, 10, 702, 347);
		getContentPane().add(listOfProducts);
		
		table = new JTable();
		listOfProducts.setViewportView(table);  table.setEnabled(false);
		
		JLabel prodIdLabel = new JLabel("Product Id"); prodIdLabel.setBounds(50, 390, 68, 17); prodIdLabel.setFont(new Font("Georgia", Font.PLAIN, 14)); getContentPane().add(prodIdLabel);
		
		productIdText = new JTextField(); productIdText.setBounds(130, 390, 126, 23); productIdText.setFont(new Font("Georgia", Font.PLAIN, 14)); getContentPane().add(productIdText); productIdText.setColumns(10);
		
		JLabel qtyLabel = new JLabel ("Quantity"); qtyLabel.setBounds(350, 390, 68, 17); qtyLabel.setFont(new Font("Georgia", Font.PLAIN, 14)); getContentPane().add(qtyLabel);
		
		quantityText = new JTextField(); quantityText.setBounds(420, 390, 126, 23); quantityText.setFont(new Font("Georgia", Font.PLAIN, 14)); getContentPane().add(quantityText); quantityText.setColumns(10);
		
		errorBox = new JTextArea(); errorBox.setBounds(100, 464, 300, 30); errorBox.setFont(new Font("Times New Roman", Font.BOLD, 14));
		errorBox.setForeground(Color.RED); errorBox.setBackground(null); errorBox.setEditable(false); getContentPane().add(errorBox);
		
		finishButton.setFont(new Font("Georgia", Font.PLAIN, 11)); finishButton.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {}});
		finishButton.setBounds(400, 464, 200, 31); finishButton.addActionListener(this); getContentPane().add(finishButton);
	
		exitButton.setFont(new Font("Georgia", Font.PLAIN, 11)); exitButton.setBounds(639, 464, 59, 31); exitButton.addActionListener(this); getContentPane().add(exitButton);
		
		enterButton.setFont(new Font("Georgia", Font.PLAIN, 8)); enterButton.setBounds(600, 390, 60, 20); enterButton.addActionListener(this); getContentPane().add(enterButton);

        help = new JLabel("Help");
        Image img4 = new ImageIcon(this.getClass().getResource("images/helpIcon.png")).getImage(); //Path of the Image
        help.setIcon(new ImageIcon(img4));
        help.setFont(new Font("Georgia", Font.PLAIN, 11));
        help.setBounds(20, 480, 89, 23);
        help.setToolTipText("If you are having difficulty accessing the system contact the IT support at itsupport@topstock.ie or 0876376526");
        getContentPane().add(help);
	
      
      
		
	}
	
	public void addToTable(){
		if((!productIdText.getText().isEmpty())&&(!quantityText.getText().isEmpty())){ //Checks that the input boxes aren't empty
			boolean there = false; int place =0;
			for(int loop1=0;loop1<prodId.size();loop1++){
				if(productIdText.getText().equals(prodId.get(loop1))){
					there = true; place = loop1; //Checks if the product already exists in the order
				}
				
			}
			if(db.inStock(productIdText.getText(), user.getShopID())){
				if(there==true){ //If the product is already in the order
					qty.set(place, Integer.parseInt(quantityText.getText()));//It just changes the quantity of the product to be ordered
					products.get(place).setChangeQty(Integer.parseInt(quantityText.getText()));
				}else{
					prodId.add(productIdText.getText());  qty.add(Integer.parseInt(quantityText.getText())); //If it's not there it adds the product to the order
					makeProduct();
				}
				
				table.setModel(DbUtils.resultSetToTableModel(db.salesSearch(prodId, user.getShopID())));
				
				
				for(int loop1=0;loop1<qty.size();loop1++){
					for(int loop2 = 0; loop2<prodId.size();loop2++){
						if(prodId.get(loop1).equals(table.getValueAt(loop2, 0))){
							table.setValueAt(qty.get(loop1), loop2, 5); //Sets the values for the quantity in the table to correspond correctly to the product id
							
						}
					}
				}
			}else{
				errorBox.setText("Item not in Stock");
			}
		}else{
			errorBox.setText("Nothing entered");
		}
	}
	public void makeProduct(){
		 
			ResultSet rset;
			rset = db.productDetails(prodId.get(prodId.size()-1));
			
			try {
				while(rset.next()){
					products.add(new Product(rset.getString(1), rset.getString(2),rset.getDouble(3),rset.getDouble(4),rset.getInt(5),rset.getInt(6))); //Gathers a product from the database
					products.get(products.size()-1).setChangeQty(qty.get(qty.size()-1)); //Sets the quantity to be sold to the desired quantity
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 
	}
	

	
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource().equals(enterButton)){
			addToTable();
			
		}
		else if(e.getSource().equals(finishButton)){
		
			addToTable();//Adds the last inputted items into the table and order
		
			boolean valid = true;
			System.out.print(valid);
			for(int outerloop=0;outerloop<prodId.size();outerloop++){
				if(db.getQtybyID(prodId.get(outerloop))>= qty.get(outerloop)){
					//Checks that there is enough of all the product in stock to sell it
				}else{
					valid = false;
				}
			}
			if(valid==true){ //If there is enough stock for all the products to be sold
				tm = table.getModel();
				FinishAndPay pay = new FinishAndPay(tm, user, products);//Opens the finish and pay window
				pay.setVisible(true);
				errorBox.setText("");
				table.setModel(new DefaultTableModel());
				prodId.clear(); qty.clear();
				productIdText.setText(""); quantityText.setText("");
				
			}
			else{
				errorBox.setText("Not enough stock to sell that many!");
			}
			
		}
		else if(e.getSource().equals(exitButton)){
			dispose();
		}
	}
}

