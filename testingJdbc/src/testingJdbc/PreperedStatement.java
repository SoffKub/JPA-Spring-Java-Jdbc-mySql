package testingJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreperedStatement {

	public static void main(String[] args) throws SQLException {

		
		Connection myCon = null;
		PreparedStatement myStat = null;
		ResultSet myRs = null;
		
	try{
		//1. Connection to database
		Connection mycon=DriverManager.getConnection("jdbc:mysql://localhost:3306/movies?user=root&password=" "&useSSL=false");
				
				
		//2. Create a statement
		myStat= mycon.prepareStatement("select * from ratings where star_score <? and hotel_name =?");
		

		// 3. Set the parameters
		myStat.setDouble(1,3);
		myStat.setString(2, "Vargens hotell");
		
		// 4. Execute SQL query
		myRs = myStat.executeQuery();
		
		// 5. Display the result set
		display(myRs);
	

		System.out.println("\n\nReuse the prepared statement:  star > 3,  hotel = vargens hotel");
		
		// 6. Set the parameters
		myStat.setDouble(1, 5);
		myStat.setString(2, "Vargens hotell");
		
		// 7. Execute SQL query
		myRs = myStat.executeQuery();
		
		// 8. Display the result set
		display(myRs);


	}
	catch (Exception exc) {
		exc.printStackTrace();
	}
	finally {
		if (myRs != null) {
			myRs.close();
		}
		
		if (myStat != null) {
			myStat.close();
		}
		
		if (myCon != null) {
			myCon.close();
		}
	}
}

private static void display(ResultSet myRs) throws SQLException {
	while (myRs.next()) {
		String city = myRs.getString("city");
		
		int star = myRs.getInt("star_score");
		String hotel = myRs.getString("hotel_name");
		
		System.out.printf("%s, %s, %.2f, %s\n", city,  star, hotel);
		}
	}
}

	

