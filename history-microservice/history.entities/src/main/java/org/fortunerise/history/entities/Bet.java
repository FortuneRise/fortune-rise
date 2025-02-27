package org.fortunerise.history.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "bet")
//@DiscriminatorColumn(name = "BET_TYPE")
public abstract class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "bet_amount")
    protected BigDecimal betAmount;

    @Column(name = "payout")
    protected BigDecimal payout;

    public abstract List<Integer> getFields();

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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
