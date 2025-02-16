package br.com.project.sonora.controllers;
import br.com.project.sonora.errors.ErrorResponse;
import br.com.project.sonora.errors.exceptions.EntityNotFoundException;
import br.com.project.sonora.models.Order;
import br.com.project.sonora.models.Post;
import br.com.project.sonora.models.Seller;
import br.com.project.sonora.repositories.OrderRepository;
import br.com.project.sonora.services.PostService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(post -> ResponseEntity.ok(post))
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/seller")
    public List<Post> getPostBySeller(@RequestBody Seller seller) {
        return postService.getPostBySeller(seller);
    }
    @GetMapping("/seller/{sellerId}")
    public List<Post> getPostBySellerById(@PathVariable Long sellerId) {
        return postService.getPostBySellerId(sellerId);
    }
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.savePost(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Post post) {
        try {
            Post updatedPost = postService.updatePost(id, post);
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
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        }


}