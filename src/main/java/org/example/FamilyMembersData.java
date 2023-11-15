package org.example;

import java.time.LocalDate;

public class FamilyMembersData {
    private String membershipEmail;
    private int memberId;
    private String relationshipToHeadOfHouseHold;
    private String firstName;
    private String lastName;
    private String email;
    private String cellNumber;
    private String alternativeNumber;
    private LocalDate dob;
    private int yearOfBirth;
    private String gender;
    private String maritalStatus;
    private LocalDate weddingAnniversary;
    private int yearOfWedding;
    private LocalDate baptismDate;
    private int yearOfBaptism;
    private String notes;

    public FamilyMembersData(String membershipEmail, int memberId, String relationshipToHeadOfHouseHold, String firstName, String lastName, String email, String cellNumber, String alternativeNumber, LocalDate dob, int yearOfBirth, String gender, String maritalStatus, LocalDate weddingAnniversary, int yearOfWedding, LocalDate baptismDate, int yearOfBaptism, String notes) {
        this.membershipEmail = membershipEmail;
        this.memberId = memberId;
        this.relationshipToHeadOfHouseHold = relationshipToHeadOfHouseHold;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cellNumber = cellNumber;
        this.alternativeNumber = alternativeNumber;
        this.dob = dob;
        this.yearOfBirth = yearOfBirth;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.weddingAnniversary = weddingAnniversary;
        this.yearOfWedding = yearOfWedding;
        this.baptismDate = baptismDate;
        this.yearOfBaptism = yearOfBaptism;
        this.notes = notes;
    }

    public String getMembershipEmail() {
        return membershipEmail;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getRelationshipToHeadOfHouseHold() {
        return relationshipToHeadOfHouseHold;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public String getAlternativeNumber() {
        return alternativeNumber;
    }

    public LocalDate getDob() {
        return dob;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public LocalDate getWeddingAnniversary() {
        return weddingAnniversary;
    }

    public int getYearOfWedding() {
        return yearOfWedding;
    }

    public LocalDate getBaptismDate() {
        return baptismDate;
    }

    public int getYearOfBaptism() {
        return yearOfBaptism;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return "membershipEmail='" + membershipEmail +
                ", memberId=" + memberId +
                ", relationshipToHeadOfHouseHold='" + relationshipToHeadOfHouseHold +
                ", firstName='" + firstName +
                ", lastName='" + lastName +
                ", email='" + email +
                ", cellNumber='" + cellNumber +
                ", alternativeNumber='" + alternativeNumber +
                ", dob=" + dob +
                ", yearOfBirth=" + yearOfBirth +
                ", gender='" + gender +
                ", maritalStatus='" + maritalStatus +
                ", weddingAnniversary=" + weddingAnniversary +
                ", yearOfWedding=" + yearOfWedding +
                ", baptismDate=" + baptismDate +
                ", yearOfBaptism=" + yearOfBaptism +
                ", notes='" + notes;
    }
}


