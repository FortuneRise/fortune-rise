package org.fortunerise.api.v1.models;

public class Bet {
    private String type; // e.g., "number", "color", "odd/even"
    private double amount;
    private int number;

    // Default constructor for JSON deserialization
    public Bet() {}

    // Constructor
    public Bet(String type, double amount, int number) {
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
