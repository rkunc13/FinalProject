package servlets;



import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SelectQuestionTypeServlet")
public class SelectQuestionTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectQuestionTypeServlet() {
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
		String questionType = request.getParameter("ques_type");
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		if (questionType.equals("text_response")) {
			RequestDispatcher disp = request.getRequestDispatcher("create_question_response_question.jsp?quiz_id="+quiz_id+"&points="+request.getParameter("points")+"&edit_mode="+request.getParameter("edit_mode"));
			disp.forward(request, response); 
		} else if (questionType.equals("fill_blank")) {
			RequestDispatcher disp = request.getRequestDispatcher("create_fill_blank_question.jsp?quiz_id="+quiz_id+"&points="+request.getParameter("points")+"&edit_mode="+request.getParameter("edit_mode"));
			disp.forward(request, response); 
		} else if (questionType.equals("multiple_choice")) {
			RequestDispatcher disp = request.getRequestDispatcher("create_multiple_choice_question.jsp?quiz_id="+quiz_id+"&points="+request.getParameter("points")+"&edit_mode="+request.getParameter("edit_mode"));
			disp.forward(request, response); 
		} else if (questionType.equals("picture")) {
			RequestDispatcher disp = request.getRequestDispatcher("create_picture_question.jsp?quiz_id="+quiz_id+"&points="+request.getParameter("points")+"&edit_mode="+request.getParameter("edit_mode"));
			disp.forward(request, response); 
		} else if (questionType.equals("multianswer")) {
			RequestDispatcher disp = request.getRequestDispatcher("create_multianswer_question.jsp?quiz_id="+quiz_id+"&points="+request.getParameter("points")+"&edit_mode="+request.getParameter("edit_mode"));
			disp.forward(request, response); 
		} else if (questionType.equals("multiple_choice_multiple_answers")) {
			RequestDispatcher disp = request.getRequestDispatcher("create_multiple_choice_multiple_answers.jsp?quiz_id="+quiz_id+"&points="+request.getParameter("points")+"&edit_mode="+request.getParameter("edit_mode"));
			disp.forward(request, response); 
		}
	}
}