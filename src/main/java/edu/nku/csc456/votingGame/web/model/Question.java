// Question.java
// for Chatty Cathy

package edu.nku.csc456.votingGame.web.model;

import java.time.LocalDateTime;

public class Question {
    String question;
    String creator;

    public Question() {
        this.question = "";
        this.creator = "";
    }

    public Question (String question, String creator) {
        this.question = question;
        this.creator = creator;
    }

    /*public Question (String sender, String recipient, String message, LocalDateTime message_date) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.message_date = message_date;
    }*/

    /*public Integer getCard_no() {
        return card_no;
    }*/

    public String getQuestion() {
        return question;
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

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    /*public void setMessage_date(LocalDateTime message_date) {
        this.message_date = message_date;
    }*/

}