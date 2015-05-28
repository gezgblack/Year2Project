package gui.admin;

import Model.Shop;
import Operations.DBOperations;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import org.apache.commons.lang3.StringUtils;


public class CreateShop extends JFrame implements ActionListener, MouseListener {

	
	private final JLabel createlbl = new JLabel("Create Shop"), namelbl = new JLabel("Shop Name: "), addrlbl = new JLabel("Address: "), phlbl = new JLabel("Phone number: "), banklbl = new JLabel("Bank: ");
	private JTextField nameTxt = new JTextField(), addrTxt = new JTextField(), phTxt = new JTextField(), bankTxt = new JTextField();
	private final JButton createBtn = new JButton("Create Shop"), cancelbtn = new JButton("Cancel");
	private JLabel errorLabel=new JLabel();
	
	public CreateShop(){
        //Configuring the GUI
		setResizable(false);
		setTitle("Create Shop");
		getContentPane().setBackground(Color.LIGHT_GRAY); getContentPane().setLayout(null); setBounds(100,100, 300, 330); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Image imgIcon = new ImageIcon(this.getClass().getResource("/gui/images/Icon.png")).getImage(); //Path of the Image
        setIconImage(imgIcon);
		
		Font ga = new Font("Georgia", Font.PLAIN, 17); Font bga = new Font("Georgia", Font.PLAIN, 12);
		
		createlbl.setFont(new Font("Georgia", Font.BOLD, 20)); createlbl.setBounds(75, 15, 200, 30); getContentPane().add(createlbl);
		
		namelbl.setFont(ga); namelbl.setBounds(20, 50, 100, 20); getContentPane().add(namelbl);
	
		nameTxt.setBounds(150, 50, 100, 20); nameTxt.setText("Name"); getContentPane().add(nameTxt); 
		nameTxt.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent m){}});  nameTxt.addMouseListener(this);
		
		addrlbl.setFont(ga); addrlbl.setBounds(20, 100, 100, 20); getContentPane().add(addrlbl);
		
		addrTxt.setBounds(150, 100, 100, 20); addrTxt.setText("Address"); getContentPane().add(addrTxt); addrTxt.addMouseListener(this);
		
		phlbl.setFont(ga); phlbl.setBounds(20, 150, 120, 20); getContentPane().add(phlbl);
		
		phTxt.setBounds(150, 150, 100, 20); phTxt.setText("Phone Number"); getContentPane().add(phTxt); phTxt.addMouseListener(this);
		
		banklbl.setFont(ga); banklbl.setBounds(20, 200, 100, 20); getContentPane().add(banklbl);
		
		bankTxt.setBounds(150, 200, 100, 20); bankTxt.setText("Bank Balance"); getContentPane().add(bankTxt); bankTxt.addMouseListener(this);
		
		errorLabel.setFont(ga); errorLabel.setBounds(20,225, 300, 20); errorLabel.setForeground(Color.red); getContentPane().add(errorLabel);
		
		createBtn.setBounds(25, 250, 125, 30); createBtn.setFont(bga); createBtn.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){}});
		createBtn.setBackground(Color.WHITE); createBtn.setForeground(Color.BLACK); createBtn.addActionListener(this); getContentPane().add(createBtn);
		
		cancelbtn.setBounds(155, 250, 125, 30); cancelbtn.setFont(bga); cancelbtn.setBackground(Color.WHITE); cancelbtn.setForeground(Color.BLACK); cancelbtn.addActionListener(this);getContentPane().add(cancelbtn);	
	    //End of Configuring the GUI
    }
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(createBtn)){
            if(nameTxt.getText().isEmpty()||addrTxt.getText().isEmpty()||phTxt.getText().isEmpty()||bankTxt.getText().isEmpty()||nameTxt.equals("Name")||addrTxt.equals("Address")||phTxt.equals("Phone Number")||bankTxt.equals("Bank Balance")){
                errorLabel.setText("Enter values");
            }else if(!StringUtils.isNumeric(phTxt.getText())){
                errorLabel.setText("Phone number must be numeric");
                errorLabel.setVisible(true);
            }else{
                Shop sh = new Shop(nameTxt.getText(), addrTxt.getText(),phTxt.getText(), Double.parseDouble(bankTxt.getText()));
                sh.addShopToDB();
            }
			
		}
		else if(e.getSource().equals(cancelbtn)){
			dispose();
			
		}
	}
	public void mouseClicked(MouseEvent m){
        //Clears the sample text when you click into the window
		if(m.getSource().equals(nameTxt)){
			if(nameTxt.getText().equals("Name")){
				nameTxt.setText(""); repaint(); revalidate();
			}
		}else if(m.getSource().equals(addrTxt)){
			if(addrTxt.getText().equals("Address")){addrTxt.setText(""); repaint(); revalidate();}
		}else if(m.getSource().equals(phTxt)){
			if(phTxt.getText().equals("Phone Number")){phTxt.setText(""); repaint(); revalidate();}
		}else if(m.getSource().equals(bankTxt)){
			if(bankTxt.getText().equals("Bank Balance")){bankTxt.setText(""); repaint(); revalidate();}
		}
	}
	public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}

}
