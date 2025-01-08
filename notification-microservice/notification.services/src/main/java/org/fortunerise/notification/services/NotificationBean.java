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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
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

    @Transactional
    private String getParameterString(UriInfo uriInfo) {
        MultivaluedMap<String, String> paramMultiMap = uriInfo.getQueryParameters();
        String paramString = paramMultiMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(value -> entry.getKey() + "=" + value))
                .collect(Collectors.joining("&"));
        if (paramString.isEmpty()) {
            paramString += "?";
        }
        else {
            paramString += "&";
        }

        return paramString;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<NotificationDto> getAllNotificationsByUserId(Integer userId, UriInfo uriInfo){
        String paramString = getParameterString(uriInfo);
        paramString += "userId=" + userId;
        QueryParameters queryParameters = QueryParameters.query(paramString).build();
        List<Notification> notifications = JPAUtils.queryEntities(em, Notification.class, queryParameters);

        return notifications.stream().map(NotificationDto::new).collect(java.util.stream.Collectors.toList());
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


