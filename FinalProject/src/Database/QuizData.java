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


	public QuizData() {
	}
	

	public void addQuiz(String name) {
		
	}
	
	public void updateQuiz(String names) {
		
	}
	

	public int getIDByName() {
		return 0;
	}
	
	public String getQuizNameByID() {
		return "Quiz";
	}
	

	public Quiz getQuizByID(int quiz_id) {
		Quiz quiz = null;
		return quiz;
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
	
	
	public List<QuizHistory> getQuizHistory() {
		
		List<QuizHistory> history = new List<QuizHistory>();
		
		return history;
	}
	
	public boolean checkQuizReported(){
		
		return true;
	}
	
	public int reportQuiz(int quiz_id){
		return 1;
	}
	
	
	public boolean authorAchievement() {
		return true;
	}
	
	public boolean quizTakerAchievement() {
		return true;
	}
	
	public boolean practiceMakesPerfect() {
		int best = 0;
		return true;
	}
	

	public boolean iAmGreatestAchievement() {
		int high = 0;
		
		return true;
	}
	
	
	public List<String> getAchievements() {
		List<String> s = new List<String>();
		return s;
	}

	public List<Quiz> getMostRecentlyCreatedQuizzes(){
		List<Quiz> q = new List<Quiz>();
		
		return q;
	}
	
	public boolean contains() {
		
		return true;
	}
	
	
	public boolean quizExists() {
		
		return true;
	}
	
	
	
	
}
