package org.fortunerise.history.entities.bets;


import org.fortunerise.entities.Bet;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
//@DiscriminatorValue("SPLIT")
public class SplitBet extends Bet {
    @Column
    private Integer field1;

    @Column
    private Integer field2;

    public SplitBet() {}

    public SplitBet(BigDecimal betAmount, Integer roll, Integer field1, Integer field2) {
        super(betAmount);
        this.field1 = field1;
        this.field2 = field2;
        this.calculatePayout(roll);
    }

    @Override
    public void calculatePayout(Integer roll) {
        if (roll.equals(field1) || roll.equals(field2)) {
            payout = betAmount.multiply(BigDecimal.valueOf(18));
        }
        else {
            payout = BigDecimal.ZERO;
        }
    }
}
