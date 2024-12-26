package org.fortunerise.history.services;

import org.fortunerise.history.entities.Game;
import org.fortunerise.history.services.GameDto;
import org.fortunerise.history.services.TransactionDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class HistoryBean {

    private Logger log = Logger.getLogger(HistoryBean.class.getName());

    @PersistenceContext(unitName = "fortune-rise-jpa")
    private EntityManager em;

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + HistoryBean.class.getSimpleName());

        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + HistoryBean.class.getSimpleName());

        // zapiranje virov
    }


    public List<GameDto> getGameDtosByUserId(Integer userId){
        String queryString = "SELECT new org.fortunerise.history.services.GameDto(g) FROM Game g WHERE g.userId = :userId";
        Query query = em.createQuery(queryString);
        query.setParameter("userId", userId);

        return (List<GameDto>) query.getResultList();
    }

    public GameDto addGameByUserId(Integer userId, GameDto gameDto){
        Game game = new Game(new Date(), gameDto.getRoll(), gameDto.getUserId());
    }


    public List<TransactionDto> getTransactionDtosByUseId(Integer userId){
        String queryString = "SELECT new org.fortunerise.history.services.TransactionDto(t) FROM Transaction t WHERE t.userId = :userId";
        Query query = em.createQuery(queryString);
        query.setParameter("userId", userId);

        return (List<TransactionDto>) query.getResultList();
    }


}
