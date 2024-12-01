package org.fortunerise.entities.bets;

import org.fortunerise.entities.Bet;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Set;


public class ColorBet extends Bet {
    private static final Set<Integer> RED_NUMBERS = Set.of(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36);

    public enum Color {
        RED,BLACK;
    }

    @Column
    @Enumerated(EnumType.STRING)
    private Color color;

    public ColorBet(BigDecimal betAmount, Integer roll, Color color) {
        super(betAmount);
        this.color = color;
        this.calculatePayout(roll);
    }


    @Override
    public void calculatePayout(Integer roll){
        if (roll != 0 && ((color == Color.BLACK) ^ RED_NUMBERS.contains(roll))) {
            payout = betAmount.multiply(BigDecimal.valueOf(2));
        }
        else {
            payout = BigDecimal.ZERO;
        }
    }
}
