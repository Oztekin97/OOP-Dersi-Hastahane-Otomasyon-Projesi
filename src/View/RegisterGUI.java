package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.*;
import Model.*;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_name;
	private JTextField fld_tcno;
	private JPasswordField fld_password;
	private Hasta hasta=new Hasta();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RegisterGUI() {
		setResizable(false);
		setTitle("Hastahane Yönetim Sistemine Giriş");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 330, 300);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Ad Soyad");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblNewLabel_1.setBounds(125, 11, 68, 14);
		w_pane.add(lblNewLabel_1);
		
		fld_name = new JTextField();
		fld_name.setColumns(10);
		fld_name.setBounds(53, 36, 220, 24);
		w_pane.add(fld_name);
		
		JLabel lblNewLabel_2 = new JLabel("TC kimlik no");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblNewLabel_2.setBounds(110, 71, 89, 14);
		w_pane.add(lblNewLabel_2);
		
		fld_tcno = new JTextField();
		fld_tcno.setColumns(10);
		fld_tcno.setBounds(53, 88, 220, 24);
		w_pane.add(fld_tcno);
		
		JLabel lblNewLabel_3 = new JLabel("Şifre");
		lblNewLabel_3.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblNewLabel_3.setBounds(139, 120, 46, 14);
		w_pane.add(lblNewLabel_3);
		
		fld_password = new JPasswordField();
		fld_password.setBounds(57, 147, 210, 24);
		w_pane.add(fld_password);
		
		JButton btn_register = new JButton("Kayıt ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_name.getText().length() == 0 || fld_tcno.getText().length() == 0 || fld_password.getText().length() == 0) {
					Helper.showMsg("doldur");
				} else {
					try {
						boolean control = hasta.register(fld_tcno.getText(), fld_password.getText(),fld_name.getText());
						if (control) {
							Helper.showMsg("success");
							LoginGUI loginGUI=new LoginGUI();
							loginGUI.setVisible(true);
							dispose();
						}
						else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_register.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_register.setBounds(96, 182, 146, 23);
		w_pane.add(btn_register);
		
		JButton btn_backto = new JButton("Geri dön");
		btn_backto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI loginGUI=new LoginGUI();
				loginGUI.setVisible(true);
				dispose();
			}
			
		});
		btn_backto.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_backto.setBounds(96, 216, 146, 23);
		w_pane.add(btn_backto);
	}
}
