package org.fortunerise.user.services;

import org.fortunerise.user.entities.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class UserBean {

    private Logger log = Logger.getLogger(UserBean.class.getName());

    private Client client;

    @PersistenceContext(unitName = "fortune-rise-jpa")
    private EntityManager em;

    @PostConstruct
    private void init() {
        log.info("Bean initialization: " + UserBean.class.getSimpleName());
        client = ClientBuilder.newClient();
        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Bean deinitialization: " + UserBean.class.getSimpleName());

        // zapiranje virov
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<UserDto> getUsers() {
        List<UserDto> userDtos = em.createQuery("SELECT new org.fortunerise.user.services.UserDto(u) FROM User u").getResultList();
        return userDtos;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public UserDto insertUser(UserDto userDto){
        User user = userDto.convertToUser();
        em.persist(user);
        em.flush();

        WebTarget base = client.target("http://localhost:8081/api");
        WebTarget target = base.path("/wallets");
        WebTarget endTarget = target.path("/{userId}").resolveTemplate("userId", user.getId());


        Response response = endTarget.request().post(Entity.json(user));
        int status = response.getStatus();

        if (status == 400) {
            throw new BadRequestException("Wallet for this user already exists!");
        }else if (status != 200) {
            throw new RuntimeException();
        }


        userDto.setId(user.getId());
        return userDto;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public UserDto getUserDtoById(Integer userId){
        return new UserDto(getUserById(userId));
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public User getUserById(Integer userId){
        String queryString = "SELECT u FROM User u WHERE u.id = :userId";
        Query query = em.createQuery(queryString);
        query.setParameter("userId", userId);

        return (User) query.getSingleResult();
    }

}



