package org.fortunerise.entities;

import javax.persistence.*;
import java.math.BigDecimal;

public class Bet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "bet")
    private BigDecimal bet;

    @Column(name = "payout")
    private BigDecimal payout;

    @Column(name = "bet_type")
    private String betType;

    @Column(name = "bet_start_field")
    private Integer betStartField;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public BigDecimal getBet() {
        return bet;
    }

    public void setBet(BigDecimal bet) {
        this.bet = bet;
    }

    public BigDecimal getPayout() {
        return payout;
    }

    public void setPayout(BigDecimal payout) {
        this.payout = payout;
    }

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    public Integer getBetStartField() {
        return betStartField;
    }

    public void setBetStartField(Integer betStartField) {
        this.betStartField = betStartField;
    }

}
