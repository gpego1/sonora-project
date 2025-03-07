package br.com.project.sonora.services;
import br.com.project.sonora.dto.AddressDTO;
import br.com.project.sonora.dto.EventDTO;
import br.com.project.sonora.dto.UserDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GeneralMusicRepository generalMusicRepository;

    @Autowired
    private ArtistRepository artistRepository;

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EventDTO getEventById(Long id) {
        return eventRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<EventDTO> getEventByGeneralMusic(GeneralMusic gender) {
        return eventRepository.findByGeneralMusics(gender).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<EventDTO> getEventByGeneralMusicId(Long genderId) {
        return eventRepository.findByGeneralMusicsId(genderId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<EventDTO> getEventByTicket(Tickets ticket) {
        return eventRepository.findEventsByTicket(ticket).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<EventDTO> getEventByTicketId(Long ticketId) {
        return eventRepository.findEventsByTicketId(ticketId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EventDTO saveEvent(Event event) {
        if (event.getAddress() == null || event.getArtists() == null) {
            throw new IllegalArgumentException("The order mus have another Customer.");
        }
        return convertToDTO(eventRepository.save(event));
    }

    @Transactional
    public EventDTO updateEvent(Long id, Event event) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        try {
            if (event.getDate() != null) {
                existingEvent.setDate(event.getDate());
            }
            if (event.getAddress() != null) {
                existingEvent.setAddress(event.getAddress());
            }
            return convertToDTO(eventRepository.save(existingEvent));
        } catch (StaleObjectStateException ex) {
            throw new OptimisticLockException("O registro que você tentou atualizar foi modificado por outro usuário. Por favor, recarregue os dados e tente novamente.");
        }
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Transactional
    public EventDTO addGenderToEvent(Long eventId, GeneralMusic gender) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (!eventOptional.isPresent()) {
            Event event = eventOptional.get();
            GeneralMusic existingGender = generalMusicRepository.findById(gender.getId()).orElse(gender);
            event.getGeneralMusics().add(existingGender);
            return convertToDTO(eventRepository.save(event));
        }
        return null;
    }
    @Transactional
    public EventDTO removeGenderFromEvent(Long eventId, Long genderId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        Optional<GeneralMusic> genderOptional = generalMusicRepository.findGenderById(genderId);

        if (eventOptional.isPresent() && genderOptional.isPresent()) {
            Event event = eventOptional.get();
            GeneralMusic gender = genderOptional.get();
            event.getGeneralMusics().remove(gender);
            return convertToDTO(eventRepository.save(event));
        }
        return null;
    }

    @Transactional
    public EventDTO addArtistToEvent(Long eventId, Artist artist) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (!eventOptional.isPresent()) {
            Event event = eventOptional.get();
            Artist existingArtist = artistRepository.findById(artist.getId()).orElse(artist);
            event.getArtists().add(existingArtist);
            return convertToDTO(eventRepository.save(event));
        }
        return null;
    }
    @Transactional
    public EventDTO removeArtistFromEvent(Long eventId, Long artistId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        Optional<Artist> artistOptional = artistRepository.findById(artistId);

        if (eventOptional.isPresent() && artistOptional.isPresent()) {
            Event event = eventOptional.get();
            Artist artist = artistOptional.get();
            event.getGeneralMusics().remove(artist);
            return convertToDTO(eventRepository.save(event));
        }
        return null;
    }
    private EventDTO convertToDTO(Event event) {
        List<GeneralMusic> genres = new ArrayList<>(event.getGeneralMusics());

        List<UserDTO> artists = event.getArtists().stream()
                .map(this::converToUserDTO)
                .collect(Collectors.toList());

        List<UserDTO> hosts = new ArrayList<>();
        hosts.add(converToUserDTO(event.getHost()));

        AddressDTO addressDTO = new AddressDTO(event.getAddress().getId(),event.getAddress().getState(), event.getAddress().getCity(), event.getAddress().getStreet(), event.getAddress().getNumber(), event.getAddress().getCep());
        return new EventDTO(event.getId(), event.getDate(), addressDTO, genres, artists, hosts);
    }
    private UserDTO converToUserDTO(Artist artist) {
        return new UserDTO(artist.getCpf(), artist.getName(), artist.getEmail(), artist.getEmail(), artist.getPassword(), artist.getPhone());
    }
    private UserDTO converToUserDTO(Host host) {
        return new UserDTO(host.getCpf(), host.getName(), host.getEmail(), host.getEmail(), host.getPassword(), host.getPhone());
    }
}

