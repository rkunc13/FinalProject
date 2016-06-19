package servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.QuestionData;
import Model.FillInTheBlank;
import Model.MultiAnswer;
import Model.MultipleChoice;
import Model.MultipleChoiceMultipleAnswers;
import Model.PictureResponse;
import Model.QuestionResponse;

@WebServlet("/QuizSinglePageDispatcherServlet")
public class QuizSinglePageDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizSinglePageDispatcherServlet() {
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
	@SuppressWarnings({ "unchecked", "deprecation" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("quit") != null) {
			RequestDispatcher disp = request.getRequestDispatcher("homepage.jsp");
			disp.forward(request, response); 
		} else {
			QuestionData qManager = (QuestionData) getServletContext().getAttribute("question manager");
			int quizID = Integer.parseInt(request.getParameter("quiz_id"));
			HttpSession session = request.getSession();
			Date startTime = (Date) session.getAttribute("start_time");
			Calendar calendar = Calendar.getInstance();
			Date endTime = new Date(calendar.getTimeInMillis()); 
			long timeDiff = endTime.getTime() - startTime.getTime();
			int minDiff = (int) (timeDiff / (60 * 1000));
			int secDiff = (int) ((timeDiff % (60 * 1000)) / 1000);
			String time = minDiff + " minutes, " + secDiff + " seconds";
			Timestamp timeTaken = new Timestamp(0, 0, 0, 0, minDiff, secDiff, 0);
			session.setAttribute("time_taken", timeTaken);
			
			ArrayList<Integer> questions = (ArrayList<Integer>) session.getAttribute("questions");
			HashMap<Integer, Integer> questionFrequency = null;
			boolean randOrder = request.getParameter("random_order").equals("true");
			boolean isPractice = request.getParameter("practice_mode").equals("true");
			boolean isFinished = false; 
			int score = 0; 
			int questionNum = 1;
			
			if (isPractice) {
				questionFrequency = (HashMap<Integer, Integer>) request.getSession().getAttribute("ques_frequency");
			} else {
				isFinished = true;
			}
			for (int questionID : questions) {
				String questionType = qManager.getTypeByID(questionID);
				if (questionType.equals("QuestionResponse")) {
					QuestionResponse question = (QuestionResponse) qManager.getQuestionByID(questionID);
					if (question.getAnswerText().equals(request.getParameter("question_" + questionNum).toLowerCase().trim())) {
						score++;
						if (isPractice) 
							questionFrequency.put(questionID, questionFrequency.get(questionID) - 1);
					}
				} else if (questionType.equals("FillInTheBlank")) {
					FillInTheBlank question = (FillInTheBlank) qManager.getQuestionByID(questionID);
					int numAnswers = question.getNumBlanks();
					int parts = 0;
					for (int i = 0; i < numAnswers; i++) {
						if (question.getAnswerAsList().get(i).equals(request.getParameter("question_" + questionNum + "_" + i).toLowerCase().trim())) {
							score++;
							parts++;
						}
					}
					if (parts == numAnswers && isPractice) 
						questionFrequency.put(questionID, questionFrequency.get(questionID) - 1);
				} else if (questionType.equals("MultipleChoice")) {
					MultipleChoice question = (MultipleChoice) qManager.getQuestionByID(questionID);
					if (question.getAnswerText().equals(request.getParameter("question_" + questionNum).toLowerCase())) {
						score++;
						if (isPractice) questionFrequency.put(questionID, questionFrequency.get(questionID) - 1);
					}
				} else if (questionType.equals("PictureResponse")) {
					PictureResponse question = (PictureResponse) qManager.getQuestionByID(questionID);
					if (question.getAnswerText().equals(request.getParameter("question_" + questionNum).toLowerCase().trim())) {
						score++;
						if (isPractice) questionFrequency.put(questionID, questionFrequency.get(questionID) - 1);
					}
				} else if (questionType.equals("MultiAnswer")) {
					MultiAnswer question = (MultiAnswer) qManager.getQuestionByID(questionID);
					int numAnswers = question.getNumAnswers();
					int parts = 0;
					boolean inOrder = question.getInOrder();
					if (inOrder) {
						ArrayList<String> answers = question.getAnswerAsList();
						for (int i = 0; i < numAnswers; i++) {
							String input = request.getParameter("question_" + questionNum + "_" + i).toLowerCase().trim();
							if (answers.get(i).equals(input)) {
								score++;
								parts++;
							}
						}
					} else {
						HashSet<String> answers = new HashSet<String>(question.getAnswerAsList());
						for (int i = 0; i < numAnswers; i++) {
							String userInput = request.getParameter("question_" + questionNum + "_" + i).toLowerCase().trim();
							if (answers.contains(userInput)) {
								score++;
								parts++;
								answers.remove(userInput);
							}
						}
					}
					if (parts == numAnswers && isPractice) questionFrequency.put(questionID, questionFrequency.get(questionID) - 1);
				} else if (questionType.equals("MultipleChoiceMultipleAnswers")) {
					MultipleChoiceMultipleAnswers question = (MultipleChoiceMultipleAnswers) qManager.getQuestionByID(questionID);
					int partials = 0;
					String[] selectedAnswers = request.getParameterValues("question_" + questionNum);
					HashSet<String> answerList = new HashSet<String>(question.getAnswerAsList());
					for (int i = 0; i < selectedAnswers.length; i++) {
						if (answerList.contains(selectedAnswers[i].toLowerCase().trim())) {
							answerList.remove(selectedAnswers[i]);
							partials++;
							score++;
						} else {
							partials--;
							score--;
						}
					}
					if (partials == question.getNumAnswers() && isPractice) questionFrequency.put(questionID, questionFrequency.get(questionID) - 1);
				}
				questionNum++;
			}
			
			if (isPractice) {
				for (int i = 0; i < questions.size(); i++) {
					int question_id = questions.get(i);
					if (questionFrequency.get(question_id) == 0) {
						questionFrequency.remove(question_id);
						questions.remove(i);
						i--;
					}
				}
				if (randOrder) 
					Collections.shuffle(questions);
				session.setAttribute("questions", questions);
				session.setAttribute("ques_frequency", questionFrequency);
				isFinished = questionFrequency.isEmpty();
			} 
			
			if (isPractice && isFinished) {
				RequestDispatcher disp = request.getRequestDispatcher("practice_finished.jsp");
				disp.forward(request, response);
			} else if (isFinished) {
				RequestDispatcher disp = request.getRequestDispatcher("quiz_results.jsp?quiz_id="+quizID+"&score="+score+"&time_taken="+time);
				disp.forward(request, response);
			} else {
				RequestDispatcher disp = request.getRequestDispatcher("quiz_single_page_view.jsp?practice_mode="+isPractice+"&random_order=" + request.getParameter("random_order"));
				disp.forward(request, response);
			}
		}
	}
}