package org.fortunerise.entities.bets;


import org.fortunerise.entities.Bet;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
//@DiscriminatorValue("CORNER")
public class CornerBet extends Bet {
    @Column
    private Integer field1;

    @Column
    private Integer field2;

    @Column
    private Integer field3;

    @Column
    private Integer field4;

    public CornerBet() {}

    public CornerBet(BigDecimal betAmount, Integer roll, Integer field1, Integer field2, Integer field3, Integer field4) {
        super(betAmount);
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.calculatePayout(roll);
    }

    @Override
    public void calculatePayout(Integer roll) {
        if (roll.equals(field1) || roll.equals(field2) || roll.equals(field3) || roll.equals(field4)) {
            payout = betAmount.multiply(BigDecimal.valueOf(8));
        }
        else {
            payout = BigDecimal.ZERO;
        }
    }
}
