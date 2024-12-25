package org.fortunerise.history.services;

import org.fortunerise.dtos.GameDto;
import org.fortunerise.dtos.TransactionDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

public class HistoryBean {

    private Logger log = Logger.getLogger(GameBean.class.getName());

    @PersistenceContext(unitName = "fortune-rise-jpa")
    private EntityManager em;

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + GameBean.class.getSimpleName());

        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + GameBean.class.getSimpleName());

        // zapiranje virov
    }


    public List<GameDto> getGameDtosByUserId(Integer userId){
        String queryString = "SELECT new org.fortunerise.dtos.GameDto(g) FROM Game g WHERE g.user.id = :userId";
        Query query = em.createQuery(queryString);
        query.setParameter("userId", userId);

        return (List<GameDto>) query.getResultList();
    }

    public List<TransactionDto> getTransactionDtosByUseId(Integer userId){
        String queryString = "SELECT new org.fortunerise.dtos.TransactionDto(t) FROM Transaction t WHERE t.wallet.user.id = :userId";
        Query query = em.createQuery(queryString);
        query.setParameter("userId", userId);

        return (List<TransactionDto>) query.getResultList();
    }

}
