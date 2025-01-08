package org.fortunerise.game.services.bets;

import org.fortunerise.game.services.bets.Bet;

import java.math.BigDecimal;

public class StreetBet extends Bet {
    private Integer field1;

    private Integer field2;

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
