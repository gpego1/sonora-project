package br.com.project.sonora.controllers;

import br.com.project.sonora.dto.ArtistDTO;
import br.com.project.sonora.models.Artist;
import br.com.project.sonora.services.ArtistService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping
    public ResponseEntity<List<ArtistDTO>> getAllArtists() {
        List<ArtistDTO> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> getArtistById(@PathVariable Long id) {
        ArtistDTO artist = artistService.getArtistById(id);
        if (artist != null) {
            return ResponseEntity.ok(artist);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ArtistDTO> createArtist(@RequestBody Artist artist) {
        ArtistDTO createdArtist = artistService.saveArtist(artist);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArtist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArtist(@PathVariable Long id, @RequestBody Artist artist) {
        try {
            ArtistDTO updatedArtist = artistService.updateArtist(id, artist);
            return ResponseEntity.ok(updatedArtist);
        } catch (OptimisticLockException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}