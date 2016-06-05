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

	
	public Quiz(int quizID, String name, Timestamp dt, int authorID) {
		this.quizID = quizID;
		this.name = name;
		this.dateCreated = dt;
		this.authorID = authorID;
	}
	
	//another extended constructors 1
	public Quiz(int quizID, String name, String description, int authorID, boolean randO, boolean multP, boolean immC, Timestamp dt, int points, boolean reported, String category, String tags) {
		
	}
	
	//another less extended constructors 2
	public Quiz(int quizID, String name, Timestamp dt, int score, int quizTakerID) {
		
	}
	
}
	