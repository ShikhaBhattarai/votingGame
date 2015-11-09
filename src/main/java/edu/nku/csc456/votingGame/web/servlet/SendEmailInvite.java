// SendEmailInvite.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.servlet;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import com.sun.xml.internal.org.jvnet.mimepull.MIMEMessage;

import java.lang.String;
import java.net.PasswordAuthentication;
import java.util.*;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.annotation.WebServlet;

/**
 * Created by Angel on 11/8/15.
 */

@WebServlet(urlPatterns = {"/invite"})
public class SendEmailInvite {
    public void sendEmail(String creatorF_name, Integer g_id, String recipient) {
        final String emailAddress = "URStem2015@gmail.com";
        final String passWord = "UR$t3mNKU";
        String inviteSubject = "You have been invited to play The Voting Game!";
        String inviteBody = "Hello there!" + "\n\nYou have been invited to play The Voting Game by " + creatorF_name + "." + "\n\nIf you would like to play, just <a href=\"http://localhost:8080/the-voting-game\">Login</a> or <a href=\"http://localhost:8080/the-voting-game\">Register</a> and join game ID: " + g_id + ".\n\nThanks!\nThe Voting Game Team";

        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.port", "587");

            Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailAddress, passWord);
                }
            });

            Message message = new MIMEMessage(session);
            recipients.stream().forEach((recipient) -> {
                try {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress.parse(recipient));
                } catch (Exception e) {}
            });

            message.setSubject(inviteSubject);
            message.setText(inviteBody);
            Transport.send(message);

            System.out.println("Player has been invited to join your game");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
