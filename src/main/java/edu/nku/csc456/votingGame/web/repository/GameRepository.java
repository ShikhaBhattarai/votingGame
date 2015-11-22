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
    private static final String CREATE_GAMEID_SQL = "INSERT INTO gameids (g_creator, is_started, p_joined) VALUES (?, ?, ?)";
    private static final String SELECT_GAMEID_SQL = "SELECT * FROM gameids WHERE g_creator = ? ORDER BY g_id DESC LIMIT 1;";
    private static final String CREATE_GAME_SQL = "INSERT INTO games (g_id, g_creator, p_joined) VALUES (?, ?, 1)";
    private static final String UPDATE_P_JOINED_SQL = "UPDATE gameids SET p_joined = p_joined + 1 WHERE g_id = ?";
    private static final String SELECT_CURRENT_SQL = "SELECT * FROM gameids WHERE g_winner='empty';";
    private static final String SELECT_PREVIOUS_SQL = "SELECT * FROM gameids WHERE g_winner!='empty';";

    public GameRepository(Connection connection) {
        this.connection = connection;
    }

    public int newGameid(String g_creator) {
        int g_id = 0;
        try (PreparedStatement statement = connection.prepareStatement(CREATE_GAMEID_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, g_creator);
            statement.setBoolean(2, false);
            statement.setInt(3, 1);
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

    public Game getGameid(String g_creator) {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_GAMEID_SQL)) {
            statement.setString(1, g_creator);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Game g = new Game(resultSet.getInt("g_id"), resultSet.getString("g_creator"), resultSet.getBoolean("is_started"), resultSet.getInt("p_joined"), resultSet.getString("g_winner"));
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
                Game g = new Game(resultSet.getInt("g_id"), resultSet.getString("g_creator"), resultSet.getBoolean("is_started"), resultSet.getInt("p_joined"), resultSet.getString("g_winner"));
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
                Game g = new Game(resultSet.getInt("g_id"), resultSet.getString("g_creator"), resultSet.getBoolean("is_started"), resultSet.getInt("p_joined"), resultSet.getString("g_winner"));
                previousgames.add(g);
                System.out.println("This Game is: " + g);
            }
            return previousgames;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void joinGame(Integer g_id) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_P_JOINED_SQL)) {
            statement.setInt(1, g_id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /*public String addToGame(int g_id, String u_name, Boolean g_creator, int p_joined) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_INTO_GAME_SQL)) {
            statement.setInt(1, g_id);
            statement.setString(2, u_name);
            statement.setBoolean(3, g_creator);
            statement.setInt(4, p_joined);
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "User already in game.";
        }
        return null;
    }*/

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
