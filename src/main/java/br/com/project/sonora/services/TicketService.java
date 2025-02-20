package br.com.project.sonora.services;

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
    private TicketRepository postRepository;

    public List<Tickets> getAllPosts() {
        return postRepository.findAll();
    }
    public Optional<Tickets> getPostById(Long id) {
        return postRepository.findById(id);
    }
    public List<Tickets> getPostBySeller(Artist artist) {
        return postRepository.findBySeller(artist);
    }
    public List<Tickets> getPostBySellerId(long sellerId) {
        return postRepository.findBySellerId(sellerId);
    }
    public Tickets savePost(Tickets post) {
        if (post.getArtist() == null || post.getArtist().getId() == null ) {
            throw new IllegalArgumentException("Post must have a seller");
        }
        return postRepository.save(post);
    }
    @Transactional
    public Tickets updatePost(Long id, Tickets post) {
        Tickets existingPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
            try {
                if(post.getPrice() != null) {
                    existingPost.setPrice(post.getPrice());
                }
                if(post.getTitle()!= null) {
                    existingPost.setTitle(post.getTitle());
                }
                if(post.getDescription()!= null) {
                    existingPost.setDescription(post.getDescription());
                }
                if(post.getDescription()!= null) {
                    existingPost.setDuration(post.getDuration());
                }
                if(post.getPrice()!= null) {
                    existingPost.setPrice(post.getPrice());
                }
                if(post.getArtist()!= null) {
                    existingPost.setArtist(post.getArtist());
                }
                return postRepository.save(existingPost);
                } catch (StaleObjectStateException e) {
                throw new OptimisticLockException("Invalid update request");
            }
    }
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
