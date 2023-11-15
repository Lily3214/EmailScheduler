package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_NAME = "Birthday.csv";
    private static final List<FamilyMembersData> familyMembersList = new ArrayList<>();
    private static final String DATE_FORMAT = "MM/dd/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static void main(String[] args) {
        loadFamilyMembersData(FILE_NAME);
        BirthdayEmailSender birthdayEmailSender = new BirthdayEmailSender();
        birthdayEmailSender.sendBirthdayEmails();

        WeddingAnniversaryEmailSender sender = new WeddingAnniversaryEmailSender();
        sender.sendAnniversaryEmails();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;


        while (running) {
            System.out.println("Welcome to Birthday Printer App");
            System.out.println("Choose an option:");
            System.out.println("A) Print previous week from Sunday to Saturday birthday");
            System.out.println("B) Print previous week from Sunday to Saturday wedding anniversary");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    printPreviousWeekBirthdays(FILE_NAME);
                    break;
                case "B":
                    printPreviousWeekWeddingAnniversaries(FILE_NAME);
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

    public static void loadFamilyMembersData(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int number = 1;
            boolean skipHeader = true;
            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 17) {
                    try {
                        String membershipEmail = parts[0];
                        int memberId = Integer.parseInt(parts[1]);
                        String relationshipToHeadOfHouseHold = parts[2];
                        String firstName = parts[3];
                        String lastName = parts[4];
                        String email = parts[5];
                        String cellNumber = parts[6];
                        String alternativeNumber = parts[7];
                        LocalDate dob = LocalDate.parse(parts[8], DateTimeFormatter.ofPattern(DATE_FORMAT));
                        int yearOfBirth = Integer.parseInt(parts[9]);
                        String gender = parts[10];
                        String maritalStatus = parts[11];
                        LocalDate weddingAnniversary = LocalDate.parse(parts[12], DateTimeFormatter.ofPattern(DATE_FORMAT));
                        int yearOfWedding = Integer.parseInt(parts[13]);
                        LocalDate baptismDate = LocalDate.parse(parts[14], DateTimeFormatter.ofPattern(DATE_FORMAT));
                        int yearOfBaptism = Integer.parseInt(parts[15]);
                        String notes = parts[16];

                        FamilyMembersData familyMember = new FamilyMembersData(membershipEmail, memberId, relationshipToHeadOfHouseHold, firstName, lastName, email, cellNumber, alternativeNumber, dob, yearOfBirth, gender, maritalStatus, weddingAnniversary, yearOfWedding, baptismDate, yearOfBaptism, notes);

                        familyMembersList.add(familyMember);
                    } catch (DateTimeParseException | NumberFormatException e) {
                        System.out.println("Error parsing line " + number + ": " + e.getMessage());
                    }
                } else {
                    System.out.println("Incomplete data in line " + number);
                }
                number++;
            }
        } catch (IOException e) {
            System.out.println("Error loading family members' data: " + e.getMessage());
        }
    }

    public static void printPreviousWeekBirthdays(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;
            boolean skipHeader = true;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                String[] data = line.split(",");
                if (data.length >= 9) {
                    String email = data[0];
                    try {
                        LocalDate currentDate = LocalDate.now();
                        // Calculate the date for the most recent Sunday
                        LocalDate previousSunday = currentDate.minusDays(currentDate.getDayOfWeek().getValue());

                        // Calculate the date for the previous Saturday
                        LocalDate previousSaturday = previousSunday.minusDays(1);

                        // Calculate the date for the previous Sunday
                        LocalDate startOfPreviousWeek = previousSunday.minusWeeks(1);

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                        LocalDate dob = LocalDate.parse(data[8], formatter);

                        // Check if 'dob' falls in the previous week
                        for (FamilyMembersData familyMember : familyMembersList) {
                            LocalDate memberDob = familyMember.getDob();
                            if (!memberDob.isBefore(startOfPreviousWeek) && !memberDob.isAfter(previousSaturday)) {
                                // Print the matching member
                                System.out.println(
                                        "membershipEmail: " + familyMember.getMembershipEmail() +
                                                ", member_id: " + familyMember.getMemberId() +
                                                ", first_name: " + familyMember.getFirstName() +
                                                ", last_name: " + familyMember.getLastName() +
                                                ", email: " + familyMember.getEmail() +
                                                ", cell_number: " + familyMember.getCellNumber() +
                                                ", Birthday: " + dob.format(formatter) +
                                                ", gender: " + familyMember.getGender() +
                                                ", marital_status: " + familyMember.getMaritalStatus() +
                                                ", wedding_anniversary: " + familyMember.getWeddingAnniversary() +
                                                ", baptism_date: " + familyMember.getBaptismDate() +
                                                ", notes: " + familyMember.getNotes()
                                );
                                break;
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void printPreviousWeekWeddingAnniversaries(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");
                String email = data[0];
                LocalDate anniversary = LocalDate.parse(data[11]);

                // Check if 'anniversary' falls in the previous week
                if (anniversary.isAfter(LocalDate.now().minusWeeks(1))) {
                    System.out.println("Anniversary: " + anniversary + " for " + email);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printAllBirthdayLists() {
        System.out.println("Birthday List:");
        for (FamilyMembersData familyMembersData : familyMembersList) {
            System.out.println(familyMembersData);
        }
    }
}
