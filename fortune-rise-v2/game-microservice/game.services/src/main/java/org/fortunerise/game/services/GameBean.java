package org.fortunerise.game.services;

import org.fortunerise.game.services.*;
import org.fortunerise.game.entities.*;
import org.fortunerise.game.entities.bets.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@ApplicationScoped
public class GameBean {

    private Logger log = Logger.getLogger(GameBean.class.getName());
    private Random random;
    private Client client;
    private JsonBuilderFactory jsonBuilderFactory;

    @PersistenceContext(unitName = "fortune-rise-jpa")
    private EntityManager em;

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + GameBean.class.getSimpleName());
        random = new Random();
        client = ClientBuilder.newClient();
        jsonBuilderFactory = javax.json.Json.createBuilderFactory(null);
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

        WebTarget base = client.target("http://localhost:8081/api");
        WebTarget target = base.path("/wallets");
        WebTarget endTarget = target.path("/{userId}").resolveTemplate("userId", userId);

        // Verjetno se bo to menjalo z klicem na history resource
        Game game = new Game(now, roll, userId);
        em.persist(game);
        em.flush();

        for (BetDto betDto : betDtos) {
            totalBet = totalBet.subtract(betDto.getBetAmount());
            /*
            if (betDto.getPromotionId() != null) {
                Promotion promotion = promotionBean.getPromotionById(betDto.getPromotionId());
                promotionBean.executePromotionOnBet(promotion, user);
            }

            */
        }

        //walletBean.updateWallet(userId, new TransactionDto(totalBet));
        Response responseBet = endTarget.request().put(Entity.json(new TransactionDto(totalBet)));

        /*
        Idk zka noce neki prav serializacijo nardit

        JsonObjectBuilder jsonBuilderBet = jsonBuilderFactory.createObjectBuilder();
        jsonBuilderBet.add("amount", totalBet.toString());
        JsonObject jsonBet = jsonBuilderBet.build();
        System.out.println(jsonBet.toString());
        Response responseBet = endTarget.request().header("Content-Type", "application/json").put(Entity.json(jsonBet));
        //Response responseBet = endTarget.request().header("Content-Type", "application/json").put(Entity.json("{\"amount\":-40}"));

        System.out.println("Status: " + responseBet.getStatus());
        System.out.println("Response: " + responseBet.readEntity(String.class));
        */
        if (responseBet.getStatus() == 400) {
            throw new BadRequestException("You don't have enough balance for this bet");
        }else if (responseBet.getStatus() != 200) {
            throw new RuntimeException();
        }




        for (BetDto betDto : betDtos) {
            Bet bet = betDto.convertToBet(roll);
            totalPayout = totalPayout.add(bet.getPayout());
            bet.setGame(game);
            em.persist(bet);
        }

        game.setPayout(totalPayout);

        //walletBean.updateWallet(userId, new TransactionDto(totalPayout));
        Response responsePayout = endTarget.request().put(Entity.json(new TransactionDto(totalPayout)));

        /*
        Enak problem kot prej

        JsonObjectBuilder jsonBuilderPayout = jsonBuilderFactory.createObjectBuilder();
        jsonBuilderPayout.add("amount", game.getPayout());
        JsonObject jsonPayout = jsonBuilderPayout.build();
        Response responsePayout = endTarget.request().put(Entity.json(jsonPayout));
        */
        if (responsePayout.getStatus() != 200) {
            throw new RuntimeException();
        }

        em.flush();

        System.out.println(totalBet);
        System.out.println(totalPayout);

        return new GameDto(game);
    }



}
