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

import Database.UserData;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		UserData uManager = (UserData) context.getAttribute("user manager");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if (uManager.containsUser(email) && uManager.checkPassword(email, password)) {
			session.setAttribute("user id", uManager.getIDByEmail(email));
			session.setAttribute("email", email);
			if(uManager.isAdmin(email)){
				RequestDispatcher disp = request.getRequestDispatcher("admin_homepage.jsp");
				disp.forward(request, response);
			} else{
				RequestDispatcher disp = request.getRequestDispatcher("homepage.jsp");
				disp.forward(request, response);
			}
		} else {
			RequestDispatcher disp = request.getRequestDispatcher("invalid_account.jsp");
			disp.forward(request, response);
		}
	}
	
}
