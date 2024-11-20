package com.creekscholar.komodohub;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String role;
    private String profilePicture;

    private String gradeLevel;     // Add this for student grade level
    private String enrolledProgram; // Add this for student program

    // Teacher-specific fields
    private String specialization;
    private String hireDate;

    // Constructor
    public User(int userId, String name, String email, String password, String role, String profilePicture) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profilePicture = profilePicture;
    }

    // Constructor for User with teacher-specific fields
    public User(int userId, String name, String email, String password, String role, String profilePicture, String specialization, String hireDate) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profilePicture = profilePicture;
        this.specialization = specialization;
        this.hireDate = hireDate;
    }



    // Constructor for User with student-specific fields
    public User(int userId, String name, String email, String password, String role, String profilePicture, String specialization, String hireDate, String gradeLevel, String enrolledProgram) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profilePicture = profilePicture;
        this.specialization = specialization;
        this.hireDate = hireDate;
        this.gradeLevel = gradeLevel;
        this.enrolledProgram = enrolledProgram;
    }

    public User(String name) {
        this.name = name;
    }

    // Empty constructor
    public User() {
    }

    // Getters and setters for each field
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    // Teacher-specific getters and setters
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getEnrolledProgram() {
        return enrolledProgram;
    }

    public void setEnrolledProgram(String enrolledProgram) {
        this.enrolledProgram = enrolledProgram;
    }

    @Override
    public String toString() {
        return name; // Display the name of the user in the ListView
    }

}
