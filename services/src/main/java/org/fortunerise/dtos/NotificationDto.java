package org.fortunerise.dtos;

public class NotificationDto {
    int id;
    private String date; // Time of notification
    private String message; // Message of notification
    private boolean read; // True if notification has been read

    public NotificationDto(int id, String date, String msg, boolean read){
        this.id = id;
        this.date = date;
        this.message = msg;
        this.read = read;
    }

    public int getID(){
        return this.id;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

}
