package org.fortunerise.entities;

import javax.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private BigDecimal balance;

    @OneToOne(mappedBy = "wallet")
    private User user;

    public Wallet() {
        this.balance = new BigDecimal("0.00");
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }
}
