package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.MessageData;
import Database.UserData;

@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
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
		ServletContext context = getServletContext();
		HttpSession session = request.getSession();
		
		MessageData msgManager = (MessageData) context.getAttribute("message manager");
		UserData uManager = (UserData) context.getAttribute("user manager");
		String senderEmail = (String) session.getAttribute("email");
		
		String msg = request.getParameter("new message");
		String receiverEmail = request.getParameter("receiver");
		
		if (uManager.containsUser(receiverEmail) && uManager.containsUser(senderEmail)) {
			session.setAttribute("message status", "sent");
			msgManager.sendMessage(msg, senderEmail, receiverEmail);
			RequestDispatcher disp = request.getRequestDispatcher("homepage.jsp");
			disp.forward(request, response);
		} else {
			session.setAttribute("message status", "failed");
			RequestDispatcher disp = request.getRequestDispatcher("homepage.jsp");
			disp.forward(request, response);
		}
	}

}