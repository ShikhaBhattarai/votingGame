// SessionHandler.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.socket;

import java.util.*;

import javax.websocket.Session;

public class SessionHandler {

	private static SessionHandler instance;
	Map<String, Session> playerToSession;
	Map<String, String> sessionIDToPlayer;

	private SessionHandler() {
		playerToSession = new HashMap<>();
		sessionIDToPlayer = new HashMap<>();
		instance = this;
	}

	public static SessionHandler getInstance() {
		if( instance == null ) {
			instance = new SessionHandler();
		}
		return instance;
	}

	public void addSession(String player, Session session) {
		playerToSession.put(player, session);
		sessionIDToPlayer.put(session.getId(), player);
	}

	public void removeSession(String player, Session session){
		playerToSession.remove(player);
		sessionIDToPlayer.remove(session.getId());
	}

	public Session getSession(String player) {
		return playerToSession.get(player);
	}

	public String getPlayer(Session session) {
		return sessionIDToPlayer.get(session.getId());
	}

	public Set<String> getPlayers() {
		return playerToSession.keySet();
	}

}