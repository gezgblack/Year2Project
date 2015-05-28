package gui;
 
import Model.User;
import Operations.DBOperations;
import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;

public class FullReportGUI extends JFrame  implements ActionListener{
    //Variables initialized
 
    private JTextField productNameText;
    private JTable table;
    private JLabel searchId,help;
    private final JButton searchButton = new JButton("Search"), exit = new JButton("Exit"),refreshButton= new JButton("Refresh"), showAll = new JButton("Show All");
    DBOperations db=new DBOperations();
    private JComboBox comboBox;
    private ResultSet rset;
    private User user;
   
 
 
    //Configuring the Window
    public FullReportGUI(User inUser) {
    	
    	user = inUser;
        setResizable(false);
        setTitle("Sell Log");
        setSize(1180, 543);
        getContentPane().setLayout(null);

        Image imgIcon = new ImageIcon(this.getClass().getResource("/gui/images/Icon.png")).getImage(); //Path of the Image
        setIconImage(imgIcon);
 
 
        productNameText = new JTextField(); //Configuring the Text Field
        productNameText.setBounds(200, 30, 160, 40);
        productNameText.setFont(new Font("Georgia", Font.PLAIN, 14));
        getContentPane().add(productNameText);
        productNameText.setColumns(10);

        comboBox = new JComboBox(); // Configuring the Combo Box
        comboBox.setModel(new DefaultComboBoxModel(new String[]{"Last Week", "Last Month", "Last Year"})); // THE CHOICES IN THE COMBO BOX
        comboBox.setFont(new Font("Georgia", Font.PLAIN, 14));
        comboBox.setBounds(950, 40, 140, 20);
        comboBox.addActionListener(this);
        getContentPane().add(comboBox);

        refreshButton.addActionListener(this);
        refreshButton.setBounds(820, 40, 80, 20);
        refreshButton.setFont(new Font("Georgia", Font.PLAIN, 11));
        getContentPane().add(refreshButton);

        help = new JLabel("Help");
        Image img4 = new ImageIcon(this.getClass().getResource("images/helpIcon.png")).getImage(); //Path of the Image
        help.setIcon(new ImageIcon(img4));
        help.setFont(new Font("Georgia", Font.PLAIN, 11));
        help.setBounds(20, 490, 89, 23);
        help.setToolTipText("If you are having difficulty accessing the system contact the IT support at itsupport@topstock.ie or 0876376526");
        getContentPane().add(help);

        searchId = new JLabel("Search Sale Id");
        searchId.setBounds(80, 30, 160, 40);
        searchId.setFont(new Font("Georgia", Font.PLAIN, 14));
        getContentPane().add(searchId);

        searchButton.setFont(new Font("Georgia", Font.PLAIN, 11));
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        searchButton.setBounds(400, 30, 120, 40);
        searchButton.addActionListener(this);
        getContentPane().add(searchButton);
 
 
        JScrollPane listOfProducts = new JScrollPane(); ////Configuring the Table full of Content
        listOfProducts.setBounds(30, 106, 1100, 347);
        getContentPane().add(listOfProducts);
 
        table = new JTable();
        listOfProducts.setViewportView(table);

 
        exit.setFont(new Font("Georgia", Font.PLAIN, 11)); //Configuring the Button
        exit.setBounds(320, 464, 70, 31);
        exit.addActionListener(this);
        getContentPane().add(exit);

        showAll.setFont(new Font("Georgia", Font.PLAIN, 11)); //Configuring the Button
        showAll.setBounds(410, 464, 100, 31);
        showAll.addActionListener(this);
        getContentPane().add(showAll);
 
        rset = db.showSales(user.getShopID());
 
        table.setModel(DbUtils.resultSetToTableModel(rset));
 
 
 
 
    }
 
 
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(searchButton)) {
 
                rset = db.searchSales(productNameText.getText(), user.getShopID());
                table.setModel(DbUtils.resultSetToTableModel(rset));
 
        }else if(e.getSource().equals(refreshButton)){

                rset = db.filterDate(comboBox.getSelectedItem().toString(), "Sales", user.getShopID());
                table.setModel(DbUtils.resultSetToTableModel(rset));

        }else if(e.getSource().equals(exit)){
            dispose();
        }
        else if(e.getSource().equals(showAll)){
            rset = db.showSales(user.getShopID());
            table.setModel(DbUtils.resultSetToTableModel(rset));
        }
 
 
 
    }

}