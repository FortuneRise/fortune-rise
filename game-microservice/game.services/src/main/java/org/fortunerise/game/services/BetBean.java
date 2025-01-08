package org.fortunerise.game.services;


import org.fortunerise.game.services.dtos.BetDto;
import org.fortunerise.game.services.bets.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.logging.Logger;

@ApplicationScoped
public class BetBean {

    private Logger log = Logger.getLogger(BetBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + BetBean.class.getSimpleName());

        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + BetBean.class.getSimpleName());

        // zapiranje virov
    }

    public BigDecimal calculatePayout(Integer roll, BetDto betDto){
        Bet bet = betDto.convertToBet(roll);
        return bet.getPayout();

    }





}
