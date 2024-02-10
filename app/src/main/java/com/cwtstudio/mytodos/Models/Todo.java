package com.cwtstudio.mytodos.Models;

public class Todo {
    private String title;
    private String description;
    private String startTime;
    private String endTime;
    private boolean isFinished;

    public Todo() {
    }

    public Todo(String title, String description, String startTime, String endTime, boolean isFinished) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isFinished = isFinished;
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

    public String getStartTime() {
        return "Start time :"+startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return "End time : "+endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}