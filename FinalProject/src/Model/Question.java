package Model;

public abstract class Question implements java.io.Serializable {
	
	abstract String getQuestionText();
	abstract int getQuizID();
	
	private static final long serialVersionUID = 1L;
	protected int quizID;
	protected int authorID;
	protected String question;
	protected String answer;
		
}