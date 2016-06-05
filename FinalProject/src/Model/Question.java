package Model;

public abstract class Question implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	protected int quizID;
	protected int authorID;
	protected String question;
	protected String answer;
	
	abstract String getQuestionText();
	abstract int getQuizID();
}
