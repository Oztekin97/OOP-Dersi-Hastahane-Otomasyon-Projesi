package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import com.toedter.calendar.JDateChooser;

import Helper.*;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class DoctorGUI extends JFrame implements IExit {

	private JPanel w_pane;
	private static Doktor doctor=new Doktor();
	private JTable table_whour;
	private DefaultTableModel whourModel = null; 
	private Object[] whourData = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor); 
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public DoctorGUI(Doktor doctor) throws SQLException {
		whourModel = new DefaultTableModel();
		Object[] colWhour= new Object[2];
		 colWhour[0]="ID";
		 colWhour[1]="Tarih";
		 whourModel.setColumnIdentifiers(colWhour);
		 whourData= new Object[2];
		 for (int i = 0; i < doctor.getWhourListesi(doctor.getId()).size(); i++) { 
				whourData[0] = doctor.getWhourListesi(doctor.getId()).get(i).getId();
				whourData[1] = doctor.getWhourListesi(doctor.getId()).get(i).getWdate();
				whourModel.addRow(whourData);
			}
		setResizable(false);
		setTitle("Hastahane Yönetim Sistemine Giriş");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 493, 468);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hoşgeldiniz "+doctor.getName());
		lblNewLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 17));
		lblNewLabel.setBounds(10, 11, 293, 33);
		w_pane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Çıkış Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DoktorYetki dy=new DoktorYetki();
				String uyar="Sayın Dr. "+ doctor.getName() + dy.loginMsg();
				JOptionPane.showMessageDialog(null, uyar, "Bilgilendirme", JOptionPane.INFORMATION_MESSAGE);
				String son="Sağlıklı günler dileriz sayın Dr. "+doctor.getName();
				JOptionPane.showMessageDialog(null, son, "Mesaj", JOptionPane.INFORMATION_MESSAGE);
				exitMsg();
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 12));
		btnNewButton.setBounds(359, 18, 89, 23);
		w_pane.add(btnNewButton);
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 55, 457, 363);
		w_pane.add(w_tab);
		
		JPanel w_whour = new JPanel();
		w_tab.addTab("Çalışma Saatleri", null, w_whour, null);
		w_whour.setLayout(null);
		
		JDateChooser select_date = new JDateChooser();
		select_date.setBounds(10, 11, 139, 20);
		w_whour.add(select_date);
		
		JComboBox select_time = new JComboBox();
		select_time.setModel(new DefaultComboBoxModel(new String[] {"10.00", "10.30", "11.00", "11.30", "12.00", "12.30", "13.30", "14.00", "14.30", "15.00"}));
		select_time.setBounds(152, 11, 66, 20);
		w_whour.add(select_time);
		
		JButton btn_addWhour = new JButton("Ekle");
		btn_addWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
				String date="";
				try {
					date=sdf.format(select_date.getDate());
				} catch (Exception e2) {
					
				}
                if(date.length()==0) {
					Helper.showMsg("Geçerli bir tarih girmelisiniz!!!");
				}
                else {
                	String time=" "+select_time.getSelectedItem().toString()+" .00";
    				String selectDate=date+time;
    				try {
						boolean control=doctor.addWhour(doctor.getId(), doctor.getName(), selectDate);
						if(control) {
							Helper.showMsg("success");
							updateWhourModel(doctor); 
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
		btn_addWhour.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 13));
		btn_addWhour.setBounds(228, 11, 95, 20);
		w_whour.add(btn_addWhour);
		
		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(10, 42, 432, 282);
		w_whour.add(w_scrollWhour);
		
		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);
		
		JButton btn_deleteWhour = new JButton("Sil");
		btn_deleteWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow=table_whour.getSelectedRow();
				if(selRow>=0) {
					String selectRow=table_whour.getModel().getValueAt(selRow, 0).toString();
					int selID=Integer.parseInt(selectRow);
					try {
						boolean control=doctor.deleteWhour(selID);
						if(control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
						}
						else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				else {
					Helper.showMsg("Bir tarih seçmelisiniz!!!");
				}
				
			}
		});
		btn_deleteWhour.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		btn_deleteWhour.setBounds(333, 11, 89, 20);
		w_whour.add(btn_deleteWhour);
	}
	public void updateWhourModel(Doktor doctor) throws SQLException {
		DefaultTableModel clearmodel = (DefaultTableModel) table_whour.getModel();
		clearmodel.setRowCount(0);
		 for (int i = 0; i < doctor.getWhourListesi(doctor.getId()).size(); i++) { 
				whourData[0] = doctor.getWhourListesi(doctor.getId()).get(i).getId();
				whourData[1] = doctor.getWhourListesi(doctor.getId()).get(i).getWdate();
				whourModel.addRow(whourData);

			}
	}
	@Override
	public void exitMsg() {
		String diyalog="Randevularınızı iptal ettirmek istiyorsanız veya yerinize refakatçi doktor atayacaksanız en az 3 gün önce başhekime bildirmelisiniz ";
		JOptionPane.showMessageDialog(null, diyalog, "Mesaj", JOptionPane.INFORMATION_MESSAGE);	
	}
}

