package org.fortunerise.api.v1.models;

public class Transaction {
    private User payer;
    private User reciever;
    private double amount;

    // Default constructor required for JSON deserialization
    public Transaction(){}

    // Constructor for manual creation
    public Transaction(User payer, User reciever, double amount){
        this.payer = payer;
        this.reciever = reciever;
        this.amount = amount;
    }



    // Getters and setters
    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public User getReciever() {
        return reciever;
    }

    public void setReciever(User reciever) {
        this.reciever = reciever;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
