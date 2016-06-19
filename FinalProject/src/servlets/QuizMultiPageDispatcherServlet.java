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

@WebServlet("/QuizDispatcherServlet")
public class QuizMultiPageDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizMultiPageDispatcherServlet() {
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
	@SuppressWarnings({ "unchecked", "deprecation" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("quit") != null) {
			RequestDispatcher disp = request.getRequestDispatcher("homepage.jsp");
			disp.forward(request, response); 
		} else {
			QuestionData qManager = (QuestionData) getServletContext().getAttribute("question manager");
			int quizID = Integer.parseInt(request.getParameter("quiz_id"));
			HttpSession session = request.getSession();
			if (request.getSession() == null)
				throw new NullPointerException();
			ArrayList<Integer> questions = (ArrayList<Integer>) session.getAttribute("questions");
			int score = Integer.parseInt(request.getParameter("score")); 
			int questionNum = Integer.parseInt(request.getParameter("question_num"));
			int questionID = questions.get(questionNum - 1);
			boolean isPractice = request.getParameter("practice_mode").equals("true");
			boolean randomOrder = request.getParameter("random_order").equals("true");
			boolean isFinished = false; 
			HashMap<Integer, Integer> quesFrequency = new HashMap<Integer, Integer>(); 
			if (isPractice) {
				quesFrequency = (HashMap<Integer, Integer>) session.getAttribute("ques_frequency");
			} else {
				isFinished = (questionNum >= questions.size());
			}
			String time = "";
			if (isFinished) {
				Date startTime = (Date) session.getAttribute("start_time");
				Calendar calendar = Calendar.getInstance();
				Date endTime = new Date(calendar.getTimeInMillis()); 
				long timeDiff = endTime.getTime() - startTime.getTime();
				int minDiff = (int) (timeDiff / (60 * 1000));
				int secDiff = (int) ((timeDiff % (60 * 1000)) / 1000);
				time = minDiff + " minutes, " + secDiff + " seconds";
				Timestamp timeTaken = new Timestamp(0, 0, 0, 0, minDiff, secDiff, 0);
				session.setAttribute("time_taken", timeTaken);
			}
			
			String type = qManager.getTypeByID(questionID);
			String correctAns = "false";
			if (type.equals("QuestionResponse")) {
				QuestionResponse question = (QuestionResponse) qManager.getQuestionByID(questionID);
				if (question.getAnswerText().equals(request.getParameter("question_" + questionNum).toLowerCase())) {
					correctAns = "true";
					score++;
				}
			} else if (type.equals("FillInTheBlank")) {
				FillInTheBlank question = (FillInTheBlank) qManager.getQuestionByID(questionID);
				int numAnswers = question.getNumBlanks();
				int partials = 0;
				for (int i = 0; i < numAnswers; i++) {
					if (question.getAnswerAsList().get(i).equals(request.getParameter("question_" + questionNum + "_" + i).toLowerCase())) {
						score++;
						partials++;
					}
				}
				if (partials == numAnswers) correctAns = "true";
			} else if (type.equals("MultipleChoice")) {
				MultipleChoice question = (MultipleChoice) qManager.getQuestionByID(questionID);
				if (question.getAnswerText().equals(request.getParameter("question_" + questionNum).toLowerCase())) {
					correctAns = "true";
					score++;
				}
			} else if (type.equals("PictureResponse")) {
				PictureResponse question = (PictureResponse) qManager.getQuestionByID(questionID);
				if (question.getAnswerText().equals(request.getParameter("question_" + questionNum).toLowerCase())) {
					correctAns = "true";
					score++;
				}
			} else if (type.equals("MultiAnswer")) {
				MultiAnswer question = (MultiAnswer) qManager.getQuestionByID(questionID);
				int numAnswers = question.getNumAnswers();
				int partials = 0;
				boolean inOrder = question.getInOrder();
				for (int i = 0; i < numAnswers; i++) {
					String input = request.getParameter("question_" + questionNum + "_" + i).toLowerCase();
					if (inOrder && question.getAnswerAsList().get(i).equals(input)) {
						score++;
						partials++;
					} else if (!inOrder) {
						HashSet<String> answers = new HashSet<String>(question.getAnswerAsList());
						if (answers.contains(input)) {
							score++;
							partials++;
							answers.remove(input);
						}
					}
				}
				correctAns = partials == numAnswers ? "true":correctAns;
			} else if (type.equals("MultipleChoiceMultipleAnswers")) {
				MultipleChoiceMultipleAnswers question = (MultipleChoiceMultipleAnswers) qManager.getQuestionByID(questionID);
				int partials = 0;
				String[] selectedAnswers = request.getParameterValues("question_" + questionNum);
				HashSet<String> answerList = new HashSet<String>(question.getAnswerAsList());
				for (int i = 0; i < selectedAnswers.length; i++) {
					if (answerList.contains(selectedAnswers[i])) {
						score++;
						partials++;
						answerList.remove(selectedAnswers[i]);
					} else {
						score--;
						partials--;
					}
				}
				correctAns = partials == question.getNumAnswers() ? "true" : correctAns;
			}
			
			if (isPractice) {
				if (correctAns.equals("true")) {		
					int newFrequency = quesFrequency.get(questionID) - 1;
					if (newFrequency == 0) {
						quesFrequency.remove(questionID);
						questions.remove(questions.indexOf(questionID));
					} else {
						quesFrequency.put(questionID, newFrequency);
					}
					isFinished = quesFrequency.isEmpty();					
				}
				if (questionNum >= questions.size()) {						
					if (randomOrder) Collections.shuffle(questions);
					questionNum = 0;
				} 
				session.setAttribute("questions", questions);
				session.setAttribute("ques_frequency", quesFrequency);
			}
			if (request.getParameter("immediate_correction").equals("true")) {
				RequestDispatcher disp = request.getRequestDispatcher("quiz_immediate_feedback.jsp?correct_answer="+correctAns+"&practice_mode="+isPractice+"&question_num="+questionNum+"&is_quiz_finished="+isFinished+"&score="+score+"&immediate_correction=true&time_taken="+time);
				disp.forward(request, response);
			} else {
				if (isFinished && isPractice) {
					RequestDispatcher disp = request.getRequestDispatcher("practice_finished.jsp"); 
					disp.forward(request, response);
				} else if (isFinished) {
					RequestDispatcher disp = request.getRequestDispatcher("quiz_results.jsp?score="+score+"&time_taken="+time);
					disp.forward(request, response);
				} else {
					RequestDispatcher disp = request.getRequestDispatcher("quiz_multiple_page_view.jsp?practice_mode="+isPractice+"&question_num="+questionNum+"&score="+score+"&quiz_id="+quizID+"&random_order="+randomOrder+"immediate_correction="+request.getParameter("immediate_correction"));
					disp.forward(request, response);
				}
			}
		}
	}
}