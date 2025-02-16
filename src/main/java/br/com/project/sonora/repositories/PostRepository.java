package br.com.project.sonora.repositories;
import br.com.project.sonora.models.Post;
import br.com.project.sonora.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long id);

}
