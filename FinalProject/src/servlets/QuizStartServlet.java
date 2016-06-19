package servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.QuestionData;
import Database.QuizData;

@WebServlet("/QuizStartServlet")
public class QuizStartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizStartServlet() {
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
		HttpSession session = request.getSession();
		QuestionData qManager = (QuestionData) getServletContext().getAttribute("question manager");
		QuizData quizData = (QuizData) getServletContext().getAttribute("quiz manager");
		int quizID = Integer.parseInt(request.getParameter("quiz_id"));
		int userID = (Integer) request.getSession().getAttribute("user id"); 
		Calendar calendar = Calendar.getInstance();
		Date startTime = new Date(calendar.getTimeInMillis());
		session.setAttribute("start_time", startTime);
		boolean multpages = request.getParameter("multiple_pages").equals("true");
		boolean randOrder = request.getParameter("random_order").equals("true");
		boolean isPractice = request.getParameter("mode").equals("practice");
		ArrayList<Integer> questions = qManager.getQuestionIDs(quizID);
		if (questions.isEmpty()) {	// no questions in quiz
			RequestDispatcher disp = request.getRequestDispatcher("quiz_results.jsp?quiz_id="+quizID+"&score=0&time_taken=\"0 seconds\"");
			disp.forward(request, response);
		} else {
			if (isPractice) {
				HashMap<Integer, Integer> questionFrequency = new HashMap<Integer, Integer>();
				int frequency = 3;
				for (int i : questions) {
					questionFrequency.put(i, frequency); 
				}
				session.setAttribute("ques_frequency", questionFrequency);
				
			} else {
				quizData.quizTakerAchievement(userID);
			}
			if (randOrder)
				Collections.shuffle(questions);
			session.setAttribute("questions", questions);
			if (multpages) {
				RequestDispatcher disp = request.getRequestDispatcher("quiz_multiple_page_view.jsp?question_num=0&score=0&practice_mode="+isPractice+"&immediate_correction="+request.getParameter("immediate_correction")+"&random_order="+request.getParameter("random_order"));
				disp.forward(request, response); 
			} else {
				RequestDispatcher disp = request.getRequestDispatcher("quiz_single_page_view.jsp?practice_mode="+isPractice+"&random_order=" + request.getParameter("random_order"));
				disp.forward(request, response); 
			}
		}
	}
}
