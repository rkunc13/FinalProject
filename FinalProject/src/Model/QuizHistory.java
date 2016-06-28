package Model;

import java.sql.Timestamp;

public class QuizHistory {

	private int quizHistoryId;
	private int quizId;
	private int userId;
	private int score;
	private int total;
	private int rating;
	private String review;
	private String name;
	private Timestamp dt;

	public QuizHistory(int quizHistoryID, int quizID, int userID, int score,
			int total, int rating, String review, String name, Timestamp dt) {
		this.quizHistoryId = quizHistoryID;
		this.quizId = quizID;
		this.userId = userID;
		this.score = score;
		this.total = total;
		this.rating = rating;
		this.review = review;
		this.name = name;
		this.dt = dt;
	}

	public int getQuizHistoryId() {
		return quizHistoryId;
	}

	public int getQuizId() {
		return quizId;
	}

	public int getUserId() {
		return userId;
	}

	public int getScore() {
		return score;
	}

	public int getTotal() {
		return total;
	}

	public int getRating() {
		return rating;
	}

	public String getReview() {
		return review;
	}

	public String getName() {
		return name;
	}

	public Timestamp getTimeStamp() {
		return dt;
	}
}
