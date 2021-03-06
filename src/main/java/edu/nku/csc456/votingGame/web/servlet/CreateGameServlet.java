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
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String action = req.getParameter("action");

		if (action.equals("creategameid")) {
			System.out.println("creategameid was called");
			String g_creator = req.getParameter("g_creator").toLowerCase();
			String p_u_name = req.getParameter("p_u_name").toLowerCase();

			int g_id = grepo.newGameid(g_creator, p_u_name);
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
			session.setAttribute("p_u_name", p_u_name);

		} else if (action.equals("creategameplay")) {
			System.out.println("creategameplay was called");
			Integer g_id = Integer.parseInt(req.getParameter("g_id"));
			String g_creator = req.getParameter("g_creator");
			String p_u_name = req.getParameter("p_u_name");

			grepo.createGamePlay(g_id, g_creator, p_u_name);
			ImmutableMap<String, String> responseMap = ImmutableMap.<String, String>builder()
					.put("result", "addedtogameplay")
					.put("g_id", Integer.toString(g_id))
					.put("g_creator", g_creator)
					.put("p_u_name", p_u_name)
					.build();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(responseMap);
			resp.setContentType("application/json");
			resp.getWriter().write(json);
			resp.flushBuffer();

			System.out.println("The player was added to gameplay");
			session.setAttribute("g_id", g_id);
			session.setAttribute("g_creator", g_creator);
			session.setAttribute("p_u_name", p_u_name);

		} else if (action.equals("getgameid")) {
			System.out.println("getgameid servlet was called");

			Game g = grepo.getGameid();
			ImmutableMap<String, String> responseMap = ImmutableMap.<String, String>builder()
					.put("result", "idretrieved")
					.put("g_id", Integer.toString(g.getG_id()))
					.put("is_started", Boolean.toString(g.getIs_started()))
					.put("p_joined", Integer.toString(g.getP_joined()))
					.put("g_winner", g.getG_winner())
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
			session.setAttribute("g_winner", g.getG_winner());

		} else if (action.equals("getcurrentgames")) {
			System.out.println("getCurrentGames servlet was called");
			// calls GameRepository getCurrentGames method
			List<Game> currentgames = grepo.getCurrentGames();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(currentgames);

			resp.setContentType("application/json");
			resp.getWriter().write(json);
			resp.flushBuffer();
		} else if (action.equals("getstartgames")) {
			System.out.println("getStartGames servlet was called");
			String p_u_name = req.getParameter("p_u_name");
			// calls GameRepository getStartGames method
			List<Game> startgames = grepo.getStartGames(p_u_name);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(startgames);

			resp.setContentType("application/json");
			resp.getWriter().write(json);
			resp.flushBuffer();
		} else if (action.equals("getjoingames")) {
			System.out.println("getJoinGames servlet was called");
			String p_u_name = req.getParameter("p_u_name");
			// calls GameRepository getJoinGames method
			List<Game> joingames = grepo.getJoinGames(p_u_name);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(joingames);

			resp.setContentType("application/json");
			resp.getWriter().write(json);
			resp.flushBuffer();
		} else if (action.equals("getpreviousgames")) {
			System.out.println("getPreviousGames servlet was called");
			// calls GameRepository getPreviousGames method
			List<Game> previousgames = grepo.getPreviousGames();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(previousgames);

			resp.setContentType("application/json");
			resp.getWriter().write(json);
			resp.flushBuffer();
		} else if (action.equals("joingame")) {
			System.out.println("joinGame servlet was called");
			// calls GameRepository joinGame() method

			Integer g_id = Integer.parseInt(req.getParameter("g_id"));
			String g_creator = req.getParameter("g_creator");
			String p_u_name = req.getParameter("p_u_name");

			grepo.joinGame(g_id, g_creator, p_u_name);
			ImmutableMap<String, String> responseMap = ImmutableMap.<String, String>builder()
					.put("result", "p_joinedupdated")
					.put("g_id", Integer.toString(g_id))
					//.put("g_creator", g_creator)
					.build();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(responseMap);
			resp.setContentType("application/json");
			resp.getWriter().write(json);
			resp.flushBuffer();

			System.out.println("The joined Game ID is: " + g_id + " Game creator: " + g_creator + " Player username: " + p_u_name);
			session.setAttribute("g_id", g_id);
			session.setAttribute("g_creator", g_creator);
			session.setAttribute("p_u_name", p_u_name);
		}
	}
}