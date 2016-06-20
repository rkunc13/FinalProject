package servlets;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.QuestionData;
import Database.QuizData;
import Model.FillInTheBlank;
import Model.MultiAnswer;
import Model.MultipleChoice;
import Model.MultipleChoiceMultipleAnswers;
import Model.PictureResponse;
import Model.QuestionResponse;

/**
 * Servlet implementation class QuestionCreationServlet
 */
@WebServlet("/QuestionCreationServlet")
public class QuestionCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionCreationServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QuestionData quesManager = (QuestionData) getServletContext().getAttribute("question manager");
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		int user_id = (Integer) request.getSession().getAttribute("user id"); 
		int points = Integer.parseInt(request.getParameter("points"));
		
		if (request.getParameter("previous") != null) {
			RequestDispatcher dispatch = request.getRequestDispatcher("add_question.jsp?quiz_id="+quiz_id+"&points="+points+"&edit_mode="+request.getParameter("edit_mode"));
			dispatch.forward(request, response); 
		} else {
			if (request.getSession().getAttribute("user id") != null) {
				user_id = (Integer) request.getSession().getAttribute("user id");
			}
			String prompt = request.getParameter("prompt");
			String answer = request.getParameter("answer");
			if (request.getParameter("ques_type").equals("question_response")) {
				QuestionResponse questionObj = new QuestionResponse(quiz_id, user_id, prompt, answer);
				quesManager.addQuestion(quiz_id, "QuestionResponse", questionObj);
				points++;
			} else if (request.getParameter("ques_type").equals("fill_blank")) {
				FillInTheBlank questionObj = new FillInTheBlank(quiz_id, user_id, prompt, answer);
				quesManager.addQuestion(quiz_id, "FillInTheBlank", questionObj);
				points+=questionObj.getNumBlanks();
			} else if (request.getParameter("ques_type").equals("multiple_choice")) {
				String choices = request.getParameter("choices");
				MultipleChoice questionObj = new MultipleChoice(quiz_id, user_id, prompt, choices, answer);
				quesManager.addQuestion(quiz_id, "MultipleChoice", questionObj);
				points++;
			} else if (request.getParameter("ques_type").equals("picture")) {
				PictureResponse questionObj = new PictureResponse(quiz_id, user_id, prompt, answer);
				quesManager.addQuestion(quiz_id, "PictureResponse", questionObj);
				points++;
			} else if (request.getParameter("ques_type").equals("multianswer")) {
				boolean inOrder =  request.getParameter("order").equals("yes");
				int numCorrect = Integer.parseInt(request.getParameter("num_correct"));
				MultiAnswer questionObj = new MultiAnswer(quiz_id, user_id, prompt, answer, numCorrect, inOrder);
				quesManager.addQuestion(quiz_id, "MultiAnswer", questionObj);
				points+=questionObj.getNumAnswers();
			} else if (request.getParameter("ques_type").equals("multiple_choice_multiple_answers")) {
				String choices = request.getParameter("choices");
				MultipleChoiceMultipleAnswers questionObj = new MultipleChoiceMultipleAnswers(quiz_id, user_id, prompt, choices, answer);
				quesManager.addQuestion(quiz_id, "MultipleChoiceMultipleAnswers", questionObj);
				points+=questionObj.getNumAnswers();
			}
			
			if (request.getParameter("next") != null) {						// next question
				RequestDispatcher dispatch = request.getRequestDispatcher("add_question.jsp?quiz_id="+quiz_id+"&points="+points+"&edit_mode="+request.getParameter("edit_mode"));
				dispatch.forward(request, response); 	
			} else if (request.getParameter("finish") != null) {			// finished questions
				QuizData quizManager = (QuizData) getServletContext().getAttribute("quiz manager");
				quizManager.updateQuizPoints(quiz_id, points);
				if (request.getParameter("edit_mode").equals("true")) {
					RequestDispatcher dispatch = request.getRequestDispatcher("edit_question.jsp?quiz_id="+quiz_id);
					dispatch.forward(request, response); 
				} else {
					RequestDispatcher dispatch = request.getRequestDispatcher("homepage.jsp");
					dispatch.forward(request, response); 
				}	
			}
		}
	}
}