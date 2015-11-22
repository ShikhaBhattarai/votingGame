// SendEmailInviteServlet.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.servlet;

import java.io.IOException;
import java.lang.String;
import java.util.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Angel on 11/8/15.
 */

@WebServlet(urlPatterns = {"/invite"})
public class SendEmailInviteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String g_id = req.getParameter("g_id");
        String g_creator = req.getParameter("g_creator");
        String e_mail = req.getParameter("e_mail");
        System.out.println("g_id: " + g_id + " g_creator: " + g_creator + " e_mail: " + e_mail);

        sendEmail(g_id, g_creator, e_mail);
    }

    public static void sendEmail(String g_id, String g_creator , String recipient) {
        final String emailAddress = "URStem2015@gmail.com";
        final String passWord = "UR$t3mNKU";
        String inviteSubject = "You have been invited to play The Voting Game!";
        String inviteBody = "Hello there!" + "\n\nYou have been invited to play The Voting Game by " + g_creator + "." + "\n\nIf you would like to play, just <a href=\"http://localhost:8080/the-voting-game\">Login</a> and join game ID: " + g_id + ".\n\nThanks!\nThe Voting Game Team";

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
            message.setFrom(new InternetAddress("URStem2015@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(inviteSubject);
            message.setContent(inviteBody, "text/html");
            Transport.send(message);

            System.out.println("Player has been invited to join your game");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
