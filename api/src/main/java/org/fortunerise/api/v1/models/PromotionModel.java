package org.fortunerise.api.v1.models;

public class PromotionModel {
    private String message; // Message for a promotion

    public PromotionModel(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
