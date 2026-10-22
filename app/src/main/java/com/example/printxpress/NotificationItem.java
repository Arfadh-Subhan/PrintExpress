package com.example.printxpress;

public class NotificationItem {

    private String title;
    private String message;
    private String date;
    private boolean isRead;

    public NotificationItem(String title, String message, String date, boolean isRead) {
        this.title = title;
        this.message = message;
        this.date = date;
        this.isRead = isRead;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}