package Model;

public class QuestionResponse extends Question {


	public QuestionResponse(int quizID, int authorID, String prompt, String answer) {
		this.quizID = quizID;
		this.authorID = authorID;
		this.question = prompt;
		this.answer = answer;
	}
}
