package testingJdbc;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.jdbc.Statement;

public class GetGKey {

	public static void main(String[] args) {


		Connection myConn = null;
		PreparedStatement myStat = null;
		final String insert = "INSERT INTO hotel (Hotel_Name, address, city, phoneNumber, web_adress, Email) VALUES (?,?,?,?,?,?)";

		try {
			// Get a connection to database
			myConn = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/movies?user=root&password=Bananer123&useSSL=false");

			// Set the parameters
			myStat = myConn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
		
			myStat.setString(1, "Hotell P�f�geln");
			myStat.setString(2, "Pilgatan 43");
			myStat.setString(3, "S�dert�lje");
			myStat.setInt(4, 5);
			myStat.setString(5, "www.pafageln.nu");
			myStat.setString(6, "info@pafageln.nu");

			myStat.executeUpdate();

			// Get key
			ResultSet rs = myStat.getGeneratedKeys();
			if (rs.next()) {
				
				int autoGeneratedID = rs.getInt(1);

				System.out.println("Auto Generated Primary Key " + autoGeneratedID);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}