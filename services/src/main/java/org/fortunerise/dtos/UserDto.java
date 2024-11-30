package org.fortunerise.dtos;

import org.fortunerise.entities.User;

public class UserDto {
    private String name;
    private String surname;
    private String username;

    // Default constructor required for JSON deserialization
    public UserDto() {}

    // Constructor
    public UserDto(String name, String surname, String username) {
        this.name = name;
        this.surname = surname;
        this.username = username;
    }

    public UserDto(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.username = user.getUsername();
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
