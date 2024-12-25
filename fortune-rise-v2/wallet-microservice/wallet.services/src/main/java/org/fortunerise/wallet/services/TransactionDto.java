package org.fortunerise.wallet.services;

//import org.fortunerise.wallet.entities.Transaction;

import java.math.BigDecimal;

public class TransactionDto {
    private Integer id;
    private BigDecimal amount;
    //private Integer walletId;
    //private Integer promotionId;

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

//  public Integer getPromotionId() {
//      return promotionId;
//  }
//
//    public Integer getWalletId() {
//        return walletId;
//    }
//
//    public void setWalletId(Integer walletId) {
//        this.walletId = walletId;
//    }
}
