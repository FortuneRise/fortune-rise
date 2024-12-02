package org.fortunerise.entities.bets;


import org.fortunerise.entities.Bet;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
//@DiscriminatorValue("STREET")
public class StreetBet extends Bet {
    @Column
    private Integer field1;

    @Column
    private Integer field2;

    @Column
    private Integer field3;

    public StreetBet() {}

    public StreetBet(BigDecimal betAmount, Integer roll, Integer field1, Integer field2, Integer field3) {
        super(betAmount);
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.calculatePayout(roll);
    }

    @Override
    public void calculatePayout(Integer roll) {
        if (roll.equals(field1) || roll.equals(field2) || roll.equals(field3)) {
            payout = betAmount.multiply(BigDecimal.valueOf(12));
        }
        else {
            payout = BigDecimal.ZERO;
        }
    }
}
