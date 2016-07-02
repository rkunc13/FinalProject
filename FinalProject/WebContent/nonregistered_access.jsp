<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="java.util.*, Database.*, Model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	UserData userManager = (UserData) getServletContext().getAttribute("user manager");
	AdminData adminManager = ((AdminData)request.getServletContext().getAttribute("admin manager"));
	DBConnection connection = (DBConnection) getServletContext().getAttribute("connection");
	QuizData quizManager = (QuizData) getServletContext().getAttribute("quiz manager");
%>
<html>
<head>
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="normalize.css">
<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>QuizMe</title>
</head>
<body>
	<a href="index.jsp"><button type="button">Back to Login</button></a>
	<h1 class="auth-center">Welcome Guest!</h1>

	<% ArrayList<String> announcements = adminManager.getAnnouncements(); %>
	<% if(announcements.size() > 0){ %>
	<div class="card">
	<h5>Announcements:</h5>
	<ul>
		<% for(int i = 0; i < announcements.size(); i++){ %>
		<li><%= announcements.get(i) %></li>
		<% } %>
	</ul>
	</div>
	<% } %>
	
	<% ArrayList<Quiz> popularQuizzes = quizManager.getPopularQuizzes(); %>
	<% if(popularQuizzes.size() > 0){ %>
		<div class="card">
		<h5>Most Popular Quizzes:</h5>
		<% for(int i = popularQuizzes.size() - 1; i >= 0 && i >= popularQuizzes.size() - 3; i--){ %>
			<p>Quiz Name: <a href="quiz_summary.jsp?quiz_id=<%= popularQuizzes.get(i).getQuizID() %>"><%= popularQuizzes.get(i).getName() %></a></p>
			<p>Description: <%= popularQuizzes.get(i).getDescription() %></p>
		<% } %>
		</div>
	<% } %>
	
</body>
</html>