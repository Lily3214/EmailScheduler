package org.example;

import java.time.LocalDate;

public class FamilyMembersData {
    private int memberId;
    private String email;
    private String name;
    private LocalDate dob;
    private String cellNumber;
    private String alternativeNumber;
    private String maritalStatus;
    private LocalDate weddingAnniversary;
    private LocalDate baptismDate;
    private String gender;
    private String note;

    public FamilyMembersData(int memberId, String email, String name, LocalDate dob, String cellNumber, String alternativeNumber, String maritalStatus, LocalDate weddingAnniversary, LocalDate baptismDate, String gender, String note) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.dob = dob;
        this.cellNumber = cellNumber;
        this.alternativeNumber = alternativeNumber;
        this.maritalStatus = maritalStatus;
        this.weddingAnniversary = weddingAnniversary;
        this.baptismDate = baptismDate;
        this.gender = gender;
        this.note = note;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public String getAlternativeNumber() {
        return alternativeNumber;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public LocalDate getWeddingAnniversary() {
        return weddingAnniversary;
    }

    public LocalDate getBaptismDate() {
        return baptismDate;
    }

    public String getGender() {
        return gender;
    }

    public String getNote() {
        return note;
    }
}
