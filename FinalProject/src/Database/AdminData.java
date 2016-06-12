package Database;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Quiz;
import Model.User;

public class AdminData {


	private ResultSet set;
	private Connection c; 

	public AdminData() {
	}
	
	public void addAnnouncement(){
		
	}
	
	public List<String> getAnnouncements(){
		List<String> s = new ArrayList<String>();
		return s;
	}
	
	public void removeAnnouncement(){
		
	}

	public List<User> getUsers(){
		List<User> s = new ArrayList<User>();
		return s;
	}
	
	public List<User> getAdmins(){
		List<User> s = new ArrayList<User>();
		return s;
	}
	
	public List<User> getNonAdmins(){
		List<User> s = new ArrayList<User>();
		return s;
	}
	
	
	public void removeUserAccount(){
		int user_id;
		Statement s;
		s.executeUpdate("DELETE FROM friends WHERE user_id1 = " + user_id);
		s.executeUpdate("DELETE FROM friends WHERE user_id2 = " + user_id);
		s.executeUpdate("DELETE FROM friend_requests WHERE from_user_id = " + user_id);
		s.executeUpdate("DELETE FROM friend_requests WHERE to_user_id = " + user_id);
		s.executeUpdate("DELETE FROM achievements WHERE user_id = " + user_id);
		s.executeUpdate("DELETE FROM challenges WHERE sender_id = " + user_id);
		s.executeUpdate("DELETE FROM challenges WHERE receiver_id = " + user_id);
		
	}

	public void promoteToAdmin(){
		
	}
	
	public List<Quiz> getQuizzes(){
		List<Quiz> quizes = new ArrayList<Quiz>();
		return quizes;
	}
	
	public List<Quiz> getReportedQuizzes(int quiz_id){
		List<Quiz> quizes = new ArrayList<Quiz>();
		return quizes;
		
	}
	
	public void historyClearForQuiz(){
		
	}
	
	
	public void questionClearForQuiz() {
	}
	
	
	public void removeQuiz(){
	}
	
	public int getNumberOfQuizzes(List<Integer> quiz_ids){
		return 1;
	}
	
	
}
