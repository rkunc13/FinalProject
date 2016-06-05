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

}
