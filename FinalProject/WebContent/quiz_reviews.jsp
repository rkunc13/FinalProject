<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*, Database.*, Model.*, java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
			QuizData quizManager = (QuizData) getServletContext().getAttribute("quiz manager");
				UserData userManager = (UserData) getServletContext().getAttribute("user manager");
				int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
				int user_id = 0;
				if((Integer) request.getSession().getAttribute("user id") != null) {
			user_id = (Integer) request.getSession().getAttribute("user id");
				}
				String quiz_name = quizManager.getQuizNameByID(quiz_id);
		%>
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="normalize.css">
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
		<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>QuizMe Quiz Reviews</title>
	</head>
	<body>
		<h1 class="auth-center"><%=quiz_name%> Reviews</h1>
		<br/>
		<% ArrayList<QuizHistory> quiz_history = quizManager.getQuizHistory(quiz_id, user_id, "reviews"); %>
		<% if(quiz_history.size() > 0){ %>
		<div class="card">
		<% int size = quiz_history.size();
		for (int i = 0; i < size; i++) { 
			String review = quiz_history.get(i).getReview();
			if (!review.trim().equals("")) { %>
				<p><%= userManager.getNameByID(quiz_history.get(i).getUserId()) %></p>
				<p><%=review%></p>	
		<%	}
		 } %>
		</div>
		<% } %>
		<p class="auth-center"><a href="quiz_summary.jsp?quiz_id=<%=quiz_id%>"><button type="button">Go Back</button></a>
		<a href="homepage.jsp"><button type="button">Return Home</button></a></p>
	</body>
</html>