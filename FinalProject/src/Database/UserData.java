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

	public boolean containsUser(String email) {
		Statement stmt; 
		try {
		
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \'" + email + "\'");
			return (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; 
	}
	
	public boolean hasUsers(){

		try {
			rs = con.createStatement().executeQuery("SELECT * FROM users");
			return rs.next();				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Integer> findUsers(int userId, String info) {
		ArrayList<Integer> possibleUsers = new ArrayList<Integer>();
		try {
			rs = con.createStatement().executeQuery("SELECT * FROM users WHERE email LIKE \'%" + info + "%\' OR name LIKE \'%" + info + "%\'");
			while (rs.next()) 
				if (rs.getInt("user_id") != userId) 
					possibleUsers.add(rs.getInt("user_id"));
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return possibleUsers;
	}
	
	public boolean isAdmin(String email) {
		try {
			rs = con.createStatement().executeQuery("SELECT * FROM users WHERE email = \'" + email + "\'");
			return rs.next() ? rs.getBoolean("admin_privilege") : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; 
	}

	public boolean checkPassword(String email, String password) {
		try {
			PreparedStatement prepareStatement = con.prepareStatement("SELECT * FROM users WHERE email = ?");
			prepareStatement.setString(1, email);										
			rs = prepareStatement.executeQuery();
			if (rs.next()) {
				password +=  rs.getString("salt"); 
				MessageDigest md = MessageDigest.getInstance("SHA");            
				password = UserData.hexToString(md.digest(password.getBytes()));				
				return password.equals(rs.getString("password"));				
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; 
	}
	
	
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			if ((val = val & 0xff) <16) 
				buff.append('0'); 
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	public void addUser(String email, String password, String name) {
		try {
			PreparedStatement prepareStatement = con.prepareStatement("INSERT INTO users (email, password, salt, name, admin_privilege) VALUES(?, ?, ?, ?, ?)");
			prepareStatement.setString(1, email);
			prepareStatement.setString(4, name);
			
			final Random r = new SecureRandom();
			byte[] saltBytes = new byte[32];			
			r.nextBytes(saltBytes);					
			String salt = UserData.hexToString(saltBytes);
			prepareStatement.setString(3, salt);
			
			MessageDigest md = MessageDigest.getInstance("SHA");
			password = password + salt; 									
			password = UserData.hexToString(md.digest(password.getBytes()));			
			prepareStatement.setString(2, password);
			prepareStatement.setBoolean(5, false);
			prepareStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addAdmin(String email, String password, String name) {
		try {
			
			PreparedStatement prepareStatement = con.prepareStatement("INSERT INTO users (email, password, salt, name, admin_privilege) VALUES(?, ?, ?, ?, ?)");
			prepareStatement.setString(1, email);
			prepareStatement.setString(4, name);
			
			final Random r = new SecureRandom();
			byte[] saltBytes = new byte[32];			
			r.nextBytes(saltBytes);		
			String salt = UserData.hexToString(saltBytes);
			prepareStatement.setString(3, salt);
			
			MessageDigest md = MessageDigest.getInstance("SHA");
			password +=  salt; 									
			password = UserData.hexToString(md.digest(password.getBytes()));					
			prepareStatement.setString(2, password);
			prepareStatement.setBoolean(5, true);
			prepareStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getIDByEmail(String email) {
		int toreturn = -1;
		try {
			rs = con.createStatement().executeQuery("SELECT * FROM users WHERE email = \'" + email + "\'");
			toreturn =  rs.next() ? rs.getInt("user_id") : -1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toreturn;
	}
	
	public String getEmailByID(int id) { 
		String toreturn = null;
		try {
			rs = con.createStatement().executeQuery("SELECT * FROM users WHERE user_id = \'" + id + "\'");
			toreturn =  rs.next() ? rs.getString("email") : null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toreturn;
	}
	
	public String getNameByID(int user_id) {
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM users WHERE user_id = \'" + user_id + "\'");
			return rs.next() ? rs.getString("name") : null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
		

	public ArrayList<Quiz> getAuthoredQuizzes(int user_id) {
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quizzes WHERE author_id = " + user_id);
			while (rs.next()) {
				quizList.add(new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"), rs.getBoolean("reported"), rs.getString("category"), rs.getString("tags")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizList;
	}
	

	public int getTopScore(int user_id, int quiz_id) {
		int highScore = -1;
		
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quiz_history WHERE user_id = " + user_id + " AND quiz_id = " + quiz_id);
			
			while (rs.next()) 
				highScore = rs.getInt("score") > highScore ? rs.getInt("score") : highScore;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return highScore;
	}
}
