package com.creekscholar.komodohub;

public class SchoolAdmin extends User {
    private String schoolName;
    private String schoolAddress;
    private String subscriptionStatus;
    private String paymentDetails;

    // Constructors
    public SchoolAdmin() {
    }

    public SchoolAdmin(long id, String name, String email,String password, String schoolName, String schoolAddress, String subscriptionStatus, String paymentDetails) {
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
        this.subscriptionStatus = subscriptionStatus;
        this.paymentDetails = paymentDetails;
    }

    // Getters and Setters

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    @Override
    public String toString() {
        return schoolName;
    }
}
