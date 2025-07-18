package io.github.josiasbarreto_dev.desafio_naruto.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jutsu {
    private Integer damage;
    private Integer chakraConsumption;
}


