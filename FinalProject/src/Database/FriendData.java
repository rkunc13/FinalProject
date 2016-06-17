package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import Model.Quiz;

public class FriendData {

	private Connection con;
	private ResultSet rs;

	public FriendData(Connection con) {
		this.con = con;
	}

	public void addFriend(String userEmail, String friendEmail) {
		Statement statement;
		int userID = 0;
		int friendID = 0;
		boolean alreadyAdded = false;
		try {
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT * FROM users WHERE email = \'"
					+ userEmail + "\'");
			if (rs.next())
				userID = rs.getInt("user_id");
			rs = statement.executeQuery("SELECT * FROM users WHERE email = \'"
					+ friendEmail + "\'");
			if (rs.next())
				friendID = rs.getInt("user_id");
			rs = statement
					.executeQuery("SELECT * FROM friends WHERE user_id1 = \'"
							+ userID + "\'");
			while (rs.next())
				alreadyAdded = rs.getInt("user_id2") == friendID
						|| alreadyAdded;
			rs = statement
					.executeQuery("SELECT * FROM friends WHERE user_id2 = \'"
							+ userID + "\'");
			while (rs.next())
				alreadyAdded = rs.getInt("user_id1") == friendID
						|| alreadyAdded;
			if (!alreadyAdded) {
				Calendar calendar = Calendar.getInstance();
				Timestamp timeStamp = new Timestamp(calendar.getTime()
						.getTime());
				PreparedStatement preparedStatement = con
						.prepareStatement("INSERT INTO friends (user_id1, user_id2, date_time) VALUES(?, ?, ?)");
				preparedStatement.setInt(1, userID);
				preparedStatement.setInt(2, friendID);
				preparedStatement.setTimestamp(3, timeStamp);
				preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Integer> getFriends(int userID) {
		Statement statement;
		ArrayList<Integer> friendsList = new ArrayList<Integer>();
		try {
			statement = con.createStatement();
			rs = statement
					.executeQuery("SELECT * FROM friends WHERE user_id1 = \'"
							+ userID + "\'");
			while (rs.next())
				friendsList.add(rs.getInt("user_id2"));
			rs = statement
					.executeQuery("SELECT * FROM friends WHERE user_id2 = \'"
							+ userID + "\'");
			while (rs.next())
				friendsList.add(rs.getInt("user_id1"));
			return friendsList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void sendFriendRequest(int from, int to) {
		Statement statement;
		try {
			boolean alreadyRequested = false;
			statement = con.createStatement();
			rs = statement
					.executeQuery("SELECT * FROM friends WHERE user_id1 = \'"
							+ from + "\'");
			while (rs.next())
				alreadyRequested = rs.getInt("user_id2") == to
						|| alreadyRequested;
			rs = statement
					.executeQuery("SELECT * FROM friends WHERE user_id2 = \'"
							+ from + "\'");
			while (rs.next())
				alreadyRequested = rs.getInt("user_id1") == to
						|| alreadyRequested;
			rs = statement
					.executeQuery("SELECT * FROM friend_requests WHERE from_user_id = \'"
							+ from + "\'");
			while (rs.next())
				alreadyRequested = rs.getInt("to_user_id") == to
						|| alreadyRequested;
			rs = statement
					.executeQuery("SELECT * FROM friend_requests WHERE to_user_id = \'"
							+ from + "\'");
			while (rs.next())
				alreadyRequested = rs.getInt("to_user_id") == to
						|| alreadyRequested;
			rs = statement
					.executeQuery("SELECT * FROM friend_requests WHERE to_user_id = \'"
							+ from + "\'");
			if (!alreadyRequested) {
				PreparedStatement preparedStatement = con
						.prepareStatement("INSERT INTO friend_requests (from_user_id, to_user_id) VALUES(?, ?)");
				preparedStatement.setInt(1, from);
				preparedStatement.setInt(2, to);
				preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Integer> showFriendRequests(int userID) {
		Statement statement;
		ArrayList<Integer> requestList = new ArrayList<Integer>();
		try {
			statement = con.createStatement();
			rs = statement
					.executeQuery("SELECT * FROM friend_requests WHERE to_user_id = \'"
							+ userID + "\'");
			while (rs.next())
				requestList.add(rs.getInt(2));
			return requestList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Integer> findMutualFriends() {
		ArrayList<Integer> mutuals = new ArrayList<Integer>();
		return mutuals;
	}

	public boolean isFriend(int userID1, int userID2) {
		Statement statement;
		try {
			statement = con.createStatement();
			rs = statement
					.executeQuery("SELECT * FROM friends WHERE user_id1 = \'"
							+ userID1 + "\'");
			while (rs.next())
				if (rs.getInt("user_id2") == userID2)
					return true;
			rs = statement
					.executeQuery("SELECT * FROM friends WHERE user_id2 = \'"
							+ userID1 + "\'");
			while (rs.next())
				if (rs.getInt("user_id1") == userID2)
					return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public int checkFriendStatus(int userID1, int userID2) {
		int areFriends = 1;
		int user1Requested = 2;
		int user2Requested = 3;
		int noRequests = 4;
		Statement statement;
		try {
			statement = con.createStatement();
			rs = statement
					.executeQuery("SELECT * FROM friends WHERE user_id1 = \'"
							+ userID1 + "\'");
			while (rs.next())
				if (rs.getInt("user_id2") == userID2)
					return areFriends;
			rs = statement
					.executeQuery("SELECT * FROM friends WHERE user_id2 = \'"
							+ userID1 + "\'");
			while (rs.next())
				if (rs.getInt("user_id1") == userID2)
					return areFriends;
			rs = statement
					.executeQuery("SELECT * FROM friend_requests WHERE from_user_id = \'"
							+ userID1 + "\'");
			while (rs.next())
				if (rs.getInt("to_user_id") == userID2)
					return user1Requested;
			rs = statement
					.executeQuery("SELECT * FROM friend_requests WHERE to_user_id = \'"
							+ userID1 + "\'");
			while (rs.next()) {
				if (rs.getInt("from_user_id") == userID2)
					return user2Requested;
			}
			return noRequests;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void deleteFriendRequest(String userEmail, String friendRequestEmail) {
		Statement stmt;
		int userId = 0;
		int friendRequestId = 0;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \'"
					+ userEmail + "\'");
			if (rs.next()) {
				userId = rs.getInt("user_id");
			}
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \'"
					+ friendRequestEmail + "\'");
			if (rs.next()) {
				friendRequestId = rs.getInt("user_id");
			}
			PreparedStatement prepStmt = con
					.prepareStatement("DELETE FROM friend_requests WHERE from_user_id = \'"
							+ friendRequestId
							+ "\' AND to_user_id = \'"
							+ userId + "\'");
			prepStmt.executeUpdate();
			prepStmt = con
					.prepareStatement("DELETE FROM friend_requests WHERE from_user_id = \'"
							+ userId
							+ "\' AND to_user_id = \'"
							+ friendRequestId + "\'");
			prepStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unfriend(String userEmail, String friendEmail) {
		Statement stmt;
		int userId = 0;
		int friendId = 0;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \'"
					+ userEmail + "\'");
			if (rs.next()) {
				userId = rs.getInt("user_id");
			}
			rs = stmt.executeQuery("SELECT * FROM users WHERE email = \'"
					+ friendEmail + "\'");
			if (rs.next()) {
				friendId = rs.getInt("user_id");
			}
			con.prepareStatement(
					"DELETE FROM friends WHERE user_id1 = \'" + userId
							+ "\' AND user_id2 = \'" + friendId + "\'")
					.executeUpdate();
			con.prepareStatement(
					"DELETE FROM friends WHERE user_id1 = \'" + friendId
							+ "\' AND user_id2 = \'" + userId + "\'")
					.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Quiz> quizTakenNewsfeed(int userID) {
		ArrayList<Quiz> recentlyTaken = new ArrayList<Quiz>();
		try {
			Statement selectStatement = con.createStatement();
			ResultSet rs = selectStatement
					.executeQuery("SELECT * FROM quiz_history");
			while (rs.next()) {
				if (isFriend(userID, rs.getInt("user_id"))) {
					Quiz quiz = new Quiz(rs.getInt("quiz_id"),
							rs.getString("name"),
							rs.getTimestamp("time_taken"), rs.getInt("score"),
							rs.getInt("user_id"));
					recentlyTaken.add(quiz);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recentlyTaken;
	}

	public ArrayList<Quiz> quizCreatedNewsfeed(int userID) {
		ArrayList<Quiz> recentlyCreated = new ArrayList<Quiz>();
		try {
			Statement selectStmt = con.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM quizzes");
			while (rs.next()) {
				if (isFriend(userID, rs.getInt("author_id"))) {
					Quiz quiz = new Quiz(rs.getInt("quiz_id"),
							rs.getString("name"), rs.getTimestamp("date_time"),
							rs.getInt("author_id"));
					recentlyCreated.add(quiz);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recentlyCreated;
	}

	public ArrayList<String> achievementEarnedNewsfeed(int userID) {
		ArrayList<String> lastAchievements = new ArrayList<String>();
		try {
			Statement selectStatement = con.createStatement();
			ResultSet rs = selectStatement
					.executeQuery("SELECT * FROM achievements");
			while (rs.next()) {
				if (isFriend(userID, rs.getInt("user_id"))) {
					lastAchievements.add(rs.getInt("user_id") + "");
					lastAchievements.add(rs.getString("description"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastAchievements;
	}

	public ArrayList<Integer> becameFriendsNewsfeed(int userID) {
		ArrayList<Integer> friends = new ArrayList<Integer>();
		try {
			Statement selectStatement = con.createStatement();
			ResultSet rs = selectStatement
					.executeQuery("SELECT * FROM friends");
			while (rs.next()) {
				if (isFriend(userID, rs.getInt("user_id1"))) {
					friends.add(rs.getInt("user_id2"));
					friends.add(rs.getInt("user_id1"));
				} else if (isFriend(userID, rs.getInt("user_id2"))) {
					friends.add(rs.getInt("user_id1"));
					friends.add(rs.getInt("user_id2"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friends;
	}
}
