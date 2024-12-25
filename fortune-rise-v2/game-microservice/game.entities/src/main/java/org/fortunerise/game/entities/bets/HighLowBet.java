package org.fortunerise.game.entities.bets;


import org.fortunerise.game.entities.Bet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Entity
//@DiscriminatorValue("HIGH_LOW")
public class HighLowBet extends Bet {

    public enum HighLow {
        HIGH,
        LOW
    }

    @Column
    @Enumerated(EnumType.STRING)
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
