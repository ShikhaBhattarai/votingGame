// GameRepository.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.repository;

import edu.nku.csc456.votingGame.web.model.Game;
import edu.nku.csc456.votingGame.web.model.Player;

import java.lang.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Angel on 11/8/15.
 */

public class GameRepository {
    private Connection connection;
    private static final String CREATE_GAMEID_SQL = "INSERT INTO games (g_creator, p_u_name, is_started, p_joined) VALUES (?, ?, ?, ?)";
    private static final String SELECT_GAMEID_SQL = "SELECT * FROM games WHERE g_creator = p_u_name ORDER BY g_id DESC LIMIT 1;";
    //private static final String CREATE_GAME_SQL = "INSERT INTO games (g_id, g_creator, p_joined) VALUES (?, ?, 1)";
    private static final String UPDATE_P_JOINED_SQL = "UPDATE games g, (SELECT COUNT(g_id) cnt FROM games WHERE g_id = ?) c SET g.p_joined = c.cnt WHERE g_id = ?;";
    private static final String SELECT_CURRENT_SQL = "SELECT * FROM games WHERE g_creator = p_u_name AND g_winner='none';";
    private static final String SELECT_PREVIOUS_SQL = "SELECT * FROM games WHERE g_creator = p_u_name AND g_winner!='none';";
    private static final String INSERT_INTO_GAMES_SQL = "INSERT INTO games (g_id, g_creator, p_u_name) VALUES (?, ?, ?)";

    public GameRepository(Connection connection) {
        this.connection = connection;
    }

    public int newGameid(String g_creator, String p_u_name) {
        int g_id = 0;
        try (PreparedStatement statement = connection.prepareStatement(CREATE_GAMEID_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, g_creator);
            statement.setString(2, p_u_name);
            statement.setBoolean(3, false);
            statement.setInt(4, 1);
            statement.execute();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    g_id = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return g_id;
    }

    public Game getGameid() {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_GAMEID_SQL)) {
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Game g = new Game(resultSet.getInt("g_id"), resultSet.getString("g_creator"), resultSet.getString("p_u_name"), resultSet.getBoolean("is_started"), resultSet.getInt("p_joined"), resultSet.getInt("p_score"), resultSet.getInt("g_round"), resultSet.getString("g_winner"));
                return g;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Game g = new Game();
        return g;
    }

    public List<Game> getCurrentGames() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_CURRENT_SQL);
            List<Game> currentgames = new ArrayList<>();
            while (resultSet.next()) {
                Game g = new Game(resultSet.getInt("g_id"), resultSet.getString("g_creator"), resultSet.getString("p_u_name"), resultSet.getBoolean("is_started"), resultSet.getInt("p_joined"), resultSet.getInt("p_score"), resultSet.getInt("g_round"), resultSet.getString("g_winner"));
                currentgames.add(g);
                System.out.println("This Game is: " + g);
            }
            return currentgames;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Game> getPreviousGames() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_PREVIOUS_SQL);
            List<Game> previousgames = new ArrayList<>();
            while (resultSet.next()) {
                Game g = new Game(resultSet.getInt("g_id"), resultSet.getString("g_creator"), resultSet.getString("p_u_name"), resultSet.getBoolean("is_started"), resultSet.getInt("p_joined"), resultSet.getInt("p_score"), resultSet.getInt("g_round"), resultSet.getString("g_winner"));
                previousgames.add(g);
                System.out.println("This Game is: " + g);
            }
            return previousgames;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void joinGame(Integer g_id, String g_creator, String p_u_name) {
        try (PreparedStatement insert = connection.prepareStatement(INSERT_INTO_GAMES_SQL)) {
            insert.setInt(1, g_id);
            insert.setString(2, g_creator);
            insert.setString(3, p_u_name);
            insert.execute();
            try (PreparedStatement update = connection.prepareStatement(UPDATE_P_JOINED_SQL)) {
                update.setInt(1, g_id);
                update.setInt(2, g_id);
                update.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Do we need this now?
    public String createGamePlay(int g_id, String g_creator, String p_u_name) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_INTO_GAMES_SQL)) {
            statement.setInt(1, g_id);
            statement.setString(2, g_creator);
            statement.setString(3, p_u_name);
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Player already in game.";
        }
        return null;
    }

    /*public Player findPlayer(String u_name) {
        try (Statement statement = connection.createStatement()) {
            //this is an example of SQL injection vulnerability
            statement.execute(SELECT_PLAYER_SQL + "'" + u_name + "'");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Player p = new Player(resultSet.getString("e_mail"), resultSet.getString("f_name"), resultSet.getString("l_name"), resultSet.getString("u_name"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Player p = new Player();
        // to AuthenticateServlet where action == login
        // or to UserServlet where action == currentuser
        return p;
    }

    public List<Player> findAll() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL);
            List<Player> players = new ArrayList<>();
            while (resultSet.next()) {
                Player p = new Player(resultSet.getString("e_mail"), resultSet.getString("f_name"), resultSet.getString("l_name"), resultSet.getString("u_name"));
                players.add(p);
            }
            return players;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Player> leaderList() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(LEADER_SQL);
            List<Player> leaders = new ArrayList<>();
            while (resultSet.next()) {
                Player l = new Player(resultSet.getString("f_name"), resultSet.getString("l_name"), resultSet.getInt("g_won"));
                leaders.add(l);
            }
            return leaders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

	public void updateUserLastChatTime(String username, LocalDateTime lastchattime ) {
		Timestamp ts = Timestamp.valueOf(lastchattime);
		try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_LASTCHATTIME_SQL)) {
			statement.setString(1, username);
			statement.setTimestamp(2, ts);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateUserLastChatWith(String username, String lastChatWith){
		try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_LASTCHATWITH_SQL)) {
			statement.setString(1, username);
			statement.setString(2, lastChatWith);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public Game newGame(Integer g_id, String g_creator) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_GAME_SQL)) {
            statement.setInt(1, g_id);
            statement.setString(2, g_creator);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Game g = new Game(resultSet.getInt("g_id"), resultSet.getString("g_creator"), resultSet.getInt("p_joined"));
                return g;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Game g = new Game();
        return g;
    }

    public List<Game> newGameList(String g_creator) {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_GAMEID_SQL)) {
            statement.setString(1, g_creator);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            List<Game> newgame = new ArrayList<>();
            while (resultSet.next()) {
                Game g = new Game(resultSet.getInt("g_id"), resultSet.getString("g_creator"), resultSet.getBoolean("is_started"), resultSet.getInt("p_joined"));
                newgame.add(g);
            }
            return newgame;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }*/
}
