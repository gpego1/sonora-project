package br.com.project.sonora.services;

import br.com.project.sonora.models.GeneralMusic;
import br.com.project.sonora.repositories.GeneralMusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneralMusicService {

    @Autowired
    private GeneralMusicRepository genderalMusicRepository;

    public List<GeneralMusic> getAllGenders() {return genderalMusicRepository.findAll(); }
    public Optional<GeneralMusic> getGenderById(Long id) { return genderalMusicRepository.findById(id); }

}
