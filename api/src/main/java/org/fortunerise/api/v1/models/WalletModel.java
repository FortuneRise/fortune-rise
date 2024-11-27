package org.fortunerise.api.v1.models;

public class WalletModel {
    private int id;
    private double balance;
    private int user_id;

    // Default constructor for JSON deserialization
    public WalletModel() {}

    // Constructor for manual creation
    public WalletModel(int id, double balance, int user_id) {
        this.id = id;
        this.balance = balance;
        this.user_id = user_id;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
