package org.fortunerise.notification.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "promotion")
public abstract class Promotion {

    public enum TriggerScenario {
        TRANSACTION, BET
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "trigger_scenario")
    @Enumerated(EnumType.STRING)
    private TriggerScenario triggerScenario;

    @ManyToMany(mappedBy = "promotions")
    private List<User> users;

    public Promotion() {}

    protected Promotion(TriggerScenario triggerScenario) {
        this.triggerScenario = triggerScenario;
    }

    public Integer getId() {
        return id;
    }

    public TriggerScenario getTriggerScenario() {
        return triggerScenario;
    }

    public List<User> getUsers() {
        return users;
    }
}
