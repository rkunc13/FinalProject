<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*, Database.*, Model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="normalize.css">
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
		<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Edit Quiz</title>
	</head>
	<body>
		<%
			QuizData quizManager = (QuizData) application.getAttribute("quiz manager");
			AdminData adminManager = ((AdminData)request.getServletContext().getAttribute("admin manager"));
			int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
			adminManager.historyClearForQuiz(quiz_id);
			int user_id = (Integer) session.getAttribute("user id");
			Quiz quiz = quizManager.getQuizByID(quiz_id);
		%>
		<div style="text-align:center">
			<div style="display:inline-block">
				<h1 class="auth-center">Edit Quiz</h1><br>
				<form class="auth-center" action="QuizCreationServlet" method="post">
					<p class="auth-center">Name: <input class="field-center" type="text" name="name" value="<%=quiz.getName()%>"/></p><br><br>
					<p>Description: <br><textarea rows="4" cols="50" name="description"><%=quiz.getDescription()%></textarea></p><br>
					<p>Category: 
						<select name="category">
							<option value="movies" <% if (quiz.getCategory().equals("movies")) out.println("selected"); %>>Movies</option>
							<option value="music" <% if (quiz.getCategory().equals("music")) out.println("selected"); %>>Music</option>
							<option value="science" <% if (quiz.getCategory().equals("science")) out.println("selected"); %>>Science</option>
							<option value="sports" <% if (quiz.getCategory().equals("sports")) out.println("selected"); %>>Sports</option>
						</select>
					</p><br>
					<p>Tags (separate with comma): <br><textarea rows="4" cols="50" name="tags" ><%=quiz.getTags()%></textarea></p><br>
					<p>Random order:
					<input type="radio" name="random_order" value="yes" <% if (quiz.isRandomOrder()) out.println("checked"); %>/> Yes
					<input type="radio" name="random_order" value="no" <% if (!quiz.isRandomOrder()) out.println("checked"); %>/> No 
					</p><br>
					<p>Multiple pages:
					<input type="radio" name="multiple_pages" value="yes" <% if (quiz.isMultiplePages()) out.println("checked"); %>/> Yes
					<input type="radio" name="multiple_pages" value="no" <% if (!quiz.isMultiplePages()) out.println("checked"); %>/> No 
					</p><br>
					<p>Immediate correction:
					<input type="radio" name="immediate_correction" value="yes" <% if (quiz.isImmediateCorrection()) out.println("checked"); %>/> Yes
					<input type="radio" name="immediate_correction" value="no" <% if (!quiz.isImmediateCorrection()) out.println("checked"); %>/> No 
					</p><br>
					<input type="hidden" name="quiz_id" value="<%=quiz_id%>"/>
					<input type="hidden" name="edit_mode" value="true"/>
					<input  class="auth-center" type="submit" value="Continue Editing" />
				</form>
			</div>
		</div>
	</body>
</html>