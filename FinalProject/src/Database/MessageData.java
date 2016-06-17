package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Challenge;

public class MessageData {
	private Connection con;
	private ResultSet rs;

	public MessageData(Connection con) {
		this.con = con;
	}

	public void sendMessage(String message, String senderEmail,
			String receiverEmail) {
		
	}
	
	public List<String> getMessage() {
		List<String> m = new ArrayList<String>();
		return m;
	}
	
	public void sendChallenge() {
		
	}

	public List<Challenge> getChallenge(int receiverID) {
		List<Challenge> challengeList = new ArrayList<Challenge>();
		return challengeList;
	}

	public void deleteChallenge() {
		
	}

	public boolean alreadyChallenged() {
		return true;
	}
}
