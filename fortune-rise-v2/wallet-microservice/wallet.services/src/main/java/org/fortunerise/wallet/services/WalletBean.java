package org.fortunerise.wallet.services;


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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.logging.Logger;

@ApplicationScoped
public class WalletBean {


    private Logger log = Logger.getLogger(WalletBean.class.getName());

    @PersistenceContext(unitName = "fortune-rise-jpa")
    private EntityManager em;

    private Client client;

    @PostConstruct
    private void init() {
        log.info("Bean initialization" + WalletBean.class.getSimpleName());
        client = ClientBuilder.newClient();
        // inicializacija virov
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
