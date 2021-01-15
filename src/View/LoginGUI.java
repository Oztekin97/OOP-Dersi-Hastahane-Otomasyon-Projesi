package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.*;



public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JPasswordField fld_hastaPass;
	private DBConnection conn = new DBConnection();
	private JPasswordField fld_doktorPass;
	private JTextField fld_hastaTcno;
	private JTextField fld_doktorTcno;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastahane Yönetim Sistemine Giriş");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JTabbedPane w_tappane = new JTabbedPane(JTabbedPane.TOP);
		w_tappane.setBounds(10, 78, 464, 258);
		w_pane.add(w_tappane);

		JPanel w_hastaLogin = new JPanel();
		w_tappane.addTab("Hasta Girişi", null, w_hastaLogin, null);
		w_hastaLogin.setLayout(null);

		JLabel fld_hastaTc = new JLabel("TC kimlik no");
		fld_hastaTc.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 18));
		fld_hastaTc.setBounds(60, 46, 108, 25);
		w_hastaLogin.add(fld_hastaTc);

		JLabel fld_hastaŞifre = new JLabel("Şifre");
		fld_hastaŞifre.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 18));
		fld_hastaŞifre.setBounds(107, 94, 44, 25);
		w_hastaLogin.add(fld_hastaŞifre);

		JButton btnNewButton = new JButton("Kayıt Ol");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI=new RegisterGUI();
				rGUI.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 18));
		btnNewButton.setBounds(77, 154, 123, 31);
		w_hastaLogin.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Giriş Yap");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_hastaTcno.getText().length() == 0 || fld_hastaPass.getText().length() == 0) {
					Helper.showMsg("doldur");
				} 
				else {
					boolean key=true;
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");	
						while (rs.next()) {
							if(rs.getString("type").equals("Hasta")) {
							if (fld_hastaTcno.getText().equals(rs.getString("tcno")) && fld_hastaPass.getText().equals(rs.getString("password"))) {
									Hasta hasta = new Hasta();
									hasta.setId(rs.getInt("id"));
									hasta.setPassword(rs.getString("password"));
									hasta.setTcno(rs.getString("tcno"));
									hasta.setName(rs.getString("name"));
									hasta.setType(rs.getString("type"));
									HastaGUI hGUI = new HastaGUI(hasta);
									hGUI.setVisible(true);
									dispose();
									key=false;
								}	
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if(key) {
						Helper.showMsg("Sisteme kayıtlı böyle bir hasta bulunamadı , lütfen sisteme kaydolunuz.");
					}
				}
				
			}
		});
		btnNewButton_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 18));
		btnNewButton_1.setBounds(265, 154, 108, 31);
		w_hastaLogin.add(btnNewButton_1);

		fld_hastaPass = new JPasswordField();
		fld_hastaPass.setBounds(198, 94, 221, 31);
		w_hastaLogin.add(fld_hastaPass);
		
		fld_hastaTcno = new JTextField();
		fld_hastaTcno.setBounds(198, 41, 221, 31);
		w_hastaLogin.add(fld_hastaTcno);
		fld_hastaTcno.setColumns(10);

		JPanel w_doktorLogin = new JPanel();
		w_tappane.addTab("Doktor Girişi", null, w_doktorLogin, null);
		w_doktorLogin.setLayout(null);

		JLabel fld_doktorTC = new JLabel("TC kimlik no");
		fld_doktorTC.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 18));
		fld_doktorTC.setBounds(64, 51, 109, 25);
		w_doktorLogin.add(fld_doktorTC);

		JLabel fld_doktorSifre = new JLabel("Şifre");
		fld_doktorSifre.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 18));
		fld_doktorSifre.setBounds(108, 103, 51, 25);
		w_doktorLogin.add(fld_doktorSifre);

		JButton btnNewButton_2 = new JButton("Giriş Yap");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_doktorTcno.getText().length() == 0 || fld_doktorPass.getText().length() == 0) {
					Helper.showMsg("doldur");
				}
				else  {
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while (rs.next()) {
							if (fld_doktorTcno.getText().equals(rs.getString("tcno")) && fld_doktorPass.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("Bashekim")) {
									Bashekim bashekim = new Bashekim();
									bashekim.setId(rs.getInt("id"));
									bashekim.setTcno(rs.getString("tcno"));
									bashekim.setPassword(rs.getString("password"));
									bashekim.setName(rs.getString("name"));
									bashekim.setType(rs.getString("type"));
									BashekimGUI bGUI = new BashekimGUI(bashekim);
									bGUI.setVisible(true);
									dispose();
								}
								else if(rs.getString("type").equals("Doktor")) {
									Doktor doctor = new Doktor();
									doctor.setId(rs.getInt("id"));
									doctor.setTcno(rs.getString("tcno"));
									doctor.setPassword(rs.getString("password"));
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
								}
								else {
									Helper.showMsg("Sisteme kayıtlı böyle bir doktor bulunamadı , lütfen bilgileri doğru girdiğinize emin olunuz!");
								}
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}	
				}
			}
		});
		btnNewButton_2.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 18));
		btnNewButton_2.setBounds(193, 172, 119, 33);
		w_doktorLogin.add(btnNewButton_2);
		
		fld_doktorPass = new JPasswordField();
		fld_doktorPass.setBounds(193, 95, 226, 33);
		w_doktorLogin.add(fld_doktorPass);
		
		fld_doktorTcno = new JTextField();
		fld_doktorTcno.setBounds(193, 44, 226, 33);
		w_doktorLogin.add(fld_doktorTcno);
		fld_doktorTcno.setColumns(10);

		JLabel lblNewLabel = new JLabel("Hastahane Otomasyon Sistemine Hoşgeldiniz");
		lblNewLabel.setBounds(10, 27, 411, 27);
		w_pane.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
	}
}

