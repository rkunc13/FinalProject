package Model;


public class MultipleChoiceMultipleAnswers extends Question {

	public MultipleChoiceMultipleAnswers(int quizID, int authorID, String prompt, String answer) {
		this.quizID = quizID;
		this.authorID = authorID;
		this.question = prompt;
		this.answer = answer;
	}

}
