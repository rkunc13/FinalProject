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
	

	public Quiz getQuizByID(int quiz_id) {
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quizzes WHERE quiz_id = \'" + quiz_id + "\'");
			if(rs.next())
				return new Quiz(quiz_id, rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"), rs.getBoolean("reported"), rs.getString("category"), rs.getString("tags"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public ArrayList<Quiz> getQuizzes() {
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quizzes ORDER BY name");
			while (rs.next()) 
				quizList.add(new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"), rs.getBoolean("reported"), rs.getString("category"), rs.getString("tags")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizList;
	}
	
	
public ArrayList<Quiz> searchQuizzes(String name, String category, String tag) {
		
		StringBuilder result = new StringBuilder("SELECT * FROM quizzes");
		if (!name.isEmpty() || !category.isEmpty() || !tag.isEmpty()) result.append(" WHERE"); 
		result.append(!name.isEmpty() || !category.isEmpty() || !tag.isEmpty() ? " WHERE" : "");
		
		if (!name.isEmpty()) {
            result.append(" name LIKE \'%" + name + "%\'");
            result.append(!category.isEmpty() || !tag.isEmpty() ? " AND" : "");
        }
        if (!category.equals("")) {
            result.append(" category LIKE \'%" + category + "%\'");
            result.append(!tag.equals("") ? " AND" : "");
        }
        
        result.append(!tag.equals("") ? " tags LIKE \'%" + tag + "%\'" : "");
        
        
        ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		try {
			ResultSet rs = con.createStatement().executeQuery(result.toString());
			while (rs.next()) 
				quizList.add(new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"), rs.getBoolean("reported"), rs.getString("category"), rs.getString("tags")));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizList;
	}
	
	public int numTimesTaken(int quiz_id) {
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \'" + quiz_id + "\'");
			rs.last(); 
			return rs.getRow(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public double avgQuizScore(int quiz_id) {
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \'" + quiz_id + "\'");
			if (rs.next()) {
				double sum = rs.getInt("score");
				while (rs.next()) 
					sum += rs.getInt("score");
				
				int count = numTimesTaken(quiz_id);
				return sum / (double) count; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int quizRange(int quiz_id) {
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \'" + quiz_id + "\'");
			if (rs.next()) {
				int high = rs.getInt("score");
				int low = high;
				while (rs.next()) {
					int sc = rs.getInt("score");
					high = sc > high ? sc : high;
					low = sc < low ? sc : low;
				}
				return high - low; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	public ArrayList<Quiz> getPopularQuizzes(){
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		int [] topFrequencies = new int[3];
		Map<Integer, Integer> frequencyes = new HashMap<Integer, Integer>();
		try{
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quiz_history");
			while (rs.next()) {
				int quizId = rs.getInt("quiz_id");
				frequencyes.put(quizId , frequencyes.containsKey(quizId) ?frequencyes.get(quizId) + 1 : 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int key: frequencyes.keySet()){
			Quiz quiz = getQuizByID(key);
			if(frequencyes.get(key) > topFrequencies[0]){
				topFrequencies[2] = topFrequencies[1];
				topFrequencies[1] = topFrequencies[0];
				topFrequencies[0] = frequencyes.get(key);
				quizList.add(0, quiz);
			} else if(frequencyes.get(key) > topFrequencies[1]){
				topFrequencies[2] = topFrequencies[1];
				topFrequencies[1] = frequencyes.get(key);
				quizList.add(1, quiz);
			} else if(frequencyes.get(key) > topFrequencies[2]){
				topFrequencies[2] = frequencyes.get(key);
				quizList.add(2, quiz);
			}
		}
		return quizList;
	}
	
	public void updateQuizPoints(int quiz_id, int points) {
		try {
			PreparedStatement updateStatement = con.prepareStatement("UPDATE quizzes SET points = ? WHERE quiz_id = ?");
			updateStatement.setInt(1, points);
			updateStatement.setInt(2, quiz_id);
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getQuizPoints(int quiz_id) {
		try {
			PreparedStatement selectStatement = con.prepareStatement("SELECT * FROM quizzes WHERE quiz_id = ?");
			selectStatement.setInt(1, quiz_id);
			ResultSet rs = selectStatement.executeQuery();
			rs.next();
			return rs.getInt("points");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public String convertToPercStr(double score, double total) {
		return ""+ String.format("%.2f", (float) score / (float) total * 100) + "%";
	}
	
	
	public double calculateRating(int quiz_id) {
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \'" + quiz_id + "\'");
			if (rs.next()) {
				double sum = rs.getDouble("rating");
				double count = 1.0;
				while (rs.next()) {
					sum += rs.getDouble("rating");
					count++;
				}
				return sum / count;  
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	

	public void addQuizResult(int quiz_id, int user_id, int score, int total, int rating, String review, String name, Timestamp time_taken) {
		try {
		    Timestamp timeStamp = new Timestamp(Calendar.getInstance().getTime().getTime());
			PreparedStatement insertStmt = con.prepareStatement("INSERT INTO quiz_history (quiz_id, user_id, score, total, rating, review, name, date_time, time_taken) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			insertStmt.setInt(1, quiz_id);
			insertStmt.setInt(2, user_id);
			insertStmt.setInt(3, score);
			insertStmt.setInt(4, total);
			insertStmt.setInt(5, rating);
			insertStmt.setString(6, review);
			insertStmt.setString(7, name);
			insertStmt.setTimestamp(8, timeStamp);
			insertStmt.setTimestamp(9, time_taken);
			insertStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public boolean checkQuizReported(int quiz_id){
		try{
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quizzes WHERE quiz_id = " + quiz_id);
			return rs.next() ?rs.getBoolean("reported") : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void reportQuiz(int quiz_id){
		try{
			PreparedStatement updateStatement = con.prepareStatement("UPDATE quizzes SET reported = 1 WHERE quiz_id = ?");
			updateStatement.setInt(1, quiz_id);
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void unreportQuiz(int quiz_id){
		try{
			PreparedStatement updateStatement = con.prepareStatement("UPDATE quizzes SET reported = 0 WHERE quiz_id = ?");
			updateStatement.setInt(1, quiz_id);
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean authorAchievement(int user_id) {
		boolean achievementAdded = false;
		boolean amateur = false;
		boolean prolific = false;
		boolean prodigious = false;
		try {
			Statement selectStatement = con.createStatement();
			ResultSet rs = selectStatement.executeQuery("SELECT * FROM quizzes WHERE author_id = \'" + user_id + "\'");
			rs.last(); 
			int count = rs.getRow(); 
			rs = selectStatement.executeQuery("SELECT * FROM achievements WHERE user_id = \'" + user_id + "\'");
			if (rs.next()) {
				amateur = rs.getString("description").equals("Amateur Author");
				prolific = rs.getString("description").equals("Prolific Author");
				prodigious = rs.getString("description").equals("Prodigious Author");
			}
			
			PreparedStatement insertStatement;
			if (count == 1 && !amateur) {
			    insertStatement = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				insertStatement.setInt(1, user_id);
				insertStatement.setString(2, "Amateur Author");
				insertStatement.executeUpdate();
				achievementAdded = true;
			}
			if (count == 5 && !prolific) {
			    insertStatement = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				insertStatement.setInt(1, user_id);
				insertStatement.setString(2, "Prolific Author");
				insertStatement.executeUpdate();
				achievementAdded = true;
			}
			if (count == 10 && !prodigious) {
				insertStatement = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				insertStatement.setInt(1, user_id);
				insertStatement.setString(2, "Prodigious Author");
				insertStatement.executeUpdate();
				achievementAdded = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return achievementAdded;
	}
	
	public boolean quizTakerAchievement(int user_id) {
		boolean quizMachine = false;
		try {
			Statement selectStatement = con.createStatement();
			ResultSet rs = selectStatement.executeQuery("SELECT * FROM quiz_history WHERE user_id = \'" + user_id + "\'");
			rs.last(); 
			int count = rs.getRow(); 
			rs = selectStatement.executeQuery("SELECT * FROM achievements WHERE user_id = \'" + user_id + "\'");
			
			quizMachine = rs.next() ?  rs.getString("description").equals("Quiz Machine") : quizMachine;
			
			if ( !quizMachine  && count == 10) {
				PreparedStatement insertStatement = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				insertStatement.setInt(1, user_id);
				insertStatement.setString(2, "Quiz Machine");
				insertStatement.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean practiceMakesPerfect(int user_id) {
		
		String achievement = "Practice Makes Perfect";
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM achievements WHERE user_id = \'" + user_id + "\'");
			boolean temp = false;
			while(rs.next()) 
				temp = rs.getString("description").equals(achievement) || temp;
			
			if (!temp) {
				PreparedStatement insertstatement = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
				insertstatement.setInt(1, user_id);
				insertstatement.setString(2, achievement);
				insertstatement.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	public boolean iAmGreatestAchievement(int user_id, int quiz_id, int score) {
		int highest = 0;
		String achievement = "I am the Greatest" + quiz_id;
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM achievements WHERE user_id = \'" + user_id + "\'");
			boolean temp =  rs.next() ? rs.getString("description").equals(achievement) : false;
			if (!temp) {
				rs = statement.executeQuery("SELECT * FROM quiz_history WHERE quiz_id = \'" + quiz_id + "\'");
				while(rs.next())
					highest = Integer.max(rs.getInt("score"), highest);
				
				
				if (score >= highest) {
					PreparedStatement prepareStatement = con.prepareStatement("INSERT INTO achievements (user_id, description) VALUES (?, ?)");
					prepareStatement.setInt(1, user_id);
					prepareStatement.setString(2, achievement);
					prepareStatement.executeUpdate();
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return false;
	}
	
	
	public ArrayList<String> getAchievements(int user_id) {
		ArrayList<String> achievements = new ArrayList<String>();
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM achievements WHERE user_id = \'" + user_id + "\'");
			while (rs.next()) 
				achievements.add(rs.getString("description"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return achievements;
	}

	public ArrayList<Quiz> getMostRecentlyCreatedQuizzes(){
		ArrayList<Quiz> recentQuizzes = new ArrayList<Quiz>();
		try{
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quizzes ORDER BY date_time DESC");
			while (rs.next()) {
				recentQuizzes.add(new Quiz(rs.getInt("quiz_id"), rs.getString("name"), rs.getString("description"), rs.getInt("author_id"), rs.getBoolean("random_order"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"), rs.getTimestamp("date_time"), rs.getInt("points"), rs.getBoolean("reported"), rs.getString("category"), rs.getString("tags")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recentQuizzes;
	}
	
	public boolean contains(String quiz_name) {
		try {
			return con.createStatement().executeQuery("SELECT * FROM quizzes WHERE name = \'" + quiz_name + "\'").next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean quizExists(int quiz_id) {
		try {
			return con.createStatement().executeQuery("SELECT * FROM quizzes WHERE quiz_id = \'" + quiz_id + "\'").next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
}
