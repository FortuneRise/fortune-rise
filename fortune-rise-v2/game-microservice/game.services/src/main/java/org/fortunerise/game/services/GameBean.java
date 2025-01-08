package org.fortunerise.game.services;


import io.github.cdimascio.dotenv.Dotenv;
import org.fortunerise.game.services.dtos.BetDto;
import org.fortunerise.game.services.dtos.GameDto;
import org.fortunerise.game.services.dtos.TransactionDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonBuilderFactory;
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

    private String walletHost;
    private String walletPort;
    private String historyHost;
    private String historyPort;


    @Inject
    private BetBean betBean;

    @PostConstruct
    private void init() {
        log.info("Bean initialization " + GameBean.class.getSimpleName());
        random = new Random();
        client = ClientBuilder.newClient();
        jsonBuilderFactory = javax.json.Json.createBuilderFactory(null);
        if (System.getenv("KUBERNETES_SERVICE_HOST") == null) {
            Dotenv dotenv = Dotenv.load();
            walletHost = dotenv.get("WALLET_HOST");
            walletPort = dotenv.get("WALLET_PORT");
            historyHost = dotenv.get("HISTORY_HOST");
            historyPort = dotenv.get("HISTORY_PORT");
        }
        else {
            walletHost = System.getenv("WALLET_HOST");
            walletPort = System.getenv("WALLET_PORT");
            historyHost = System.getenv("HISTORY_HOST");
            historyPort = System.getenv("HISTORY_PORT");
        }
    }

    @PreDestroy
    private void destroy() {
        log.info("Bean deinitialization: " + GameBean.class.getSimpleName());

        // zapiranje virov
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public GameDto playGame(Integer userId, List<BetDto> betDtos){
        Integer roll = random.nextInt(37);
        BigDecimal totalPayout = BigDecimal.ZERO;
        BigDecimal totalBet = BigDecimal.ZERO;
        Date now = new Date();

        // Creating base for http requests

        WebTarget baseWallet = client.target("http://" + walletHost +":" + walletPort + "/api");
        WebTarget baseHistory = client.target("http://" + historyHost +":" + historyPort + "/api");

        WebTarget targetWallet = baseWallet.path("/wallets");
        WebTarget endTargetWallet = targetWallet.path("/{userId}").resolveTemplate("userId", userId);

        WebTarget targetHistory = baseHistory.path("/history/games");
        WebTarget endTargetHistory = targetHistory.path("/{userId}").resolveTemplate("userId", userId);


        //calculate bet and send request to wallet

        for (BetDto betDto : betDtos) {
            totalBet = totalBet.subtract(betDto.getBetAmount());
        }


        log.info("Total bet: " + totalBet);
        TransactionDto transactionDto = new TransactionDto(totalBet);
        log.info("Transaction: " + transactionDto);
        Response responseBet = endTargetWallet.request().put(Entity.json(transactionDto));

        if (responseBet.getStatus() == 400) {
            throw new BadRequestException("You don't have enough balance for this bet");
        }else if (responseBet.getStatus() != 200) {
            throw new RuntimeException("Problem with betting");
        }


        //calculate payout and send to wallet

        for (BetDto betDto : betDtos) {
            totalPayout = totalPayout.add(betBean.calculatePayout(roll, betDto));
        }


        Response responsePayout = endTargetWallet.request().put(Entity.json(new TransactionDto(totalPayout)));


        if (responsePayout.getStatus() != 200) {
            throw new RuntimeException("Problem with payout");
        }

        log.info("Payout" + totalPayout);

        // send the game to history

        GameDto gameDto = new GameDto(userId, totalPayout, roll, now);
        gameDto.setBets(betDtos);

        Response responseHistory = endTargetHistory.request().post(Entity.json(gameDto));
        log.info(responseHistory.getStatusInfo().toString());
        log.info(responseHistory.readEntity(String.class));

        if (responseHistory.getStatus() != 201) {
            throw new RuntimeException("Problem with history");
        }

        return gameDto;
    }



}
