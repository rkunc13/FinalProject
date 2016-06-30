package Model;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiAnswer extends Question {
	private static final long serialVersionUID = 1L;
	private boolean inOrder;
	private int numCorrect;

	public MultiAnswer(int quizID, int authorID, String prompt, String answer,
			int numCorrect, boolean inOrder) {
		this.quizID = quizID;
		this.authorID = authorID;
		this.question = prompt;
		this.answer = answer;
		this.inOrder = inOrder;
		this.numCorrect = numCorrect;
	}

	public int getQuizID() {
		return quizID;
	}

	public String getQuestionText() {
		return question;
	}

	public String getAnswerAsText() {
		return answer;
	}

	public ArrayList<String> getAnswerAsList() {
		return new ArrayList<String>(Arrays.asList(answer.trim().toLowerCase()
				.split("\\s*,\\s*")));
	}

	public boolean getInOrder() {
		return inOrder;
	}

	public int getNumAnswers() {
		return numCorrect;
	}
}
