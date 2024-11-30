package org.fortunerise.dtos;

public class TransactionDto {
    private UserDto payer;
    private UserDto reciever;
    private double amount;

    // Default constructor required for JSON deserialization
    public TransactionDto(){}

    // Constructor for manual creation
    public TransactionDto(UserDto payer, UserDto reciever, double amount){
        this.payer = payer;
        this.reciever = reciever;
        this.amount = amount;
    }



    // Getters and setters
    public UserDto getPayer() {
        return payer;
    }

    public void setPayer(UserDto payer) {
        this.payer = payer;
    }

    public UserDto getReciever() {
        return reciever;
    }

    public void setReciever(UserDto reciever) {
        this.reciever = reciever;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
