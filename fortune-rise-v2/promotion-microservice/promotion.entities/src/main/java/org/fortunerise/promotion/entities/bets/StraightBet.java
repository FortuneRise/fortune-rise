package org.fortunerise.promotion.entities.bets;


import org.fortunerise.entities.Bet;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
//@DiscriminatorValue("STRAIGHT")
public class StraightBet extends Bet {
    @Column
    private Integer field1;

    public StraightBet() {}

    public StraightBet(BigDecimal betAmount, Integer roll, Integer field1) {
        super(betAmount);
        this.field1 = field1;
        this.calculatePayout(roll);
    }

    @Override
    public void calculatePayout(Integer roll) {
        if (roll.equals(field1)) {
            payout = betAmount.multiply(BigDecimal.valueOf(36));
        }
        else {
            payout = BigDecimal.ZERO;
        }
    }
}
