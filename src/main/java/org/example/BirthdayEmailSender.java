package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

// Class to send Birthday Email notification
public class BirthdayEmailSender {

    // SMTP server properties
    private static final String CSV_FILE_PATH = "familyMembers.csv";
    private static final String FROM_EMAIL = "yourEmail@gmail.com";
    private static final String PASSWORD = "yourPassword";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    public void sendBirthdayEmails() {
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            String[] header = reader.readNext();

            if (header != null && header.length == 11) {
                String[] record;
                while ((record = reader.readNext()) != null) {
                    String email = record[1];
                    String name = record[2];
                    String dobString = record[3];

                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Date dob = dateFormat.parse(dobString);

                    // Check if it's the person's birthday today
                    if (isTodayBirthday(dob)) {
                        try {
                            // Send birthday email to everyone in the CSV file
                            sendBirthdayEmailToAll(name);
                            System.out.println("Birthday email sent to everyone on " + name + "'s birthday");
                        } catch (MessagingException e) {
                            System.err.println("Error sending birthday email to everyone on " + name + "'s birthday: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (IOException | CsvValidationException | ParseException e) {
            System.err.println("Error processing CSV file: " + e.getMessage());
        }
    }

    private static boolean isTodayBirthday(Date dob) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
        String today = dateFormat.format(new Date());
        String birthday = dateFormat.format(dob);
        return today.equals(birthday);
    }

    private static void sendBirthdayEmailToAll(String name) throws MessagingException {
        // Setup mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, String.valueOf(PASSWORD));
            }
        });

        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(FROM_EMAIL));

        // Set the recipients' email addresses
        // Read the CSV file again and get all the email addresses
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            String[] header = reader.readNext();
            if (header != null && header.length == 11) {
                String[] record;
                String allEmails = "";
                while ((record = reader.readNext()) != null) {
                    String email = record[1];
                    allEmails += email + ",";
                }
                allEmails = allEmails.substring(0, allEmails.length() - 1);

                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(allEmails));
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        message.setSubject("Happy Birthday!");

        message.setText("Dear " + name +", Here is wishing you a very happy and blessed birthday. - St. Mary's Linden family.");

        Transport.send(message);
    }
}
