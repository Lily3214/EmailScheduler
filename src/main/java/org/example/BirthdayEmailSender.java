package org.example;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class BirthdayEmailSender {
    public void sendBirthdayEmails() {
        String csvFilePath = "Birthday.csv";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date today = new Date();

        try {
            CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader(csvFilePath));

            for (CSVRecord record : csvParser) {
                String emailAddress = record.get(0);
                String dateOfBirthString = record.get("dob");

                try {
                    Date dateOfBirth = dateFormat.parse(dateOfBirthString);
                    if (isBirthday(today, dateOfBirth)) {
                        sendEmail(emailAddress, record.get("first_name"));
                    }
                } catch (ParseException e) {
                    System.err.println("Error parsing date for: " + emailAddress);
                }
            }
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(String recipientEmail, String firstName) throws MessagingException {
        final String username = "Your email address"; // Your email address
        final String password = "Your email password"; // Your email password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Happy Birthday, " + firstName + "!");
        message.setText("Wishing you a fantastic birthday! Have a wonderful day.");

        Transport.send(message);
        System.out.println("Birthday wishes sent to: " + recipientEmail);
    }

    private boolean isBirthday(Date today, Date dateOfBirth) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("MM/dd");
        String todayStr = dayFormat.format(today);
        String dobStr = dayFormat.format(dateOfBirth);

        return todayStr.equals(dobStr);
    }
}
