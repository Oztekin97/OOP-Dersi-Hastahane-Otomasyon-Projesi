package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;



import Model.*;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Point;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import Helper.*;
import javax.swing.JComboBox;

public class BashekimGUI extends JFrame {
	static Bashekim bashekim = new Bashekim();
	Clinic clinic = new Clinic();
	private JPanel w_pane;
	private JTextField fld_Name;
	private JTextField fld_Tcno;
	private JTextField fld_Sifre;
	private JTextField fld_doktorID;
	private DefaultTableModel doktormodel = null;
	private Object[] doktorData = null;
	private JTable table_doktor;
	private JTable table_clinic;
	private JTextField fld_clinicName;
	private DefaultTableModel clinicModel = null;
	private Object[] clinicData = null;
	private JPopupMenu clinicMenu;
	private JTable table_worker;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BashekimGUI frame = new BashekimGUI(bashekim);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public BashekimGUI(Bashekim bashekim) throws SQLException {
		// doctor Model
		doktormodel = new DefaultTableModel();
		Object[] colldoktorname = new Object[4];
		colldoktorname[0] = "ID";
		colldoktorname[1] = "AD SOYAD";
		colldoktorname[2] = "TC NO";
		colldoktorname[3] = "ŞİFRE";
		
		doktormodel.setColumnIdentifiers(colldoktorname);
		doktorData = new Object[4];
		for (int i = 0; i < bashekim.getDoktorListesi().size(); i++) {
			doktorData[0] = bashekim.getDoktorListesi().get(i).getId();
			doktorData[1] = bashekim.getDoktorListesi().get(i).getName();
			doktorData[2] = bashekim.getDoktorListesi().get(i).getTcno();
			doktorData[3] = bashekim.getDoktorListesi().get(i).getPassword();
			doktormodel.addRow(doktorData);

		}

		// clinic Model
		clinicModel = new DefaultTableModel();
		Object[] colClinic = new Object[2];
		colClinic[0] = "ID";
		colClinic[1] = "Poliklinik Adı";
		clinicModel.setColumnIdentifiers(colClinic);
		clinicData = new Object[2];
		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
		
		//worker Model
		DefaultTableModel workerModel=new DefaultTableModel();
		Object[] colWorker=new Object[2];
		colWorker[0]="ID";
		colWorker[1]="Ad Soyad";
		workerModel.setColumnIdentifiers(colWorker);
		Object[] workerData=new Object[2];
		
		
		setResizable(false);
		setTitle("Hastahane Yönetim Sistemine Giriş");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 493, 468);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hoşgeldiniz "+bashekim.getName());
		lblNewLabel.setBounds(10, 33, 201, 33);
		lblNewLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 17));
		w_pane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Çıkış Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BashekimYetki by=new BashekimYetki();
				String uyar="Sayın Dr. "+ bashekim.getName() + by.loginMsg();
				JOptionPane.showMessageDialog(null, uyar, "Bilgilendirme", JOptionPane.INFORMATION_MESSAGE);
				String son="Sağlıklı günler dileriz sayın Dr. "+bashekim.getName();
				JOptionPane.showMessageDialog(null, son, "Mesaj", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		});
		btnNewButton.setBounds(359, 40, 89, 23);
		btnNewButton.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 12));
		w_pane.add(btnNewButton);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 77, 457, 341);
		w_pane.add(w_tab);

		JPanel w_doctor = new JPanel();
		w_tab.addTab("Doktor Yönetimi", null, w_doctor, null);
		w_doctor.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Ad Soyad");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblNewLabel_1.setBounds(318, 9, 68, 14);
		w_doctor.add(lblNewLabel_1);

		fld_Name = new JTextField();
		fld_Name.setBounds(268, 35, 174, 27);
		w_doctor.add(fld_Name);
		fld_Name.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("TC kimlik no");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblNewLabel_2.setBounds(318, 69, 89, 14);
		w_doctor.add(lblNewLabel_2);

		fld_Tcno = new JTextField();
		fld_Tcno.setBounds(268, 86, 174, 27);
		w_doctor.add(fld_Tcno);
		fld_Tcno.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Şifre");
		lblNewLabel_3.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblNewLabel_3.setBounds(340, 124, 46, 14);
		w_doctor.add(lblNewLabel_3);

		fld_Sifre = new JTextField();
		fld_Sifre.setBounds(268, 143, 174, 27);
		w_doctor.add(fld_Sifre);
		fld_Sifre.setColumns(10);

		JButton btn_adddoktor = new JButton("Ekle");
		btn_adddoktor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_Name.getText().length() == 0 || fld_Tcno.getText().length() == 0|| fld_Sifre.getText().length() == 0) {
					Helper.showMsg("doldur");
				} else {
					try {
						boolean control = bashekim.addDoktor(fld_Tcno.getText(), fld_Sifre.getText(),fld_Name.getText());
						if (control) {
							Helper.showMsg("success");
							fld_Tcno.setText(null);
							fld_Sifre.setText(null);
							fld_Name.setText(null);
							updateDoktorModel();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_adddoktor.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		btn_adddoktor.setBounds(322, 181, 89, 23);
		w_doctor.add(btn_adddoktor);

		JLabel lblNewLabel_4 = new JLabel("Kullanıcı id");
		lblNewLabel_4.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblNewLabel_4.setBounds(318, 215, 78, 14);
		w_doctor.add(lblNewLabel_4);

		fld_doktorID = new JTextField();
		fld_doktorID.setBounds(274, 241, 154, 23);
		w_doctor.add(fld_doktorID);
		fld_doktorID.setColumns(10);

		JButton btn_deldoktor = new JButton("Sil");
		btn_deldoktor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_doktorID.getText().length() == 0) {
					Helper.showMsg("Geçerli bir doktor seçmelisiniz!!!");
				} else {
					if (Helper.confirm("sure")) {
						int selectID = Integer.parseInt(fld_doktorID.getText());
						try {
							boolean control = bashekim.deleteDoktor(selectID);
							if (control) {
								Helper.showMsg("success");
								fld_doktorID.setText(null);
								updateDoktorModel();
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btn_deldoktor.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		btn_deldoktor.setBounds(322, 279, 89, 23);
		w_doctor.add(btn_deldoktor);

		JScrollPane w_scrolldoktor = new JScrollPane();
		w_scrolldoktor.setBounds(10, 10, 248, 292);
		w_doctor.add(w_scrolldoktor);

		table_doktor = new JTable(doktormodel);
		w_scrolldoktor.setViewportView(table_doktor);

		table_doktor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_doktorID.setText(table_doktor.getValueAt(table_doktor.getSelectedRow(), 0).toString());
				} catch (Exception ex) {

				}
			}
		});
		table_doktor.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int SelectID = Integer.parseInt(table_doktor.getValueAt(table_doktor.getSelectedRow(), 0).toString());
					String SelectName = table_doktor.getValueAt(table_doktor.getSelectedRow(), 1).toString();
					String SelectTcno = table_doktor.getValueAt(table_doktor.getSelectedRow(), 2).toString();
					String SelectSifre = table_doktor.getValueAt(table_doktor.getSelectedRow(), 3).toString();
					try {
						boolean control = bashekim.updateDoktor(SelectID, SelectTcno, SelectSifre, SelectName);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		JPanel w_clinic = new JPanel();
		w_tab.addTab("Poliklinikler", null, w_clinic, null);
		w_clinic.setLayout(null);

		JScrollPane w_scroll_clinic = new JScrollPane();
		w_scroll_clinic.setBounds(10, 11, 154, 291);
		w_clinic.add(w_scroll_clinic);

		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Güncelle");
		JMenuItem deleteMenu = new JMenuItem("Sil");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);
		
        //Kliniğe sağ tıklanınca güncelleme işlemleri
		updateMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString()); 
				Clinic selectClinic = clinic.getFetch(selID);
				UpdateClinicGUI updateGUI = new UpdateClinicGUI(selectClinic);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				updateGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						try {
							updateClinicModel();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		});
		
        //Kliniğe sağ tıklanınca silme işlemleri
		deleteMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Helper.confirm("sure")) {
					int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
					try {
						if (clinic.deleteClinic(selID)) {
							Helper.showMsg("success");
							updateClinicModel();
						} else {
							Helper.showMsg("Error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});

		table_clinic = new JTable(clinicModel);
		table_clinic.setComponentPopupMenu(clinicMenu);
		table_clinic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();  //tıklanan yerin koordinatlarını alır
				int selectedRow = table_clinic.rowAtPoint(point);
				table_clinic.setRowSelectionInterval(selectedRow, selectedRow);
			}
		});

		w_scroll_clinic.setViewportView(table_clinic);

		JLabel lblNewLabel_1_1 = new JLabel("Poliklinik adı");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(186, 10, 92, 14);
		w_clinic.add(lblNewLabel_1_1);

		fld_clinicName = new JTextField();
		fld_clinicName.setColumns(10);
		fld_clinicName.setBounds(186, 35, 92, 23);
		w_clinic.add(fld_clinicName);

		JButton btn_addclinic = new JButton("Ekle");
		btn_addclinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_clinicName.getText().length() == 0) {
					Helper.showMsg("doldur");
				} else {
					try {
						if (clinic.addClinic(fld_clinicName.getText())) {
							Helper.showMsg("success");
							fld_clinicName.setText(null);
							updateClinicModel();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_addclinic.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		btn_addclinic.setBounds(186, 69, 69, 23);
		w_clinic.add(btn_addclinic);

		JScrollPane w_scrollWorker = new JScrollPane();
		w_scrollWorker.setBounds(288, 11, 154, 291);
		w_clinic.add(w_scrollWorker);
		
		table_worker = new JTable();
		w_scrollWorker.setViewportView(table_worker);
		
		JComboBox select_doktor = new JComboBox();
		select_doktor.setBounds(186, 207, 92, 22);
		for(int i=0;i<bashekim.getDoktorListesi().size();i++) {
			select_doktor.addItem(new Item(bashekim.getDoktorListesi().get(i).getId(),bashekim.getDoktorListesi().get(i).getName()));
		}
		select_doktor.addActionListener(e->{
			JComboBox c=(JComboBox) e.getSource();
			Item item=(Item) c.getSelectedItem();
		});
		w_clinic.add(select_doktor);
		
		JButton btn_addWorker = new JButton("Ekle");
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			int sel_Row=table_clinic.getSelectedRow();
            if(sel_Row>0) {
				String selClinic=table_clinic.getModel().getValueAt(sel_Row,0).toString();
				int selClinicID=Integer.parseInt(selClinic);
				Item doktorItem=(Item) select_doktor.getSelectedItem();
				try {
					boolean control=bashekim.addWorker(doktorItem.getKey(),selClinicID);
					if(control) {
						Helper.showMsg("success");
						DefaultTableModel clearModel=(DefaultTableModel) table_worker.getModel();
					    clearModel.setRowCount(0);
					    for(int i=0;i<bashekim.getClinicDoktorListesi(selClinicID).size();i++) {   
							workerData[0]=bashekim.getClinicDoktorListesi(selClinicID).get(i).getId();
							workerData[1]=bashekim.getClinicDoktorListesi(selClinicID).get(i).getName();
							workerModel.addRow(workerData);
					    }
					    table_worker.setModel(workerModel);
					}
					else {
						Helper.showMsg("error");
					}
				}catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
            else {
            	Helper.showMsg("Bir poliklinik seçmelisiniz!!!");
            }
			}
		});
		btn_addWorker.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		btn_addWorker.setBounds(196, 240, 69, 23);
		w_clinic.add(btn_addWorker);
		
		JButton btn_workerSelect = new JButton("Seç");
		btn_workerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sel_Row=table_clinic.getSelectedRow();
				if(sel_Row>0) {
					String selClinic=table_clinic.getModel().getValueAt(sel_Row,0).toString();
					int selClinicID=Integer.parseInt(selClinic);
					DefaultTableModel clearModel=(DefaultTableModel) table_worker.getModel();
				    clearModel.setRowCount(0);
				    try {
				    	  for(int i=0;i<bashekim.getClinicDoktorListesi(selClinicID).size();i++) {
								workerData[0]=bashekim.getClinicDoktorListesi(selClinicID).get(i).getId();
								workerData[1]=bashekim.getClinicDoktorListesi(selClinicID).get(i).getName();
								workerModel.addRow(workerData);
							}	
				    }catch(SQLException e1) {
				    	e1.printStackTrace();
				    }
				    table_worker.setModel(workerModel);
				}
				 else {
		            	Helper.showMsg("Bir poliklinik seçmelisiniz!!!");
		            }
			}
		});
		btn_workerSelect.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		btn_workerSelect.setBounds(186, 160, 89, 23);
		w_clinic.add(btn_workerSelect);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Poliklinik adı");
		lblNewLabel_1_1_1.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(186, 130, 92, 14);
		w_clinic.add(lblNewLabel_1_1_1);

	}

	public void updateDoktorModel() throws SQLException {
		DefaultTableModel clearmodel = (DefaultTableModel) table_doktor.getModel();
		clearmodel.setRowCount(0);
		for (int i = 0; i < bashekim.getDoktorListesi().size(); i++) {
			doktorData[0] = bashekim.getDoktorListesi().get(i).getId();
			doktorData[1] = bashekim.getDoktorListesi().get(i).getName();
			doktorData[2] = bashekim.getDoktorListesi().get(i).getTcno();
			doktorData[3] = bashekim.getDoktorListesi().get(i).getPassword();
			doktormodel.addRow(doktorData);
		}
	}

	public void updateClinicModel() throws SQLException {
		DefaultTableModel clearmodel = (DefaultTableModel) table_clinic.getModel();
		clearmodel.setRowCount(0);
		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
	}
}

