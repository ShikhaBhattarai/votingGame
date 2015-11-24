// QuestionRepository.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.repository;

import edu.nku.csc456.votingGame.web.model.Question;
import java.sql.*;
import java.util.*;

public class QuestionRepository {

	private Connection connection;
	private static final String INSERT_SQL = "INSERT INTO questions (question, creator) VALUES (?, ?)";
	//private static final String SELECT_MESSAGE_SQL = "SELECT * FROM messages WHERE (sender = ? AND recipient = ?) OR (sender = ? AND recipient = ?) ORDER BY message_date";

	public QuestionRepository(Connection connection) {
		this.connection = connection;
	}

	public void addQuestion(String question, String creator) {
		try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
			statement.setString(1, question);
			statement.setString(2, creator);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*public List<Question> findQuestions(String question) {
		try(PreparedStatement statement = connection.prepareStatement(SELECT_QUESTION_SQL)) {
			statement.setString(1, question);
			statement.execute();
			ResultSet resultSet = statement.getResultSet();
			List<Question> questionsList = new ArrayList<>();
			while (resultSet.next() ) {
				Question q = new Question(resultSet.getString("question"));
				questionsList.add(q);
			}
			return questionsList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}*/

}