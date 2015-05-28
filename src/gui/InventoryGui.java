package gui;

import Model.User;
import Operations.DBOperations;
import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryGui extends JFrame implements ActionListener{

	//DECLARING ALL THE NECESSARY VARIABLE
	private JTextField productNameText;
	private JTextField productIdText;
	private JTable table;
	private final JButton exitButton = new JButton("Exit");
    private JButton searchIdButton = new JButton("Search");
    private JButton searchNameButton = new JButton("Search");
	private JButton showAllButton = new JButton("Show All");
    private JLabel help;
    DBOperations db=new DBOperations();
    private ResultSet rset;
    private User user;
	
	
	//Configuring the Window
	public InventoryGui(User inUser) throws SQLException {

        user = inUser;

		setTitle("Inventory");
		setResizable(false);
		setSize(781,543);
		getContentPane().setLayout(null);

        Image imgIcon = new ImageIcon(this.getClass().getResource("images/Icon.png")).getImage(); //Path of the Image
        setIconImage(imgIcon);
		
		JLabel prodNameLabel = new JLabel("Product Name"); //Configuring the Label
		prodNameLabel.setBounds(30, 33, 91, 17);
		prodNameLabel.setFont(new Font("Georgia", Font.PLAIN, 14));
		getContentPane().add(prodNameLabel);
		
		productNameText = new JTextField(); //Configuring the Text Field
		productNameText.setBounds(160, 30, 126, 23);
		productNameText.setFont(new Font("Georgia", Font.PLAIN, 14));
		getContentPane().add(productNameText);
		productNameText.setColumns(10);
		
		JLabel prodIdLabel = new JLabel("Product Id"); //Configuring the Label
		prodIdLabel.setBounds(484, 36, 68, 17);
		prodIdLabel.setFont(new Font("Georgia", Font.PLAIN, 14));
		getContentPane().add(prodIdLabel);
		
		productIdText = new JTextField(); //Configuring the Text Field
		productIdText.setBounds(587, 33, 126, 23);
		productIdText.setFont(new Font("Georgia", Font.PLAIN, 14));
		getContentPane().add(productIdText);
		productIdText.setColumns(10);

        help = new JLabel("Help");
        Image img4 = new ImageIcon(this.getClass().getResource("images/helpIcon.png")).getImage(); //Path of the Image
        help.setIcon(new ImageIcon(img4));
        help.setFont(new Font("Georgia", Font.PLAIN, 11));
        help.setBounds(20, 485, 89, 23);
        help.setToolTipText("If you are having difficulty accessing the system contact the IT support at itsupport@topstock.ie or 0876376526");
        getContentPane().add(help);
		
		 //Configuring the Button
		searchNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		searchNameButton.setBounds(195, 67, 91, 23);
		searchNameButton.setFont(new Font("Georgia", Font.PLAIN, 11));
        searchNameButton.addActionListener(this);
		getContentPane().add(searchNameButton);
		

		searchIdButton.setBounds(624, 67, 89, 23);//Configuring the Button
		searchIdButton.setFont(new Font("Georgia", Font.PLAIN, 11));
        searchIdButton.addActionListener(this);
		getContentPane().add(searchIdButton);
		
		JScrollPane listOfProducts = new JScrollPane(); ////Configuring the Table full of Content
		listOfProducts.setBounds(30, 106, 702, 347);
		getContentPane().add(listOfProducts);
		
		table = new JTable();
		listOfProducts.setViewportView(table);


		exitButton.setFont(new Font("Georgia", Font.PLAIN, 11)); //Configuring the Button
		exitButton.setBounds(639, 464, 59, 31);
        exitButton.addActionListener(this);
        getContentPane().add(exitButton);

		showAllButton = new JButton("Show All");
		showAllButton.setFont(new Font("Georgia", Font.PLAIN, 11));
		showAllButton.setBounds(523, 464, 91, 31);
		showAllButton.addActionListener(this);
		getContentPane().add(showAllButton);




        rset = db.showStock(user.getShopID());
        table.setModel(DbUtils.resultSetToTableModel(rset));

	}

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==exitButton){
            dispose();
        }

        //Search by Id Button Action

        if(e.getSource()==searchIdButton){
            rset=db.searchId(productIdText.getText(), user.getShopID());
            table.setModel(DbUtils.resultSetToTableModel(rset));
            productIdText.setText("");

        }
        //Search by name Button Action
        if(e.getSource()==searchNameButton ){
            rset =db.searchName(productNameText.getText(), user.getShopID());
            table.setModel(DbUtils.resultSetToTableModel(rset));
            productNameText.setText("");
        }

		if(e.getSource().equals(showAllButton)){
			rset = db.showStock(user.getShopID());
			table.setModel(DbUtils.resultSetToTableModel(rset));

		}

    }
}
