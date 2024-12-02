package org.fortunerise.entities.bets;

import org.fortunerise.entities.Bet;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
//@DiscriminatorValue("COLUMN")
public class ColumnBet extends Bet {

    public enum Columns {
        FIRST,
        SECOND,
        THIRD
    }

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Columns columns;

    public ColumnBet() {}

    public ColumnBet(BigDecimal betAmount, Integer roll, Columns columns) {
        super(betAmount);
        this.columns = columns;
        this.calculatePayout(roll);
    }

    @Override
    public void calculatePayout(Integer roll){

        this.payout = BigDecimal.ZERO;

        if(roll == 0){
            return;
        }

        switch (columns){
            case FIRST:
                if(roll % 3 == 1) this.payout = betAmount.multiply(BigDecimal.valueOf(3));
                break;
            case SECOND:
                if(roll % 3 == 2) this.payout = betAmount.multiply(BigDecimal.valueOf(3));
                break;
            case THIRD:
                if(roll % 3 == 0) this.payout = betAmount.multiply(BigDecimal.valueOf(3));
                break;
        }



    }
}
