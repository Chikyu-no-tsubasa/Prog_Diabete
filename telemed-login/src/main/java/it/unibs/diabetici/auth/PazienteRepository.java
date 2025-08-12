package it.unibs.diabetici.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PazienteRepository extends JpaRepository<Paziente, Long> {}
