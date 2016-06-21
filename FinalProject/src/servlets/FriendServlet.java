package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.FriendData;
import Database.UserData;

@WebServlet("/FriendServlet")
public class FriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendServlet() {
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
		
		FriendData fManager = (FriendData) context.getAttribute("friends manager");
		UserData uManager = (UserData) context.getAttribute("user manager");
		String uEmail = (String) request.getSession().getAttribute("email");
		String fEmail = request.getParameter("friendEmail");
		String dec = request.getParameter("decision");
		
		if (uManager.containsUser(uEmail) && uManager.containsUser(fEmail)) {
			if (uEmail.equals(fEmail)) {
				RequestDispatcher disp = request.getRequestDispatcher("homepage.jsp");
				disp.forward(request, response);
			} else if (dec.equals("Accept From Home Page")) {
	 			fManager.addFriend(uEmail, fEmail);
				fManager.addFriend(fEmail, uEmail);
				fManager.deleteFriendRequest(uEmail, fEmail);
				RequestDispatcher disp = request.getRequestDispatcher("homepage.jsp");
				disp.forward(request, response);
			} else if (dec.equals("Accept From Friend Page")) {
	 			fManager.addFriend(uEmail, fEmail);
				fManager.addFriend(fEmail, uEmail);
				fManager.deleteFriendRequest(uEmail, fEmail);
				RequestDispatcher disp = request.getRequestDispatcher("friend_homepage.jsp");
				disp.forward(request, response);
			} else if (dec.equals("Accept From Search Page")) {
	 			fManager.addFriend(uEmail, fEmail);
				fManager.addFriend(fEmail, uEmail);
				fManager.deleteFriendRequest(uEmail, fEmail);
				RequestDispatcher disp = request.getRequestDispatcher("search_user.jsp");
				disp.forward(request, response);
			} else if (dec.equals("Reject From Home Page")) {
				fManager.deleteFriendRequest(uEmail, fEmail);
				RequestDispatcher disp = request.getRequestDispatcher("homepage.jsp");
				disp.forward(request, response);
			} else if (dec.equals("Reject From Friend Page")) {
				fManager.deleteFriendRequest(uEmail, fEmail);
				RequestDispatcher disp = request.getRequestDispatcher("friend_homepage.jsp");
				disp.forward(request, response);
			} else if (dec.equals("Reject From Search Page")) {
				fManager.deleteFriendRequest(uEmail, fEmail);
				RequestDispatcher disp = request.getRequestDispatcher("search_user.jsp");
				disp.forward(request, response);
			} else if (dec.equals("Unfriend")) {
				fManager.unfriend(uEmail, fEmail);
				RequestDispatcher disp = request.getRequestDispatcher("friend_homepage.jsp");
				disp.forward(request, response);
			}
		}
	}
}