package br.com.project.sonora.services;
import br.com.project.sonora.models.GeneralMusic;
import br.com.project.sonora.repositories.GeneralMusicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneralMusicService {

    @Autowired
    private GeneralMusicRepository genderalMusicRepository;
    @Autowired
    private GeneralMusicRepository generalMusicRepository;

    public List<GeneralMusic> getAllGenders() {return genderalMusicRepository.findAll(); }
    public Optional<GeneralMusic> getGenderById(Long id) { return genderalMusicRepository.findGenderById(id); }

    public GeneralMusic saveMusicGender(GeneralMusic musicGender) {
        if(musicGender.getTitle() == null || musicGender.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        return genderalMusicRepository.save(musicGender);
    }
    @Transactional
    public GeneralMusic updateGeneralMusic(Long id, GeneralMusic generalMusic) {
        GeneralMusic existingGeneralMusic = generalMusicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gênero musical não encontrado."));
        try {
            if (generalMusic.getTitle() != null) {
                existingGeneralMusic.setTitle(generalMusic.getTitle());
            }
            if (generalMusic.getDescription() != null) {
                existingGeneralMusic.setDescription(generalMusic.getDescription());
            }
            if (generalMusic.getEvents() != null) {
                existingGeneralMusic.setEvents(generalMusic.getEvents());
            }
            return generalMusicRepository.save(existingGeneralMusic);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new IllegalArgumentException("Conflito de concorrência ao atualizar o gênero musical. Alguém pode ter alterado os dados.");
        }
    }
    public void deleteMusicGender(Long id) {
        genderalMusicRepository.deleteById(id);
    }
}