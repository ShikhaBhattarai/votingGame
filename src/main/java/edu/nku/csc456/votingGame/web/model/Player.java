// Player.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.model;

import java.lang.*;
import java.time.*;

public class Player {
    String e_mail;
    String f_name;
    String l_name;
    String u_name;
    Integer g_won;
    Integer g_lost;
    //String lastchatwith;
    //LocalDateTime  lastchattime;
    //boolean online;

    public Player() {
        this.e_mail = "";
        this.f_name = "";
        this.l_name = "";
        this.u_name = "";
        this.g_won = 0;
        this.g_lost = 0;
    }

    public Player(String f_name,String u_name, Integer g_won, Integer g_lost) {
        this.f_name = f_name;
        this.u_name = u_name;
        this.g_won = g_won;
        this.g_lost = g_lost;
    }

    public Player(String e_mail, String f_name,String l_name, String u_name) {
        this.e_mail = e_mail;
        this.f_name = f_name;
        this.l_name = l_name;
        this.u_name = u_name;
    }

    public Player(String e_mail, String f_name,String l_name, String u_name, Integer g_won) {
        this.e_mail = e_mail;
        this.f_name = f_name;
        this.l_name = l_name;
        this.u_name = u_name;
        this.g_won = g_won;
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

    public Integer getG_won() {
        return g_won;
    }

    public Integer getG_lost() {
        return g_lost;
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

    public void setG_won(Integer g_won) {
        this.g_won = g_won;
    }

    public void setG_lost(Integer g_lost) {
        this.g_lost = g_lost;
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