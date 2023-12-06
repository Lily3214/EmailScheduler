package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    private static final String FILE_NAME = "familyMembers.csv";
    private static final List<FamilyMembersData> familyMembersList = new ArrayList<>();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/d/yyyy");

    public static void main(String[] args) {
        // Send birthday and anniversary emails
        sendEventEmails();

        // Load family members' data
        loadFamilyMembersData(FILE_NAME);

        // User menu using switch
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nWelcome to Birthday Printer App");
            System.out.println("Choose an option:");
            System.out.println("A) Print previous week from Sunday to Saturday birthday");
            System.out.println("B) Print previous week from Sunday to Saturday wedding anniversary");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    printPreviousWeekBirthdayLists();
                    break;
                case "B":
                    printPreviousWeekWeekWeddingAnniversaries();
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
        scanner.close();
    }

    // send event email
    private static void sendEventEmails() {
        // send birthday notification
        BirthdayEmailSender birthdayEmailSender = new BirthdayEmailSender();
        birthdayEmailSender.sendBirthdayEmails();

        // send wedding anniversary notification
        WeddingAnniversaryEmailSender weddingAnniversaryEmailSender = new WeddingAnniversaryEmailSender();
        weddingAnniversaryEmailSender.sendAnniversaryEmails();
    }

    // load all family members data from csv file
    private static void loadFamilyMembersData(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.readLine();
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 11) {
                    try {
                        FamilyMembersData familyMember = createFamilyMember(parts);
                        familyMembersList.add(familyMember);
                    } catch (Exception e) {
                        handleDataParsingError(lineNumber, e.getMessage());
                    }
                } else {
                    System.out.println("Incomplete data in line " + lineNumber);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("Error loading family members' data: " + e.getMessage());
        }
    }

    private static FamilyMembersData createFamilyMember(String[] parts) {
        int memberId = Integer.parseInt(parts[0]);
        String email = parts[1];
        String name = parts[2];
        LocalDate dob = LocalDate.parse(parts[3], DATE_FORMATTER);
        String cellNumber = parts[4];
        String alternativeNumber = parts[5];
        String gender = parts[6];
        String maritalStatus = parts[7];
        LocalDate weddingAnniversary = LocalDate.parse(parts[8], DATE_FORMATTER);
        LocalDate baptismDate = LocalDate.parse(parts[9], DATE_FORMATTER);
        String note = parts[10];

        return new FamilyMembersData(memberId, email, name, dob, cellNumber, alternativeNumber, maritalStatus, weddingAnniversary, baptismDate, gender, note);
    }

    // parse error handler
    private static void handleDataParsingError(int lineNumber, String errorMessage) {
        System.out.println("Error parsing line " + lineNumber + ": " + errorMessage);
    }

    private static void printPreviousWeekBirthdayLists() {
        LocalDate currentDate = LocalDate.now();
        // Calculate the date for the most recent Sunday
        LocalDate previousSunday = currentDate.minusDays(currentDate.getDayOfWeek().getValue());

        // Calculate the date for the previous Saturday
        LocalDate previousSaturday = previousSunday.minusDays(1);

        // Calculate the date for the previous Sunday
        LocalDate startOfPreviousWeek = previousSunday.minusWeeks(1);

        List<FamilyMembersData> birthdayList = new ArrayList<>();

        System.out.println("List of previous week Birthday Lists");
        for (FamilyMembersData familyMember : familyMembersList) {
            LocalDate dob = familyMember.getDob();
            if (!dob.isBefore(startOfPreviousWeek) && !dob.isAfter(previousSaturday)) {
                birthdayList.add(familyMember);
            }
        }

        birthdayList.sort(Comparator.comparing(f -> f.getDob().getDayOfWeek().getValue()));

        for (FamilyMembersData familyMember : birthdayList) {
            printMemberDetails(familyMember);
        }
    }

    private static void printPreviousWeekWeekWeddingAnniversaries() {
        LocalDate currentDate = LocalDate.now();
        // Calculate the date for the most recent Sunday
        LocalDate previousSunday = currentDate.minusDays(currentDate.getDayOfWeek().getValue());

        // Calculate the date for the previous Saturday
        LocalDate previousSaturday = previousSunday.minusDays(1);

        // Calculate the date for the previous Sunday
        LocalDate startOfPreviousWeek = previousSunday.minusWeeks(1);

        List<FamilyMembersData> anniversaryList = new ArrayList<>();

        System.out.println("List of previous week Wedding Anniversary");
        for (FamilyMembersData familyMember : familyMembersList) {
            LocalDate anniversary = familyMember.getWeddingAnniversary();
            if (anniversary != null && !anniversary.isBefore(startOfPreviousWeek) && !anniversary.isAfter(previousSaturday)) {
                anniversaryList.add(familyMember);
            }
        }

        anniversaryList.sort(Comparator.comparing(f -> f.getWeddingAnniversary().getDayOfWeek().getValue()));

        for (FamilyMembersData familyMember : anniversaryList) {
            printMemberDetails(familyMember); // Use the existing method for printing member details
        }
    }

    // returns a boolean value. this method check if the date is within next week from Sunday to Saturday
    private static boolean isInNextWeek(LocalDate dob, LocalDate nextSunday, LocalDate nextSaturday) {
        return !dob.isBefore(nextSunday) && !dob.isAfter(nextSaturday);
    }

    private static void printMemberDetails(FamilyMembersData familyMember) {
        System.out.println(
                "Member ID: " + familyMember.getMemberId()  +
                        ", Name: " + familyMember.getName() +
                        ", Email: " + familyMember.getEmail()+
                        ", Cell Number: " + familyMember.getCellNumber() +
                        ", Birthday: " + familyMember.getDob().format(DATE_FORMATTER) +
                        ", Gender: " + familyMember.getGender() +
                        ", Marital Status: " + familyMember.getMaritalStatus() +
                        ", Wedding Anniversary: " + formatDate(familyMember.getWeddingAnniversary()) +
                        ", Baptism Date: " + formatDate(familyMember.getBaptismDate()) +
                        ", Notes: " + familyMember.getNote()
        );
    }
    private static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : "N/A";
    }
}
