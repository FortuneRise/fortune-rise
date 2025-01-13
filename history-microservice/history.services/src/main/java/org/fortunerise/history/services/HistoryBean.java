package org.fortunerise.history.services;

import com.kumuluz.ee.rest.beans.QueryFilter;
import com.kumuluz.ee.rest.beans.QueryFilterExpression;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.enums.FilterExpressionOperation;
import com.kumuluz.ee.rest.enums.FilterOperation;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
    public List<GameDto> getGameDtosByUserId(Integer userId, QueryParameters query){

        QueryFilter newqf = new QueryFilter("userId", FilterOperation.EQ,userId.toString());
        QueryFilterExpression nqfe = new QueryFilterExpression(newqf);
        QueryFilterExpression qfe = query.getFilterExpression();
        QueryFilterExpression endqfe = null;

        if(qfe != null){
            endqfe = new QueryFilterExpression(FilterExpressionOperation.AND, nqfe, qfe);
        }else {
            endqfe = nqfe;
        }

        query.setFilterExpression(endqfe);


        List<Game> games = JPAUtils.queryEntities(em, Game.class, query);
        return games.stream().map(GameDto::new).collect(Collectors.toList());
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<BetDto> getBetDtosByGameId(Integer gameId, QueryParameters query){
        List<Game> games = JPAUtils.queryEntities(em, Game.class, (p, cb, r) -> cb.and(p, cb.equal(r.get("id"), gameId)));
        List<Bet> gameBets = games.get(0).getBets();
        return gameBets.stream().map(BetDto::new).collect(Collectors.toList());
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Long getGameCount(Integer userId, QueryParameters query){
        Long count = JPAUtils.queryEntitiesCount(em, Game.class, (p, cb, r) -> cb.and(p, cb.equal(r.get("userId"), userId)));
        return count;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public GameDto addGameByUserId(Integer userId, GameDto gameDto){
        Game game = new Game(gameDto.getDate(), gameDto.getRoll(), gameDto.getUserId());
        game.setPayout(gameDto.getPayout());

        List<Bet> bets = gameDto.getBets().stream().map(el -> {  Bet bet = el.convertToBet(gameDto.getRoll());
                                                                 bet.setGame(game);
                                                                 return bet;
                                                                }).collect(java.util.stream.Collectors.toList());
        game.setBets(bets);
        em.persist(game);
        em.flush();

        return gameDto;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<TransactionDto> getTransactionDtosByUseId(Integer userId, QueryParameters query){

        QueryFilter newqf = new QueryFilter("userId", FilterOperation.EQ,userId.toString());
        QueryFilterExpression nqfe = new QueryFilterExpression(newqf);
        QueryFilterExpression qfe = query.getFilterExpression();
        QueryFilterExpression endqfe = null;

        if(qfe != null){
            endqfe = new QueryFilterExpression(FilterExpressionOperation.AND, nqfe, qfe);
        }else {
            endqfe = nqfe;
        }

        query.setFilterExpression(endqfe);

        List<Transaction> allTransactions = JPAUtils.queryEntities(em, Transaction.class, query);
        return allTransactions.stream().map(TransactionDto::new).collect(Collectors.toList());
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Long getTransactionCount(Integer userId, QueryParameters query){

        QueryFilter newqf = new QueryFilter("userId", FilterOperation.EQ,userId.toString());
        QueryFilterExpression nqfe = new QueryFilterExpression(newqf);
        QueryFilterExpression qfe = query.getFilterExpression();
        QueryFilterExpression endqfe = null;

        if(qfe != null){
            endqfe = new QueryFilterExpression(FilterExpressionOperation.AND, nqfe, qfe);
        }else {
            endqfe = nqfe;
        }

        query.setFilterExpression(endqfe);

        Long count = JPAUtils.queryEntitiesCount(em, Transaction.class, query);
        return count;
    }


    @Transactional(Transactional.TxType.REQUIRED)
    public TransactionDto addTransactionByUser(Integer userId, TransactionDto transactionDto){
        Transaction transaction = new Transaction(transactionDto.getUserId(), transactionDto.getWalletId(), transactionDto.getAmount());
        em.persist(transaction);
        em.flush();
        return transactionDto;
    }


}
