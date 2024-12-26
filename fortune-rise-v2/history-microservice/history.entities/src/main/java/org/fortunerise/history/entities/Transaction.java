package org.fortunerise.history.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "userId")
    private Integer userId;

    @Column
    private Integer walletId;

    @Column(name = "amount")
    private BigDecimal amount;

    public Transaction() {}

    public Transaction(Integer userId, Integer walletId, BigDecimal amount) {
        this.userId = userId;
        this.walletId = walletId;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public Integer getWallet() {
        return walletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWalletId() {
        return walletId;
    }

    public void setWalletId(Integer walletId) {
        this.walletId = walletId;
    }
}
