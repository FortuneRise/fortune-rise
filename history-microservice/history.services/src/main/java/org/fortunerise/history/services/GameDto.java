package org.fortunerise.history.services;

import org.fortunerise.history.entities.Game;
import org.fortunerise.history.services.BetDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class GameDto {
    private int gameId;
    private BigDecimal payout;
    private int roll;

    private Date date;

    private Integer userId;
    private List<BetDto> bets;

    public GameDto() {}

    public GameDto(Game game) {
        gameId = game.getId();
        payout = game.getPayout();
        roll = game.getRoll();
        date = game.getDate();
        userId = game.getUserId();
        bets = null;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<BetDto> getBets() {
        return bets;
    }

    public void setBets(List<BetDto> bets) {
        this.bets = bets;
    }
}
