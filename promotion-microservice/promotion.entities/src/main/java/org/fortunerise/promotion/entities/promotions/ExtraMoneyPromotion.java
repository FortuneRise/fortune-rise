package org.fortunerise.promotion.entities.promotions;

import org.fortunerise.promotion.entities.Promotion;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("EXTRA_MONEY")
@Table(name = "extra_money_promotion")
public class ExtraMoneyPromotion extends Promotion {
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "threshold")
    private BigDecimal threshold;

    public ExtraMoneyPromotion() {}

    public ExtraMoneyPromotion(BigDecimal amount, BigDecimal threshold) {
        super(TriggerScenario.DEPOSIT);
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
