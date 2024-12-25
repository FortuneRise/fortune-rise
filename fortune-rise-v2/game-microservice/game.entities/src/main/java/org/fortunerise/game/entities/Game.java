package org.fortunerise.game.entities;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "roll")
    private Integer roll;

    @Column
    private BigDecimal payout;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Bet> bets;

    public Game() {}

    public Game(Date date, Integer roll, Integer userId) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
