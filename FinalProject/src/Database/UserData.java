package Database;



import java.sql.Connection;
import java.util.List;

import Model.Quiz;
import Model.QuizHistory;

public class UserData {
	private Connection con; 
	
	public UserData(Connection con) {
		this.con = con; 
	}

	public boolean containsUser() {
		return true;
	}
	
	public boolean hasUsers(){
		return true;
	}
	
	public List<Integer> findUsers() {
		List<Integer> u = new ArrayList<Integer>();
		
		return u;
	}
	
	public boolean isAdmin() {
		return true;
	}

	public boolean checkPassword() {
		return true;
	}
	
	
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			if ((val = val & 0xff) <16) 
				buff.append('0'); 
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	public void addUser() {
		
	}
	
	public void addAdmin() {
		
	}

	public int getIDByEmail() {
		return 0;
	}
	
	public String getEmailByID() { 
		return null;
	}
	
	public String getNameByID() {
		return "name";
	}
	
	

	public ArrayList<Quiz> getAuthoredQuizzes() {
		return null;
	}
	

	public int getTopScore() {
		return 0;
	}
}
