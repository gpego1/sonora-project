package br.com.project.sonora.controllers;

import br.com.project.sonora.dto.GeneralMusicDTO;
import br.com.project.sonora.models.GeneralMusic;
import br.com.project.sonora.services.GeneralMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genders")
public class GeneralMusicController {

    @Autowired
    private GeneralMusicService generalMusicService;

    @GetMapping
    public ResponseEntity<List<GeneralMusicDTO>> getAllGenders() {
        List<GeneralMusicDTO> genders = generalMusicService.getAllGenders();
        return ResponseEntity.ok(genders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralMusicDTO> getGenderById(@PathVariable Long id) {
        GeneralMusicDTO gender = generalMusicService.getGenderById(id);
        if (gender != null) {
            return ResponseEntity.ok(gender);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<GeneralMusicDTO> createGender(@RequestBody GeneralMusic generalMusic) {
        GeneralMusicDTO createdGender = generalMusicService.saveMusicGender(generalMusic);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGender);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGender(@PathVariable Long id, @RequestBody GeneralMusic generalMusic) {
        try {
            GeneralMusicDTO updatedGender = generalMusicService.updateGeneralMusic(id, generalMusic);
            return ResponseEntity.ok(updatedGender);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGender(@PathVariable Long id) {
        generalMusicService.deleteMusicGender(id);
        return ResponseEntity.noContent().build();
    }
}