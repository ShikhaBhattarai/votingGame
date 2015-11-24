// Question.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.model;

public class Question {
    String question;
    String creator;

    public Question() {
        this.question = "";
        this.creator = "";
    }

    public Question (String question) {
        this.question = question;
    }

    public Question (String question, String creator) {
        this.question = question;
        this.creator = creator;
    }

    public String getQuestion() {
        return question;
    }

    public String getCreator() {
        return creator;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}