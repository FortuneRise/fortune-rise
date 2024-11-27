package org.fortunerise.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.util.logging.Logger;

@ApplicationScoped
public class WalletBean {
    private Logger log = Logger.getLogger(WalletBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + WalletBean.class.getSimpleName());

        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + WalletBean.class.getSimpleName());

        // zapiranje virov
    }
}
