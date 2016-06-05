package Model;

import java.util.ArrayList;
import java.util.Arrays;

public class MultipleChoice extends Question {

	private static final long serialVersionUID = 1L;
	private String choices;

	public MultipleChoice(int quizID, int authorID, String prompt,
			String choices, String answer) {
		this.quizID = quizID;
		this.authorID = authorID;
		this.question = prompt;
		this.choices = choices;
		this.answer = answer;
	}

	public int getQuizID() {
		return quizID;
	}

	public int getNumChoices() {
		return choices.split(",").length;
	}

	public String getQuestionText() {
		return question;
	}

	public String getChoicesAsText() {
		return choices;
	}

	public ArrayList<String> getChoicesAsList() {
		return new ArrayList<String>(Arrays.asList(choices.trim().toLowerCase()
				.split("\\s*,\\s*")));
	}

	public String getAnswerText() {
		return answer.toLowerCase();
	}
}
