package br.com.project.sonora.controllers;
import br.com.project.sonora.errors.ErrorResponse;
import br.com.project.sonora.errors.exceptions.EntityNotFoundException;
import br.com.project.sonora.models.Tickets;
import br.com.project.sonora.models.Artist;
import br.com.project.sonora.services.TicketService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public List<Tickets> getAllPosts() {
        return ticketService.getAllPosts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Tickets> getPostById(@PathVariable Long id) {
        return ticketService.getPostById(id)
                .map(post -> ResponseEntity.ok(post))
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/seller")
    public List<Tickets> getPostBySeller(@RequestBody Artist artist) {
        return ticketService.getPostBySeller(artist);
    }
    @GetMapping("/seller/{sellerId}")
    public List<Tickets> getPostBySellerById(@PathVariable Long sellerId) {
        return ticketService.getPostBySellerId(sellerId);
    }
    @PostMapping
    public Tickets createPost(@RequestBody Tickets post) {
        return ticketService.savePost(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Tickets post) {
        try {
            Tickets updatedPost = ticketService.updatePost(id, post);
            return ResponseEntity.ok(updatedPost);
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
        public ResponseEntity<Void> deletePost (@PathVariable Long id){
            ticketService.deletePost(id);
            return ResponseEntity.noContent().build();
        }


}