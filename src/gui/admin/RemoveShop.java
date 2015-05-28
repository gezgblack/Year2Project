package gui.admin;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import Operations.DBOperations;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RemoveShop extends JFrame implements ActionListener {


    private JTextField idtxt;
    private JButton btnRemoveFromStock,btnCancel;
    DBOperations db = new DBOperations();
    private int shopId;



    public RemoveShop() {


        setTitle("Remove Shop");
        setResizable(false);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setBounds(100, 100, 410, 260);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Remove Shop");
        lblNewLabel.setFont(new Font("Georgia", Font.PLAIN, 19));
        lblNewLabel.setBounds(140, 10, 150, 25);
        getContentPane().add(lblNewLabel);

        JLabel lblProductName = new JLabel("Shop ID");
        lblProductName.setFont(new Font("Georgia", Font.PLAIN, 17));
        lblProductName.setBounds(50, 60, 150, 25);
        getContentPane().add(lblProductName);



        idtxt = new JTextField();
        idtxt.setColumns(10);
        idtxt.setBounds(185, 60, 150, 20);
        getContentPane().add(idtxt);



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
            int id = Integer.parseInt(idtxt.getText());
           // db.removeShop(id);
      }
        else if(e.getSource().equals(btnCancel)){
            dispose();
        }

    }

}
