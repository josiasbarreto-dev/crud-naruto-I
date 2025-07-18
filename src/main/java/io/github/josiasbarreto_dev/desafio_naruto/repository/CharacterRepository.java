package io.github.josiasbarreto_dev.desafio_naruto.repository;

import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    boolean existsByName(String name);
    Optional <Character> findByName(String name);
}
