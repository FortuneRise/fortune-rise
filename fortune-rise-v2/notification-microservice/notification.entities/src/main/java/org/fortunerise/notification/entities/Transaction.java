package org.fortunerise.notification.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @Column(name = "amount")
    private BigDecimal amount;

    public Transaction() {}

    public Transaction(Wallet wallet, BigDecimal amount) {
        this.wallet = wallet;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
