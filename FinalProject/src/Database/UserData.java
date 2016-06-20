package Database;



import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import Model.Quiz;
import Model.QuizHistory;

public class UserData {
	private Connection con; 
	private ResultSet rs; 
	
	public UserData(Connection con) {
		this.con = con; 
	}
	
}
