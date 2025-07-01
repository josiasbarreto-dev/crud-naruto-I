package io.github.josiasbarreto_dev.desafio_naruto.repository;
import io.github.josiasbarreto_dev.desafio_naruto.model.TaijutsuNinja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaijutsuRepository extends JpaRepository<TaijutsuNinja, Long>{
}
