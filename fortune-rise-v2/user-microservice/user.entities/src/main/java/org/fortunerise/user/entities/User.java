package org.fortunerise.user.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_table")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(name = "user_name")
    private String userName;


    public User() {}

    public User(String name, String surname, String userName){
        this.name = name;
        this.surname = surname;
        this.userName = userName;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
