package org.fortunerise.promotion.services;

import org.fortunerise.promotion.entities.Promotion;
import org.fortunerise.promotion.entities.UserLink;
import org.fortunerise.promotion.entities.promotions.ExtraMoneyPromotion;
import org.fortunerise.promotion.entities.promotions.FreeBetPromotion;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PromotionBean {

    public enum TriggerScenario {
        ALL, DEPOSIT, BET
    }

    private Client client;

    @PersistenceContext(unitName = "fortune-rise-jpa")
    private EntityManager em;

    private Logger log = Logger.getLogger(PromotionBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Bean initialization: " + PromotionBean.class.getSimpleName());

        client = ClientBuilder.newClient();
    }

    @PreDestroy
    private void destroy() {
        log.info("Bean deinitialization: " + PromotionBean.class.getSimpleName());
    }

    @Transactional
    public List<PromotionDto> getPromotionDtosByUserId(Integer userId, TriggerScenario triggerScenario) {
        String queryString = switch (triggerScenario) {
            case ALL -> "SELECT new org.fortunerise.promotion.services.PromotionDto(ul.promotion) FROM UserLink ul WHERE ul.userId = :userId";
            case DEPOSIT ->
                    "SELECT new org.fortunerise.promotion.services.PromotionDto(ul.promotion) FROM UserLink ul WHERE ul.userId = :userId AND ul.promotion.triggerScenario = 'DEPOSIT'";
            case BET ->
                    "SELECT new org.fortunerise.promotion.services.PromotionDto(ul.promotion) FROM UserLink ul WHERE ul.userId = :userId AND ul.promotion.triggerScenario = 'BET'";
        };

        return (List<PromotionDto>) em.createQuery(queryString).setParameter("userId", userId).getResultList();
    }

    @Transactional
    public Promotion getPromotionById(Integer promotionId) {
        String queryString = "SELECT p FROM Promotion p WHERE p.id = :promotionId";
        Query query = em.createQuery(queryString);
        query.setParameter("promotionId", promotionId);

        return (Promotion) query.getSingleResult();
    }

    @Transactional
    public UserLink getUserLinkByUserIdAndPromotionId(Integer userId, Integer promotionId) {
        String queryString = "SELECT ul FROM UserLink ul WHERE ul.promotion.id = :promotionId AND ul.userId = :userId";
        Query query = em.createQuery(queryString);
        query.setParameter("promotionId", promotionId);
        query.setParameter("userId", userId);
        UserLink userLink =  (UserLink) query.getSingleResult();
        if (userLink == null) {
            throw new IllegalArgumentException("UserLink not found! userId = " + userId + ", promotionId = " + promotionId);
        }

        return userLink;
    }

    @Transactional
    public Boolean verifyPromotion(Integer userId, Integer promotionId, TransactionDto transactionDto) {
        UserLink userLink = getUserLinkByUserIdAndPromotionId(userId, promotionId);
        Promotion promotion = userLink.getPromotion();

        boolean eligible = true;
        if (promotion instanceof ExtraMoneyPromotion extraMoneyPromotion) {
            eligible = transactionDto.getAmount().compareTo(extraMoneyPromotion.getAmount()) >= 0;
        }

        return eligible;
    }

    @Transactional
    public Response applyPromotion(Integer userId, Integer promotionId) {
        UserLink userLink = getUserLinkByUserIdAndPromotionId(userId, promotionId);
        Promotion promotion = userLink.getPromotion();

        TransactionDto transactionDto;

        if (promotion instanceof ExtraMoneyPromotion extraMoneyPromotion) {
            transactionDto = new TransactionDto(extraMoneyPromotion.getAmount());
        }
        else if (promotion instanceof FreeBetPromotion freeBetPromotion) {
            transactionDto = new TransactionDto(freeBetPromotion.getAmount());
        }
        else {
            throw new IllegalArgumentException("Unknown subclass of Promotion: " + promotion.getClass().getSimpleName());
        }

        WebTarget base = client.target("http://localhost:8081/api");
        WebTarget target = base.path("/wallets");
        WebTarget endTarget = target.path("/{userId}").resolveTemplate("userId", userId);

        Response response = endTarget.request().put(Entity.json(transactionDto));

        if (response.getStatus() != 200) {
            throw new RuntimeException();
        }

        em.remove(userLink);

        return response;
    }

    @Transactional
    public void addPromotionToUser(Integer userId, Integer promotionId) {
        UserLink userLink = new UserLink(userId, getPromotionById(promotionId));
        em.persist(userLink);
        em.flush();
    }

    @Transactional
    public List<PromotionDto> getPromotionDtos() {
        String QueryString = "SELECT new org.fortunerise.promotion.services.PromotionDto(p) FROM Promotion p";
        return (List<PromotionDto>) em.createQuery(QueryString).getResultList();
    }

    @Transactional
    public PromotionDto createPromotion(PromotionDto promotionDto) {
        Promotion promotion = promotionDto.convertToPromotion();
        em.persist(promotion);
        em.flush();
        
        return new PromotionDto(promotion);
    }
}
