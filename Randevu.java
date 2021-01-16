package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Randevu {
	private int id,doctor_id,hasta_id; 
	private String doctor_name,hasta_name,app_date;
	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	public Randevu(int id, int doctor_id, int hasta_id, String doctor_name, String hasta_name, String app_date) {
		super();
		this.id = id;
		this.doctor_id = doctor_id;
		this.hasta_id = hasta_id;
		this.doctor_name = doctor_name;
		this.hasta_name = hasta_name;
		this.app_date = app_date;
	}
	public Randevu() {}
	
	public ArrayList<Randevu> getHastaList(int hasta_id) throws SQLException {
		ArrayList<Randevu> list = new ArrayList<>();
		Randevu obj;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM randevu WHERE hasta_id =" + hasta_id);
			while (rs.next()) {
				obj = new Randevu();
				obj.setId(rs.getInt("id"));
				obj.setDoctor_id(rs.getInt("doctor_id"));
				obj.setDoctor_name(rs.getString("doctor_name"));
				obj.setHasta_id(rs.getInt("hasta_id"));
				obj.setHasta_name(rs.getString("hasta_name"));
				obj.setApp_date(rs.getString("app_date"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
			con.close();
		}
		return list;

	}
	
	public ArrayList<Randevu> getDoktorList(int doctor_id) throws SQLException {
		ArrayList<Randevu> list = new ArrayList<>();
		Randevu obj;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM randevu WHERE doctor_id =" + doctor_id);
			while (rs.next()) {
				obj = new Randevu();
				obj.setId(rs.getInt("id"));
				obj.setDoctor_id(rs.getInt("doctor_id"));
				obj.setDoctor_name(rs.getString("doctor_name"));
				obj.setHasta_id(rs.getInt("hasta_id"));
				obj.setHasta_name(rs.getString("hata_name"));
				obj.setApp_date(rs.getString("app_date"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
			con.close();
		}
		return list;

	}
	public boolean deleteRandevu(int id) throws SQLException {
		String query = "DELETE FROM randevu WHERE id=?";
		boolean key = false;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			key = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else
			return false;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}
	public int getHasta_id() {
		return hasta_id;
	}
	public void setHasta_id(int hasta_id) {
		this.hasta_id = hasta_id;
	}
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public String getHasta_name() {
		return hasta_name;
	}
	public void setHasta_name(String hasta_name) {
		this.hasta_name = hasta_name;
	}
	public String getApp_date() {
		return app_date;
	}
	public void setApp_date(String app_date) {
		this.app_date = app_date;
	}
	
}
