// CreateGameServlet.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.servlet;

import com.google.common.base.Strings;
import com.google.common.primitives.Booleans;
import edu.nku.csc456.votingGame.web.listener.MysqlContextListener;
import edu.nku.csc456.votingGame.web.model.Game;
import edu.nku.csc456.votingGame.web.repository.GameRepository;
import edu.nku.csc456.votingGame.web.model.Player;
import edu.nku.csc456.votingGame.web.repository.PlayerRepository;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns = {"/gamecreator"})
public class CreateGameServlet extends HttpServlet {
	GameRepository grepo;
	PlayerRepository prepo;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		grepo = (GameRepository) config.getServletContext().getAttribute(MysqlContextListener.GAME_REPOSITORY_KEY);
		prepo = (PlayerRepository) config.getServletContext().getAttribute(MysqlContextListener.PLAYER_REPOSITORY_KEY);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
		String action = req.getParameter("action");
		String g_creator = req.getParameter("g_creator");
		if (action.equals("getnewgame")) {
				// calls GameRepository getGames method
				List<Game> newgame = grepo.newGameList(g_creator);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json = gson.toJson(newgame);

				resp.setContentType("application/json");
				resp.getWriter().write(json);
				resp.flushBuffer();
			}
		}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//HttpSession session = req.getSession();
		String action = req.getParameter("action");

		if (action.equals("creategameid")) {
			System.out.println("creategameid was called");
			HttpSession session = req.getSession();
			String g_creator = req.getParameter("g_creator").toLowerCase();

			int g_id = grepo.newGameid(g_creator);
			ImmutableMap<String, String> responseMap = ImmutableMap.<String, String>builder()
					.put("result", "idcreated")
					.put("g_id", Integer.toString(g_id))
					.put("g_creator", g_creator)
					.build();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(responseMap);
			resp.setContentType("application/json");
			resp.getWriter().write(json);
			resp.flushBuffer();

			System.out.println("The created Game ID is: " + g_id);
			session.setAttribute("g_id", g_id);
			session.setAttribute("g_creator", g_creator);

		} else if (action.equals("getgameid")) {
			System.out.println("getgameid servlet was called");
			HttpSession session = req.getSession();
			String g_creator = (String) session.getAttribute("g_creator");

			Game g = grepo.getGameid(g_creator);
			ImmutableMap<String, String> responseMap = ImmutableMap.<String, String>builder()
					.put("result", "idretrieved")
					.put("g_id", Integer.toString(g.getG_id()))
					.put("is_started", Boolean.toString(g.getIs_started()))
					.put("p_joined", Integer.toString(g.getP_joined()))
					.build();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(responseMap);
			resp.setContentType("application/json");
			resp.getWriter().write(json);
			resp.flushBuffer();

			System.out.println("The servlet-retrieved Game ID is: " + g.getG_id());
			session.setAttribute("g_id", g.getG_id());
			session.setAttribute("g_creator", g.getG_creator());
			session.setAttribute("is_started", g.getIs_started());
			session.setAttribute("p_joined", g.getP_joined());

		} else if (action.equals("creategame")) {
			HttpSession session = req.getSession();
			Integer g_id = (Integer) session.getAttribute("g_id");
			String g_creator = (String) session.getAttribute("g_creator");

			Game g = grepo.newGame(g_id, g_creator);
			ImmutableMap<String, String> responseMap = ImmutableMap.<String, String>builder()
					.put("result", "gamecreated")
					.build();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(responseMap);
			resp.setContentType("application/json");
			resp.getWriter().write(json);
			resp.flushBuffer();

			session.setAttribute("g_id", g.getG_id());
			session.setAttribute("g_creator", g.getG_creator());
			session.setAttribute("p_joined", g.getP_joined());

		// called by ng-voting-game getCurrentGames() function
		} else if (action.equals("getcurrentgames")) {
			System.out.println("getCurrentGames servlet was called");
			// calls GameRepository getCurrentGames method
			List<Game> currentGames = grepo.getCurrentGames();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(currentGames);

			resp.setContentType("application/json");
			resp.getWriter().write(json);
			resp.flushBuffer();
		}
	}
}