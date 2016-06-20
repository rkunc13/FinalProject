package Database;



import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import Model.Quiz;
import Model.QuizHistory;

public class QuizData {

	private Connection con; 

	public QuizData(Connection con) {
		this.con = con; 
	}	
}
