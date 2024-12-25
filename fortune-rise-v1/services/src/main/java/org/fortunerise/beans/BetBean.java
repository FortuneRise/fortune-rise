package org.fortunerise.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
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
}
