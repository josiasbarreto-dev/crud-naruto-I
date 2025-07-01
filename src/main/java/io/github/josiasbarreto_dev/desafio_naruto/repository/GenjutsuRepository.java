package io.github.josiasbarreto_dev.desafio_naruto.repository;
import io.github.josiasbarreto_dev.desafio_naruto.model.GenjutsuNinja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenjutsuRepository extends JpaRepository<GenjutsuNinja, Long>{
}
