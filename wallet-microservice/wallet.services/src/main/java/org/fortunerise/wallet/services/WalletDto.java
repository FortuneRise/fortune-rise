package org.fortunerise.wallet.services;

import org.fortunerise.wallet.entities.Wallet;

import java.math.BigDecimal;

public class WalletDto {

    private BigDecimal balance;

    public WalletDto() {}

    public WalletDto(BigDecimal balance) {
        this.balance = balance;
    }

    public WalletDto(Wallet wallet){
        this.balance = wallet.getBalance();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
