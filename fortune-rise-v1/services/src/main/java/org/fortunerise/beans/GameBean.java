package org.fortunerise.beans;

import org.fortunerise.dtos.*;
import org.fortunerise.entities.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@ApplicationScoped
public class GameBean {

    private Logger log = Logger.getLogger(GameBean.class.getName());
    private Random random = new Random();

    @PersistenceContext(unitName = "fortune-rise-jpa")
    private EntityManager em;

    @Inject
    private UserBean userBean;

    @Inject
    private WalletBean walletBean;

    @Inject
    private PromotionBean promotionBean;

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

    @Transactional(Transactional.TxType.REQUIRED)
    public GameDto playGame(Integer userId, List<BetDto> betDtos){
        Integer roll = random.nextInt(37);
        BigDecimal totalPayout = BigDecimal.ZERO;
        BigDecimal totalBet = BigDecimal.ZERO;
        Date now = new Date();

        User user = userBean.getUserById(userId);

        Game game = new Game(now, roll, user);
        em.persist(game);
        em.flush();

        for (BetDto betDto : betDtos) {
            totalBet = totalBet.subtract(betDto.getBetAmount());
            if (betDto.getPromotionId() != null) {
                Promotion promotion = promotionBean.getPromotionById(betDto.getPromotionId());
                promotionBean.executePromotionOnBet(promotion, user);
            }
        }
        walletBean.updateWallet(userId, new TransactionDto(totalBet));

        for (BetDto betDto : betDtos) {
            Bet bet = betDto.convertToBet(roll);
            totalPayout = totalPayout.add(bet.getPayout());
            bet.setGame(game);
            em.persist(bet);
        }

        game.setPayout(totalPayout);
        walletBean.updateWallet(userId, new TransactionDto(totalPayout));
        em.flush();

        return new GameDto(game);
    }



}
