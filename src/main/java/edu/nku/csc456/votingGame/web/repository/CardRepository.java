// MessageRepository.java
// for Chatty Cathy

package edu.nku.csc456.votingGame.web.repository;

import edu.nku.csc456.votingGame.web.model.Card;
import java.sql.*;
import java.util.*;

public class CardRepository {

	private Connection connection;
	private static final String INSERT_SQL = "INSERT INTO cards (question, creator) VALUES (?, ?)";
	//private static final String SELECT_MESSAGE_SQL = "SELECT * FROM messages WHERE (sender = ? AND recipient = ?) OR (sender = ? AND recipient = ?) ORDER BY message_date";

	public CardRepository(Connection connection) {
		this.connection = connection;
	}

	public void saveCard(String question, String creator) {
		try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
			Card c = new Card(question, creator);
			statement.setString(1, c.getQuesiton());
			statement.setString(2, c.getCreator());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*public List<Card> findCards(String u1, String u2) {
		try(PreparedStatement statement = connection.prepareStatement(SELECT_MESSAGE_SQL)) {
			statement.setString(1, u1);
			statement.setString(2, u2);
			statement.setString(3, u2);
			statement.setString(4, u1);
			statement.execute();
			ResultSet resultSet = statement.getResultSet();
			List<Message> messagesList = new ArrayList<>();
			while (resultSet.next() ) {
				Message m = new Message(resultSet.getString("sender"), resultSet.getString("recipient"), resultSet.getString("message"), (resultSet.getTimestamp("message_date")).toLocalDateTime());
				messagesList.add(m);
			}
			return messagesList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}*/

}