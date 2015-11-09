// GameEndpoint.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.socket;

import edu.nku.csc456.votingGame.web.repository.PlayerRepository;
//import edu.nku.csc456.votingGame.web.repository.CardRepository;
import edu.nku.csc456.votingGame.web.listener.MysqlContextListener;

import java.time.*;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/game")
public class GameEndpoint {

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Game session opened (id: " + session.getId() + ")");
	}

	@OnClose
	public void onClose(Session session) {
		//PlayerRepository urepo = new PlayerRepository(MysqlContextListener.connection);
		//LocalDateTime ldt = LocalDateTime.now();
		System.out.println("Game session closed (id: " + session.getId() + ")");
		//System.out.println("De-registering player: " + SessionHandler.getInstance().getPlayer(session) + " for session " + session.getId());
		//SessionHandler.getInstance().removeSession(SessionHandler.getInstance().getPlayer(session), session);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		PlayerRepository prepo = new PlayerRepository(MysqlContextListener.connection);
		if( message.startsWith("register=") ) {
			String u_name = message.substring("register=".length());
			System.out.println("Registering player: " + u_name + " for session " + session.getId());
			try {
				SessionHandler.getInstance().addSession(u_name, session);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/*private void sendChat(String message, Session session) {
		UserRepository urepo = new UserRepository(MysqlContextListener.connection);
		MessageRepository mrepo = new MessageRepository(MysqlContextListener.connection);
		NotReadRepository nrrepo = new NotReadRepository(MysqlContextListener.connection);
		String[] info = message.split("\\|");
		if( info.length == 3 ) {
			String from = info[0];
			String to = info[1];
			String text = info[2];
			System.out.println("creating message: '" + text + "' to " + to + " from " + from);

			Session toSession = SessionHandler.getInstance().getSession(to);
			Session fromSession = SessionHandler.getInstance().getSession(from);

			if(toSession != null){
				sendMessage(message, toSession);
			}
			if(fromSession != null){
				sendMessage(message, fromSession);
			}

			mrepo.saveMessage(from,to,text);
			urepo.updateUserLastChatWith(from, to);
			nrrepo.insertNotReadMapping(to, from);
		}
	}

	private void sendMessage(String message, Session s) {
		if( s != null ) {
			System.out.println("Sending '" + message + " to session " + s.getId());
			s.getAsyncRemote().sendText(message);
		}
	}*/

}