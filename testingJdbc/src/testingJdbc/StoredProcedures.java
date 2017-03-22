package testingJdbc;
import java.sql.*;

/**
 * Test calling stored procedure with IN parameters
 *  
 *
 */

public class StoredProcedures {



	public static void main(String[] args) throws Exception {

		Connection myCon = null;
		CallableStatement myStat = null;

		try {
			// Get a connection to database
			myCon=DriverManager.getConnection("jdbc:mysql://localhost:3306/movies?user=root&password=" "&useSSL=false");

			String ratings = "hotel_name";
			int stars = 6;
			
			// Show star BEFORE
			System.out.println("star_score BEFORE\n");
			showStars(myCon, ratings);

			// Prepare the stored procedure call
			myStat = myCon
					.prepareCall("{call increase_star_score_for_ratings(?, ?)}");

			// Set the parameters
			myStat.setString(1, ratings);
			myStat.setDouble(2, stars);

			// Call stored procedure
			System.out.println("\n\nCalling stored procedure.  increase_stars_for_ratings('" + ratings + "', " + stars + ")");
			myStat.execute();
			System.out.println("Finished calling stored procedure");

			// Show salaries AFTER
			System.out.println("\n\nSalaries AFTER\n");
			showStars(myCon, ratings);

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myCon, myStat, null);
		}
	}

	private static void showStars(Connection myCon, String theDepartment) throws SQLException {
		PreparedStatement myStat = null;
		ResultSet myRs = null;

		try {
			// Prepare statement
			myStat = myCon
					.prepareStatement("select * from ratings where hotel=?");

			myStat.setString(1, "vargens hotel");
			
			// Execute SQL query
			myRs = myStat.executeQuery();

			// Process result set
			while (myRs.next()) {
				String hotelNamn = myRs.getString("Hotel_name");
				int stars = myRs.getInt("star_score");
				String city = myRs.getString("city");
				
				System.out.printf("%s, %s, %.2f\n", hotelNamn,  stars, city);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myStat, myRs);
		}

	}

	private static void close(Connection myCon, Statement myStat,
			ResultSet myRs) throws SQLException {
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

	private static void close(Statement myStmt, ResultSet myRs)
			throws SQLException {

		close(null, myStmt, myRs);
	}
}
