package br.com.project.sonora.controllers;

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
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.saveEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        Event updatedEvent = eventService.updateEvent(id, event);
        return updatedEvent != null ? new ResponseEntity<>(updatedEvent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/gender/{genderId}")
    public ResponseEntity<List<Event>> getEventsByGenderId(@PathVariable Long genderId) {
        List<Event> events = eventService.getEventByGeneralMusicId(genderId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/gender")
    public ResponseEntity<List<Event>> getEventsByGender(@RequestBody GeneralMusic generalMusic) {
        List<Event> events = eventService.getEventByGeneralMusic(generalMusic);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PostMapping("/{eventId}/genders")
    public ResponseEntity<Event> addGenderToEvent(@PathVariable Long eventId, @RequestBody GeneralMusic gender) {
        Event updatedEvent = eventService.addGenderToEvent(eventId, gender);
        return updatedEvent != null ? new ResponseEntity<>(updatedEvent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{eventId}/genders/{genderId}")
    public ResponseEntity<Event> removeGenderFromEvent(@PathVariable Long eventId, @PathVariable Long genderId) {
        Event updatedEvent = eventService.removeGenderFromEvent(eventId, genderId);
        return updatedEvent != null ? new ResponseEntity<>(updatedEvent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{eventId}/artists")
    public ResponseEntity<Event> addArtistToEvent(@PathVariable Long eventId, @RequestBody Artist artist) {
        Event updatedEvent = eventService.addArtistToEvent(eventId, artist);
        return updatedEvent != null ? new ResponseEntity<>(updatedEvent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{eventId}/artists/{artistId}")
    public ResponseEntity<Event> removeArtistFromEvent(@PathVariable Long eventId, @PathVariable Long artistId) {
        Event updatedEvent = eventService.removeArtistFromEvent(eventId, artistId);
        return updatedEvent != null ? new ResponseEntity<>(updatedEvent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/ticket")
    public ResponseEntity<List<Event>> getEventsByGender(@RequestBody Tickets ticket) {
        List<Event> events = eventService.getEventByTicket(ticket);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<Event>> getEventsByTicketId(@PathVariable Long ticketId) {
        List<Event> events = eventService.getEventByGeneralMusicId(ticketId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}