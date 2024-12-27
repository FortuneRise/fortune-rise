package org.fortunerise.notification.services;

import com.kumuluz.ee.rest.beans.QueryFilterExpression;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.fortunerise.notification.entities.Notification;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class NotificationBean {

    @PersistenceContext(unitName = "fortune-rise-jpa")
    private EntityManager em;

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

    @Transactional(Transactional.TxType.REQUIRED)
    public List<NotificationDto> getAllNotifiacationsByUsrId(Integer userId, QueryParameters queryParameters){
        List<Notification> allNotifications = JPAUtils.queryEntities(em, Notification.class, (p, cb, r) -> cb.and(p, cb.equal(r.get("id"), userId)));
        List<NotificationDto> notificationDtos = allNotifications.stream().map(el -> new NotificationDto(el)).collect(java.util.stream.Collectors.toList());

        return notificationDtos;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public NotificationDto readNotificationDto(Integer notificationId, Integer userId){
        return new NotificationDto(readNotification(notificationId, userId));
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Notification readNotification(Integer notificationId, Integer userId){
        String queryString = "SELECT n FROM Notification n WHERE n.id = :notificationId AND n.userId = :userId" ;
        Query query = em.createQuery(queryString);
        query.setParameter("notificationId", notificationId);
        query.setParameter("userId", userId);

        Notification notification = (Notification) query.getSingleResult();
        notification.setRead(Boolean.TRUE);

        return notification;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public NotificationDto addNotification(NotificationDto notificationDto, Integer userId){

        String content = notificationDto.getContent();
        Date now = new Date();

        Notification notification = new Notification(userId, content, now);
        em.persist(notification);
        em.flush();

        return new NotificationDto(notification);
    }

}


