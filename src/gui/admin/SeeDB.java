package gui.admin;

import Operations.DBOperations;
import net.proteanit.sql.DbUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SeeDB extends JFrame implements ActionListener{
	
	//Variables initialized
    private  JPanel contentPane;
    JComboBox tablesToDisplayBox;
	private JTable table;
	private final JButton exitButton,searchButton = new JButton("Search");
    private ResultSet rset;
    private DBOperations so = new DBOperations();
  
	
	
	//Configuring the Window
	public SeeDB() throws SQLException {

        setResizable(false);
        setTitle("Show Database");
        setBounds(100, 100, 1200, 552);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        Image imgIcon = new ImageIcon(this.getClass().getResource("/gui/images/Icon.png")).getImage(); //Path of the Image
        setIconImage(imgIcon);

        tablesToDisplayBox = new JComboBox();
        tablesToDisplayBox.setModel(new DefaultComboBoxModel(new String[]{"Users", "Products", "Delivery","Sales","Shop"})); // THE CHOICES IN THE COMBO BOX
        tablesToDisplayBox.setBounds(564, 29, 133, 20);
        tablesToDisplayBox.addActionListener(this);
        contentPane.add(tablesToDisplayBox);

        searchButton.setBounds(707, 28, 89, 23);
        searchButton.addActionListener(this);
        contentPane.add(searchButton);

        JScrollPane listOfData = new JScrollPane();
        listOfData.setBounds(29, 91, 1150, 347);
        getContentPane().add(listOfData);
        table = new JTable();
        listOfData.setViewportView(table);
        table.setEnabled(false);

        exitButton = new JButton("Exit");
        exitButton.setBounds(707, 453, 89, 23);
        exitButton.addActionListener(this);
        contentPane.add(exitButton);

        JLabel lblChooseTableTo = new JLabel("Choose Table to View");
        lblChooseTableTo.setBounds(432, 32, 122, 14);
        contentPane.add(lblChooseTableTo);

	}

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(searchButton)){
            rset=so.showDb(tablesToDisplayBox.getSelectedItem().toString());
            table.setModel(DbUtils.resultSetToTableModel(rset));
        }
        else if(e.getSource().equals(exitButton)){
            dispose();
        }
    }
}
