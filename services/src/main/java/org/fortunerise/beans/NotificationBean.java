package org.fortunerise.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.util.logging.Logger;

@ApplicationScoped
public class NotificationBean {

    private Logger log = Logger.getLogger(NotificationBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + NotificationBean.class.getSimpleName());

        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + NotificationBean.class.getSimpleName());

        // zapiranje virov
    }
}
