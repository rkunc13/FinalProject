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

}
