// QuestionServlet.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.servlet;

import com.google.common.collect.ImmutableMap;
import edu.nku.csc456.votingGame.web.listener.MysqlContextListener;
import edu.nku.csc456.votingGame.web.model.Question;
import edu.nku.csc456.votingGame.web.repository.QuestionRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/questions"})
public class QuestionServlet extends HttpServlet {

	QuestionRepository qrepo;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		qrepo = (QuestionRepository) config.getServletContext().getAttribute(MysqlContextListener.QUESTION_REPOSITORY_KEY);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		HttpSession session = req.getSession(false);

		// called by ng-voting-game addQuestion() function
		if (action.equals("addquestion")) {
			String question = req.getParameter("question");
			String creator = req.getParameter("creator").toLowerCase();
			// calls QuestionRepository addQuestion() method
			qrepo.addQuestion(question, creator);
			ImmutableMap<String,String> responseMap = ImmutableMap.<String, String>builder()
					.put("result", "questionadded")
					.put("question", question)
					.put("creator", creator)
					.build();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(responseMap);

			resp.setContentType("application/json");
			resp.getWriter().write(json);
			resp.flushBuffer();

			session.setAttribute("question", question);
			session.setAttribute("creator", creator);
		}
	}
}