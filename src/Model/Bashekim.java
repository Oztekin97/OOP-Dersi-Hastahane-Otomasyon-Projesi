package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Bashekim extends User {
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Bashekim(int id, String tcno, String password, String name, String type) {
		super(id, tcno, password, name, type);
	}

	public Bashekim() {

	}

	public ArrayList<User> getDoktorListesi() throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE type='Doktor'");
			while (rs.next()) {
				obj = new User(rs.getInt("id"), rs.getString("tcno"), rs.getString("password"), rs.getString("name"),rs.getString("type"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<User> getClinicDoktorListesi(int clinic_id) throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT u.id,u.tcno,u.password,u.name,u.type FROM worker w LEFT JOIN user u ON w.user_id=u.id WHERE w.clinic_id="+ clinic_id ); //hata
			while (rs.next()) {
obj = new User(rs.getInt("u.id"), rs.getString("u.tcno"), rs.getString("u.password"), rs.getString("u.name"),rs.getString("u.type"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	public boolean addDoktor(String tcno, String password, String name) throws SQLException {
		String query = "INSERT INTO user" + "(tcno,password,name,type) VALUES" + "(?,?,?,?)";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, tcno);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, "doktor");
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

	public boolean deleteDoktor(int id) throws SQLException {
		String query = "DELETE FROM user WHERE id=?";
		boolean key = false;
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

	public boolean updateDoktor(int id, String tcno, String password, String name) throws SQLException {
		String query = "UPDATE user SET name=?,SET password=?,SET tcno=? WHERE id=?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, tcno);
			preparedStatement.setInt(4, id);
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
	  //Çalışanları ekler ve çalışanların aynı kliniğe birden fazla kez eklenmesini engeller
	public boolean addWorker(int user_id,int clinic_id) throws SQLException {
		String query = "INSERT INTO worker" + "(user_id,clinic_id) VALUES" + "(?,?)";
		boolean key = false;
		int count=0;
		try {
			st = con.createStatement();
			rs=st.executeQuery("SELECT * FROM worker WHERE clinic_id="+ clinic_id +" AND user_id="+ user_id );
			while(rs.next()) {
				count++;
			}
			if(count==0) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, user_id);
				preparedStatement.setInt(2, clinic_id);
				preparedStatement.executeUpdate();
			}
			key = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else
			return false;
	}
}
