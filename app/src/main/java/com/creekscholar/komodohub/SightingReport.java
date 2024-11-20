package com.creekscholar.komodohub;

public class SightingReport extends User {

    // Fields corresponding to the columns in the database table
    private int sightingId; // Primary Key
    private String speciesName;
    private String description;
    private String photo;
    private String location;
    private String reportedDate; // Required field
    private String status; // Can be "Pending", "Approved", or "Rejected"
    private int studentId; // Foreign Key referencing the Students table

    // Constructor
    public SightingReport(int sightingId, String speciesName, String description, String photo,
                          String location, String reportedDate, String status, int studentId) {
        this.sightingId = sightingId;
        this.speciesName = speciesName;
        this.description = description;
        this.photo = photo;
        this.location = location;
        this.reportedDate = reportedDate;
        this.status = status;
        this.studentId = studentId;
    }

    public SightingReport(String speciesName, String description,
                          String location, String reportedDate, String status) {
        this.speciesName = speciesName;
        this.description = description;
        this.location = location;
        this.reportedDate = reportedDate;
        this.status = status;
    }

    // Empty constructor (optional, but useful)
    public SightingReport() {
    }

    // Getters and setters
    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(String reportedDate) {
        this.reportedDate = reportedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status.equals("Pending") || status.equals("Approved") || status.equals("Rejected")) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    // Optional: toString method for debugging or logging
    @Override
    public String toString() {
        return "SightingReport{" +
                "sightingId=" + sightingId +
                ", speciesName='" + speciesName + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", location='" + location + '\'' +
                ", reportedDate='" + reportedDate + '\'' +
                ", status='" + status + '\'' +
                ", studentId=" + studentId +
                '}';
    }
}

