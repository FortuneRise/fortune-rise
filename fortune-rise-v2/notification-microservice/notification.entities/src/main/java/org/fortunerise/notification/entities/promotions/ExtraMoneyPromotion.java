package org.fortunerise.notification.entities.promotions;

import org.fortunerise.entities.Promotion;

import javax.persistence.Column;
import java.math.BigDecimal;

public class ExtraMoneyPromotion extends Promotion {
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "threshold")
    private BigDecimal threshold;

    public ExtraMoneyPromotion(BigDecimal amount, BigDecimal threshold) {
        super(TriggerScenario.TRANSACTION);
        this.amount = amount;
        this.threshold = threshold;
    }

    public BigDecimal getThreshold() {
        return threshold;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
