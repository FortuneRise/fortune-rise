package org.fortunerise.notification.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Boolean read;

    @Column
    private String content;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    private Integer userId;

    public Notification(){}

    public Notification(Integer userId, String content, Date date){
        this.read = Boolean.FALSE;
        this.content = content;
        this.date = date;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUser() {
        return userId;
    }

    public void setUser(Integer user) {
        this.userId = user;
    }
}
