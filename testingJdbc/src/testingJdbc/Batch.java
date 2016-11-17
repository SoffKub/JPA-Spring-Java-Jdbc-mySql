package testingJdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Batch {

//	try (Connection connection = DriverManager.getConnection(...)) {
//		// Turn of auto commit
//		connection.setAutoCommit(false);
//		// Create and prepare statement
//		try (Statement statement = connection.createStatement()) {
//		// Add employees to batch
//		statement.addBatch("INSERT INTO Employee (firstName, lastName, employeeNumber)
//		VALUES ('Luke','Skywalker','1001')");
//		statement.addBatch("INSERT INTO Employee (firstName, lastName, employeeNumber)
//		VALUES ('Leia','Skywalker','1002')");
//		statement.addBatch("INSERT INTO Employee (firstName, lastName, employeeNumber)
//		VALUES ('Obi-Wan','Kenobi','1003')");
//		// Try to execute batch
//		statement.executeBatch();
//		// Commit transaction
//		connection.commit();
//		}
//		catch (SQLException e) {
//		connection.rollback();
//		throw e;
//		}
//		}
//}
}
