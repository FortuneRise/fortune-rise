package org.fortunerise.game.services.bets;

import org.fortunerise.game.services.bets.Bet;

import java.math.BigDecimal;

public class StraightBet extends Bet {
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
