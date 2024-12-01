package org.fortunerise.beans;

import org.fortunerise.entities.User;
import org.fortunerise.dtos.UserDto;
import org.fortunerise.entities.Wallet;

import javax.enterprise.context.ApplicationScoped;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class UserBean {

    private Logger log = Logger.getLogger(UserBean.class.getName());

    @PersistenceContext(unitName = "fortune-rise-jpa")
    private EntityManager em;

    @PostConstruct
    private void init() {
        log.info("Bean initialization: " + UserBean.class.getSimpleName());

        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Bean deinitialization: " + UserBean.class.getSimpleName());

        // zapiranje virov
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<UserDto> getUsers() {
        List<UserDto> userDtos = em.createQuery("SELECT new org.fortunerise.dtos.UserDto(u) FROM User u").getResultList();
        return userDtos;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void insertUser(UserDto userDto){
        User user = convertUserDtoToUser(userDto);
        em.persist(user);
        em.flush();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public UserDto getUserById(Integer usrID){
        User user = em.find(User.class, usrID);
        if(user == null){
            return null;
        }

        return new UserDto(user);
    }

    private static User convertUserDtoToUser(UserDto userDto){
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setUsername(userDto.getUsername());
        user.setWallet(new Wallet());

        return user;
    }

}