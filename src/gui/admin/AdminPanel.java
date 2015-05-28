package gui.admin;

import gui.Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

import Model.User;

public class AdminPanel extends JFrame implements ActionListener{
	
	private final JButton seeDB= new JButton("See Database"), createU = new JButton("Create User"), createS = new JButton("Create Shop"),deleteS = new JButton("Delete Shop"), logOut = new JButton("Log Out");
	private User user;
	public AdminPanel(User inUser){

		user = inUser;
		
		setTitle("Admin Panel");
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setBounds(100, 100, 560, 250); setTitle("Admin Panel");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

        Image imgIcon = new ImageIcon(this.getClass().getResource("/gui/images/Icon.png")).getImage(); //Path of the Image
        setIconImage(imgIcon);
		
		Font ga = new Font("Georgia", Font.PLAIN, 15);
		
		seeDB.setBounds(50,20,200,50); seeDB.setFont(ga);
		seeDB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			}
		});
		seeDB.setBackground(Color.WHITE); seeDB.setForeground(Color.BLACK); seeDB.addActionListener(this); getContentPane().add(seeDB);
		
		createU.setBounds(300, 20, 200, 50); createU.setFont(ga); createU.setBackground(Color.WHITE); createU.setForeground(Color.BLACK); createU.addActionListener(this); getContentPane().add(createU);
		
		createS.setBounds(50, 120, 200, 50); createS.setFont(ga); createS.setBackground(Color.WHITE); createS.setForeground(Color.BLACK); createS.addActionListener(this); getContentPane().add(createS);

        logOut.setBounds(300, 120, 200, 50);
        logOut.setFont(ga);
        logOut.setBackground(Color.WHITE);
        logOut.setForeground(Color.BLACK);
        logOut.addActionListener(this);
        getContentPane().add(logOut);

    }
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource().equals(seeDB)){
            try {
                SeeDB sd = new SeeDB();
                sd.setVisible(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }else if(e.getSource().equals(createU)){
			CreateUser cu = new CreateUser(user); cu.setVisible(true);
		}else if(e.getSource().equals(createS)){
			CreateShop cs = new CreateShop(); cs.setVisible(true);
        }
        else if(e.getSource().equals(logOut)){
            Login lgn = new Login();
            lgn.setVisible(true);
            dispose();

        }

	}
	

}
