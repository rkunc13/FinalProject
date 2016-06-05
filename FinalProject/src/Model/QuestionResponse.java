package Model;

public class QuestionResponse extends Question {

	private static final long serialVersionUID = 1L;

	public QuestionResponse(int quizID, int authorID, String prompt,
			String answer) {
		this.quizID = quizID;
		this.authorID = authorID;
		this.question = prompt;
		this.answer = answer;
	}

	public int getQuizID() {
		return quizID;
	}

	public String getQuestionText() {
		return question;
	}

	public String getAnswerText() {
		return answer.toLowerCase();
	}
}
