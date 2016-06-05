package Model;

import java.util.ArrayList;
import java.util.Arrays;


public class FillInTheBlank extends Question {

	private static final long serialVersionUID = 1L;

	public FillInTheBlank(int quizID, int authorID, String prompt, String answer) {
		this.quizID = quizID;
		this.authorID = authorID;
		this.question = prompt;
		this.answer = answer;
	}

	public int getQuizID() {
		return quizID;
	}

	public int getNumBlanks() {
		return 1 + answer.length() - answer.replace(",", "").length();
	}
	public String getQuestionText() {
		return question.replaceAll("\\*", "__________");
	}

	public String getQuestionRegex() {
		return question;
	}

	public String getAnswerAsText() {
		return answer;
	}
	public ArrayList<String> getAnswerAsList() {
		return new ArrayList<String>(
				Arrays.asList(answer.trim().toLowerCase().split("\\s*,\\s*")));
	}

}
