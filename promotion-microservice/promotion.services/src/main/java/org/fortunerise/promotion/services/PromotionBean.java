package org.fortunerise.promotion.services;

import com.kumuluz.ee.rest.beans.QueryFilter;
import com.kumuluz.ee.rest.beans.QueryFilterExpression;
import com.kumuluz.ee.rest.enums.FilterExpressionOperation;
import com.kumuluz.ee.rest.enums.FilterOperation;
import io.github.cdimascio.dotenv.Dotenv;
import org.fortunerise.promotion.entities.Promotion;
import org.fortunerise.promotion.entities.UserLink;
import org.fortunerise.promotion.entities.promotions.ExtraMoneyPromotion;
import org.fortunerise.promotion.entities.promotions.FreeBetPromotion;

import com.kumuluz.ee.rest.utils.JPAUtils;
import com.kumuluz.ee.rest.beans.QueryParameters;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class PromotionBean {

    public enum TriggerScenario {
        ALL, DEPOSIT, BET
    }

    private Client client;

    private String walletHost;
    private String walletPort;
    private String notificationHost;
    private String notificationPort;

    @PersistenceContext(unitName = "fortune-rise-jpa")
    private EntityManager em;

    private Logger log = Logger.getLogger(PromotionBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Bean initialization: " + PromotionBean.class.getSimpleName());

        client = ClientBuilder.newClient();
        if (System.getenv("KUBERNETES_SERVICE_HOST") == null) {
            Dotenv dotenv = Dotenv.load();
            walletHost = dotenv.get("WALLET_HOST");
            walletPort = dotenv.get("WALLET_PORT");
            notificationHost = dotenv.get("NOTIFICATION_HOST");
            notificationPort = dotenv.get("NOTIFICATION_PORT");
        }
        else {
            walletHost = System.getenv("WALLET_HOST");
            walletPort = System.getenv("WALLET_PORT");
            notificationHost = System.getenv("NOTIFICATION_HOST");
            notificationPort = System.getenv("NOTIFICATION_PORT");
        }
    }

    @PreDestroy
    private void destroy() {
        log.info("Bean deinitialization: " + PromotionBean.class.getSimpleName());
    }

    @Transactional
    private String getParameterString(UriInfo uriInfo) {
        MultivaluedMap<String, String> paramMultiMap = uriInfo.getQueryParameters();
        String paramString = paramMultiMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(value -> entry.getKey() + "=" + value))
                .collect(Collectors.joining("&"));
        if (paramString.isEmpty()) {
            paramString += "?";
        }
        else {
            paramString += "&";
        }

        return paramString;
    }

    @Transactional
    public List<PromotionDto> getPromotionDtos(UriInfo uriInfo) {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Promotion> promotions = JPAUtils.queryEntities(em, Promotion.class, queryParameters);
        return promotions.stream().map(PromotionDto::new).collect(java.util.stream.Collectors.toList());
    }

    @Transactional
    public List<PromotionDto> getPromotionDtosByUserId(Integer userId, TriggerScenario triggerScenario, QueryParameters query) {
        Boolean isAll = (triggerScenario == TriggerScenario.ALL);
        QueryFilter newqfUserId = new QueryFilter("userId", FilterOperation.EQ,userId.toString());
        QueryFilterExpression nqfeUI = new QueryFilterExpression(newqfUserId);
        //QueryFilter newqfTriggerScenario = new QueryFilter("triggerScenario", FilterOperation.EQ,triggerScenario.toString());
        //QueryFilterExpression nqfeTS = new QueryFilterExpression(newqfTriggerScenario);

        //QueryFilterExpression nqfe = new QueryFilterExpression(FilterExpressionOperation.AND, nqfeUI, nqfeTS);

        QueryFilterExpression qfe = query.getFilterExpression();
        QueryFilterExpression endqfe = null;

        if(qfe != null){
            endqfe = new QueryFilterExpression(FilterExpressionOperation.AND, nqfeUI, qfe);
        }else {
            endqfe = nqfeUI;
        }

        query.setFilterExpression(endqfe);
        /*
        String paramString = getParameterString(uriInfo);
        paramString += "userId=" + userId;
        switch (triggerScenario) {
            case DEPOSIT:
                paramString += "&triggerScenario=DEPOSIT";
                break;
            case BET:
                paramString += "&triggerScenario=BET";
                break;
            default:
                break;
        }

        QueryParameters queryParameters = QueryParameters.query(paramString).build();
         */

        List<UserLink> userLinks = JPAUtils.queryEntities(em, UserLink.class, query);

        return userLinks.stream().map(UserLink::getPromotion).filter(promotion -> (promotion.getTriggerScenario().toString().equals(triggerScenario.toString())) || isAll).map(PromotionDto::new).toList();
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

        WebTarget base = client.target("http://" + walletHost + ":" + walletPort + "/api");
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

        WebTarget base = client.target("http://" + notificationHost + ":" + notificationPort + "/api");
        WebTarget target = base.path("/notifications");
        WebTarget endTarget = target.path("/{userId}").resolveTemplate("userId", userId);

        NotificationDto notification = new NotificationDto(new Date(),"You have a new promotion!",false);

        Response response = endTarget.request().post(Entity.json(notification));

        if(response.getStatus() >= 300) {
            log.info("Failed while calling" + response.getStatus());
            throw new RuntimeException();
        }



        UserLink userLink = new UserLink(userId, getPromotionById(promotionId));
        em.persist(userLink);
        em.flush();
    }


    @Transactional
    public PromotionDto createPromotion(PromotionDto promotionDto) {
        Promotion promotion = promotionDto.convertToPromotion();
        em.persist(promotion);
        em.flush();
        
        return new PromotionDto(promotion);
    }
}
