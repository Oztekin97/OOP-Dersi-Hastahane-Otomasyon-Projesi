package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Clinic;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class UpdateClinicGUI extends JFrame {

	private JPanel contentPane;
	private JTextField fld_clinicName;
	private JButton btn_updateClinic;
	private static Clinic clinic=new Clinic();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateClinicGUI frame = new UpdateClinicGUI(clinic);
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
	public UpdateClinicGUI(Clinic clinic) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 225, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Poliklinik Adı");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(54, 11, 103, 14);
		contentPane.add(lblNewLabel);
		
		fld_clinicName = new JTextField();
		fld_clinicName.setBounds(57, 36, 100, 20);
		contentPane.add(fld_clinicName);
		fld_clinicName.setText(clinic.getName());
		fld_clinicName.setColumns(10);
		
		
		btn_updateClinic = new JButton("Düzenle");
		btn_updateClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Helper.confirm("sure")) {
					try { 
						clinic.updateClinic(clinic.getId(),fld_clinicName.getText());
						Helper.showMsg("success");
						dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_updateClinic.setBounds(57, 61, 100, 23);
		contentPane.add(btn_updateClinic);
	}

}

