package br.com.project.sonora.services;

import br.com.project.sonora.models.Artist;
import br.com.project.sonora.repositories.ArtistRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public Optional<Artist> getSellerById(Long id) {
        return artistRepository.findById(id);
    }

    public Artist saveSeller(Artist artist) {
        return artistRepository.save(artist);
    }

    @Transactional
    public Artist updateSeller(Long id, Artist artist) {
        Artist existingArtist = artistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));

        try {
            if (!artist.getName().equals(existingArtist.getName())) {
                existingArtist.setName(artist.getName());
            }
            if (!artist.getCpf().equals(existingArtist.getCpf())) {
                existingArtist.setCpf(artist.getCpf());
            }
            if (!artist.getEmail().equals(existingArtist.getEmail())) {
                existingArtist.setEmail(artist.getEmail());
            }
            if (!artist.getPhone().equals(existingArtist.getPhone())) {
                existingArtist.setPhone(artist.getPhone());
            }
            if (!artist.getPassword().equals(existingArtist.getPassword())) {
                existingArtist.setPassword(artist.getPassword());
            }

            return artistRepository.save(existingArtist);
        } catch (StaleObjectStateException ex) {
            throw new OptimisticLockException("The data you were trying to update has been modified by another user. Please refresh the data and try again.");
        }
    }

    public void deleteSeller(Long id) {
        artistRepository.deleteById(id);
    }
}
