package org.fortunerise.beans;

import org.fortunerise.dtos.BetDto;
import org.fortunerise.dtos.GameDto;
import org.fortunerise.dtos.UserDto;
import org.fortunerise.dtos.WalletDto;
import org.fortunerise.entities.Bet;
import org.fortunerise.entities.Game;
import org.fortunerise.entities.User;
import org.fortunerise.entities.Wallet;

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
        }
        walletBean.updateWallet(userId, new WalletDto(totalBet));

        for (BetDto betDto : betDtos) {
            Bet bet = betDto.convertToBet(roll);
            totalPayout = totalPayout.add(bet.getPayout());
            bet.setGame(game);
            em.persist(bet);
        }

        game.setPayout(totalPayout);
        walletBean.updateWallet(userId, new WalletDto(totalPayout));
        em.flush();

        return new GameDto(game);
    }

    public List<GameDto> getGameDtosByUserId(Integer userId){
        String queryString = "SELECT new org.fortunerise.dtos.GameDto(g) FROM Game g WHERE g.user.id = :userId";
        Query query = em.createQuery(queryString);
        query.setParameter("userId", userId);

        return (List<GameDto>) query.getResultList();
    }


}
