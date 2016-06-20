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

@WebServlet("/AccountCreationServlet")
public class AccountCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountCreationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserData uManager = (UserData) getServletContext().getAttribute("user manager");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		if (uManager.containsUser(email)) {
			RequestDispatcher disp = request
					.getRequestDispatcher("used_account.jsp");
			disp.forward(request, response);
		} else if (uManager.hasUsers()) {
			session.setAttribute("email", email);
			session.setAttribute("user id", uManager.getIDByEmail(email));
			uManager.addUser(email, password, name);
			RequestDispatcher disp = request
					.getRequestDispatcher("homepage.jsp");
			disp.forward(request, response);
		} else {
			session.setAttribute("email", email);
			session.setAttribute("user id", uManager.getIDByEmail(email));
			uManager.addAdmin(email, password, name);
			RequestDispatcher disp = request
					.getRequestDispatcher("admin_homepage.jsp");
			disp.forward(request, response);
		}

	}
}
