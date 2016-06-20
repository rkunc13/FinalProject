package servlets;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.QuizData;

@WebServlet("/QuizReviewServlet")
public class QuizReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizReviewServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QuizData qManager = (QuizData) getServletContext().getAttribute("quiz manager");
		int quizID = Integer.parseInt(request.getParameter("quiz_id"));
		int userID = (Integer) request.getSession().getAttribute("user id");
		int score = Integer.parseInt(request.getParameter("score"));
		int total = Integer.parseInt(request.getParameter("total"));
		Timestamp time = (Timestamp) request.getSession().getAttribute("time_taken");
		
		qManager.iAmGreatestAchievement(userID, quizID, score);
		
		int rating = 0; 												
		rating = request.getParameter("rating") != null ? Integer.parseInt(request.getParameter("rating")) : rating;
		String review = request.getParameter("review");
		String name = request.getParameter("name");
		qManager.addQuizResult(quizID, userID, score, total, rating, review, name, time);
		
		RequestDispatcher disp = request.getRequestDispatcher("homepage.jsp");
		disp.forward(request, response); 
	}
}