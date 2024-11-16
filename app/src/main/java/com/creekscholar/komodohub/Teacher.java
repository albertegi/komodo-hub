package com.creekscholar.komodohub;


public class Teacher extends User implements DisplayableEntity {
    private String department;
    private String qualification;
    private String employmentDate;

    // Constructors, Getters, and Setters


    public Teacher() {
    }



    public Teacher(long id, String name, String email, String department, String qualification, String employmentDate) {
        this.department = department;
        this.qualification = qualification;
        this.employmentDate = employmentDate;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(String employmentDate) {
        this.employmentDate = employmentDate;
    }

    @Override
    public String toString() {
        return getName();
    }

    // Method for getDescription based on desired information
    public String getDescription() {
        return "Teacher Name: " + getName();
    }
}

