package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Doktor extends User{
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	public Doktor() {
		
	}
	public Doktor(int id, String tcno, String password, String name, String type) {
		super(id, tcno, password, name, type);
	}

	public boolean addWhour(int doctor_id , String doctor_name , String wdate ) throws SQLException {
		String query = "INSERT INTO whour" + "(doctor_id ,doctor_name , wdate) VALUES" + "(?,?,?)";
		 int key = 0; //Veri eklenip , eklenmediğini kontrol eden anahtar (yapı)
		int count=0;
		try {
			st = con.createStatement();
			rs=st.executeQuery("SELECT * FROM whour WHERE status='a' AND doctor_id= "+ doctor_id+" AND wdate='"+wdate+"' ");
			while(rs.next()) {
				count++;
				break;
			}
			if(count==0) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1,doctor_id );
				preparedStatement.setString(2,doctor_name );
				preparedStatement.setString(3, wdate );
				preparedStatement.executeUpdate();	
			}
			key = 1; //Veri eklenmiş demek
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key==1)
			return true;
		else
			return false;
	}
	public ArrayList<Whour> getWhourListesi(int doctor_id) throws SQLException {
		ArrayList<Whour> list = new ArrayList<>();
		Whour obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM whour WHERE status='a' AND doctor_id= "+doctor_id);
			while (rs.next()) {
				obj = new Whour();
				obj.setId(rs.getInt("id"));
				obj.setDoctor_id(rs.getInt("doctor_id"));
				obj.setDoctor_name(rs.getString("doctor_name"));
				obj.setStatus(rs.getString("status"));
				obj.setWdate(rs.getString("wdate")); 
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}
	public boolean deleteWhour(int id) throws SQLException {
		String query = "DELETE FROM whour WHERE id=?";
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
}
