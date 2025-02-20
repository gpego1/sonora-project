package br.com.project.sonora.repositories;
import br.com.project.sonora.models.Tickets;
import br.com.project.sonora.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, Long> {
    Optional<Tickets> findById(Long id);
    List<Tickets> findBySeller(Artist artist);
    List<Tickets> findBySellerId(Long sellerId);
}
