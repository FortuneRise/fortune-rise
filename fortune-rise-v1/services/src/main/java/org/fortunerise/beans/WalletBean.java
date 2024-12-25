package org.fortunerise.beans;

import org.fortunerise.dtos.TransactionDto;
import org.fortunerise.dtos.WalletDto;
import org.fortunerise.entities.Promotion;
import org.fortunerise.entities.Transaction;
import org.fortunerise.entities.User;
import org.fortunerise.entities.Wallet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.math.BigDecimal;
import java.util.logging.Logger;

@ApplicationScoped
public class WalletBean {

    @Inject
    private PromotionBean promotionBean;

    @Inject
    private UserBean userBean;

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

    @Transactional(Transactional.TxType.REQUIRED)
    public Wallet updateWallet(Integer userId, TransactionDto transactionDto) {
        String queryString = "SELECT w FROM Wallet w WHERE w.user.id = :userId";
        Query query = em.createQuery(queryString);
        query.setParameter("userId", userId);
        Wallet wallet = (Wallet) query.getSingleResult();
        BigDecimal balance = wallet.getBalance();
        BigDecimal change = transactionDto.getAmount();

        if (change.signum() == -1 && change.abs().compareTo(balance) > 0) {
            throw new IllegalArgumentException("Illegal balance change!");
        }

        Transaction transaction = new Transaction(wallet, change);

        wallet.setBalance(balance.add(change));
        em.persist(transaction);
        em.flush();

        if (transactionDto.getPromotionId() != null) {
            User user = userBean.getUserById(userId);
            Promotion promotion = promotionBean.getPromotionById(transactionDto.getPromotionId());
            promotionBean.executePromotionOnTransaction(promotion, user, transaction);
        }

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


    public Wallet getWalletByUserId(Integer userId) {
        String queryString = "SELECT w FROM Wallet w WHERE w.user.id = :userId";
        Query query = em.createQuery(queryString);
        query.setParameter("userId", userId);

        return (Wallet) query.getSingleResult();
    }

}
