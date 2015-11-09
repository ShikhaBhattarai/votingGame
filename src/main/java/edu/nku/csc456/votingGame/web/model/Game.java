// Game.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.model;

/**
 * Created by Angel on 11/8/15.
 */

public class Game {
    Integer g_id;
    String g_creator;
    //String l_name;
    //String u_name;
    //Integer g_won;
    //String lastchatwith;
    //LocalDateTime  lastchattime;
    boolean isStarted;

    public Game() {
        this.g_id = null;
        this.g_creator = "";
        //this.l_name = "";
        //this.u_name = "";
        //this.g_won = 0;
        this.isStarted = false;
    }

    public Game(Integer g_id) {
        this.g_id = g_id;
    }

    public Game(Integer g_id,String g_creator) {
        this.g_id = g_id;
        this.g_creator = g_creator;
        //this.g_won = g_won;
    }

    public Game(Integer g_id, String g_creator, Boolean isStarted) {
        this.g_id = g_id;
        this.g_creator = g_creator;
        this.isStarted = isStarted;
        //this.u_name = u_name;
    }

    /*public Player(String e_mail, String f_name,String l_name, String u_name, Integer g_won) {
        this.e_mail = e_mail;
        this.f_name = f_name;
        this.l_name = l_name;
        this.u_name = u_name;
        this.g_won = g_won;
    }*/

    public Integer getG_id() {
        return g_id;
    }

    public String getG_creator() {
        return g_creator;
    }

    public boolean isStarted() {
        return isStarted;
    }

    /*public String getL_name() {
        return l_name;
    }

    public String getU_name() {
        return u_name;
    }

    public Integer getG_won() {
        return g_won;
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

    public void setG_id(Integer g_id) {
        this.g_id = g_id;
    }

    public void setG_creator(String g_creator) {
        this.g_creator = g_creator;
    }

    public void setStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    /*public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public void setG_won(Integer g_won) {
        this.g_won = g_won;
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
