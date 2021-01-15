package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import Helper.*;

public class Hasta extends User  {
	Statement st = null;
	ResultSet rs = null;
	Connection con =conn.connDb();
	PreparedStatement preparedStatement = null;
	

	public Hasta() {
	super();	
	}

	public Hasta(int id,String tcno, String password, String name,String type) {
		super(id, tcno, password, name, type);
	}

	public boolean register(String tcno ,String password, String name ) throws SQLException {
		String query = "INSERT INTO user" + "(tcno ,password ,name,type) VALUES" + "(?,?,?,?)";
		 int key = 0;
		boolean dublicate=false;
		try {
			st = con.createStatement();
			rs=st.executeQuery("SELECT * FROM user WHERE tcno = '"+tcno+"' ");
			while(rs.next()) {
				dublicate=true;
				Helper.showMsg("Girmiş olduğunuz TC no zaten sistemde kayıtlı!!!");
				break;
			}
			if(!dublicate) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1,tcno);
				preparedStatement.setString(2,password);
				preparedStatement.setString(3,name);
				preparedStatement.setString(4,"Hasta");
				preparedStatement.executeUpdate();
				key = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key==1)
			return true;
		else
			return false;
	}
	
	public boolean addRandevu(int doctor_id, int hasta_id, String doctor_name, String hasta_name, String app_date) throws SQLException {
		String query = "INSERT INTO randevu" + "(doctor_id,doctor_name,hasta_id,hasta_name,app_date) VALUES" + "(?,?,?,?,?)";
		 int key = 0;
		try {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1,doctor_id);
				preparedStatement.setString(2,doctor_name);
				preparedStatement.setInt(3,hasta_id);
				preparedStatement.setString(4,hasta_name);
				preparedStatement.setString(5,app_date);
				preparedStatement.executeUpdate();
				key = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key==1)
			return true;
		else
			return false;
	}
	
	public boolean updateWhourStatus(int doctor_id,String wdate) throws SQLException {
		String query ="UPDATE whour SET status = ? WHERE doctor_id = ? AND wdate = ?";
		 int key = 0;
		try {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1,"p");
				preparedStatement.setInt(2,doctor_id);
				preparedStatement.setString(3,wdate);
				preparedStatement.executeUpdate();
				key = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key==1)
			return true;
		else
			return false;
	}
}
