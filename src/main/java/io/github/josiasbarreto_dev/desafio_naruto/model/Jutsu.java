package io.github.josiasbarreto_dev.desafio_naruto.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jutsu")
@Data
@NoArgsConstructor
public class Jutsu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer damage;
    private Integer chakraConsumption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;

    public Jutsu(String name, Integer damage, Integer chakraConsumption) {
        this.name = name;
        this.damage = damage;
        this.chakraConsumption = chakraConsumption;
    }
}


