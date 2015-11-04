// PlayerRepository.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.repository;

import edu.nku.csc456.votingGame.web.model.Player;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.*;

public class PlayerRepository {

	private Connection connection;
	private static final String INSERT_SQL = "INSERT INTO players (e_mail, f_name, l_name, u_name) VALUES (?, ?, ?, ? )";
	private static final String SELECT_ALL_SQL = "SELECT * FROM players;";
	private static final String SELECT_PLAYER_SQL = "SELECT * FROM players WHERE u_name = ";
	private static final String LEADER_SQL = "SELECT f_name, l_name, g_won FROM players ORDER BY g_won DESC;";
	//private static final String UPDATE_USER_LASTCHATTIME_SQL = "UPDATE users SET lastchattime= ? WHERE username = ?";
	//private static final String UPDATE_USER_LASTCHATWITH_SQL = "UPDATE users SET lastchatwith= ? WHERE username = ?";
	//private static final String UPDATE_USER_ISONLINE_SQL = "UPDATE users SET isonline= ? WHERE username = ?";

	public PlayerRepository(Connection connection) {
		this.connection = connection;
	}

	public void savePlayer(String e_mail, String f_name, String l_name, String u_name) {
		try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
			statement.setString(1, e_mail);
			statement.setString(2, f_name);
			statement.setString(3, l_name);
			statement.setString(4, u_name.toLowerCase());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Player findPlayer(String u_name) {
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
			List<Player> leaders = new ArrayList<Player>();
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

	/*
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