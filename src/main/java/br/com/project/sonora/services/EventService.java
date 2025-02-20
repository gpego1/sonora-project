package br.com.project.sonora.services;
import br.com.project.sonora.models.Customer;
import br.com.project.sonora.models.Event;
import br.com.project.sonora.models.Tickets;
import br.com.project.sonora.repositories.EventRepository;
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

    public List<Event> getAllOrders() {
        return eventRepository.findAll();
    }
    public Optional<Event> getOrderById(Long id) {
        return eventRepository.findById(id);
    }
    public List<Event> getOrderByCustomer(Customer customer) {
        return eventRepository.findByCustomer(customer);
    }
    public List<Event> getOrderByCustomerId(Long customerId) {
        return eventRepository.findByCustomerId(customerId);
    }
    public List<Event> getOrdersByPost(Tickets post) {
        return eventRepository.findByPost(post);
    }


    public Event saveOrder(Event event) {
        if (event.getCustomer() == null || event.getCustomer().getId() == null) {
            throw new IllegalArgumentException("The order mus have another Customer.");
        }
        return eventRepository.save(event);
    }
    @Transactional
    public Event updateOrder(Long id, Event event) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    try {
        if (event.getDate() != null) {
            existingEvent.setDate(event.getDate());
        }
        if (event.getAddress() != null) {
            existingEvent.setAddress(event.getAddress());
        }
        if (event.getTicket() != null && event.getTicket().getId() != null) {
            Tickets post = new Tickets();
            post.setId(event.getTicket().getId());
            existingEvent.setTicket(post);
        }
        return eventRepository.save(existingEvent);
    } catch (StaleObjectStateException ex) {
        throw new OptimisticLockException("O registro que você tentou atualizar foi modificado por outro usuário. Por favor, recarregue os dados e tente novamente.");
    }
    }
    public void deleteOrder(Long id) {
        eventRepository.deleteById(id);
    }
}
