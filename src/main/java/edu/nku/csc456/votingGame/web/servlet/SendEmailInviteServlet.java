// SendEmailInviteServlet.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.servlet;

import java.lang.String;
import java.util.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.servlet.annotation.WebServlet;

/**
 * Created by Angel on 11/8/15.
 */

@WebServlet(urlPatterns = {"/invite"})
public class SendEmailInviteServlet {
    public static void sendEmail(String g_creatorF_name, Integer g_id, String recipient) {
        final String emailAddress = "URStem2015@gmail.com";
        final String passWord = "UR$t3mNKU";
        String inviteSubject = "You have been invited to play The Voting Game!";
        String inviteBody = "Hello there!" + "\n\nYou have been invited to play The Voting Game by " + g_creatorF_name + "." + "\n\nIf you would like to play, just <a href=\"http://localhost:8080/the-voting-game\">Login</a> or <a href=\"http://localhost:8080/the-voting-game\">Register</a> and join game ID: " + g_id + ".\n\nThanks!\nThe Voting Game Team";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAddress, passWord);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("TheVotingGameTeam@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(inviteSubject);
            message.setText(inviteBody);
            Transport.send(message);

            System.out.println("Player has been invited to join your game");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
