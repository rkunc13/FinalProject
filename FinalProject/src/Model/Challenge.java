package Model;

public class Challenge {
	int senderID;
	int receiverID;
	int quizID;
	int score;

	public Challenge(int senderID, int receiverID, int quizID, int score) {
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.quizID = quizID;
		this.score = score;
	}

	public int getSenderId() {
		return senderID;
	}

	public int getReceiverId() {
		return receiverID;
	}

	public int getQuizId() {
		return quizID;
	}

	public int getScore() {
		return score;
	}
}
