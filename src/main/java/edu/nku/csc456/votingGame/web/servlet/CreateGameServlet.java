// CreateGameServlet.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.servlet;

import edu.nku.csc456.votingGame.web.listener.MysqlContextListener;
import edu.nku.csc456.votingGame.web.model.Game;
import edu.nku.csc456.votingGame.web.repository.GameRepository;
import edu.nku.csc456.votingGame.web.model.Player;
import edu.nku.csc456.votingGame.web.repository.PlayerRepository;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns = {"/creategame"})
public class CreateGameServlet extends HttpServlet {
	GameRepository grepo;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		grepo = (GameRepository) config.getServletContext().getAttribute(MysqlContextListener.GAME_REPOSITORY_KEY);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		GameRepository grepo = (GameRepository) getServletContext().getAttribute(MysqlContextListener.GAME_REPOSITORY_KEY);
		PlayerRepository prepo = (PlayerRepository) getServletContext().getAttribute(MysqlContextListener.PLAYER_REPOSITORY_KEY);
		String action = req.getParameter("action");

		if (action.equals("creategame")) {
			String g_creator = (String) session.getAttribute("u_name");
			grepo.newGame(g_creator);
			ImmutableMap<String, String> responseMap = ImmutableMap.<String, String>builder()
					.put("result", "created")
					.put("g_creator", g_creator)
					.build();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(responseMap);
			resp.setContentType("application/json");
			resp.getWriter().write(json);
			resp.flushBuffer();
			session.setAttribute("g_creator", g_creator);
		} else if (action.equals("getgameid")) {
			String g_creator = (String) session.getAttribute("g_creator");
			Game g = grepo.getGame(g_creator);
			Player p = prepo.findPlayer(g_creator);
			session.setAttribute("g_creator", g_creator);
			session.setAttribute("g_id", g.getG_id());
			session.setAttribute("isStarted", g.isStarted());
			session.setAttribute("g_creatorF_name", p.getF_name());
		}
	}
}