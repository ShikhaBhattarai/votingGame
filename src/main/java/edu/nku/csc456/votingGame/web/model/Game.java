// Game.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.model;

/**
 * Created by Angel on 11/8/15.
 */

public class Game {
    int g_id;
    String g_creator;
    //String l_name;
    //String u_name;
    //String lastchatwith;
    //LocalDateTime  lastchattime;
    boolean is_started;
    int p_joined;
    String g_winner;

    public Game() {
        this.g_id = 0;
        this.g_creator = "";
        //this.l_name = "";
        //this.u_name = "";
        this.is_started = false;
        this.p_joined = 0;
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

    public Game(Integer g_id, String g_creator, Boolean is_started, Integer p_joined, String g_winner) {
        this.g_id = g_id;
        this.g_creator = g_creator;
        this.is_started = is_started;
        this.p_joined = p_joined;
        this.g_winner = g_winner;
    }

    public int getG_id() {
        return g_id;
    }

    public String getG_creator() {
        return g_creator;
    }

    public boolean getIs_started() {
        return is_started;
    }

    public int getP_joined() {
        return p_joined;
    }

    /*public String getL_name() {
        return l_name;
    }

    public String getU_name() {
        return u_name;
    }

    public boolean isOnline() {
        return online;
    }

    public LocalDateTime getLastchattime() {
        return lastchattime;
    }

    public String getLastchatwith() {
        return lastchatwith;
    }*/

    public void setG_id(int g_id) {
        this.g_id = g_id;
    }

    public void setG_creator(String g_creator) {
        this.g_creator = g_creator;
    }

    public void setIs_started(boolean is_started) {
        this.is_started = is_started;
    }

    public void setP_joined(int p_joined) {
        this.p_joined = p_joined;
    }

    /*public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setLastchattime(LocalDateTime lastchattime) {
        this.lastchattime = lastchattime;
    }

    public void setLastChatWith(String lastchatwith) {
        this.lastchatwith = lastchatwith;
    }*/
}
