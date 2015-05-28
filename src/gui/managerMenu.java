package gui;

import Operations.DBOperations;
import gui.admin.CreateUser;
import Model.User;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.sql.SQLException;

public class managerMenu extends JFrame implements ActionListener{

	private JPanel logOutPanel,sellPanel,inventoryPanel;
	private JButton sellButton, btnInventory, btnSearch, btnLogout, addStockButton, deleteStockButton, editStockButton, deliveryButton, sellLogButton,addCashierButton;
	private JLabel sellLogo,sellLabel,inventoryLogo,inventoryLabel,logOutLogo,logOutLabel,help;
    private DBOperations db = new DBOperations();
    User user;
	

	public managerMenu(User inUser) {
		this.user = inUser;
        
        Font ga = new Font("Georgia", Font.PLAIN, 15);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		int logoutH =0;

        setResizable(false);
		if(user.getuserLevel() ==1){
			setBounds(100, 100, 450, 800);
			setTitle("Manager Menu");
			logoutH=670;
		}else if (user.getuserLevel()==2){
			setBounds(100, 100, 450, 300);
			setTitle("Cashier Menu");
			logoutH=170;
		}
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Image imgIcon = new ImageIcon(this.getClass().getResource("images/Icon.png")).getImage(); //Path of the Image
        setIconImage(imgIcon);
		
		
		JLabel lblLoggedInAs = new JLabel("Logged in as: "+user.getName() +"  Money: "+db.getShopMoney(user.getShopID()));
		lblLoggedInAs.setBounds(0, 10, 440, 20);
		lblLoggedInAs.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoggedInAs.setFont(new Font("Georgia", Font.PLAIN, 16));
		getContentPane().add(lblLoggedInAs);
		getContentPane().setLayout(null);
		
		// The Sell Panel and all of its contents
        sellPanel = new JPanel();
        sellPanel.setBackground(Color.LIGHT_GRAY);
        sellPanel.setBounds(27, 30, 183, 143);//11
        getContentPane().add(sellPanel);
        sellPanel.setLayout(null);

        sellLogo = new JLabel("");
        Image img1 = new ImageIcon(this.getClass().getResource("images/sellLogo.png")).getImage();
        sellLogo.setIcon(new ImageIcon(img1));
        sellLogo.setBounds(26, 0, 147, 132);
        sellPanel.add(sellLogo);

        sellLabel = new JLabel("Sell Stock");
        sellLabel.setFont(ga);
        sellLabel.setBounds(53, 129, 74, 14);
        sellPanel.add(sellLabel);

        help = new JLabel("Help");
        Image img4 = new ImageIcon(this.getClass().getResource("images/helpIcon.png")).getImage(); //Path of the Image
        help.setIcon(new ImageIcon(img4));
        help.setFont(new Font("Georgia", Font.PLAIN, 11));
        help.setBounds(20, 750, 89, 23);
        help.setToolTipText("If you are having difficulty accessing the system contact the IT support at itsupport@topstock.ie or 0876376526");
        getContentPane().add(help);

        //Initialising a MouseListener
        sellPanel.addMouseListener(new MouseAdapter() {
            @Override //Sell actionListener
            public void mouseClicked(MouseEvent e) {
                if (e.getSource().equals(sellPanel)) {
                    SellStockGUI ss = new SellStockGUI(user); //When mouse is clicked on this panel a new SellStockGui is opened to the user
                    ss.setVisible(true);
                }
            }
        });
		
     // The Inventory Panel and all of its contents
        inventoryPanel = new JPanel();
        inventoryPanel.setBackground(Color.LIGHT_GRAY);
        inventoryPanel.setBounds(220, 30, 183, 143); //11
        getContentPane().add(inventoryPanel);
        inventoryPanel.setLayout(null);

        inventoryLogo = new JLabel("");
        Image img2 = new ImageIcon(this.getClass().getResource("images/inventoryLogo.png")).getImage();
        inventoryLogo.setIcon(new ImageIcon(img2));
        inventoryLogo.setBounds(22, 0, 137, 132);
        inventoryPanel.add(inventoryLogo);

        inventoryLabel = new JLabel("Inventory");
        inventoryLabel.setFont(ga);
        inventoryLabel.setBounds(49, 129, 82, 14);
        inventoryPanel.add(inventoryLabel);

        //Initialising a MouseListener
        inventoryPanel.addMouseListener(new MouseAdapter() {
            @Override //Inventory actionListener
            public void mouseClicked(MouseEvent e) {
                InventoryGui inv = null;
                try {
                    inv = new InventoryGui(user);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                inv.setVisible(true);
            }
        });

		
		
		//Only accessible to manager
		if(user.getuserLevel()==1){

            addCashierButton = new JButton("Add Cashier");
            addCashierButton.setBackground(Color.LIGHT_GRAY);
            Image img5 = new ImageIcon(this.getClass().getResource("images/addCashierLogo.png")).getImage();
            addCashierButton.setIcon(new ImageIcon(img5));
            addCashierButton.setFont(ga);
            addCashierButton.setBounds(135, 190, 183, 65);
            addCashierButton.addActionListener(this);
            getContentPane().add(addCashierButton);

			addStockButton = new JButton("Add Stock");
            addStockButton.setBackground(Color.LIGHT_GRAY);
            Image img10 = new ImageIcon(this.getClass().getResource("images/addStockIcon.png")).getImage();
            addStockButton.setIcon(new ImageIcon(img10));
            addStockButton.setFont(ga);
            addStockButton.setBounds(135, 270, 183, 65);
            addStockButton.addActionListener(this);
			getContentPane().add(addStockButton);
			
			deleteStockButton = new JButton("Delete Stock");
            deleteStockButton.setBackground(Color.LIGHT_GRAY);
            Image img9 = new ImageIcon(this.getClass().getResource("images/deleteStockIcon.png")).getImage();
            deleteStockButton.setIcon(new ImageIcon(img9));
            deleteStockButton.setFont(ga);
            deleteStockButton.setBounds(135, 350, 183, 65);
            deleteStockButton.addActionListener(this);
			getContentPane().add(deleteStockButton);
			
			editStockButton = new JButton("Edit Stock");
            editStockButton.setBackground(Color.LIGHT_GRAY);
            Image img8 = new ImageIcon(this.getClass().getResource("images/editStockIcon.png")).getImage();
            editStockButton.setIcon(new ImageIcon(img8));
            editStockButton.setFont(ga);
            editStockButton.setBounds(135, 430, 183, 65);
            editStockButton.addActionListener(this);
			getContentPane().add(editStockButton);
			
			deliveryButton = new JButton("Delivery");
            deliveryButton.setBackground(Color.LIGHT_GRAY);
            Image img7 = new ImageIcon(this.getClass().getResource("images/deliveryIcon.png")).getImage();
            deliveryButton.setIcon(new ImageIcon(img7));
            deliveryButton.setFont(ga);
            deliveryButton.setBounds(135, 510, 183, 65);
            deliveryButton.addActionListener(this);
			getContentPane().add(deliveryButton);
			
			sellLogButton = new JButton("Sell Log");
            sellLogButton.setBackground(Color.LIGHT_GRAY);
            Image img6 = new ImageIcon(this.getClass().getResource("images/sellLogIcon.png")).getImage();
            sellLogButton.setIcon(new ImageIcon(img6));
            sellLogButton.setFont(ga);
            sellLogButton.setBounds(135, 590, 183, 65);
            sellLogButton.addActionListener(this);
            getContentPane().add(sellLogButton);

		}
			
			
		
		// The LogOut Panel and all of its contents
        logOutPanel = new JPanel();
        logOutPanel.setBackground(Color.LIGHT_GRAY);
        if(user.getuserLevel()==1){
        logOutPanel.setBounds(160, logoutH, 130, 150);}//156
        else{logOutPanel.setBounds(140, 175, 140, 73);}//156
        getContentPane().add(logOutPanel);
        logOutPanel.setLayout(null);

        logOutLogo = new JLabel("");
        Image img3 = new ImageIcon(this.getClass().getResource("images/logoutLogo.png")).getImage();
        logOutLogo.setIcon(new ImageIcon(img3));
        logOutLogo.setBounds(43, 0, 48, 56);
        logOutPanel.add(logOutLogo);

        logOutLabel = new JLabel("Log out");
        logOutLabel.setFont(ga);
        logOutLabel.setBounds(43, 53, 77, 20);
        logOutPanel.add(logOutLabel);

        //Initialising a MouseListener
        logOutPanel.addMouseListener(new MouseAdapter() {
            @Override//Logout actionListener
            public void mouseClicked(MouseEvent e) {
                if (e.getSource().equals(logOutPanel)) {
                    Login lgn = new Login();
                    lgn.setVisible(true);
                    dispose();
                }
            }
        });

        
	}
	//btnLogout.setBounds(160, logoutH, 130, 40);
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource().equals(sellButton)){
			SellStockGUI ss = new SellStockGUI(user);
			ss.setVisible(true);
			
		}
		else if(e.getSource().equals(btnInventory)){
            InventoryGui inv = null;
            try {
                inv = new InventoryGui(user);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            inv.setVisible(true);
		}
		else if(e.getSource().equals(addStockButton)){
			addProduct ap = new addProduct(user);
			ap.setVisible(true);
		}
        else if(e.getSource().equals(addCashierButton)){
            CreateUser ac = new CreateUser(user);
            ac.setVisible(true);
        }
		else if(e.getSource().equals(deleteStockButton)){
			removeProduct rp = new removeProduct(user);
			rp.setVisible(true);
		}
		else if(e.getSource().equals(editStockButton)){
			try{
				EditGUI eg = new EditGUI(user);
				eg.setVisible(true);
			}catch(SQLException e2){
				e2.printStackTrace();
			}
		}
		else if(e.getSource().equals(deliveryButton)) {
            DeliveryGUI delivery = new DeliveryGUI(user);
            delivery.setVisible(true);
        }
        else if(e.getSource().equals(sellLogButton)){
            FullReportGUI reports = new FullReportGUI(user);
            reports.setVisible(true);
        }
		else if(e.getSource().equals(btnLogout)){
			
			Login lgn = new Login();
			lgn.setVisible(true);
			dispose();
		}

	}
}
