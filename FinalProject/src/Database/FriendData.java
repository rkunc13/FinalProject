package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Quiz;

public class FriendData {

	private Connection con;
	private ResultSet rs;

	public FriendData(Connection con) {
		this.con = con;
	}

	public void addFriend(String userEmail, String friendEmail) {
		
	}

	public List<Integer> getFriends(int userID) {
		
		List<Integer> fl = new ArrayList<Integer>();
		return fl;
	}

	public void sendFriendRequest(int from, int to) {
		
	}

	public List<Integer> showFriendRequests(int userID) {
		List<Integer> rl = new ArrayList<Integer>();
		
		return rl;
	}

	public ArrayList<Integer> findMutualFriends() {
		return null;
	}

	public boolean isFriend() {
		
		return true;
	}

	public int checkFriendStatus() {
		return 0;
	}

	public void deleteFriendRequest(String userEmail, String friendRequestEmail) {
		
	}

	public void unfriend() {
		
	}

	public List<Quiz> quizTakenNewsfeed(int userID) {
		List<Quiz> rt = new ArrayList<Quiz>();
		
		return rt;
	}

	public List<Quiz> quizCreatedNewsfeed() {
		List<Quiz> rc = new ArrayList<Quiz>();
		
		return rc;
	}

	public List<String> achievementEarnedNewsfeed() {
		List<String> la = new ArrayList<String>();
		return la;
	}

	public List<Integer> becameFriendsNewsfeed() {
		List<Integer> fs = new ArrayList<Integer>();
		return fs;
	}
}
