package gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.table.TableModel;
import Model.Product;
import Model.User;


public class FinishAndPay extends JFrame implements ActionListener{

	//DECLARING ALL THE NECESSARY VARIABLES
	private JTextField ccNumber, cardHolderName, ccvNumber, addressField, phoneField;
	private JComboBox<String> ccCombo, monthComboBox, yearsComboBox; 
	private JButton btnConfirm, btnCancel;
	private JTable table;
	private JLabel CreditCardNumLabel,cardTypeLabel,expirationDate,cardHolder,ccvLabel,addressLabel,phoneLabel,wrongCc,help;
	private ArrayList<Product> products;
    private User user;
	

	
	public FinishAndPay(TableModel tm,User inUser, ArrayList<Product> inProducts) {
		user = inUser;
		products = inProducts;
        
        //Start of GUI creation
		setTitle("Credit Card Verification");
		setResizable(false);
		setBounds(100, 100, 600, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

        Image imgIcon = new ImageIcon(this.getClass().getResource("images/Icon.png")).getImage(); //Path of the Image
        setIconImage(imgIcon);
		
		cardTypeLabel = new JLabel("Credit Card");
		cardTypeLabel.setFont(new Font("Georgia", Font.PLAIN, 15));
		cardTypeLabel.setBounds(26, 16, 95, 22);
		getContentPane().add(cardTypeLabel);
		
		ccCombo = new JComboBox<String>();
		ccCombo.setBounds(26, 38, 95, 20);
		ccCombo.setModel(new DefaultComboBoxModel<String>(new String[]{"Visa", "Visa Debit", "MasterCard"}));
		ccCombo.setFont(new Font("Georgia", Font.PLAIN, 14));
		getContentPane().add(ccCombo);
		
		CreditCardNumLabel = new JLabel("Credit Card Number");
		CreditCardNumLabel.setFont(new Font("Georgia", Font.PLAIN, 15));
		CreditCardNumLabel.setBounds(141, 20, 143, 14);
		getContentPane().add(CreditCardNumLabel);
		
		ccNumber = new JTextField();
		ccNumber.setBounds(131, 39, 153, 20);
		getContentPane().add(ccNumber);
		ccNumber.setColumns(10);
		
		expirationDate = new JLabel("Expiration Date");
		expirationDate.setFont(new Font("Georgia", Font.PLAIN, 15));
		expirationDate.setBounds(312, 20, 116, 14);
		getContentPane().add(expirationDate);
		
		monthComboBox = new JComboBox<String>();
		monthComboBox.setModel(new DefaultComboBoxModel<String>(new String[]{"MM", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		monthComboBox.setBounds(311, 39, 47, 20);
		
		getContentPane().add(monthComboBox);
		
		yearsComboBox = new JComboBox<String>();
		yearsComboBox.setBounds(368, 39, 60, 20);
		yearsComboBox.setModel(new DefaultComboBoxModel<String>(new String[]{"YYYY", "2015", "2016", "2017", "2018", "2019", "2020"}));
		getContentPane().add(yearsComboBox);
		
		cardHolder = new JLabel("Cardholder's Name");
		cardHolder.setFont(new Font("Georgia", Font.PLAIN, 15));
		cardHolder.setBounds(26, 81, 143, 22);
		getContentPane().add(cardHolder);
		
		cardHolderName = new JTextField();
		cardHolderName.setBounds(169, 83, 153, 20);
		getContentPane().add(cardHolderName);
		cardHolderName.setColumns(10);
		
		ccvLabel = new JLabel("CCV");
		ccvLabel.setFont(new Font("Georgia", Font.PLAIN, 15));
		ccvLabel.setBounds(449, 20, 46, 14);
		getContentPane().add(ccvLabel);

		wrongCc = new JLabel("Credit Card Details Invalid");
		wrongCc.setBounds(50, 145, 150, 100);
		wrongCc.setForeground(Color.red);
		wrongCc.setVisible(false);
		getContentPane().add(wrongCc);
		
		ccvNumber = new JTextField();
		ccvNumber.setBounds(443, 39, 52, 20);
		getContentPane().add(ccvNumber);
		ccvNumber.setColumns(10);
		
		addressLabel = new JLabel("Address");
		addressLabel.setFont(new Font("Georgia", Font.PLAIN, 15));
		addressLabel.setBounds(26, 114, 143, 14);
		getContentPane().add(addressLabel);
		
		addressField = new JTextField();
		addressField.setBounds(169, 112, 153, 20);
		getContentPane().add(addressField);
		addressField.setColumns(10);
		
		phoneLabel = new JLabel("Phone");
		phoneLabel.setFont(new Font("Georgia", Font.PLAIN, 15));
		phoneLabel.setBounds(26, 141, 143, 14);
		getContentPane().add(phoneLabel);
		
		phoneField = new JTextField();
		phoneField.setBounds(169, 139, 153, 20);
		getContentPane().add(phoneField);
		phoneField.setColumns(10);

        help = new JLabel("Help");
        Image img4 = new ImageIcon(this.getClass().getResource("images/helpIcon.png")).getImage(); //Path of the Image
        help.setIcon(new ImageIcon(img4));
        help.setFont(new Font("Georgia", Font.PLAIN, 11));
        help.setBounds(20, 410, 89, 23);
        help.setToolTipText("If you are having difficulty accessing the system contact the IT support at itsupport@topstock.ie or 0876376526");
        getContentPane().add(help);
		
		JScrollPane listOfProducts = new JScrollPane(); ////Configuring the Table full of Content
		listOfProducts.setBounds(10, 210, 560, 180);
		getContentPane().add(listOfProducts);
		
		table = new JTable();
		table.setModel(tm);
		listOfProducts.setViewportView(table);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(292, 391, 89, 23);
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConfirm.addActionListener(this);
		getContentPane().add(btnConfirm);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(391, 391, 89, 23);
		btnCancel.addActionListener(this);
		getContentPane().add(btnCancel);
        //End of gui creation
	}
	
	//Luhn test checks the validity of a Credit Card number
	 private static boolean luhnTest(String number){
	        int s1 = 0, s2 = 0;
	        String reverse = new StringBuffer(number).reverse().toString();
	        for(int i = 0 ;i < reverse.length();i++){
	            int digit = Character.digit(reverse.charAt(i), 10);
	            if(i % 2 == 0){//this is for odd digits, they are 1-indexed in the algorithm
	                s1 += digit;
	            }else{//add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
	                s2 += 2 * digit;
	                if(digit >= 5){
	                    s2 -= 9;
	                }
	            }
	        }
	        return (s1 + s2) % 10 == 0;
	    }
	 	 
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource().equals(btnConfirm)){
			
			if((ccNumber.getText().isEmpty())||(monthComboBox.getSelectedItem().equals("MM"))||yearsComboBox.getSelectedItem().equals("YYYY")||ccvNumber.getText().isEmpty()||cardHolderName.getText().isEmpty()||addressField.getText().isEmpty()||phoneField.getText().isEmpty()){
				
				
					
            }else{
            	//Valid Luhn test number 49927398716
            	
            	boolean luhnpass = luhnTest(ccNumber.getText());
            	if(luhnpass == false){
					wrongCc.setVisible(true);
            		System.out.print("Invalid card details");
            	}else{
            		
            		Calendar dt = Calendar.getInstance();
            		int year = dt.get(dt.YEAR), month =dt.get(dt.MONTH), expYear = Integer.parseInt(yearsComboBox.getSelectedItem().toString()), expMon = Integer.parseInt(monthComboBox.getSelectedItem().toString());
            		
            		if((expYear>year)||((expYear==year)&&(expMon>month))){
            			System.out.println("Sale Complete");
            			
            			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            			Date currentdate = new Date();
            			
            			String custName = cardHolderName.getText(), date = dateFormat.format(currentdate).toString();
            			
            			System.out.print("\nDate: "+ date+"\n");
            			
            			for(int loop1=0;loop1<products.size();loop1++){    
                                products.get(loop1).sell(user.getUsername(), custName, date);//Sells the item.
                                dispose();//Closes the Finish and pay window
            			}
            		}else{
            			System.out.print("Invalid card details");
            		}
            		
            	}
            }
            


			
		}
		else if(e.getSource().equals(btnCancel)){
			dispose();
		}
	}
	
}