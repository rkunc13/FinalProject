package Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private Connection con;
	private Statement stmt;

	public DBConnection() {
		
	}


	public Connection getConnection() {
		return con;
	}
	
	
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
