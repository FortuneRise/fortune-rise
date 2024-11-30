package org.fortunerise.dtos;

public class PromotionDto {
    private String message; // Message for a promotion

    public PromotionDto(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
