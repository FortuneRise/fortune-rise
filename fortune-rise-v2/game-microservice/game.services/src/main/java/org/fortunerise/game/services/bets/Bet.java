package org.fortunerise.game.services.bets;

import java.math.BigDecimal;


public abstract class Bet {

    private Integer id;
    private Integer gameId;
    protected BigDecimal betAmount;
    protected BigDecimal payout;

    public Bet() {}

    protected Bet(BigDecimal betAmount) {
        this.betAmount = betAmount;
    }

    public abstract void calculatePayout(Integer roll);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGame() {
        return gameId;
    }

    public void setGame(Integer game) {
        this.gameId = game;
    }

    public BigDecimal getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(BigDecimal betAmount) {
        this.betAmount = betAmount;
    }

    public BigDecimal getPayout() {
        return payout;
    }

    public void setPayout(BigDecimal payout) {
        this.payout = payout;
    }


}
