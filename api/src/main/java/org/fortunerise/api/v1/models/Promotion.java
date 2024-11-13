package org.fortunerise.api.v1.models;

public class Promotion {
    private String message; // Message for a promotion

    public Promotion(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
