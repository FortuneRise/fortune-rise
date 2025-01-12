package org.fortunerise.history.entities.bets;


import org.fortunerise.history.entities.Bet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    public List<Integer> getFields(){
        List<Integer> fields = new ArrayList<Integer>();
        if (highLow == HighLow.HIGH){ fields.add(0);
        }else if(highLow == HighLow.LOW) {fields.add(1);}
        return fields;
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
