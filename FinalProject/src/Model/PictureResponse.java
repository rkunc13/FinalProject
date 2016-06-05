package Model;

public class PictureResponse extends Question {

	private static final long serialVersionUID = 1L;

	public PictureResponse(int quizID, int authorID, String url, String answer) {
		this.quizID = quizID;
		this.authorID = authorID;
		this.question = url;
		this.answer = answer;
	}
}
