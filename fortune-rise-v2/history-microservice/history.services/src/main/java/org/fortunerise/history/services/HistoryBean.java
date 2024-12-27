package org.fortunerise.history.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.fortunerise.history.entities.Bet;
import org.fortunerise.history.entities.Game;
import org.fortunerise.history.entities.Transaction;
import org.fortunerise.history.services.GameDto;
import org.fortunerise.history.services.TransactionDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
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

    @Transactional(Transactional.TxType.REQUIRED)
    public List<GameDto> getGameDtosByUserId(Integer userId, QueryParameters queryParameters){
        String queryString = "SELECT new org.fortunerise.history.services.GameDto(g) FROM Game g WHERE g.userId = :userId";
        TypedQuery<GameDto> query = JPAUtils.queryEntities(em, GameDto.class, queryString, queryParameters);
        query.setParameter("userId", userId);

        return (List<GameDto>) query.getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public GameDto addGameByUserId(Integer userId, GameDto gameDto){
        Game game = new Game(gameDto.getDate(), gameDto.getRoll(), gameDto.getUserId());
        game.setPayout(gameDto.getPayout());

        List<Bet> bets = gameDto.getBets().stream().map(el -> {
                                                                        Bet bet = el.convertToBet(gameDto.getRoll());
                                                                        bet.setGame(game);
                                                                        return bet;
                                                                     })
                                                                     .collect(java.util.stream.Collectors.toList());
        game.setBets(bets);
        em.persist(game);
        em.flush();

        return gameDto;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<TransactionDto> getTransactionDtosByUseId(Integer userId){
        String queryString = "SELECT new org.fortunerise.history.services.TransactionDto(t) FROM Transaction t WHERE t.userId = :userId";
        Query query = em.createQuery(queryString);
        query.setParameter("userId", userId);

        return (List<TransactionDto>) query.getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransactionDto addTransactionByUser(Integer userId, TransactionDto transactionDto){
        Transaction transaction = new Transaction(transactionDto.getUserId(), transactionDto.getWalletId(), transactionDto.getAmount());
        em.persist(transaction);
        em.flush();
        return transactionDto;
    }


}
