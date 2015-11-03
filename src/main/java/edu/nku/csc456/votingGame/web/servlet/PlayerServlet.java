// PlayerServlet.java
// for Chatty Cathy

package edu.nku.csc456.votingGame.web.servlet;

import edu.nku.csc456.votingGame.web.listener.MysqlContextListener;
import edu.nku.csc456.votingGame.web.model.Player;
//import edu.nku.csc456.votingGame.web.repository.CardRepository;
import edu.nku.csc456.votingGame.web.repository.PlayerRepository;
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
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/players"})
public class PlayerServlet extends HttpServlet {

	PlayerRepository prepo;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		prepo = (PlayerRepository) config.getServletContext().getAttribute(MysqlContextListener.PLAYER_REPOSITORY_KEY);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");

		// called by ng-voting-game getPlayers() function
		if (action.equals("findall")) {
			// calls PlayerRepository findAll method
			List<Player> players = prepo.findAll();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(players);

			resp.setContentType("application/json");
			resp.getWriter().write(json);
			resp.flushBuffer();
		// called by ng-voting-game getCurrentUser() function
		} else if (action.equals("currentplayer")) {
			HttpSession session = req.getSession(false);
			String u_name = (String) session.getAttribute("u_name");
			// calls PlayerRepository findPlayer method
			Player p = prepo.findPlayer(u_name);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(p);
			resp.setContentType("application/json");
			resp.getWriter().write(json);
			resp.flushBuffer();
		}

	}
}