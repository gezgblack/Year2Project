package gui;

import java.awt.*;

import javax.swing.*;

import Model.Product;
import Model.User;
import Operations.DBOperations;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class removeProduct extends JFrame implements ActionListener {

	
	private JTextField nametxt, qtytxt;
	private JButton btnRemoveFromStock,btnCancel;
	DBOperations db = new DBOperations();
    private User user;
    private JLabel help;


	
	public removeProduct(User inUser) {

        user = inUser;
	
		setTitle("Remove Stock");
		setResizable(false);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setBounds(100, 100, 410, 260);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

        Image imgIcon = new ImageIcon(this.getClass().getResource("/gui/images/Icon.png")).getImage(); //Path of the Image
        setIconImage(imgIcon);
		
		JLabel lblNewLabel = new JLabel("Remove Product");
		lblNewLabel.setFont(new Font("Georgia", Font.PLAIN, 19));
		lblNewLabel.setBounds(140, 10, 150, 25);
		getContentPane().add(lblNewLabel);
		
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setFont(new Font("Georgia", Font.PLAIN, 17));
		lblProductName.setBounds(50, 60, 150, 25);
		getContentPane().add(lblProductName);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Georgia", Font.PLAIN, 17));
		lblQuantity.setBounds(50, 100, 150, 25);
		getContentPane().add(lblQuantity);
		
		nametxt = new JTextField();
		nametxt.setColumns(10);
		nametxt.setBounds(185, 60, 150, 20);
		getContentPane().add(nametxt);
		
		qtytxt = new JTextField();
		qtytxt.setColumns(10);
		qtytxt.setBounds(185, 100, 150, 20);
		getContentPane().add(qtytxt);

        help = new JLabel("Help");
        Image img4 = new ImageIcon(this.getClass().getResource("images/helpIcon.png")).getImage(); //Path of the Image
        help.setIcon(new ImageIcon(img4));
        help.setFont(new Font("Georgia", Font.PLAIN, 11));
        help.setBounds(20, 210, 89, 23);
        help.setToolTipText("If you are having difficulty accessing the system contact the IT support at itsupport@topstock.ie or 0876376526");
        getContentPane().add(help);
		
		btnRemoveFromStock = new JButton("Remove ");
		btnRemoveFromStock.setFont(new Font("Georgia", Font.PLAIN, 14));
		btnRemoveFromStock.setBackground(Color.WHITE);
		btnRemoveFromStock.setBounds(50, 150, 120, 40);
		btnRemoveFromStock.addActionListener(this);
		getContentPane().add(btnRemoveFromStock);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setFont(new Font("Georgia", Font.PLAIN, 14));
		btnCancel.setBackground(Color.WHITE);
		btnCancel.setBounds(215, 150, 120, 40);
        btnCancel.addActionListener(this);
		getContentPane().add(btnCancel);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource().equals(btnRemoveFromStock)){
			ResultSet rset = db.getProductByName(nametxt.getText(), user.getShopID());
						
			try {
				while(rset.next()){
					Product rp = new Product(rset.getString(1), rset.getString(2),rset.getDouble(3),rset.getDouble(4),rset.getInt(5),rset.getInt(6));//Gathers the product to be removed from the database
					rp.remove(Integer.parseInt(qtytxt.getText()));//Removes the product
					dispose();
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
				
			
		}
        else if(e.getSource().equals(btnCancel)){
            dispose();
        }
		
	}

}
