package org.fortunerise.game.services;

import org.fortunerise.game.entities.Game;

import java.math.BigDecimal;
import java.util.Date;

public class GameDto {
    private int gameId;
    private BigDecimal payout;
    private int roll;
    private Date date;

    public GameDto(Game game) {
        gameId = game.getId();
        payout = game.getPayout();
        roll = game.getRoll();
        date = game.getDate();
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public BigDecimal getPayout() {
        return payout;
    }

    public void setPayout(BigDecimal payout) {
        this.payout = payout;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }
}
