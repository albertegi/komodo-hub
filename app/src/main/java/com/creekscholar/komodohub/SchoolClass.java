package com.creekscholar.komodohub;



public class SchoolClass implements DisplayableEntity {
    private long id;               // Unique identifier for the class
    private String className;      // Name of the class, e.g., "Math 101"
    private String gradeLevel;     // Grade level, e.g., "Grade 10"
    private long teacherId;        // ID of the teacher assigned to the class

    // Constructor
    public SchoolClass() {}

    public SchoolClass(long id, String className, String gradeLevel, long teacherId) {
        this.id = id;
        this.className = className;
        this.gradeLevel = gradeLevel;
        this.teacherId = teacherId;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String getName() {
        return "";
    }

    // Method for getDescription based on desired information
    public String getDescription() {
        return "Class Name: " + className + ", Grade Level: " + gradeLevel;
    }

//    @Override
//    public String toString() {
//        return "Class Name: " + className + ", Grade Level: " + gradeLevel;
//    }
}

