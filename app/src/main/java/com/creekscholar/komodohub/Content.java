package com.creekscholar.komodohub;

public class Content {

    private long contentId;
    private String title;
    private String description;
    private String type;
    private String filePath;
    private String publishDate;
    private long classId;
    private long teacherId;

    // Constructor
    public Content(long contentId, String title, String description, String type, String filePath, String publishDate, long classId, long teacherId) {
        this.contentId = contentId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.filePath = filePath;
        this.publishDate = publishDate;
        this.classId = classId;
        this.teacherId = teacherId;

    }

    public Content(String title, String description, String filePath, String publishDate) {
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.publishDate = publishDate;
    }

    public Content(String title, String description, String filePath) {
        this.title = title;
        this.description = description;
        this.filePath = filePath;
    }

    // Getters and Setters

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

}
