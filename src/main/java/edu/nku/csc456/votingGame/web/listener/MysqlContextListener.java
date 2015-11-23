package edu.nku.csc456.votingGame.web.listener;

import edu.nku.csc456.votingGame.web.repository.QuestionRepository;
import edu.nku.csc456.votingGame.web.repository.GameRepository;
import edu.nku.csc456.votingGame.web.repository.PlayerRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MysqlContextListener implements ServletContextListener {

    public static final String DATABASE_CONNECTION_KEY = "dbconn";
    public static final String PLAYER_REPOSITORY_KEY = "playerRepository";
    public static final String QUESTION_REPOSITORY_KEY = "questionRepository";
    public static final String GAME_REPOSITORY_KEY = "gameRepository";


    public static Connection connection;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://mysql-instance1.cczvbj12qf0c.us-west-2.rds.amazonaws.com/the_voting_game?user=csc456&password=fall2015");
            //connection = DriverManager.getConnection("jdbc:mysql://localhost/the_voting_game?user=root&password=nku");
            sce
                .getServletContext()
                .setAttribute(DATABASE_CONNECTION_KEY, connection);

            sce
                .getServletContext()
                .setAttribute(PLAYER_REPOSITORY_KEY, new PlayerRepository(connection));

            sce
                .getServletContext()
                .setAttribute(QUESTION_REPOSITORY_KEY, new QuestionRepository(connection));

            sce
                .getServletContext()
                .setAttribute(GAME_REPOSITORY_KEY, new GameRepository(connection));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if( connection != null ) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}