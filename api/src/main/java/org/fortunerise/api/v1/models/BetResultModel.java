package org.fortunerise.api.v1.models;

public class BetResultModel {
    private boolean win;
    private double amount;

    // Constructor
    public BetResultModel(boolean win, double amount) {
        this.win = win;
        this.amount = amount;
    }

    // Getters and Setters
    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
