package br.com.project.sonora.services;
import br.com.project.sonora.models.*;
import br.com.project.sonora.repositories.ArtistRepository;
import br.com.project.sonora.repositories.EventRepository;
import br.com.project.sonora.repositories.GeneralMusicRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GeneralMusicRepository generalMusicRepository;

    @Autowired
    private ArtistRepository artistRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> getEventByGeneralMusic(GeneralMusic gender) {
        return eventRepository.findByGeneralMusics(gender);
    }

    public List<Event> getEventByGeneralMusicId(Long genderId) {
        return eventRepository.findByGeneralMusicsId(genderId);
    }

    public List<Event> getEventByTicket(Tickets ticket) {return eventRepository.findEventsByTicket(ticket);}

    public List<Event> getEventByTicketId(Long ticketId) {return eventRepository.findEventsByTicketId(ticketId);}

    public Event saveEvent(Event event) {
        if (event.getAddress() == null || event.getArtists() == null) {
            throw new IllegalArgumentException("The order mus have another Customer.");
        }
        return eventRepository.save(event);
    }

    @Transactional
    public Event updateEvent(Long id, Event event) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        try {
            if (event.getDate() != null) {
                existingEvent.setDate(event.getDate());
            }
            if (event.getAddress() != null) {
                existingEvent.setAddress(event.getAddress());
            }
            return eventRepository.save(existingEvent);
        } catch (StaleObjectStateException ex) {
            throw new OptimisticLockException("O registro que você tentou atualizar foi modificado por outro usuário. Por favor, recarregue os dados e tente novamente.");
        }
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Transactional
    public Event addGenderToEvent(Long eventId, GeneralMusic gender) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (!eventOptional.isPresent()) {
            Event event = eventOptional.get();
            GeneralMusic existingGender = generalMusicRepository.findById(gender.getId()).orElse(gender);
            event.getGeneralMusics().add(existingGender);
            return eventRepository.save(event);
        }
        return null;
    }
    @Transactional
    public Event removeGenderFromEvent(Long eventId, Long genderId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        Optional<GeneralMusic> genderOptional = generalMusicRepository.findGenderById(genderId);

        if (eventOptional.isPresent() && genderOptional.isPresent()) {
            Event event = eventOptional.get();
            GeneralMusic gender = genderOptional.get();
            event.getGeneralMusics().remove(gender);
            return eventRepository.save(event);
        }
        return null;
    }

    @Transactional
    public Event addArtistToEvent(Long eventId, Artist artist) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (!eventOptional.isPresent()) {
            Event event = eventOptional.get();
            Artist existingArtist = artistRepository.findById(artist.getId()).orElse(artist);
            event.getArtists().add(existingArtist);
            return eventRepository.save(event);
        }
        return null;
    }
    @Transactional
    public Event removeArtistFromEvent(Long eventId, Long artistId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        Optional<Artist> artistOptional = artistRepository.findById(artistId);

        if (eventOptional.isPresent() && artistOptional.isPresent()) {
            Event event = eventOptional.get();
            Artist artist = artistOptional.get();
            event.getGeneralMusics().remove(artist);
            return eventRepository.save(event);
        }
        return null;
    }
}

