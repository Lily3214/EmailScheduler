package org.example;

import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class ScheduledEmailSender {
        public static void main(String[] args) {
            // Define email properties
            String toEmail = "changeToYourEmail@gmail.com";
            String fromEmail = "changeToYourEmail@gmail.com";
            final String username = "changeToYourEmail@gmail.com";
            final String password = "changeToYourEmail";

            System.out.println("Sending Scheduled Email...");

            // Set up email properties for Gmail SMTP
            Properties emailProperties = new Properties();
            emailProperties.put("mail.smtp.host", "smtp.gmail.com");
            emailProperties.put("mail.smtp.port", "587"); // Use TLS port 587
            emailProperties.put("mail.smtp.auth", "true");
            emailProperties.put("mail.smtp.starttls.enable", "true"); // Use TLS

            // Create a Session with authentication
            Session session = Session.getInstance(emailProperties, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // Set Up the date and time when you want to send the email
            Calendar scheduledTime = Calendar.getInstance();
            scheduledTime.set(2023, Calendar.NOVEMBER, 5, 19, 50, 0); // Year, Month (0-based), Day, Hour, Minute, Second
            long initialDelay = scheduledTime.getTimeInMillis() - System.currentTimeMillis();

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

            ScheduledFuture<?> scheduledFuture = scheduler.schedule(
                    () -> sendScheduledEmail(session, fromEmail, toEmail),
                    initialDelay, TimeUnit.MILLISECONDS
            );
        }

    private static void sendScheduledEmail(Session session, String from, String to) {
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Scheduled Email Subject");

            // Set the email content
            message.setText("Hello, this is a scheduled email sent using JavaMail API.");

            // Send the email
            Transport.send(message);

            System.out.println("Scheduled email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
