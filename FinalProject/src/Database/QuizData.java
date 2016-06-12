package Database;



import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Quiz;
import Model.QuizHistory;

public class QuizData {

	private Connection con; 

	public QuizData(Connection con) {
		this.con = con; 
	}
	

	public void addQuiz(String name, String description, int author, String category, String tags, boolean random, boolean pages, boolean correction, int points) {
		try {
			
		    Timestamp timeStamp = new Timestamp(Calendar.getInstance().getTime().getTime());
		    tags = tags.replace(" ", "");
			PreparedStatement insertStatement = con.prepareStatement("INSERT INTO quizzes (name, description, author_id, category, tags, random_order, multiple_pages, immediate_correction, date_time, points, reported) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			insertStatement.setString(1, name);
			insertStatement.setString(2, description);
			insertStatement.setInt(3, author);
			insertStatement.setString(4, category);
			insertStatement.setString(5, tags);
			insertStatement.setBoolean(6, random);
			insertStatement.setBoolean(7, pages);
			insertStatement.setBoolean(8, correction);
			insertStatement.setTimestamp(9, timeStamp);
			insertStatement.setInt(10, points);
			insertStatement.setBoolean(11, false);
			insertStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateQuiz(String name, String description, String category, String tags, boolean random, boolean pages, boolean correction, int quiz_id) {
		try {
			tags = tags.replace(" ", "");
			PreparedStatement updateStatement = con.prepareStatement("UPDATE quizzes SET name = ?, description = ?, category = ?, tags = ?, random_order = ?, multiple_pages = ?, immediate_correction = ? WHERE quiz_id = ?");
			updateStatement.setString(1, name);
			updateStatement.setString(2, description);
			updateStatement.setString(3, category);
			updateStatement.setString(4, tags);
			updateStatement.setBoolean(5, random);
			updateStatement.setBoolean(6, pages);
			updateStatement.setBoolean(7, correction);
			updateStatement.setInt(8, quiz_id);
			updateStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public int getIDByName(String name) {
		try {
			PreparedStatement selectStatemet = con.prepareStatement("SELECT * FROM quizzes WHERE name = ?");
			selectStatemet.setString(1, name);
			ResultSet rs = selectStatemet.executeQuery();
			rs.next();
			return rs.getInt("quiz_id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public String getQuizNameByID(int id) {
		try {
			ResultSet rs =  con.createStatement().executeQuery("SELECT * FROM quizzes WHERE quiz_id = \'" + id + "\'");
			if (rs.next()) {
				return rs.getString("name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return "";
	}
	
	

	public List<Quiz> getQuizzes() {

        List<Quiz> q = new ArrayList<Quiz>();
		
		return q;
	}
	
	
public List<Quiz> searchQuizzes(String name, String category, String tag) {
		
        List<Quiz> q = new ArrayList<Quiz>();
		
		return q;
	}
	
	public int numTimesTaken() {
		int count = 0;
		return count;
	}
	
	public double avgQuizScore() {
		double avrg = 0;
		return avrg;
	}
	
	public int[] quizRange() {
		int[] res = new int[2];
		return res;
	}
	
	
	public List<Quiz> getPopularQuizzes(){
		List<Quiz> q = new ArrayList<Quiz>();
		
		return q;
	}
	
	public void updateQuizPoints() {
		
	}
	
	public int getQuizPoints() {
		return 0;
	}
	
	public String convertToPercStr(int score, int total) {
		return "converted";
	}
	
	
	public int calculateRating() {
		
		return 1;
	}
	

	public int addQuizResult() {
		return 1;
	}
	
	
	public List<QuizHistory> getQuizHistory(int quiz_id) {
		
		List<QuizHistory> history = new ArrayList<QuizHistory>();
		
		return history;
	}
	
	public boolean checkQuizReported(int quiz_id){
		
		return true;
	}
	
	public int reportQuiz(int quiz_id){
		return 1;
	}
	
	
	public boolean authorAchievement(int quiz_id) {
		return true;
	}
	
	public boolean quizTakerAchievement(int quiz_id) {
		return true;
	}
	
	public boolean practiceMakesPerfect(int quiz_id) {
		int best = 0;
		return true;
	}
	

	public boolean iAmGreatestAchievement(int quiz_id) {
		int high = 0;
		
		return true;
	}
	
	
	public List<String> getAchievements() {
		List<String> s = new ArrayList<String>();
		return s;
	}

	public List<Quiz> getMostRecentlyCreatedQuizzes(){
		List<Quiz> q = new List<Quiz>();
		
		return q;
	}
	
	public boolean contains(int quiz_id) {
		
		return true;
	}
	
	
	public boolean quizExists(int quiz_id) {
		
		return true;
	}
	
	
	
	
}
