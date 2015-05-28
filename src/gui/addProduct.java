package gui;


import Model.Product;
import Model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addProduct extends JFrame implements ActionListener {

    //DECLARING ALL THE NECESSARY VARIABLE
    private JTextField nameTxt, manTxt,catTxt, qtyTxt, priceTxt, costTxt;
    JLabel addLabel,stockNameLabel,manufacturerLabel,categoryLabel,quantityLabel,priceLabel,costLabel,help;
    JButton addButton, btnCancel;
    private User user;

    //CONFIGURING THE WINDOW
    public addProduct(User inUser) { //shoppeId : Each shop has can only add products into its own shop.

        setTitle("Add Stock");
        setResizable(false);
        user = inUser;

        Image imgIcon = new ImageIcon(this.getClass().getResource("/gui/images/Icon.png")).getImage(); //Path of the Image
        setIconImage(imgIcon);
        
        getContentPane().setBackground(Color.LIGHT_GRAY); setBounds(100, 100, 450, 370); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); getContentPane().setLayout(null);

        addLabel = new JLabel("Add Product"); addLabel.setFont(new Font("Georgia", Font.PLAIN, 18)); addLabel.setBounds(160, 10, 110, 30); getContentPane().add(addLabel);

        stockNameLabel = new JLabel("Stock Name"); stockNameLabel.setFont(new Font("Georgia", Font.PLAIN, 17)); stockNameLabel.setBounds(50, 70, 100, 20); getContentPane().add(stockNameLabel);

        manufacturerLabel = new JLabel("Manufacturer"); manufacturerLabel.setFont(new Font("Georgia", Font.PLAIN, 17)); manufacturerLabel.setBounds(50, 100, 120, 20); getContentPane().add(manufacturerLabel);

        categoryLabel = new JLabel("Category"); categoryLabel.setFont(new Font("Georgia", Font.PLAIN, 17)); categoryLabel.setBounds(50, 130, 100, 20); getContentPane().add(categoryLabel);

        quantityLabel = new JLabel("Quantity"); quantityLabel.setFont(new Font("Georgia", Font.PLAIN, 17)); quantityLabel.setBounds(50, 160, 100, 20); getContentPane().add(quantityLabel);

        priceLabel = new JLabel("Price"); priceLabel.setFont(new Font("Georgia", Font.PLAIN, 17)); priceLabel.setBounds(50, 190, 100, 20); getContentPane().add(priceLabel);

        costLabel = new JLabel("Cost"); costLabel.setFont(new Font("Georgia", Font.PLAIN, 17)); costLabel.setBounds(50, 220, 100, 20); getContentPane().add(costLabel);

        nameTxt = new JTextField(); nameTxt.setBounds(210, 70, 150, 20); getContentPane().add(nameTxt); nameTxt.addActionListener(this); nameTxt.setColumns(10);

        manTxt = new JTextField(); manTxt.setColumns(10); manTxt.setBounds(210, 100, 150, 20); getContentPane().add(manTxt);

        catTxt = new JTextField(); catTxt.setColumns(10); catTxt.setBounds(210, 130, 150, 20); getContentPane().add(catTxt);

        qtyTxt = new JTextField(); qtyTxt.setColumns(10); qtyTxt.setBounds(210, 160, 150, 20); getContentPane().add(qtyTxt);

        priceTxt = new JTextField(); priceTxt.setColumns(10); priceTxt.setBounds(210, 190, 150, 20); getContentPane().add(priceTxt);

        costTxt = new JTextField(); costTxt.setColumns(10); costTxt.setBounds(210, 220, 150, 20); getContentPane().add(costTxt);

        help = new JLabel("Help"); Image img4 = new ImageIcon(this.getClass().getResource("images/helpIcon.png")).getImage(); //Path of the Image
        help.setIcon(new ImageIcon(img4));
        help.setFont(new Font("Georgia", Font.PLAIN, 11));
        help.setBounds(20, 320, 89, 23);
        help.setToolTipText("If you are having difficulty accessing the system contact the IT support at itsupport@topstock.ie or 0876376526");
        getContentPane().add(help);

        addButton = new JButton("Add Product"); addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        addButton.setBackground(Color.WHITE); addButton.setFont(new Font("Georgia", Font.PLAIN, 14)); addButton.setBounds(80, 270, 120, 40); addButton.addActionListener(this); getContentPane().add(addButton);

        btnCancel = new JButton("Cancel"); btnCancel.setBackground(Color.WHITE); btnCancel.setFont(new Font("Georgia", Font.PLAIN, 14)); btnCancel.setBounds(210, 270, 120, 40); btnCancel.addActionListener(this); getContentPane().add(btnCancel);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(addButton)||e.getSource().equals(nameTxt)){
            if((nameTxt.getText().isEmpty())||(manTxt.getText().isEmpty())||(catTxt.getText().isEmpty())||(qtyTxt.getText().isEmpty())||(priceTxt.getText().isEmpty())||costTxt.getText().isEmpty()){

            }else{
                String name = nameTxt.getText(); //RETRIEVE THE FOLLOWING STRING DATA FROM THE TEXT FIELDS
                int qty = Integer.parseInt(qtyTxt.getText()); //RETRIEVE AND PARSE THE QUANTITY FOUND IN THE TEXT FIELD
                double price = Double.parseDouble(priceTxt.getText()); double cost = Double.parseDouble(costTxt.getText()); //RETRIEVE AND PARSE THE PRICE FOUND IN THE TEXT FIELD

                Product np = new Product(name, price, cost, qty, user.getShopID());
                np.addProductToDB(); //CREATING A NEW PRODUCT OBJECT

            }

        }
        else if(e.getSource().equals(btnCancel)){ //CLOSE THE WINDOW
            dispose();
        }

    }
}