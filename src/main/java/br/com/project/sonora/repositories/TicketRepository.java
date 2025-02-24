package br.com.project.sonora.repositories;
import br.com.project.sonora.models.Event;
import br.com.project.sonora.models.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, Long> {
    Optional<Tickets> findById(Long id);
    List<Tickets> findByEvent(Event event);
    List<Tickets> findByEventId(Long eventId);
}
