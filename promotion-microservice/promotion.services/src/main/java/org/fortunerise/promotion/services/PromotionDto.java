package org.fortunerise.promotion.services;

import org.fortunerise.promotion.entities.Promotion;
import org.fortunerise.promotion.entities.promotions.ExtraMoneyPromotion;
import org.fortunerise.promotion.entities.promotions.FreeBetPromotion;

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

    public Promotion convertToPromotion() {
        return switch (type) {
            case FREE_BET -> new FreeBetPromotion(parameters.get(0));
            case EXTRA_MONEY -> new ExtraMoneyPromotion(parameters.get(0), parameters.get(1));
        };
    }

    public Integer getId() {
        return id;
    }

    public List<BigDecimal> getParameters() {
        return parameters;
    }

    public Promotion.TriggerScenario getTriggerScenario() {
        return triggerScenario;
    }

    public Type getType() {
        return type;
    }
}
