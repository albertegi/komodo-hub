package com.creekscholar.komodohub;

public class Class implements DisplayableEntity {
    private long classId;
    private String className;
    private String subject;
    private int teacherId;
    private int schoolId;

    // Empty constructor for flexibility
    public Class() {}

    // Constructor with parameters for quick initialization
    public Class(long classId, String className, String subject, int teacherId, int schoolId) {
        this.classId = classId;
        this.className = className;
        this.subject = subject;
        this.teacherId = teacherId;
        this.schoolId = schoolId;
    }

    // Getters and setters for each property
    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    // toString method for displaying class details in ListView
    @Override
    public String toString() {
        return "Class Name: " + className + "\nSubject: " + subject;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }
}

