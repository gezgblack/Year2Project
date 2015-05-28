package gui;

import Model.User;
import Operations.DBOperations;
import gui.admin.AdminPanel;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener {

    //Initializing all the JFrame variables

    private JPanel contentPane;
    private JLabel loginLabel, passwordLabel, loginIcon,help,errorLabel;
    private JTextField loginText;
    private JButton loginButton, exitButton, helpButton;
    private JPasswordField passwordField;
    DBOperations db=new DBOperations();
    MathsEncrypt s = new MathsEncrypt();

    
    public static void main(String[] args) { //This part just launches the window to show what it looks like
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login(); // Creating the object
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    // Creating the Form
    public Login() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 566, 358);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        Font ga = new Font("Georgia", Font.PLAIN, 17);

        Image imgIcon = new ImageIcon(this.getClass().getResource("images/icon.png")).getImage(); //Path of the Image
        setIconImage(imgIcon);

        //Configuring the Username Label
        loginLabel = new JLabel("Username");
        loginLabel.setFont(ga); //Changing Font and Size
        loginLabel.setBounds(198, 94, 77, 14);
        contentPane.add(loginLabel);

        //Configuring the Password Label
        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(ga);
        passwordLabel.setBounds(198, 133, 77, 30);
        contentPane.add(passwordLabel);
        
        errorLabel = new JLabel("");
        errorLabel.setFont(ga);
        errorLabel.setForeground(Color.red);
        errorLabel.setBounds(200, 220, 250, 30);
        contentPane.add(errorLabel);

        //Configuring the Login Text Box
        loginText = new JTextField();
        loginText.setBounds(285, 90, 149, 29);
        contentPane.add(loginText);
        loginText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginText.requestFocus();
			}
		});
        loginText.addActionListener(this);
        loginText.setColumns(10);

        //Configuring the Log In Button
        loginButton = new JButton("Login");
        Image img = new ImageIcon(this.getClass().getResource("images/buttonIcon.png")).getImage(); // Path of the Image
        loginButton.setIcon(new ImageIcon(img)); //Parsing the image into the Button
        loginButton.setFont(new Font("Georgia", Font.PLAIN, 14));
        loginButton.setBounds(313, 187, 128, 33);
        loginButton.addActionListener(this);
        contentPane.add(loginButton);

        //Configuring the Password Text Box
        passwordField = new JPasswordField();
        passwordField.setEchoChar('*'); //Selecting the secret character
        passwordField.setBounds(285, 134, 149, 29);
        passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passwordField.requestFocus();
			}
		});
        passwordField.addActionListener(this);
        contentPane.add(passwordField);

        //Configuring the Login Image
        loginIcon = new JLabel("");
        loginIcon.setBounds(60, 50, 128, 128);
        Image img2 = new ImageIcon(this.getClass().getResource("images/loginIcon.png")).getImage(); //Path of the Image
        loginIcon.setIcon(new ImageIcon(img2)); //Parsing the image into the Button
        contentPane.add(loginIcon);

        //Configuring the Exit Button
        exitButton = new JButton("Exit");
        Image img3 = new ImageIcon(this.getClass().getResource("images/exitIcon.png")).getImage(); //Path of the Image
        exitButton.setIcon(new ImageIcon(img3)); //Parsing the image into the Button
        exitButton.setFont(new Font("Georgia", Font.PLAIN, 14));
        exitButton.setBounds(198, 187, 101, 33);
        exitButton.addActionListener(this);
        contentPane.add(exitButton);

        help = new JLabel("Help");
        Image img4 = new ImageIcon(this.getClass().getResource("images/helpIcon.png")).getImage(); //Path of the Image
        help.setIcon(new ImageIcon(img4));
        help.setFont(new Font("Georgia", Font.PLAIN, 11));
        help.setBounds(20, 286, 89, 23);
        help.setToolTipText("If you are having difficulty accessing the system contact the IT support at itsupport@topstock.ie or 0876376526");
        contentPane.add(help);

    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(loginButton)||e.getSource().equals(passwordField)||e.getSource().equals(loginText)){


        	ResultSet rset;
    		User user = new User();
    		rset = db.Login(loginText.getText(),s.encrypt(passwordField.getText(), loginText.getText()));
    		try {
				while(rset.next()){
					user = new User(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(5), rset.getInt(6), rset.getInt(7));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        	
            if(user.getuserLevel() == 0){
            	AdminPanel ap = new AdminPanel(user);
            	ap.setVisible(true);
            	dispose();

            }
        	else if(user.getuserLevel() == 1||user.getuserLevel()==2){
        		
        		managerMenu mm = new managerMenu(user);
        		mm.setVisible(true);
        		dispose();
        	
        	}else{
        		errorLabel.setText("Incorrect username or Password");
        	}


        }
        else if(e.getSource().equals(exitButton)){
            db.closeDB();
            System.exit(0);
        }


    }
}
