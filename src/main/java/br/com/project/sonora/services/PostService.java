package br.com.project.sonora.services;

import br.com.project.sonora.models.Post;
import br.com.project.sonora.repositories.PostRepository;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }
    public Post savePost(Post post) {
        if (post.getSeller() == null || post.getSeller().getId() == null ) {
            throw new IllegalArgumentException("Post must have a seller");
        }
        return postRepository.save(post);
    }
    @Transactional
    public Post updatePost(Long id, Post post) {
        Post existingPost = postRepository.findById(id)
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
                if(post.getSeller()!= null) {
                    existingPost.setSeller(post.getSeller());
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
