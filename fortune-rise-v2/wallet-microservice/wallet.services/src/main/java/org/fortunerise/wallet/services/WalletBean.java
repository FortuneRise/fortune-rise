package org.fortunerise.wallet.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.fortunerise.wallet.entities.Wallet;
import org.postgresql.util.PSQLException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.logging.Logger;
import io.github.cdimascio.dotenv.Dotenv;

@ApplicationScoped
public class WalletBean {


    private Logger log = Logger.getLogger(WalletBean.class.getName());

    @PersistenceContext(unitName = "fortune-rise-jpa")
    private EntityManager em;

    private Client client;

    private String apiKey;

    @PostConstruct
    private void init() {
        log.info("Bean initialization" + WalletBean.class.getSimpleName());
        client = ClientBuilder.newClient();
        Dotenv dotenv = Dotenv.load();
        apiKey = dotenv.get("API_KEY");
        log.info("API KEY: " + apiKey);
    }

    @PreDestroy
    private void destroy() {
        log.info("Bean deinitialization " + WalletBean.class.getSimpleName());

        // zapiranje virov
    }


    @Transactional(Transactional.TxType.REQUIRED)
    public Wallet updateWallet(Integer userId, TransactionDto transactionDto) {
        Wallet wallet = getWalletByUserId(userId);
        BigDecimal balance = wallet.getBalance();
        BigDecimal change = transactionDto.getAmount();

        if (transactionDto.getCurrency() != null) {
            WebTarget base = client.target("https://v6.exchangerate-api.com/v6");
            WebTarget target = base.path("/" + apiKey + "/latest");
            WebTarget endTarget = target.path("/{currency}").resolveTemplate("currency", transactionDto.getCurrency());

            Response response = endTarget.request().get();
            try {
                String jsonResponse = response.readEntity(String.class);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(jsonResponse);
                String result = jsonNode.get("result").asText();
                if (result.equals("error")) {
                    if (jsonNode.get("error-type").asText().equals("unsupported-code")) {
                        throw new IllegalArgumentException("Illegal currency: " + transactionDto.getCurrency());
                    }
                    else {
                        throw new RuntimeException();
                    }
                }
                JsonNode conversionRatesNode = jsonNode.get("conversion_rates");
                BigDecimal conversionRate = conversionRatesNode.get("USD").decimalValue();
                change = change.multiply(conversionRate);
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }

        log.info("Balance: " + balance);
        log.info("Change: " + change);
        log.info("UserId: " + userId);


        if (change.signum() == -1 && change.abs().compareTo(balance) > 0) {
            throw new IllegalArgumentException("Illegal balance change!");
        }


        wallet.setBalance(balance.add(change));

        WebTarget baseHistory = client.target("http://localhost:8085/api");
        WebTarget targetHistory = baseHistory.path("/history/transactions");
        WebTarget endTargetHistory = targetHistory.path("/{userId}").resolveTemplate("userId", userId);

        TransactionDto transaction = new TransactionDto(change);
        transaction.setUserId(userId);
        transaction.setWalletId(wallet.getId());

        Response responseHistory = endTargetHistory.request().post(Entity.json(transaction));

        if (responseHistory.getStatus() != 201) {
            throw new RuntimeException("Problem with history");
        }


        /*
        Ta koda se bo nadomsetila s nekim klicem na promotions api


        em.persist(transaction);
        em.flush();

        if (transactionDto.getPromotionId() != null) {
            User user = userBean.getUserById(userId);
            Promotion promotion = promotionBean.getPromotionById(transactionDto.getPromotionId());
            promotionBean.executePromotionOnTransaction(promotion, user, transaction);
        }
         */


        em.persist(wallet);
        em.flush();

        return wallet;
    }



    @Transactional(Transactional.TxType.REQUIRED)
    public WalletDto updateWalletDto(Integer userId, TransactionDto transactionDto) {
        return new WalletDto(updateWallet(userId, transactionDto));
    }



    @Transactional(Transactional.TxType.REQUIRED)
    public WalletDto getWalletDtoByUserId(Integer userId) {
        return new WalletDto(getWalletByUserId(userId));
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Wallet getWalletByUserId(Integer userId) {
        String queryString = "SELECT w FROM Wallet w WHERE w.userId = :userId";
        Query query = em.createQuery(queryString);
        query.setParameter("userId", userId);

        return (Wallet) query.getSingleResult();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public WalletDto addWalletForUser(Integer userId){
        Wallet newWallet = new Wallet();
        newWallet.setUserId(userId);
        em.persist(newWallet);
        em.flush();


        return new WalletDto(newWallet);
    }





}
