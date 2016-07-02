<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*, Database.*, Model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="normalize.css">
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
		<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>
			<%
				QuizData quizManager = (QuizData) application.getAttribute("quiz manager");
					QuestionData questionManager = (QuestionData) application.getAttribute("question manager");
					int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
					Quiz quiz = quizManager.getQuizByID(quiz_id);
					out.println(quiz.getName());
			%>
		</title>
	</head>
	<body>
	<h1 class="auth-center"><%=quiz.getName() %></h1>
		<form action="QuizDispatcherServlet" method="post">
			<div class="card">
			<%
				ArrayList<Integer> questions = (ArrayList<Integer>) session.getAttribute("questions");
				int question_number = Integer.parseInt(request.getParameter("question_num"));
				int question_id = questions.get(question_number);
				question_number++;
				String type = questionManager.getTypeByID(question_id);
				if (type.equals("QuestionResponse")) {
					QuestionResponse question = (QuestionResponse) questionManager.getQuestionByID(question_id);
					out.println("<p>" + question_number + ".) " + question.getQuestionText() + "</p>");
					out.println("<input class=\"light-edge\" type=\"text\" name=\"question_" + question_number + "\"/>");
				} else if (type.equals("FillInTheBlank")) {
					FillInTheBlank question = (FillInTheBlank) questionManager.getQuestionByID(question_id);
					out.println("<p>" + question_number + ".) " + question.getQuestionText() + "</p>");
					int num_answers = question.getNumBlanks();
					for (int i = 0; i < num_answers; i++) {
						out.println("<input class=\"light-edge\" type=\"text\" name=\"question_" + question_number + "_" + i + "\"/>");
					}
				} else if (type.equals("MultipleChoice")) {
					MultipleChoice question = (MultipleChoice) questionManager.getQuestionByID(question_id);
					out.println("<p>" + question_number + ".) " + question.getQuestionText() + "</p>");
					ArrayList<String> choices = question.getChoicesAsList();
					for (String choice : choices) {
						out.println("<input class=\"light-edge\" type=\"radio\" name=\"question_" + question_number + "\" value=\"" + choice + "\"> " + choice + "<br>");
					}
				} else if (type.equals("PictureResponse")) {
					PictureResponse question = (PictureResponse) questionManager.getQuestionByID(question_id);
					out.println("<p>" + question_number + ".) " + "</p>");
					out.println("<p><img class=\"light-edge\" src=\"" + question.getQuestionText() + "\"/></p>");
					out.println("<input class=\"light-edge\" type=\"text\" name=\"question_" + question_number + "\"/>");
				} else if (type.equals("MultiAnswer")) {
					MultiAnswer question = (MultiAnswer) questionManager.getQuestionByID(question_id);
					out.println("<p>" + question_number + ".) " + question.getQuestionText() + "</p>");
					int size = question.getNumAnswers();
					for (int i = 0; i < size; i++) {
						out.println("<p><input class=\"light-edge\" type=\"text\" name=\"question_" + question_number + "_" + i + "\"/></p>");
					}
				} else if (type.equals("MultipleChoiceMultipleAnswers")) {
					MultipleChoiceMultipleAnswers question = (MultipleChoiceMultipleAnswers) questionManager.getQuestionByID(question_id);
					out.println("<p>" + question_number + ".) " + question.getQuestionText() + "</p>");
					ArrayList<String> choices = question.getChoicesAsList();
					for (String choice : choices) {
						out.println("<input class=\"light-edge\" type=\"checkbox\" name=\"question_" + question_number + "\" value=\"" + choice + "\"> " + choice + "<br>");
					}
					out.println("<p></p>");
				} 
			%>
			</div>
			<input type="hidden" name="quiz_id" value="<%=quiz_id%>" />
			<input type="hidden" name="question_num" value="<%=question_number%>" />
			<input type="hidden" name="practice_mode" value="<%=request.getParameter("practice_mode")%>" />
			<input type="hidden" name="random_order" value="<%=request.getParameter("random_order")%> "/>
			<input type="hidden" name="score" value="<%=request.getParameter("score")%>" />
			<input type="hidden" name="immediate_correction" value="<%=request.getParameter("immediate_correction")%>" />
			<% 
			if (request.getParameter("practice_mode").equals("true")) {											
				out.println("<input type=\"submit\" name=\"quit\" value=\"Quit\" style=\"float:right\"/>");
			}
			%>
			<p><input type="submit" value="Next" style="float:right"/></p>
		</form>
	</body>
</html>