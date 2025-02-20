package br.com.project.sonora.repositories;

import br.com.project.sonora.models.Customer;
import br.com.project.sonora.models.Event;
import br.com.project.sonora.models.GeneralMusic;
import br.com.project.sonora.models.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findById(Long id);
    List<Event> findByGender(GeneralMusic gender);
    List<Event> findByGenderId(Long genderId);
    List<Event> findByPost(Tickets post);

}
