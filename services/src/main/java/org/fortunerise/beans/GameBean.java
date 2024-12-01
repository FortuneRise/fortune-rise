package org.fortunerise.beans;

import org.fortunerise.dtos.BetDto;
import org.fortunerise.entities.Bet;
import org.fortunerise.entities.Game;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@ApplicationScoped
public class GameBean {

    private Logger log = Logger.getLogger(GameBean.class.getName());

    @PersistenceContext(unitName = "fortune-rise-jpa")
    private EntityManager em;

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

    public void playGame(Integer userId, List<BetDto> madeBets){
        Random random = new Random();

        Game newGame = new Game();
        newGame.setDate(LocalDateTime.now());
        ArrayList<Bet> betList = new ArrayList<>();

        Integer roll = random.nextInt(37);

        for(BetDto betDto : madeBets){
            betList.add(betDto.convertToBet(roll));
        }

        // Add sum of bets


    }


}
