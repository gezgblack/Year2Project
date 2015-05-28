package gui;

/**
 * Created by Ghost on 14/05/2015.
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class generatoeGui extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField textField;
    private JButton btnGenerate;
    private JProgressBar progressBar;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    generatoeGui frame = new generatoeGui();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public generatoeGui() {
        setTitle("gg 3Izi5Me");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 490, 380);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblRpAmount = new JLabel("RP AMOUNT");
        lblRpAmount.setForeground(new Color(255, 255, 255));
        lblRpAmount.setFont(new Font("Georgia", Font.BOLD, 18));
        lblRpAmount.setBounds(112, 120, 138, 26);
        contentPane.add(lblRpAmount);

        textField = new JTextField();
        textField.setBounds(242, 126, 86, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        btnGenerate = new JButton("gEnERAtE");
        btnGenerate.setFont(new Font("Georgia", Font.PLAIN, 14));
        btnGenerate.setBounds(179, 171, 111, 32);
        btnGenerate.addActionListener(this);
        contentPane.add(btnGenerate);

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setBackground(new Color(255, 255, 255));
        progressBar.setBounds(156, 214, 150, 16);

        contentPane.add(progressBar);

        JLabel lblBuffalotitsHaxr = new JLabel("BuffaloTits Hax0R");
        lblBuffalotitsHaxr.setForeground(new Color(255, 255, 255));
        lblBuffalotitsHaxr.setBounds(347, 11, 125, 14);
        contentPane.add(lblBuffalotitsHaxr);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("D:\\Editing\\Sony Vegas\\Projects\\Montage 6\\points.png"));
        lblNewLabel.setBounds(-84, -12, 556, 373);
        contentPane.add(lblNewLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnGenerate)){
           int n= progressBar.getValue();



                progressBar.setValue(100);
            JOptionPane.showMessageDialog(new JFrame(),"GG WP","Fuck her RIght in the pussy",JOptionPane.INFORMATION_MESSAGE);

        }
    }
}
