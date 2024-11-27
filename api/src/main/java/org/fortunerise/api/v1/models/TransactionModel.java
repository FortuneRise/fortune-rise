package org.fortunerise.api.v1.models;

public class TransactionModel {
    private UserModel payer;
    private UserModel reciever;
    private double amount;

    // Default constructor required for JSON deserialization
    public TransactionModel(){}

    // Constructor for manual creation
    public TransactionModel(UserModel payer, UserModel reciever, double amount){
        this.payer = payer;
        this.reciever = reciever;
        this.amount = amount;
    }



    // Getters and setters
    public UserModel getPayer() {
        return payer;
    }

    public void setPayer(UserModel payer) {
        this.payer = payer;
    }

    public UserModel getReciever() {
        return reciever;
    }

    public void setReciever(UserModel reciever) {
        this.reciever = reciever;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
