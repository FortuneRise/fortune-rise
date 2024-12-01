package org.fortunerise.entities.bets;

import org.fortunerise.entities.Bet;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity

public class TrioBet extends Bet {

    @Column
    private Integer field1;

    @Column
    private Integer field2;

    @Column
    private Integer field3;

    @Override
    public void calculatePayout(Integer roll) {
        if (roll.equals(field1) || roll.equals(field2) || roll.equals(field3)) {
            payout =
        }
    }
}
