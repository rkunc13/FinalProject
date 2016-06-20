package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import Model.Quiz;

public class FriendData {

	private Connection con;
	private ResultSet rs;

	public FriendData(Connection con) {
		this.con = con;
	}
	
}
