package org.fortunerise.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "promotions")
    private List<User> users;
}
