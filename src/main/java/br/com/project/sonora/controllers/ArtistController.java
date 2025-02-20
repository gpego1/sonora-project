package br.com.project.sonora.controllers;

import br.com.project.sonora.models.Artist;
import br.com.project.sonora.services.ArtistService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping
    public List<Artist> getAllSellers() {
        return artistService.getAllArtists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getSellerById(@PathVariable Long id) {
        return artistService.getSellerById(id)
                .map(seller -> ResponseEntity.ok(seller))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Artist createSeller(@RequestBody Artist artist) {
        return artistService.saveSeller(artist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeller(@PathVariable Long id, @RequestBody Artist artist) {
        try {
            Artist updatedArtist = artistService.updateSeller(id, artist);
            return ResponseEntity.ok(updatedArtist);
        } catch (OptimisticLockException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        artistService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }
}