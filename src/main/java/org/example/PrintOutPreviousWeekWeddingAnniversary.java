package org.example;

/*
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PrintOutPreviousWeekWeddingAnniversary {

    public static void printPreviousWeekWeddingAnniversaries(List<FamilyMembersData> familyMembers) {
        LocalDate currentDate = LocalDate.now();
        LocalDate previousSunday = currentDate.minusDays(currentDate.getDayOfWeek().getValue());
        LocalDate previousSaturday = previousSunday.minusDays(1);
        LocalDate startOfPreviousWeek = previousSunday.minusWeeks(1);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        System.out.println("Wedding Anniversaries for the Previous Week from Sunday to Saturday:");

        for (FamilyMembersData member : familyMembers) {
            LocalDate anniversaryDate = member.getWeddingAnniversary();
            if (isInPreviousWeek(anniversaryDate, startOfPreviousWeek, previousSaturday)) {
                System.out.println("Name: " + member.getFirstName() + " " + member.getLastName() +
                        ", Wedding Anniversary: " + anniversaryDate.format(dateFormatter));
            }
        }
    }

    private static boolean isInPreviousWeek(LocalDate date, LocalDate startOfPreviousWeek, LocalDate previousSaturday) {
        return !date.isBefore(startOfPreviousWeek) && !date.isAfter(previousSaturday);
    }
}
*/
