package com.creekscholar.komodohub;

public class Student extends User implements DisplayableEntity{

    private int studentId;
    private String admitDate;
    private String gradeLevel;
    private String enrolledProgram;
    private int classId;

    // Constructor for Student that includes all fields from User and Student
    public Student(int userId, String name, String email, String password, String role, String profilePicture,
                   int studentId, String admitDate, String gradeLevel, String enrolledProgram, int classId) {
        // Initialize superclass (User) fields
        super(userId, name, email, password, role, profilePicture);

        // Initialize subclass (Student) fields
        this.admitDate = admitDate;
        this.studentId = studentId;
        this.gradeLevel = gradeLevel;
        this.enrolledProgram = enrolledProgram;
        this.classId = classId;
    }



    public Student() {
    }


    // Getters and Setters for Student-specific fields


    public String getAdmitDate() {
        return admitDate;
    }

    public void setAdmitDate(String admitDate) {
        this.admitDate = admitDate;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    // Override toString method to include both User and Student details
    @Override
    public String toString() {
        return super.toString() + // Call toString() from User to include User fields
                ", Student Name: " + getName();
    }

    @Override
    public String getDescription() {
        return "Student Name: " + getName();
    }
}
