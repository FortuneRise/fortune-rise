package org.fortunerise.promotion.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
@Table(name = "promotion")
public abstract class Promotion {

    public enum TriggerScenario {
        DEPOSIT, BET
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "promotion_seq", sequenceName = "promotion_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "trigger_scenario")
    @Enumerated(EnumType.STRING)
    private TriggerScenario triggerScenario;

    @OneToMany(mappedBy = "promotion")
    private List<UserLink> userLinks;

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

}
