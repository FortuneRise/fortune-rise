package org.fortunerise.history.entities.bets;

import org.fortunerise.entities.Bet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Entity
//@DiscriminatorValue("PARITY")
public class ParityBet extends Bet {

    @Column
    @Enumerated(EnumType.STRING)
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
