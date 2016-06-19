package servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import Database.AdminData;
import Database.DBConnection;
import Database.FriendData;
import Database.MessageData;
import Database.QuestionData;
import Database.QuizData;
import Database.UserData;

@WebListener
public class QuizListener implements ServletContextListener, HttpSessionListener {

    /**
     * Default constructor. 
     */
    public QuizListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
    	ServletContext context = arg0.getServletContext();
    	DBConnection con = (DBConnection) context.getAttribute("connection");
    	con.close();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	DBConnection con = new DBConnection();
    	UserData uManager = new UserData(con.getConnection());
    	QuizData qManager = new QuizData(con.getConnection());
    	QuestionData questionData = new QuestionData(con.getConnection());
    	AdminData aManager = new AdminData(con.getConnection());
    	MessageData msgManager = new MessageData(con.getConnection());
    	FriendData fManager = new FriendData(con.getConnection());
        ServletContext context = arg0.getServletContext();
        context.setAttribute("connection", con);
        context.setAttribute("user manager", uManager);
        context.setAttribute("quiz manager", qManager);
        context.setAttribute("question manager", questionData);
        context.setAttribute("admin manager", aManager);
        context.setAttribute("message manager", msgManager);
        context.setAttribute("friends manager", fManager);
    }
	
}
