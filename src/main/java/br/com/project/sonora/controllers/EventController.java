package br.com.project.sonora.controllers;

import br.com.project.sonora.dto.EventDTO;
import br.com.project.sonora.models.Artist;
import br.com.project.sonora.models.Event;
import br.com.project.sonora.models.GeneralMusic;
import br.com.project.sonora.models.Tickets;
import br.com.project.sonora.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        EventDTO event = eventService.getEventById(id);
        return event != null ? new ResponseEntity<>(event, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody Event event) {
        EventDTO createdEvent = eventService.saveEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        EventDTO updatedEvent = eventService.updateEvent(id, event);
        return updatedEvent != null ? new ResponseEntity<>(updatedEvent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/gender/{genderId}")
    public ResponseEntity<List<EventDTO>> getEventsByGenderId(@PathVariable Long genderId) {
        List<EventDTO> events = eventService.getEventByGeneralMusicId(genderId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/gender")
    public ResponseEntity<List<EventDTO>> getEventsByGender(@RequestBody GeneralMusic generalMusic) {
        List<EventDTO> events = eventService.getEventByGeneralMusic(generalMusic);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PostMapping("/{eventId}/genders")
    public ResponseEntity<EventDTO> addGenderToEvent(@PathVariable Long eventId, @RequestBody GeneralMusic gender) {
        EventDTO updatedEvent = eventService.addGenderToEvent(eventId, gender);
        return updatedEvent != null ? new ResponseEntity<>(updatedEvent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{eventId}/genders/{genderId}")
    public ResponseEntity<EventDTO> removeGenderFromEvent(@PathVariable Long eventId, @PathVariable Long genderId) {
        EventDTO updatedEvent = eventService.removeGenderFromEvent(eventId, genderId);
        return updatedEvent != null ? new ResponseEntity<>(updatedEvent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{eventId}/artists")
    public ResponseEntity<EventDTO> addArtistToEvent(@PathVariable Long eventId, @RequestBody Artist artist) {
        EventDTO updatedEvent = eventService.addArtistToEvent(eventId, artist);
        return updatedEvent != null ? new ResponseEntity<>(updatedEvent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{eventId}/artists/{artistId}")
    public ResponseEntity<EventDTO> removeArtistFromEvent(@PathVariable Long eventId, @PathVariable Long artistId) {
        EventDTO updatedEvent = eventService.removeArtistFromEvent(eventId, artistId);
        return updatedEvent != null ? new ResponseEntity<>(updatedEvent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/ticket")
    public ResponseEntity<List<EventDTO>> getEventsByGender(@RequestBody Tickets ticket) {
        List<EventDTO> events = eventService.getEventByTicket(ticket);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<EventDTO>> getEventsByTicketId(@PathVariable Long ticketId) {
        List<EventDTO> events = eventService.getEventByTicketId(ticketId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}