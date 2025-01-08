package org.fortunerise.game.services.bets;


import org.fortunerise.game.services.bets.Bet;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;


public class CornerBet extends Bet {
    private Integer field1;

    private Integer field2;

    private Integer field3;

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
