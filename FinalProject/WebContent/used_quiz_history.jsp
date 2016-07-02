<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import ="java.util.*, Database.*, Model.*"%>
<%
	UserData userManager = (UserData) getServletContext().getAttribute("user manager");
%>
<% int userId = Integer.parseInt(request.getParameter("id")); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="normalize.css">
<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Quiz History</title>
</head>
<body>
<% ArrayList<QuizHistory> history = userManager.getQuizHistoryById(userId); %>
<h1><%=userManager.getNameByID(userId)%>'s Quiz History</h1>
<% if(history.size() > 0){ %>
<div class="card">
<h5>Full History:</h5>
<% for(int i = history.size() - 1; i >= 0; i--){ %>
	<ul>
		<li><a href="quiz_summary.jsp?quiz_id=<%= history.get(i).getQuizId() %>"><%= history.get(i).getName() %></a> (<%=String.format("%.2f", (float)history.get(i).getScore()/(float)history.get(i).getTotal()*100) %>%)</li>
	</ul>
<% } %>
</div>
<% } %>
</body>
</html>