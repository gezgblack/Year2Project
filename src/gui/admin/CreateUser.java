package gui.admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import Model.User;
import Operations.DBOperations;
import gui.MathsEncrypt;
import org.apache.commons.lang3.StringUtils;

public class CreateUser extends JFrame implements ActionListener {
	
	private JTextField userTxt = new JTextField(), phText = new JTextField(), addrTxt = new JTextField(), nameTxt = new JTextField(), emailTxt = new JTextField();
	private JPasswordField pass1 = new JPasswordField(), pass2 = new JPasswordField();
	private final JButton addBtn = new JButton("Add User"), cancelBtn = new JButton("Cancel");
	private final JLabel userlbl = new JLabel("Username: "), passlbl = new JLabel("Password: "), pass2lbl = new JLabel("Verify Password: "), phlbl = new JLabel("Phone Number: "), addresslbl = new JLabel("Address: "), namelbl = new JLabel("Full Name: "), emaillbl = new JLabel("Email: "), usrlvllbl = new JLabel("Position: "), storelbl = new JLabel("Store: "),errorLabel = new JLabel("Password did not match");
	private JComboBox<String> userLevel = new JComboBox<String>(new String[] {"Admin", "Manager","Cashier"}), shopName;

	//private JComboBox<Integer> shopName;
	private DBOperations db = new DBOperations();
    MathsEncrypt s = new MathsEncrypt();
    private User user;


	
	public CreateUser(User inUser){
		user = inUser;
        int btnHeight;
		getContentPane().setBackground(Color.LIGHT_GRAY); getContentPane().setLayout(null); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Set the window size depending on the users clearance level.
        if(user.getuserLevel()==0) {
            setBounds(100, 100, 350, 600);
            btnHeight = 510;
        }else {
            setBounds(100, 100, 350, 500);
            btnHeight = 410;
        }

        Font ga = new Font("Georgia", Font.PLAIN, 17); Font bga = new Font("Georgia", Font.PLAIN, 12);

        errorLabel.setFont(ga); errorLabel.setBounds(65, 25, 250, 20); getContentPane().add(errorLabel);errorLabel.setForeground(Color.red); errorLabel.setVisible(false);
		
		userlbl.setFont(ga); userlbl.setBounds(30, 70, 100, 20); getContentPane().add(userlbl);
		
		userTxt.setBounds(180, 70, 100, 20); userTxt.setText("Username"); getContentPane().add(userTxt);  userTxt.addActionListener(this);
		
		passlbl.setFont(ga); passlbl.setBounds(30, 120, 100, 20); getContentPane().add(passlbl);
		
		pass1.setBounds(180, 120, 100, 20); getContentPane().add(pass1);
		
		pass2lbl.setFont(ga); pass2lbl.setBounds(30, 170, 180, 20); getContentPane().add(pass2lbl);
		
		pass2.setBounds(180, 170, 100, 20); getContentPane().add(pass2);
		
		phlbl.setFont(ga); phlbl.setBounds(30, 220, 180,20); getContentPane().add(phlbl);
		
		phText.setBounds(180, 220, 100, 20); phText.setText("Phone Number"); getContentPane().add(phText); phText.addActionListener(this);
		
		addresslbl.setFont(ga); addresslbl.setBounds(30, 270, 180, 20); getContentPane().add(addresslbl);
		
		addrTxt.setBounds(180, 270, 100, 20); addrTxt.setText("Address"); getContentPane().add(addrTxt); addrTxt.addActionListener(this);
		
		namelbl.setFont(ga); namelbl.setBounds(30, 320, 180, 20); getContentPane().add(namelbl);
		
		nameTxt.setBounds(180, 320, 100, 20); nameTxt.setText("Full Name"); getContentPane().add(nameTxt); nameTxt.addActionListener(this);
		
		emaillbl.setFont(ga); emaillbl.setBounds(30, 370, 180, 20); getContentPane().add(emaillbl);
		
		emailTxt.setBounds(180, 370, 100, 20); emailTxt.setText("Email"); getContentPane().add(emailTxt); emailTxt.addActionListener(this);

        Image imgIcon = new ImageIcon(this.getClass().getResource("/gui/images/Icon.png")).getImage(); //Path of the Image
        setIconImage(imgIcon);

        //If currently logged in user is an admin display extra options.
        if(user.getuserLevel()==0) {
            usrlvllbl.setFont(ga);
            usrlvllbl.setBounds(30, 430, 100, 20);
            getContentPane().add(usrlvllbl);

            userLevel.setBounds(180, 420, 100, 30);
            getContentPane().add(userLevel);

            storelbl.setFont(ga);
            storelbl.setBounds(30, 480, 50, 20);
            getContentPane().add(storelbl);

            shopName = new JComboBox<String>(db.getShops());
            shopName.setBounds(80, 470, 200, 30);
            getContentPane().add(shopName);
        }

		
		
		

            addBtn.setBounds(40, btnHeight, 100, 30); addBtn.setFont(bga); addBtn.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){}}); addBtn.setBackground(Color.WHITE); addBtn.setForeground(Color.BLACK); addBtn.addActionListener(this); getContentPane().add(addBtn);

            cancelBtn.setBounds(200, btnHeight, 100, 30); cancelBtn.setFont(bga); cancelBtn.setBackground(Color.WHITE); cancelBtn.setForeground(Color.BLACK); cancelBtn.addActionListener(this); getContentPane().add(cancelBtn);

		
		
		
	}


	public void actionPerformed(ActionEvent e){
		
		if(e.getSource().equals(addBtn)){
            if(userTxt.getText().isEmpty()||phText.getText().isEmpty()||addrTxt.getText().isEmpty()||nameTxt.getText().isEmpty()||emailTxt.getText().isEmpty()||pass1.getText().isEmpty()||pass2.getText().isEmpty()){
                errorLabel.setText("Empty fields");

            }else if(userTxt.getText().equals("Username")||phText.getText().equals("Phone Number")||addrTxt.getText().equals("Address")||nameTxt.getText().equals("Full Name")||emailTxt.getText().equals("Email")){
                errorLabel.setText("Enter values");
            }else if(!(s.encrypt(pass1.getText(), userTxt.getText()).equals(s.encrypt(pass2.getText(), userTxt.getText())))){
                errorLabel.setText("Passwords do not match");
                errorLabel.setVisible(true);
            }else if(!StringUtils.isNumeric(phText.getText())){
                errorLabel.setText("Phone number must be numeric");
                errorLabel.setVisible(true);
            }else if(db.usernameExists(userTxt.getText())==true){
                errorLabel.setText("Username already exists");
                errorLabel.setVisible(true);
            }else{
            	int shopId = user.getShopID(), newUsersLevel =2;
                if(user.getuserLevel()==0){
                	shopId = db.getShopIdOfShopName(shopName.getSelectedItem().toString());
                	if(userLevel.getSelectedItem().equals("Admin")){
                		newUsersLevel = 0;
                	}else if(userLevel.getSelectedItem().equals("Manager")){
                		newUsersLevel = 1;
                	}else if(userLevel.getSelectedItem().equals("Cashier")){
                		newUsersLevel = 2;
                	}
                }
                User nu = new User(userTxt.getText(), phText.getText(), addrTxt.getText(), nameTxt.getText(), emailTxt.getText(), shopId, newUsersLevel, s.encrypt(pass1.getText(), userTxt.getText()));
                nu.addUserToDB();
                dispose();
            }





		
		}
        else if(e.getSource().equals(cancelBtn)){
            dispose();
        }
		
	}

}
