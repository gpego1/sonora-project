package br.com.project.sonora.controllers;
import br.com.project.sonora.errors.ErrorResponse;
import br.com.project.sonora.models.Customer;
import br.com.project.sonora.models.Event;
import br.com.project.sonora.models.Tickets;
import br.com.project.sonora.repositories.EventRepository;
import br.com.project.sonora.repositories.TicketRepository;
import br.com.project.sonora.services.EventService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private EventService eventService;

    @Autowired
    private TicketRepository postRepository;
    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public List<Event> getAllOrders() {
        return eventService.getAllOrders();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Event> getOrderById(@PathVariable Long id) {
        return eventService.getOrderById(id)
                .map(order -> ResponseEntity.ok(order))
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/customer")
    public List<Event> getOrderByCustomer(@RequestBody Customer customer) {
        return eventService.getOrderByCustomer(customer);
    }
    @GetMapping("/customer/{customerId}")
        public List<Event> getOrderByCostumerId(@PathVariable Long customerId){
            return eventService.getOrderByCustomerId(customerId);
    }
    @GetMapping("/post/{postId}/orders")
    public List<Event> getOrdersByPost(@PathVariable Long postId) {
        Tickets post = postRepository.findById(postId).orElse(null);
        return eventService.getOrdersByPost(post);
    }

    @PostMapping
    public Event createOrder(@RequestBody Event event) {
        return eventService.saveOrder(event);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody Event event) {
        try {
            Event updatedEvent = eventService.updateOrder(id, event);
            return ResponseEntity.ok(updatedEvent);
        } catch (OptimisticLockException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage()));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred."));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        eventService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}