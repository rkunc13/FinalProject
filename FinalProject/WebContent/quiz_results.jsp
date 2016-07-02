<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*, java.util.*, Database.*, Model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="style.css">
	<link rel="stylesheet" href="normalize.css">
	<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
	<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
	
	<link rel="stylesheet" href="jquery.rating.css">
	<script src="jquery.js"></script>
	<script src="jquery.rating.js"></script>
	<title>
		<%
			FriendData friendsManager = (FriendData) getServletContext().getAttribute("friends manager");
				UserData userManager = (UserData) application.getAttribute("user manager");
				QuizData quizManager = (QuizData) application.getAttribute("quiz manager");
				QuestionData questionManager = (QuestionData) application.getAttribute("question manager");
				MessageData messageManager = (MessageData) getServletContext().getAttribute("message manager");
				int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
				int user_id = (Integer) session.getAttribute("user id");
				Quiz quiz = quizManager.getQuizByID(quiz_id);
				out.println(quiz.getName() + " Results");
				int score = Integer.parseInt(request.getParameter("score"));
				int total = quizManager.getQuizPoints(quiz_id);
				String name = quiz.getName();
		%>
	</title>
</head>
<body>
	<h1><%=quiz.getName()%> Results</h1>
	<p>Score: <%=score%> / <%= total%> (<%=quizManager.convertToPercStr(score, total) %>)</p>
	<p>Time Taken: <%=request.getParameter("time_taken")%></p> 
	
	<form action="QuizReviewServlet" method="post">
		<p>Review:</p>
		<textarea name="review" rows="5" cols="50"></textarea>
		<p>Rating:</p>
		<input type="radio" name="rating" value="1" class="star">
        <input type="radio" name="rating" value="2" class="star">
        <input type="radio" name="rating" value="3" class="star">
        <input type="radio" name="rating" value="4" class="star">
        <input type="radio" name="rating" value="5" class="star">
        <br>
		
        <input type="hidden" name="quiz_id" value="<%=quiz_id%>" />
        <input type="hidden" name="score" value="<%=score%>" />
        <input type="hidden" name="total" value="<%=total%>" />
        <input type="hidden" name="time_taken" value="<%=request.getParameter("time_taken")%>" />
        <input type="hidden" name="name" value="<%=name%>" />
        <input type="submit" name="finish" value="Finish" />
	</form>
	
	
	<% ArrayList<QuizHistory> pastPerformance = quizManager.getQuizHistory(quiz_id, user_id, "past performance"); %>
	<% if(pastPerformance.size() > 0){ %>
	<div class="card">
	<h5>Your Past Performance</h5>
		<ul><%
		int size = pastPerformance.size();
		for (int i = 0; i < size; i++) {
			out.println("<li>");
	        out.println("Date: " + pastPerformance.get(i).getTimeStamp() + " Score: " + pastPerformance.get(i).getScore());
		}
		%></ul>
	</div>
	<% } %>
	
	<% ArrayList<Integer> friendsList = friendsManager.getFriends(user_id); %> 
	<% if(friendsList.size() > 0){ %>
	<div class="card">
		<h5>Your Friends' Performances</h5>
		<ul>
		<%for (int i = 0 ; i < friendsList.size() ; i++) { %>
			<% if (quiz.getAuthorID() != friendsList.get(i)) { %>
				<li><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendsList.get(i))%>"><%=userManager.getNameByID(friendsList.get(i))%> (<%=quizManager.convertToPercStr(userManager.getTopScore(user_id, quiz_id), total)%>)</a></li>	
			<%}%>
		<%}%>
		</ul>
	</div>
	<% } %>
		
	
	<div class="card">
	<h5>Challenge Friends</h5>
		<% if (friendsList.size() > 0) { %>
			<ul>
			<%for (int i = 0 ; i < friendsList.size() ; i++) { %>
				<% if (quiz.getAuthorID() != friendsList.get(i)) { %>
					<li><a href="friend_homepage.jsp?friendEmail=<%=userManager.getEmailByID(friendsList.get(i))%>"><%=userManager.getNameByID(friendsList.get(i))%></a>
					<% if (messageManager.alreadyChallenged(user_id, friendsList.get(i), quiz_id)) { %>
						<p> Already sent a challenge! </p>
					<%} else {%>
						<form action="ChallengeServlet" method="post">
							<input type="hidden" value=<%=user_id%> name="sender_id" />
							<input type="hidden" value=<%=friendsList.get(i)%> name="receiver_id" />
							<input type="hidden" value=<%=quiz_id%> name="quiz_id" />
							<input type="hidden" name="time_taken" value="<%=request.getParameter("time_taken")%>" />
							<input type="hidden" value=<%=score%> name="score" />
							<input type="submit" value="Send Challenge" name="decision" />
						</form>
					<%}%>
					</li>	
				<%}%>
			<%}%>
			</ul>
		<%} else {%>
			<p> Currently don't have any friend to challenge. </p>
		<%}%>
	</div>

</body>
</html>