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
//@DiscriminatorValue("DOZEN")
public class DozenBet extends Bet {

    public enum Dozen {
        FIRST,
        SECOND,
        THIRD;
    }

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Dozen dozen;

    public DozenBet() {}

    public DozenBet(BigDecimal betAmount, Integer roll, Dozen dozen) {
        super(betAmount);
        this.dozen = dozen;
        this.calculatePayout(roll);
    }

    @Override
    public List<Integer> getFields(){
        List<Integer> fields = new ArrayList<Integer>();
        if (dozen == Dozen.FIRST){ fields.add(0);
        }else if(dozen == Dozen.SECOND) {fields.add(1);
        }else if(dozen == Dozen.THIRD) {fields.add(2);}
        return fields;
    }

    @Override
    public void calculatePayout(Integer roll){
        switch (dozen) {
            case FIRST:
                if (roll >= 1 && roll <= 12) {
                    payout = betAmount.multiply(BigDecimal.valueOf(3));
                    return;
                }
                break;
            case SECOND:
                if (roll >= 13 && roll <= 24) {
                    payout = betAmount.multiply(BigDecimal.valueOf(3));
                    return;
                }
                break;
            case THIRD:
                if (roll >= 25 && roll <= 36) {
                    payout = betAmount.multiply(BigDecimal.valueOf(3));
                    return;
                }
                break;
        }

        payout = BigDecimal.ZERO;
    }
}
