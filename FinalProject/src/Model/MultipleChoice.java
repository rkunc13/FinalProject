package Model;

import java.util.ArrayList;
import java.util.Arrays;

public class MultipleChoice extends Question {


	public MultipleChoice(int quizID, int authorID, String prompt,String choices, String answer) {
	}

	public int getQuizID() {
		return 1;
	}

	public int getNumChoices() {
		return 1;
	}

	public String getQuestionText() {
		return "res";
	}

	public String getChoicesAsText() {
		return "choice";
	}

	public ArrayList<String> getChoicesAsList() {
		return Null;
	}

	public String getAnswerText() {
		return "resp"
	}
}
