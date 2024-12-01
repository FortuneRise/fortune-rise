package org.fortunerise.beans;

import org.fortunerise.dtos.WalletDto;
import org.fortunerise.entities.User;
import org.fortunerise.entities.Wallet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.logging.Logger;

@ApplicationScoped
public class WalletBean {

    private Logger log = Logger.getLogger(WalletBean.class.getName());

    @PersistenceContext(unitName = "fortune-rise-jpa")
    private EntityManager em;

    @PostConstruct
    private void init() {
        log.info("Bean initialization" + WalletBean.class.getSimpleName());

        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Bean deinitialization " + WalletBean.class.getSimpleName());

        // zapiranje virov
    }


    //This no work
    //Why?
    //Don't know
    @Transactional(Transactional.TxType.REQUIRED)
    public boolean updateWallet(Integer userId, BigDecimal amount){
        String queryString = "SELECT w FROM Wallet w WHERE w.user.id = :userId";
        Query query = em.createQuery(queryString);
        query.setParameter("userId", userId);

        Wallet wallet = (Wallet) query.getSingleResult();
        BigDecimal balance = wallet.getBalance();

        if(amount.signum() == -1 && amount.abs().compareTo(balance) == 1){
            return false;
        }

        amount = balance.add(amount);
        wallet.setBalance(balance);
        return true;



    }

    @Transactional(Transactional.TxType.REQUIRED)
    public WalletDto getWalletByUserId(Integer userId) {
        String queryString = "SELECT w FROM Wallet w WHERE w.user.id = :userId";
        Query query = em.createQuery(queryString);
        query.setParameter("userId", userId);

        try {
            Wallet wallet = (Wallet) query.getSingleResult();
            return WalletDto.convertWalletToWalletDto(wallet);
        }catch (NoResultException e){
            return null;
        }
    }

}
