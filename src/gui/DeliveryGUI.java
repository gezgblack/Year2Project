package gui;


import javax.swing.*;

import Model.User;
import Operations.DBOperations;
import net.proteanit.sql.DbUtils;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;


public class DeliveryGUI extends JFrame implements ActionListener {

    //DECLARING ALL THE NECESSARY VARIABLE
	private JTextField orderIdText;
    JLabel productNameLabel;
    private JTable table;
    private JLabel help;
	private JComboBox comboBox;
	private JButton addButton,exitButton,searchIdButton,showAllButton,refreshButton;
    DBOperations db = new DBOperations();
    private ResultSet rset;
    private User user;


    //CONFIGURING THE WINDOW
	public DeliveryGUI(User inUser) { //shoppeId : Each shop has its own individual delivery list
        user = inUser;
        setTitle("Delivery");
        setResizable(false);
        setSize(781, 543);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        Image imgIcon = new ImageIcon(this.getClass().getResource("/gui/images/Icon.png")).getImage(); //Path of the Image
        setIconImage(imgIcon);

        productNameLabel = new JLabel("Search Order Id");
        productNameLabel.setBounds(30, 33, 200, 17);
        productNameLabel.setFont(new Font("Georgia", Font.PLAIN, 14));
        getContentPane().add(productNameLabel);

        orderIdText = new JTextField();
        orderIdText.setBounds(160, 30, 126, 23);
        orderIdText.setFont(new Font("Georgia", Font.PLAIN, 14));
        getContentPane().add(orderIdText);
        orderIdText.setColumns(10);

        searchIdButton = new JButton("Search");
        searchIdButton.setBounds(195, 67, 91, 23);
        searchIdButton.setFont(new Font("Georgia", Font.PLAIN, 11));
        searchIdButton.addActionListener(this);
        getContentPane().add(searchIdButton);

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this);
        refreshButton.setBounds(400, 40, 80, 20);
        refreshButton.setFont(new Font("Georgia", Font.PLAIN, 11));
        getContentPane().add(refreshButton);

        comboBox = new JComboBox(); // Configuring the Combo Box
        comboBox.setModel(new DefaultComboBoxModel(new String[]{"Last Week", "Last Month", "Last Year"})); // THE CHOICES IN THE COMBO BOX
        comboBox.setFont(new Font("Georgia", Font.PLAIN, 14));
        comboBox.setBounds(550, 40, 140, 20);
        comboBox.addActionListener(this);
        getContentPane().add(comboBox);

        JScrollPane listOfProducts = new JScrollPane(); ////Configuring the Table full of Content
        listOfProducts.setBounds(30, 106, 702, 347);
        getContentPane().add(listOfProducts);
        table = new JTable();
        listOfProducts.setViewportView(table);
        table.setEnabled(false); //THE CONTENTS OF THE TABLE CANNOT BE EDITED

        addButton = new JButton("Add new Delivery details");
        addButton.setFont(new Font("Georgia", Font.PLAIN, 11));
        addButton.setBounds(300, 464, 200, 25);
        addButton.addActionListener(this);
        getContentPane().add(addButton);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Georgia", Font.PLAIN, 11));
        exitButton.setBounds(639, 464, 59, 25);
        exitButton.addActionListener(this);
        getContentPane().add(exitButton);

        showAllButton = new JButton("Show All");
        showAllButton.setFont(new Font("Georgia", Font.PLAIN, 11));
        showAllButton.setBounds(523, 464, 89, 23);
        showAllButton.addActionListener(this);
        getContentPane().add(showAllButton);

        help = new JLabel("Help");
        Image img4 = new ImageIcon(this.getClass().getResource("images/helpIcon.png")).getImage(); //Path of the Image
        help.setIcon(new ImageIcon(img4));
        help.setFont(new Font("Georgia", Font.PLAIN, 11));
        help.setBounds(20, 490, 89, 23);
        help.setToolTipText("If you are having difficulty accessing the system contact the IT support at itsupport@topstock.ie or 0876376526");
        getContentPane().add(help);
        //End of Configuring the window

        rset = db.showDelivery(user.getShopID()); // DISPLAY ALL THE DELIVERY INFO WHEN THE WINDOW IS OPENED
        try {

            table.setModel(DbUtils.resultSetToTableModel(rset));

        } catch (Exception ex) {
            System.out.println("Error here at delivery GUI" + ex);
        }

    }

        public void actionPerformed(ActionEvent e){
            if(e.getSource().equals(addButton)){
                addDelivery add = new addDelivery(user);//Opens the add new delivery window
                add.setVisible(true);
            }
            else if(e.getSource().equals(exitButton)){
                dispose();
            }
            else if(e.getSource().equals(searchIdButton)){
                rset=db.searchOrderId(orderIdText.getText(), user.getShopID()); //SEARCHING FOR AN ORDER BY ORDER ID
                table.setModel(DbUtils.resultSetToTableModel(rset));
            }
            else if(e.getSource().equals(showAllButton)){
                orderIdText.setText("");  //CLEAR THE TEXT FIELD
                rset = db.showDelivery(user.getShopID()); //DISPLAY ALL THE DELIVERIES
                table.setModel(DbUtils.resultSetToTableModel(rset));
            }
            else if(e.getSource().equals(refreshButton)){

                    rset = db.filterDate(comboBox.getSelectedItem().toString(), "Delivery", user.getShopID());
                    table.setModel(DbUtils.resultSetToTableModel(rset));

            }
        }
    }
