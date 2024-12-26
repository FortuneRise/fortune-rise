package org.fortunerise.promotion.services;

import org.fortunerise.promotion.entities.Promotion;
import org.fortunerise.promotion.services.PromotionDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PromotionBean {

    public enum TriggerScenario {
        ALL, DEPOSIT, BET
    }

    @PersistenceContext(unitName = "fortune-rise-jpa")
    private EntityManager em;

    private Logger log = Logger.getLogger(PromotionBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Bean initialization: " + PromotionBean.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Bean deinitialization: " + PromotionBean.class.getSimpleName());
    }

    @Transactional
    public List<PromotionDto> getPromotionsByUserId(Integer userId, TriggerScenario triggerScenario) {
        String queryString = switch (triggerScenario) {
            case ALL -> "SELECT new org.fortunerise.promotion.services.PromotionDto(p) FROM Promotion p";
            case DEPOSIT ->
                    "SELECT new org.fortunerise.promotion.services.PromotionDto(p) FROM Promotion p WHERE p.triggerScenario = 'DEPOSIT'";
            case BET ->
                    "SELECT new org.fortunerise.promotion.services.PromotionDto(p) FROM Promotion p WHERE p.triggerScenario = 'BET'";
        };

        return (List<PromotionDto>) em.createQuery(queryString).getResultList();
    }
}
