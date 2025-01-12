package org.fortunerise.history.entities;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer userId;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private LocalDateTime date;

    @Column(name = "roll")
    private Integer roll;

    @Column
    private BigDecimal payout;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Bet> bets;

    public Game() {}

    public Game(LocalDateTime date, Integer roll, Integer userId) {
        this.date = date;
        this.roll = roll;
        this.userId = userId;
    }


    public Integer getId() { return id; }

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

    public Integer getRoll() {
        return roll;
    }

    public void setRoll(Integer roll) {
        this.roll = roll;
    }

    public BigDecimal getPayout() {
        return payout;
    }

    public void setPayout(BigDecimal payout) {
        this.payout = payout;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }


}
