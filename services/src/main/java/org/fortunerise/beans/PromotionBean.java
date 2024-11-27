package org.fortunerise.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.util.logging.Logger;

@ApplicationScoped
public class PromotionBean {


    private Logger log = Logger.getLogger(PromotionBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + PromotionBean.class.getSimpleName());

        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + PromotionBean.class.getSimpleName());

        // zapiranje virov
    }
}
