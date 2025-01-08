package org.fortunerise.game.services.bets;

import org.fortunerise.game.services.bets.Bet;

import java.math.BigDecimal;


public class SplitBet extends Bet {
    private Integer field1;

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
