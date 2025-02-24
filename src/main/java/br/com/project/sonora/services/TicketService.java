package br.com.project.sonora.services;

import br.com.project.sonora.models.Event;
import br.com.project.sonora.models.Tickets;
import br.com.project.sonora.models.Artist;
import br.com.project.sonora.repositories.TicketRepository;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public List<Tickets> getAllTickets() {return ticketRepository.findAll();}
    public Optional<Tickets> getTicketById(Long id) {return ticketRepository.findById(id);}
    public List<Tickets> getTicketByEvent(Event event) {
        return ticketRepository.findByEvent(event);
    }
    public List<Tickets> getTicketByEventsId(long eventId) {
        return ticketRepository.findByEventId(eventId);
    }
    public Tickets saveTicket(Tickets ticket) {
        if (ticket.getEvent() == null || ticket.getEvent().getId() == null) {
            throw new IllegalArgumentException("Post must have a seller");
        }
        return ticketRepository.save(ticket);
    }
    @Transactional
    public Tickets updateTicket(Long id, Tickets ticket) {
        Tickets existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
            try {
                if(ticket.getPrice() != null) {
                    existingTicket.setPrice(ticket.getPrice());
                }
                if(ticket.getTitle()!= null) {
                    existingTicket.setTitle(ticket.getTitle());
                }
                if(ticket.getDescription()!= null) {
                    existingTicket.setDescription(ticket.getDescription());
                }
                if(ticket.getDescription()!= null) {
                    existingTicket.setDuration(ticket.getDuration());
                }
                if(ticket.getPrice()!= null) {
                    existingTicket.setPrice(ticket.getPrice());
                }
                if(ticket.getEvent()!= null) {
                    existingTicket.setEvent(ticket.getEvent());
                }
                return ticketRepository.save(existingTicket);
                } catch (StaleObjectStateException e) {
                throw new OptimisticLockException("Invalid update request");
            }
    }
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
