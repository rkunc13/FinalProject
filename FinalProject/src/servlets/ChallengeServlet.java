package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.MessageData;

@WebServlet("/ChallengeServlet")
public class ChallengeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengeServlet() {
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
		ServletContext context = getServletContext();
		MessageData msgManager = (MessageData) context.getAttribute("message manager");
		int receiverID = Integer.parseInt(request.getParameter("receiver_id"));
		int senderID = Integer.parseInt(request.getParameter("sender_id"));
		int score = Integer.parseInt(request.getParameter("score"));
		int quizID = Integer.parseInt(request.getParameter("quiz_id"));
		String dec = request.getParameter("decision");
		String time = request.getParameter("time_taken");
		
		if (dec.equals("Accept Challenge")) {
			msgManager.deleteChallenge(senderID, receiverID, quizID, score);
			RequestDispatcher disp = request.getRequestDispatcher("quiz_summary.jsp");
			disp.forward(request, response);
		} else if (dec.equals("Send Challenge")) {
			msgManager.sendChallenge(senderID, receiverID, quizID, score);
			RequestDispatcher disp = request.getRequestDispatcher("quiz_results.jsp?time_taken="+time);
			disp.forward(request, response);
		}else if (dec.equals("Reject Challenge")) {
			msgManager.deleteChallenge(senderID, receiverID, quizID, score);
			RequestDispatcher disp = request.getRequestDispatcher("homepage.jsp");
			disp.forward(request, response);
		} 
	}

}