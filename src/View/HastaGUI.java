package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.HastaYetki;
import Helper.Helper;
import Helper.IExit;
import Helper.Item;
import Helper.UserYetkiBilgilendirme;
import Model.*;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class HastaGUI extends JFrame implements IExit {

	private JPanel w_pane;
	static Hasta hasta = new Hasta();
	private Clinic clinic=new Clinic();
	private JTable table_doctor;
	private DefaultTableModel doktormodel;
	private Object[] doktorData = null;
	private JTable table_whour;
	private Whour whour=new Whour();
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private int selectdoktor_id=0;
	private String selectdoktor_name=null;
	private JTable table_rdv;
	private DefaultTableModel randevuModel;
	private Object[] randevuData = null;
	private Randevu randevu=new Randevu();
	HastaYetki hyetki=new HastaYetki();
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HastaGUI frame = new HastaGUI(hasta);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
  
	public HastaGUI(Hasta hasta) throws SQLException {
		doktormodel = new DefaultTableModel();
		Object[] coldoktor = new Object[2];
		coldoktor[0] = "ID";
		coldoktor[1] = "Ad Soyad";
		doktormodel.setColumnIdentifiers(coldoktor);
		doktorData = new Object[2];
		
		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];
		
		randevuModel = new DefaultTableModel();
		Object[] colRandevu = new Object[3];
		colRandevu[0] = "ID";
		colRandevu[1] = "Doktor Adı";
		colRandevu[2] = "Tarih";
		randevuModel.setColumnIdentifiers(colRandevu);
		randevuData = new Object[3];
		
		 for(int i=0;i<randevu.getHastaList(hasta.getId()).size();i++) {
				randevuData[0]=randevu.getHastaList(hasta.getId()).get(i).getId();
				randevuData[1]=randevu.getHastaList(hasta.getId()).get(i).getDoctor_name();
				randevuData[2]=randevu.getHastaList(hasta.getId()).get(i).getApp_date();
				randevuModel.addRow(randevuData);
			}	
		
		setResizable(false);
		setTitle("Hastahane Yönetim Sistemine Giriş");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 493, 468);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hoşgeldiniz "+hasta.getName());
		lblNewLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 17));
		lblNewLabel.setBounds(10, 11, 312, 33);
		w_pane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Çıkış Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserYetkiBilgilendirme uyb; //pointer
			    HastaYetki hy=new HastaYetki();
			    uyb=hy;
				String uyar="Sayın "+ hasta.getName() + uyb.loginMsg();
				JOptionPane.showMessageDialog(null, uyar, "Bilgilendirme", JOptionPane.INFORMATION_MESSAGE);
				String son="Sağlıklı günler dileriz "+hasta.getName()+" randevunuzdan en az 15 dk önce giriş yaptırmalısınız.";
				JOptionPane.showMessageDialog(null, son, "Uyarı", JOptionPane.INFORMATION_MESSAGE);
				exitMsg();
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 12));
		btnNewButton.setBounds(349, 18, 85, 23);
		w_pane.add(btnNewButton);
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 55, 457, 363);
		w_pane.add(w_tab);
		
		JPanel w_randevu = new JPanel();
		w_tab.addTab("Randevu Sistemi", null, w_randevu, null);
		w_randevu.setLayout(null);
		
		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 28, 158, 296);
		w_randevu.add(w_scrollDoctor);
		
		table_doctor = new JTable(doktormodel);
		w_scrollDoctor.setViewportView(table_doctor);
		
		JLabel lblNewLabel_1_1 = new JLabel("Doktor Listesi");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(10, 11, 98, 14);
		w_randevu.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Poliklinik adı");
		lblNewLabel_1_1_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(170, 11, 92, 14);
		w_randevu.add(lblNewLabel_1_1_1);
		
		JComboBox select_clinic = new JComboBox();
		select_clinic.setBounds(170, 36, 109, 29);
		select_clinic.addItem("Poliklinik Seçiniz");
		for(int i=0;i<clinic.getList().size();i++) {
			select_clinic.addItem(new Item(clinic.getList().get(i).getId(), clinic.getList().get(i).getName()));
		}
		select_clinic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	         if(select_clinic.getSelectedIndex()!=0) {
	        	 JComboBox c=(JComboBox) e.getSource();
	        	 Item item=(Item) c.getSelectedItem();
	        	 System.out.println(item.getKey() + "-" + item.getValue());
	        	 DefaultTableModel clearModel=(DefaultTableModel) table_doctor.getModel();
	        	 clearModel.setRowCount(0);
	        	 try {
					for(int i=0;i<clinic.getClinicDoktorListesi(item.getKey()).size();i++) {
						 doktorData[0]=clinic.getClinicDoktorListesi(item.getKey()).get(i).getId();
						 doktorData[1]=clinic.getClinicDoktorListesi(item.getKey()).get(i).getName();
						 doktormodel.addRow(doktorData);
					 }
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
	         }
	         else {
	        	 DefaultTableModel clearModel=(DefaultTableModel) table_doctor.getModel();
	        	 clearModel.setRowCount(0); 
	         }
				
			}
		});
		w_randevu.add(select_clinic);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Doktor Adı");
		lblNewLabel_1_1_1_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblNewLabel_1_1_1_1.setBounds(178, 94, 92, 14);
		w_randevu.add(lblNewLabel_1_1_1_1);
		
		JButton btn_selDoctor = new JButton("Seç");
		btn_selDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row=table_doctor.getSelectedRow();
				if(row>=0) {
					String value=table_doctor.getModel().getValueAt(row,0).toString();
					int id=Integer.parseInt(value);
					DefaultTableModel clearModel=(DefaultTableModel) table_whour.getModel();
				    clearModel.setRowCount(0);
				    try {
				    	  for(int i=0;i<whour.getWhourListesi(id).size();i++) {
								whourData[0]=whour.getWhourListesi(id).get(i).getId();
								whourData[1]=whour.getWhourListesi(id).get(i).getWdate();
								whourModel.addRow(whourData);
							}	
				    }catch(SQLException e1) {
				    	e1.printStackTrace();
				    }
				    table_whour.setModel(whourModel);
				    selectdoktor_id=id;
				    selectdoktor_name=table_doctor.getModel().getValueAt(row, 1).toString();
				}
				 else {
		            	Helper.showMsg("Bir doktor seçmelisiniz!!!");
		            }
			}
		});
		btn_selDoctor.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		btn_selDoctor.setBounds(173, 119, 106, 29);
		w_randevu.add(btn_selDoctor);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Doktor Listesi");
		lblNewLabel_1_1_2.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblNewLabel_1_1_2.setBounds(294, 11, 98, 14);
		w_randevu.add(lblNewLabel_1_1_2);
		
		JScrollPane w_scrollWhours = new JScrollPane();
		w_scrollWhours.setBounds(284, 28, 158, 296);
		w_randevu.add(w_scrollWhours);
		
		table_whour = new JTable();
		w_scrollWhours.setViewportView(table_whour);
		
		JButton btn_addRdv = new JButton("Randevu Al");
		btn_addRdv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow=table_whour.getSelectedRow();
				if(selRow>=0) {
					String date=table_whour.getModel().getValueAt(selRow,1).toString();
                    try {
						boolean control=hasta.addRandevu(selectdoktor_id, hasta.getId(),selectdoktor_name, hasta.getName(),date);
						if(control) {
							Helper.showMsg("success");
							hasta.updateWhourStatus(selectdoktor_id, date);
							updateWhourModel(selectdoktor_id);
							updateRandevuModel(hasta.getId());
						}
						else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				else {
					Helper.showMsg("Geçerli bir tarih girmelisiniz!!!");
				}
			}
		});
		btn_addRdv.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 12));
		btn_addRdv.setBounds(173, 207, 106, 29);
		w_randevu.add(btn_addRdv);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Randevu");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblNewLabel_1_1_1_1_1.setBounds(178, 182, 92, 14);
		w_randevu.add(lblNewLabel_1_1_1_1_1);
		
		JPanel w_rdv = new JPanel();
		w_tab.addTab("Randevu Listem", null, w_rdv, null);
		w_rdv.setLayout(null);
		
		JScrollPane w_scrollRdv = new JScrollPane();
		w_scrollRdv.setBounds(10, 11, 432, 313);
		w_rdv.add(w_scrollRdv);
		
		table_rdv = new JTable(randevuModel);
		w_scrollRdv.setViewportView(table_rdv);
	}
	
	public void updateWhourModel(int doktor_id) throws SQLException {
		DefaultTableModel clearModel=(DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		 for(int i=0;i<whour.getWhourListesi(doktor_id).size();i++) {
				whourData[0]=whour.getWhourListesi(doktor_id).get(i).getId();
				whourData[1]=whour.getWhourListesi(doktor_id).get(i).getWdate();
				whourModel.addRow(whourData);
			}	
	}
	
	public void updateRandevuModel(int hasta_id) throws SQLException {
		DefaultTableModel clearModel=(DefaultTableModel) table_rdv.getModel();
		clearModel.setRowCount(0);
		 for(int i=0;i<randevu.getHastaList(hasta_id).size();i++) {
				randevuData[0]=randevu.getHastaList(hasta_id).get(i).getId();
				randevuData[1]=randevu.getHastaList(hasta_id).get(i).getDoctor_name();
				randevuData[2]=randevu.getHastaList(hasta_id).get(i).getApp_date();
				randevuModel.addRow(randevuData);
			}	
			
	}

	@Override
	public void exitMsg() {
		String diyalog="Randevunuza gelemeyecekseniz en az 1 saat önce iptal ettirmelisiniz. ";
		JOptionPane.showMessageDialog(null, diyalog, "Mesaj", JOptionPane.INFORMATION_MESSAGE);
	}
}

