package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Challenge;

public class MessageData {
	private Connection con;
	private ResultSet rs;

	public MessageData(Connection con) {
		this.con = con;
	}

	public void sendMessage(String message, String senderEmail,
			String receiverEmail) {
		
		int senderID = 0;
		int receiverID = 0;
		try {
			Statement statement;
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT * FROM users WHERE email = \'"
					+ senderEmail + "\'");
			senderID = rs.next() ? rs.getInt("user_id") : senderID;

			rs = statement.executeQuery("SELECT * FROM users WHERE email = \'"
					+ receiverEmail + "\'");
			
			receiverID = rs.next() ? rs.getInt("user_id") : receiverID;


			statement
					.executeQuery("INSERT INTO messages (sender_id, receiver_id, data) VALUES("
							+ senderID
							+ ","
							+ receiverID
							+ ",\'"
							+ message
							+ "\')");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getMessage(String receiverEmail) {
		Statement statement;
		Statement statement2;
		int receiverID = 0;
		ArrayList<String> msgs = new ArrayList<String>();
		try {
			statement = con.createStatement();
			statement2 = con.createStatement();
			rs = statement.executeQuery("SELECT * FROM users WHERE email = \'"
					+ receiverEmail + "\'");
			
			receiverID = rs.next() ? rs.getInt("user_id") : receiverID;
			
			rs = statement
					.executeQuery("SELECT * FROM messages WHERE receiver_id = \'"
							+ receiverID + "\'");

			while (rs.next()) {
				ResultSet rs1 = statement2
						.executeQuery("SELECT * FROM users WHERE user_id = \'"
								+ rs.getInt("sender_id") + "\'");
				String senderEmail = rs1.next() ? rs1.getString("email") :"";

				msgs.add(rs.getString("data"));
				msgs.add(senderEmail);
			}
			return msgs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void sendChallenge(int senderID, int receiverID, int quizID,
			int score) {
		Statement statement;
		if (!alreadyChallenged(senderID, receiverID, quizID)) {
			try {
				statement = con.createStatement();
				statement.executeQuery("INSERT INTO challenges (sender_id, receiver_id, quiz_id, score) VALUES("
								+ senderID
								+ "."
								+ receiverID
								+ ","
								+ quizID
								+ "," + score + ")");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Challenge> getChallenge(int receiverID) {
		ArrayList<Challenge> challengeList = new ArrayList<Challenge>();
		try {
			rs = con.createStatement()
					.executeQuery("SELECT * FROM challenges WHERE receiver_id = \'"
							+ receiverID + "\'");
			while (rs.next()) 
				challengeList.add(new Challenge(rs.getInt("sender_id"),
						rs.getInt("receiver_id"), rs.getInt("quiz_id"),
						rs.getInt("score")));
			
			return challengeList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return challengeList;
	}

	public void deleteChallenge(int senderID, int receiverID, int quizID,
			int score) {
		try {
			con.createStatement().executeQuery("DELETE FROM challenges WHERE sender_id = \'"
							+ senderID
							+ "\' AND receiver_id = \'"
							+ receiverID
							+ "\' AND quiz_id = \'"
							+ quizID
							+ "\' AND score = \'" + score + "\'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean alreadyChallenged(int senderID, int receiverID, int quizID) {
		try {
			rs = con.createStatement()
					.executeQuery("SELECT * FROM challenges WHERE sender_id = \'"
							+ senderID
							+ "\' AND receiver_id = \'"
							+ receiverID
							+ "\' AND quiz_id = \'"
							+ quizID
							+ "\'");
			if (rs.next())
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
