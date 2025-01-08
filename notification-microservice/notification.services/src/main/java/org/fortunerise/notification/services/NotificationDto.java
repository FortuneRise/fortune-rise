package org.fortunerise.notification.services;

import org.fortunerise.notification.entities.Notification;

import java.util.Date;

public class NotificationDto {
    private Integer id;
    private Boolean read; // True if notification has been read
    private String content; // Message of notification
    private Date date; // Time of notification



    public NotificationDto() {}

    public NotificationDto(Date date, String msg, boolean read){
        this.date = date;
        this.content = msg;
        this.read = read;
    }

    public NotificationDto(Notification notification){
        this.id = notification.getId();
        this.content = notification.getContent();
        this.read = notification.getRead();
        this.date = notification.getDate();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {return id; }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public Boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

}
