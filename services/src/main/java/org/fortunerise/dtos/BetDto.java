package org.fortunerise.dtos;

public class BetDto {
    private String type; // e.g., "number", "color", "odd/even"
    private double amount;
    private int number;

    // Default constructor for JSON deserialization
    public BetDto() {}

    // Constructor
    public BetDto(String type, double amount, int number) {
        this.type = type;
        this.amount = amount;
        this.number = number;
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getNumberSelected() {
        return number;
    }

    public void setNumberSelected(int number) {
        this.number = number;
    }
}
