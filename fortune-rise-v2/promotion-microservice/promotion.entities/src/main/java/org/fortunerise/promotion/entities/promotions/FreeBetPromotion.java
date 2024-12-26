package org.fortunerise.promotion.entities.promotions;

import org.fortunerise.promotion.entities.Promotion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "free_bet_promotion")
public class FreeBetPromotion extends Promotion {
    @Column(name = "amount")
    private BigDecimal amount;

    public FreeBetPromotion() {}

    public FreeBetPromotion(BigDecimal amount) {
        super(TriggerScenario.BET);
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
