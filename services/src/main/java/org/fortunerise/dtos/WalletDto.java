package org.fortunerise.dtos;

import org.fortunerise.entities.Wallet;

import java.math.BigDecimal;

public class WalletDto {

    private BigDecimal balance;


    public WalletDto(){

    }

    public WalletDto(Wallet wallet){
        this.balance = wallet.getBalance();
    }


    public static WalletDto convertWalletToWalletDto(Wallet wallet){
        if (wallet == null){
            return null;
        }

        return new WalletDto(wallet);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
