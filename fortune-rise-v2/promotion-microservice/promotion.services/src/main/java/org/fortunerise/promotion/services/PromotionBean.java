package org.fortunerise.promotion.services;

import org.fortunerise.promotion.services.PromotionDto;
import org.fortunerise.dtos.TransactionDto;
import org.fortunerise.entities.Promotion;
import org.fortunerise.entities.Transaction;
import org.fortunerise.entities.User;
import org.fortunerise.entities.promotions.ExtraMoneyPromotion;
import org.fortunerise.entities.promotions.FreeBetPromotion;

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

    @PersistenceContext(unitName = "fortune-rise-jpa")
    private EntityManager em;

    @Inject
    private WalletBean walletBean;

    private Logger log = Logger.getLogger(PromotionBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Bean initialization: " + PromotionBean.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Bean deinitialization: " + PromotionBean.class.getSimpleName());
    }

    public Promotion getPromotionById(Integer promotionId) {
        String queryString = "SELECT p FROM Promotion p WHERE p.id = :promotionId";
        Query query = em.createQuery(queryString);
        query.setParameter("promotionId", promotionId);

        return (Promotion) query.getSingleResult();
    }

    @Transactional
    public void executePromotionOnBet(Promotion promotion, User user) {
        if (promotion == null) {
            return;
        }
        else if (promotion instanceof FreeBetPromotion) {
            executeFreeBetPromotion((FreeBetPromotion) promotion, user);
        }
    }

    @Transactional
    public void executePromotionOnTransaction(Promotion promotion, User user, Transaction transaction) {
        if (promotion == null) {
            return;
        }
        else if (promotion instanceof ExtraMoneyPromotion) {
            executeExtraMoneyPromotion((ExtraMoneyPromotion) promotion, user, transaction);
        }
    }

    @Transactional
    private void executeFreeBetPromotion(FreeBetPromotion promotion, User user) {
        walletBean.updateWallet(user.getId(), new TransactionDto(promotion.getAmount()));
        user.removePromotion(promotion);
    }

    @Transactional
    private void executeExtraMoneyPromotion(ExtraMoneyPromotion promotion, User user, Transaction transaction) {
        if (transaction.getAmount().compareTo(promotion.getThreshold()) < 0) {
            return;
        }
        walletBean.updateWallet(user.getId(), new TransactionDto(promotion.getAmount()));
        user.removePromotion(promotion);
    }

    @Transactional
    public List<PromotionDto> getPromotions() {
        String queryString = "SELECT new org.fortunerise.dtos.PromotionDto(p) FROM Promotion p";
        Query query = em.createQuery(queryString);
        return (List<PromotionDto>) query.getResultList();
    }
}
