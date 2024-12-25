package org.fortunerise.history.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_table")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(name = "user_name")
    private String userName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications;


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_promotion",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "promotion_id")
    )
    private List<Promotion> promotions;

    public User() {}

    public User(String name, String surname, String userName){
        this.name = name;
        this.surname = surname;
        this.userName = userName;
        this.wallet = new Wallet();

    }

    public void removePromotion(Promotion promotion){
        promotions.remove(promotion);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
