package org.fortunerise.dtos;

import org.fortunerise.entities.Promotion;
import org.fortunerise.entities.promotions.ExtraMoneyPromotion;
import org.fortunerise.entities.promotions.FreeBetPromotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PromotionDto {
    public enum Type {
        FREE_BET, EXTRA_MONEY
    }

    private Integer id;
    private Promotion.TriggerScenario triggerScenario;
    private Type type;
    private List<BigDecimal> parameters;

    public PromotionDto() {}

    public PromotionDto(Promotion promotion) {
        this.id = promotion.getId();
        this.triggerScenario = promotion.getTriggerScenario();
        if (promotion instanceof FreeBetPromotion freeBetPromotion) {
            type = Type.FREE_BET;
            parameters = new ArrayList<>();
            parameters.add(freeBetPromotion.getAmount());
        }
        else if (promotion instanceof ExtraMoneyPromotion extraMoneyPromotion) {
            type = Type.EXTRA_MONEY;
            parameters = new ArrayList<>();
            parameters.add(extraMoneyPromotion.getAmount());
            parameters.add(extraMoneyPromotion.getThreshold());
        }
    }
}
