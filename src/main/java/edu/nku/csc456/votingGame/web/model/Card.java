// Card.java
// for Chatty Cathy

package edu.nku.csc456.votingGame.web.model;

import java.time.LocalDateTime;

public class Card {
    String quesiton;
    String creator;

    public Card() {
        this.quesiton = "";
        this.creator = "";
    }

    public Card (String quesiton, String creator) {
        this.quesiton = quesiton;
        this.creator = creator;
    }

    /*public Card (String sender, String recipient, String message, LocalDateTime message_date) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.message_date = message_date;
    }*/

    /*public Integer getCard_no() {
        return card_no;
    }*/

    public String getQuesiton() {
        return quesiton;
    }

    public String getCreator() {
        return creator;
    }

    /*public LocalDateTime getMessage_date() {
        return message_date;
    }*/

    /*public void setCard_no(Integer card_no) {
        this.card_no = card_no;
    }*/

    public void setQuesiton(String quesiton) {
        this.quesiton = quesiton;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    /*public void setMessage_date(LocalDateTime message_date) {
        this.message_date = message_date;
    }*/

}