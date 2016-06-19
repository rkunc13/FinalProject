package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.QuizData;

@WebServlet("/QuizCreationServlet")
public class QuizCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizCreationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		QuizData qManager = (QuizData) getServletContext().getAttribute("quiz manager");
		int userID = (Integer) session.getAttribute("user id"); 
		
		// Set parameters for quiz and add to database
		String name = "";																			// defaults it to blank
		name = request.getParameter("name");
		name = name.equals("") ? "Untitled" : name;
		String description = "";
		description = request.getParameter("description");
		String category = request.getParameter("category");
		String tags = request.getParameter("tags");
		boolean randOrder = request.getParameter("random_order").equals("yes");
		boolean multpages = request.getParameter("multiple_pages").equals("yes");
		boolean immediateCorrection = request.getParameter("immediate_correction").equals("yes");
		boolean editMode = request.getParameter("edit_mode").equals("true");
		
		if (editMode) {
			int quizID = Integer.parseInt(request.getParameter("quiz_id"));
			qManager.updateQuiz(name, description, category, tags, randOrder, multpages, immediateCorrection, quizID);
			
			RequestDispatcher disp = request.getRequestDispatcher("edit_question.jsp?quiz_id="+quizID);
			disp.forward(request, response);
		} else if (!qManager.contains(name)){
			qManager.addQuiz(name, description, userID, category, tags, randOrder, multpages, immediateCorrection, 0);
			qManager.authorAchievement(userID);
			int quizID = qManager.getIDByName(name);
			RequestDispatcher disp = request.getRequestDispatcher("add_question.jsp?quiz_id="+quizID+"&points=0&edit_mode=false");
			disp.forward(request, response);
		} else {
			RequestDispatcher disp = request.getRequestDispatcher("create_quiz.jsp?error=duplicate_name");
			disp.forward(request, response);
		}
	}
}

