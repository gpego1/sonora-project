package br.com.project.sonora.services;

import br.com.project.sonora.dto.GeneralMusicDTO;
import br.com.project.sonora.models.GeneralMusic;
import br.com.project.sonora.repositories.GeneralMusicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneralMusicService {

    @Autowired
    private GeneralMusicRepository generalMusicRepository;

    public List<GeneralMusicDTO> getAllGenders() {
        return generalMusicRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public GeneralMusicDTO getGenderById(Long id) {
        return generalMusicRepository.findGenderById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public GeneralMusicDTO saveMusicGender(GeneralMusic musicGender) {
        if (musicGender.getTitle() == null || musicGender.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        return convertToDTO(generalMusicRepository.save(musicGender));
    }

    @Transactional
    public GeneralMusicDTO updateGeneralMusic(Long id, GeneralMusic generalMusic) {
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
            return convertToDTO(generalMusicRepository.save(existingGeneralMusic));
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new IllegalArgumentException("Conflito de concorrência ao atualizar o gênero musical. Alguém pode ter alterado os dados.");
        }
    }

    public void deleteMusicGender(Long id) {
        generalMusicRepository.deleteById(id);
    }

    private GeneralMusicDTO convertToDTO(GeneralMusic generalMusic) {
        return new GeneralMusicDTO(
                generalMusic.getId(),
                generalMusic.getTitle(),
                generalMusic.getDescription()
        );
    }
}