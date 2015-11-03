// Player.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.model;
import java.time.*;

public class Player {
    String e_mail;
    String f_name;
    String l_name;
    String u_name;
    Integer games_won;
    //String lastchatwith;
    //LocalDateTime  lastchattime;
    //boolean online;

    public Player() {
        this.e_mail = "";
        this.f_name = "";
        this.l_name = "";
        this.u_name = "";
    }

    public Player(String e_mail, String f_name, String l_name, String u_name) {
        this.e_mail = e_mail;
        this.f_name = f_name;
        this.l_name = l_name;
        this.u_name = u_name;
        this.games_won = games_won;
    }

    public Player(String e_mail, String f_name,String l_name, String u_name, Integer games_won) {
        this.e_mail = e_mail;
        this.f_name = f_name;
        this.l_name = l_name;
        this.u_name = u_name;
        this.games_won = games_won;
    }

    public String getE_mail() {
        return e_mail;
    }

    public String getF_name() {
        return f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public String getU_name() {
        return u_name;
    }

    public Integer getGames_won() {
        return games_won;
    }

    /*public boolean isOnline() {
        return online;
    }

    public LocalDateTime getLastchattime() {
        return lastchattime;
    }

    public String getLastchatwith() {
        return lastchatwith;
    }*/

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public void setGames_won(Integer games_won) {
        this.games_won = games_won;
    }

    /*public void setOnline(boolean online) {
        this.online = online;
    }

    public void setLastchattime(LocalDateTime lastchattime) {
        this.lastchattime = lastchattime;
    }

    public void setLastChatWith(String lastchatwith) {
        this.lastchatwith = lastchatwith;
    }*/

}