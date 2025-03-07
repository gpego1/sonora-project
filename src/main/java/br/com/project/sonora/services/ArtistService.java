package br.com.project.sonora.services;

import br.com.project.sonora.dto.ArtistDTO;
import br.com.project.sonora.models.Artist;
import br.com.project.sonora.repositories.ArtistRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public List<ArtistDTO> getAllArtists() {
        return artistRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ArtistDTO getArtistById(Long id) {
        return artistRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public ArtistDTO saveArtist(Artist artist) {
        return convertToDTO(artistRepository.save(artist));
    }

    @Transactional
    public ArtistDTO updateArtist(Long id, Artist artist) {
        Artist existingArtist = artistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found"));

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

            return convertToDTO(artistRepository.save(existingArtist));
        } catch (StaleObjectStateException ex) {
            throw new OptimisticLockException("The data you were trying to update has been modified by another user. Please refresh the data and try again.");
        }
    }

    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    private ArtistDTO convertToDTO(Artist artist) {
        return new ArtistDTO(
                artist.getId(),
                artist.getName(),
                artist.getCpf(),
                artist.getEmail(),
                artist.getPhone()
        );
    }
}