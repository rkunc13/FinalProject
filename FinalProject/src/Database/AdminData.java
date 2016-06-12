package Database;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Quiz;
import Model.User;

public class AdminData {


	private Connection con; 
	private ResultSet rs;

	public AdminData(Connection con) {
		this.con = con;
	}
	
	public void addAnnouncement(String announcement){
		try {
			PreparedStatement query = con.prepareStatement("INSERT INTO announcements (description) VALUES(?)");
			query.setString(1, announcement);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getAnnouncements(){
		ArrayList<String> announcements = new ArrayList<String>();
		try {
			rs = con.prepareStatement("SELECT * FROM announcements").executeQuery();
			while(rs.next())
				announcements.add(rs.getString("description"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return announcements;
	}
	
	public void removeAnnouncement(int announcement_id){
		try {
			con.prepareStatement("DELETE FROM announcements WHERE announcement_id = " + announcement_id).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<User> getUsers(){
		ArrayList<User> users = new ArrayList<User>();
		try {
			PreparedStatement query = con.prepareStatement("SELECT * FROM users");
			rs = query.executeQuery();
			while(rs.next())
				users.add(new User(rs.getInt("user_id"), rs.getString("email"), rs.getString("name"), rs.getBoolean("admin_privilege")));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public ArrayList<User> getAdmins(){
		ArrayList<User> users = new ArrayList<User>();
		try {
			PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM users WHERE admin_privilege = 1");
			rs = prepStmt.executeQuery();
			while(rs.next()){
				users.add(new User(rs.getInt("user_id"), rs.getString("email"), rs.getString("name"), rs.getBoolean("admin_privilege")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public ArrayList<User> getNonAdmins(){
		ArrayList<User> users = new ArrayList<User>();
		try {
			
			rs = con.prepareStatement("SELECT * FROM users WHERE admin_privilege = 0").executeQuery();
			while(rs.next()){
				users.add(new User(rs.getInt("user_id"), rs.getString("email"), rs.getString("name"), rs.getBoolean("admin_privilege")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	
	public void removeUserAccount(int user_id){
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM users WHERE user_id = " + user_id);
			ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes WHERE author_id = " + user_id);
			while (rs.next()) {
				historyClearForQuiz(rs.getInt("quiz_id"));
				questionClearForQuiz(rs.getInt("quiz_id"));
				removeQuiz(rs.getInt("quiz_id"));
			}
			stmt.executeUpdate("DELETE FROM friends WHERE user_id1 = " + user_id);
			stmt.executeUpdate("DELETE FROM friends WHERE user_id2 = " + user_id);
			stmt.executeUpdate("DELETE FROM friend_requests WHERE from_user_id = " + user_id);
			stmt.executeUpdate("DELETE FROM friend_requests WHERE to_user_id = " + user_id);
			stmt.executeUpdate("DELETE FROM achievements WHERE user_id = " + user_id);
			stmt.executeUpdate("DELETE FROM challenges WHERE sender_id = " + user_id);
			stmt.executeUpdate("DELETE FROM challenges WHERE receiver_id = " + user_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void promoteToAdmin(int user_id){
		try {
			con.prepareStatement("UPDATE users SET admin_privilege = true WHERE user_id = " + user_id).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Quiz> getQuizzes(){
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		try {
			con.prepareStatement("SELECT * FROM quizzes").executeQuery();
			while(rs.next())
				quizzes.add(new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"), rs.getBoolean("reported"), rs.getString("category"), rs.getString("tags")));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quizzes;
	}
	
	public ArrayList<Quiz> getReportedQuizzes(){
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		try {
			con.prepareStatement("SELECT * FROM quizzes WHERE reported = 1").executeQuery();
			while(rs.next()){
				Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"), rs.getBoolean("reported"), rs.getString("category"), rs.getString("tags"));
				quizzes.add(quiz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quizzes;
	}
	
	public void historyClearForQuiz(int quiz_id){
		try {
			con.prepareStatement("DELETE FROM quiz_history WHERE quiz_id = " + quiz_id).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void questionClearForQuiz(int quiz_id) {
		try {
			con.prepareStatement("DELETE FROM questions WHERE quiz_id = " + quiz_id).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void removeQuiz(int quiz_id){
		try {
			con.prepareStatement("DELETE FROM quizzes WHERE quiz_id = " + quiz_id).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getNumberOfQuizzes(){
		int count = 0;
		try {
			con.prepareStatement("SELECT * FROM quiz_history").executeQuery();
			while(rs.next())
				count++;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
}
