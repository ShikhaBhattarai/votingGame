package edu.nku.csc456.votingGame.web.listener;

import edu.nku.csc456.votingGame.web.repository.CardRepository;
import edu.nku.csc456.votingGame.web.repository.UserRepository;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class MysqlContextListener implements ServletContextListener {

    public static final String DATABASE_CONNECTION_KEY = "dbconn";
    public static final String USER_REPOSITORY_KEY = "userRepository";
    public static final String CARD_REPOSITORY_KEY = "cardRepository";

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
            sce
                .getServletContext()
                .setAttribute(DATABASE_CONNECTION_KEY, connection);

            sce
                .getServletContext()
                .setAttribute(USER_REPOSITORY_KEY, new UserRepository(connection));

            sce
                .getServletContext()
                .setAttribute(MESSAGE_REPOSITORY_KEY, new MessageRepository(connection));

            sce
                .getServletContext()
                .setAttribute(UNREAD_REPOSITORY_KEY, new UnreadRepository(connection));

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