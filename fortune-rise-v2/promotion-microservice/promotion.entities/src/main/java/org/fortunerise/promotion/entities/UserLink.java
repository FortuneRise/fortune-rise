package org.fortunerise.promotion.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_link")
public class UserLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

    public Promotion getPromotion() {
        return promotion;
    }

    public UserLink() {}

    public UserLink(Integer userId, Promotion promotion) {
        this.userId = userId;
        this.promotion = promotion;
    }
}
