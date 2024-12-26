package org.fortunerise.game.services.bets;

import org.fortunerise.game.services.bets.Bet;

import java.math.BigDecimal;

public class SixLineBet extends Bet {

    private Integer field1;

    private Integer field2;

    private Integer field3;

    private Integer field4;

    private Integer field5;

    private Integer field6;

    public SixLineBet() {}

    public SixLineBet(BigDecimal betAmount, Integer roll, Integer field1, Integer field2, Integer field3, Integer field4, Integer field5, Integer field6) {
        super(betAmount);
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
        this.field6 = field6;
        this.calculatePayout(roll);
    }

    @Override
    public void calculatePayout(Integer roll) {
        if (roll.equals(field1) || roll.equals(field2) || roll.equals(field3) || roll.equals(field4) || roll.equals(field5) || roll.equals(field6)) {
            payout = betAmount.multiply(BigDecimal.valueOf(6));
        }
        else {
            payout = BigDecimal.ZERO;
        }
    }
}
