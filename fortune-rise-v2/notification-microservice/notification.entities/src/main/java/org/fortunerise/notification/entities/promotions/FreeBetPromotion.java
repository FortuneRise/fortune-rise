package org.fortunerise.notification.entities.promotions;

import org.fortunerise.entities.Promotion;

import javax.persistence.Column;
import java.math.BigDecimal;

public class FreeBetPromotion extends Promotion {
    @Column(name = "amount")
    private BigDecimal amount;

    public FreeBetPromotion(BigDecimal amount) {
        super(TriggerScenario.BET);
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
