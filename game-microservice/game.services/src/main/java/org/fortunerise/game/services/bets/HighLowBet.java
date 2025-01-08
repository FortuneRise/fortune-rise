package org.fortunerise.game.services.bets;

import org.fortunerise.game.services.bets.Bet;

import java.math.BigDecimal;

public class HighLowBet extends Bet {

    public enum HighLow {
        HIGH,
        LOW
    }


    private HighLow highLow;

    public HighLowBet() {}

    public HighLowBet(BigDecimal betAmount, Integer roll, HighLow highLow) {
        super(betAmount);
        this.highLow = highLow;
        this.calculatePayout(roll);
    }

    @Override
    public void calculatePayout(Integer roll){

        this.payout = BigDecimal.ZERO;

        switch (highLow){
            case HIGH:
                if(1 <= roll && roll <= 18) this.payout = betAmount.multiply(BigDecimal.valueOf(2));
                break;
            case LOW:
                if(19 <= roll && roll <= 36) this.payout = betAmount.multiply(BigDecimal.valueOf(2));
                break;
        }

    }
}
