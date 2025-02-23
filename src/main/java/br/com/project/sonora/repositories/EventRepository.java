package br.com.project.sonora.repositories;

import br.com.project.sonora.models.Customer;
import br.com.project.sonora.models.Event;
import br.com.project.sonora.models.GeneralMusic;
import br.com.project.sonora.models.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findById(Long id);
    List<Event> findByGeneralMusics(GeneralMusic gender);
    List<Event> findByGeneralMusicsId(@Param("genderId") Long genderId);
    List<Event> findByTicket(Tickets ticket);
    List<Event> findEventByTicketId(@Param("ticketId") Long ticketId);

}
