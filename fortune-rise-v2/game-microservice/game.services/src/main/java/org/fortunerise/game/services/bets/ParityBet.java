package org.fortunerise.game.services.bets;

import org.fortunerise.game.services.bets.Bet;

import java.math.BigDecimal;


public class ParityBet extends Bet {


    private Parity parity;

    public enum Parity {
        EVEN, ODD
    }

    public ParityBet() {}

    public ParityBet(BigDecimal betAmount, Integer roll, Parity parity) {
        super(betAmount);
        this.parity = parity;
        this.calculatePayout(roll);
    }

    @Override
    public void calculatePayout(Integer roll){
        if (roll != 0 && ((parity == Parity.EVEN) ^ (roll % 2 == 1))) {
            payout = betAmount.multiply(BigDecimal.valueOf(2));
        }
        else {
            payout = BigDecimal.ZERO;
        }
    }
}
