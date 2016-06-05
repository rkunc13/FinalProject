package Model;

import java.sql.Timestamp;

public class Quiz {
	
	private int quizID;
	private String name;
	private int authorID;
	private Timestamp dateCreated;

	
	public Quiz(int quizID, String name, Timestamp dt, int authorID) {
		this.quizID = quizID;
		this.name = name;
		this.dateCreated = dt;
		this.authorID = authorID;
	}
	
}
	