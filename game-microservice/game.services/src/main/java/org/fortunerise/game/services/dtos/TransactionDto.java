package org.fortunerise.game.services.dtos;

//import org.fortunerise.history.entities.Transaction;

import java.math.BigDecimal;

public class TransactionDto {
    private Integer id;
    private BigDecimal amount;
    private Integer walletId;

    private Integer userId;

    public TransactionDto() {}

    public TransactionDto(BigDecimal amount) {
        this.amount = amount;
    }

    /*
    public TransactionDto(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        //this.walletId = transaction.getWallet().getId();
    }
    */


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


//    public Integer getPromotionId() {
//        return promotionId;
//    }
    public Integer getWalletId() {
        return walletId;
    }

    public void setWalletId(Integer walletId) {
        this.walletId = walletId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String toString() {
        return "TransactionDto [id=" + id + ", amount=" + amount + "]";
    }
}
