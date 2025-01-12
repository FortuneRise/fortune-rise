package org.fortunerise.game.services.dtos;

//import org.fortunerise.history.entities.Game;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class GameDto {
    private int gameId;
    private BigDecimal payout;
    private int roll;

    private LocalDateTime date;

    private Integer userId;
    private List<BetDto> bets;

    public GameDto() {}

    /*
    public GameDto(Game game) {
        gameId = game.getId();
        payout = game.getPayout();
        roll = game.getRoll();
        date = game.getDate();
        userId = game.getUserId();
        bets = null;
    }
    */

    public GameDto(Integer userId, BigDecimal payout, int roll, LocalDateTime date) {
        this.userId = userId;
        this.payout = payout;
        this.roll = roll;
        this.date = date;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<BetDto> getBets() {
        return bets;
    }

    public void setBets(List<BetDto> bets) {
        this.bets = bets;
    }
}
