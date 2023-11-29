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

// Class to send Wedding anniversary Email notification
public class WeddingAnniversaryEmailSender {

    // SMTP server properties
    private static final String CSV_FILE_PATH = "familyMembers.csv";
    private static final String FROM_EMAIL = "yourEmail@gmail.com";
    private static final String PASSWORD = "yourPassword";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    public void sendAnniversaryEmails() {
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            String[] header = reader.readNext();

            if (header != null && header.length == 11) {
                String[] record;
                while ((record = reader.readNext()) != null) {
                    String email = record[1];
                    String name = record[2];
                    String anniversaryString = record[8];

                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Date anniversary = dateFormat.parse(anniversaryString);

                    // Check if it's the person's anniversary today
                    if (isTodayAnniversary(anniversary)) {
                        try {
                            // Send anniversary email to everyone in the CSV file
                            sendAnniversaryEmailToAll(name);
                            System.out.println("Anniversary email sent to everyone on " + name + "'s anniversary");
                        } catch (MessagingException e) {
                            System.err.println("Error sending anniversary email to everyone on " + name + "'s anniversary: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (IOException | CsvValidationException | ParseException e) {
            System.err.println("Error processing CSV file: " + e.getMessage());
        }
    }

    private static boolean isTodayAnniversary(Date anniversary) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
        String today = dateFormat.format(new Date());
        String anniversaryDay = dateFormat.format(anniversary);
        return today.equals(anniversaryDay);
    }

    private static void sendAnniversaryEmailToAll(String name) throws MessagingException {
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
                // Remove the last comma from the string
                allEmails = allEmails.substring(0, allEmails.length() - 1);

                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(allEmails));
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        message.setSubject("Happy Anniversary!");

        message.setText("Dear " + name +", Here is wishing you both a happy anniversary and a blessed year ahead. - St. Mary's Linden family.");

        Transport.send(message);
    }
}
