package br.com.project.sonora.controllers;
import br.com.project.sonora.models.GeneralMusic;
import br.com.project.sonora.services.GeneralMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genders")
public class GeneralMusicController {


    @Autowired
    private GeneralMusicService genderService;

    @GetMapping
    public List<GeneralMusic> getAllCustomers() {
        return genderService.getAllGenders();
    }

    @GetMapping("/{id}")
    public Optional<GeneralMusic>getCustomerById(@PathVariable Long id) {return genderService.getGenderById(id);}

    @PostMapping
    public GeneralMusic createGender(@RequestBody GeneralMusic gender) {
        return genderService.saveMusicGender(gender);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMusicGender(@PathVariable Long id, @RequestBody GeneralMusic gender) {
        try {
            GeneralMusic updatedGender = genderService.updateGeneralMusic(id, gender);
            return ResponseEntity.ok(updatedGender);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGender(@PathVariable Long id) {
        genderService.deleteMusicGender(id);
        return ResponseEntity.noContent().build();
    }


}