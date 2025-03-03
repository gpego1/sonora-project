package br.com.project.sonora.repositories;

import br.com.project.sonora.models.Host;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<Host, Long> {
}
