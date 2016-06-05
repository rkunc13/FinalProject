package Model;

import java.sql.Timestamp;

public class Quiz {
	
	private int quizID;
	private String name;
	private String description;
	private int authorID;
	private boolean randomOrder;
	private boolean multiplePages;
	private boolean immediateCorrection;
	private Timestamp dateCreated;
	private int points;
	private boolean reported;
	private String category;
	private String tags;
	private int score;
	private int quizTakerID;

	public Quiz(int quizID, String name, String description, int authorID, boolean randO, boolean multP, boolean immC, Timestamp dt, int points, boolean reported, String category, String tags) {
		this.quizID = quizID;
		this.name = name;
		this.description = description;
		this.authorID = authorID;
		this.randomOrder = randO;
		this.multiplePages = multP;
		this.immediateCorrection = immC;
		this.dateCreated = dt;
		this.points = points;
		this.reported = reported;
		this.category = category;
		this.tags = tags;
	}
	
	public Quiz(int quizID, String name, Timestamp dt, int score, int quizTakerID) {
		this.quizID = quizID;
		this.name = name;
		this.dateCreated = dt;
		this.score = score;
		this.quizTakerID = quizTakerID;
	}
	
	public Quiz(int quizID, String name, Timestamp dt, int authorID) {
		this.quizID = quizID;
		this.name = name;
		this.dateCreated = dt;
		this.authorID = authorID;
	}
	
	public int getQuizID() {
		return quizID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getAuthorID() {
		return authorID;
	}
	
	public boolean isRandomOrder() {
		return randomOrder;
	}
	
	public boolean isMultiplePages() {
		return multiplePages;
	}
	
	public boolean isImmediateCorrection() {
		return immediateCorrection;
	}
	
	public Timestamp getDateAndTime() {
		return dateCreated;
	}
	
	public int getPoints() {
		return points;
	}
	
	public boolean isReported(){
		return reported;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getTags() {
		return tags;
	}
	
	public int getScore() {
		return score;
	}

	public int getQuizTakerId() {
		return quizTakerID;
	}
}
