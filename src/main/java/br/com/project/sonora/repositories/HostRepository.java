package br.com.project.sonora.repositories;

import br.com.project.sonora.models.Host;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HostRepository extends JpaRepository<Host, Long> {
    Optional<Host> findById(Long id);
    Optional<Host> findByEmail(String email);
}
