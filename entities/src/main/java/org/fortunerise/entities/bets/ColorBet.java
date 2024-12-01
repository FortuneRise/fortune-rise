package org.fortunerise.entities.bets;

import org.fortunerise.entities.Bet;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class ColorBet extends Bet {

    @Enumerated(EnumType.STRING)
    private Color color;

    private enum Color{
        RED,BLACK;
    }

    @Override
    public void calculatePayout(Integer roll){
        Integer reds[] = {1,2,3,4}; // Update value
    }
}
