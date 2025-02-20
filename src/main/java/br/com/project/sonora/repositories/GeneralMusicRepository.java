package br.com.project.sonora.repositories;

import br.com.project.sonora.models.GeneralMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface GeneralMusicRepository extends JpaRepository<GeneralMusic, Long> {
    Optional<GeneralMusic> findGenderById(Long id);
}
