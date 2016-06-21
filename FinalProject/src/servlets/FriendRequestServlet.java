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

@WebServlet("/FriendRequestServlet")
public class FriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendRequestServlet() {
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
		
		String friendEmail = request.getParameter("friendEmail");
		String decision = request.getParameter("requestSent");


		if (uManager.containsUser(uEmail) && uManager.containsUser(friendEmail)) {
			fManager.sendFriendRequest(uManager.getIDByEmail(uEmail), uManager.getIDByEmail(friendEmail));
			if (decision.equals("Request from Friend Page")) {
				RequestDispatcher disp = request.getRequestDispatcher("friend_homepage.jsp");
				disp.forward(request, response);
			} else if (decision.equals("Request from Search Page")) {
				RequestDispatcher disp = request.getRequestDispatcher("search_user.jsp");
				disp.forward(request, response);
			}
		}
				
	}

}
