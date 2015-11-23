// Game.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.model;

/**
 * Created by Angel on 11/8/15.
 */

public class Game {
    int g_id;
    String g_creator;
    String p_u_name;
    boolean is_started;
    int p_joined;
    int p_score;
    int g_round;
    String g_winner;

    public Game() {
        this.g_id = 0;
        this.g_creator = "";
        this.p_u_name = "";
        this.is_started = false;
        this.p_joined = 0;
        this.p_score = 0;
        this.g_round = 0;
        this.g_winner = "";
    }

    public Game(String g_creator) {
        this.g_creator = g_creator;
    }

    public Game(Integer g_id, String g_creator, Integer p_joined) {
        this.g_id = g_id;
        this.g_creator = g_creator;
        this.p_joined = p_joined;
    }

    public Game(Integer g_id, String g_creator, Boolean is_started, Integer p_joined) {
        this.g_id = g_id;
        this.g_creator = g_creator;
        this.is_started = is_started;
        this.p_joined = p_joined;
    }

    public Game(Integer g_id, String g_creator, String p_u_name, Boolean is_started, Integer p_joined, Integer p_score, Integer g_round, String g_winner) {
        this.g_id = g_id;
        this.g_creator = g_creator;
        this.p_u_name = p_u_name;
        this.is_started = is_started;
        this.p_joined = p_joined;
        this.p_score = p_score;
        this.g_round = g_round;
        this.g_winner = g_winner;
    }

    public int getG_id() {
        return g_id;
    }

    public String getG_creator() {
        return g_creator;
    }

    public String getP_u_name() {
        return p_u_name;
    }

    public boolean getIs_started() {
        return is_started;
    }

    public int getP_joined() {
        return p_joined;
    }

    public int getP_score() {
        return p_score;
    }

    public int getG_round() {
        return g_round;
    }

    public String getG_winner() {
        return g_winner;
    }

    public void setG_id(int g_id) {
        this.g_id = g_id;
    }

    public void setG_creator(String g_creator) {
        this.g_creator = g_creator;
    }

    public void setP_u_name(String p_u_name) {
        this.p_u_name = p_u_name;
    }

    public void setIs_started(boolean is_started) {
        this.is_started = is_started;
    }

    public void setP_joined(int p_joined) {
        this.p_joined = p_joined;
    }

    public void setP_score(int p_score) {
        this.p_score = p_score;
    }

    public void setG_round(int g_round) {
        this.g_round = g_round;
    }

    public void setG_winner(String g_winner) {
        this.g_winner = g_winner;
    }
}
