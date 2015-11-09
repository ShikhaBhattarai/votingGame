// GameRepository.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.repository;

import edu.nku.csc456.votingGame.web.model.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Angel on 11/8/15.
 */

public class GameRepository {
    private Connection connection;
    private static final String CREATE_GAME_SQL = "INSERT INTO games (g_creator) VALUES (?)";
    //private static final String SELECT_ALL_SQL = "SELECT * FROM players;";
    private static final String SELECT_GAME_SQL = "SELECT g_id FROM games WHERE g_creator = ";
    //private static final String LEADER_SQL = "SELECT f_name, l_name, g_won FROM players ORDER BY g_won DESC;";

    public GameRepository(Connection connection) {
        this.connection = connection;
    }

    public void newGame(String g_creator) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_GAME_SQL)) {
            statement.setString(1, g_creator.toLowerCase());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Game getGame(String g_creator) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(SELECT_GAME_SQL + "'" + g_creator + "'");
            ResultSet resultSet = statement.getResultSet();
            Game g = new Game(resultSet.getInt("g_id"), resultSet.getString("g_creator"), resultSet.getBoolean("isStarted"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Game g = new Game();
        return g;
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

	public void updateUserOnlineStatus(String username, boolean isonline) {
		try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_ISONLINE_SQL)) {
			statement.setString(1, username);
			statement.setBoolean(2, isonline);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	}*/
}
