package io.github.josiasbarreto_dev.desafio_naruto.repository;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjutsuNinja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NinjutsuRepository extends JpaRepository<NinjutsuNinja, Long>{
}
