package br.com.project.sonora.services;

import br.com.project.sonora.models.Seller;
import br.com.project.sonora.repositories.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    public Optional<Seller> getSellerById(Long id) {
        return sellerRepository.findById(id);
    }

    public Seller saveSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Transactional
    public Seller updateSeller(Long id, Seller seller) {
        Seller existingSeller = sellerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));

        try {
            if (!seller.getName().equals(existingSeller.getName())) {
                existingSeller.setName(seller.getName());
            }
            if (!seller.getCpf().equals(existingSeller.getCpf())) {
                existingSeller.setCpf(seller.getCpf());
            }
            if (!seller.getEmail().equals(existingSeller.getEmail())) {
                existingSeller.setEmail(seller.getEmail());
            }
            if (!seller.getPhone().equals(existingSeller.getPhone())) {
                existingSeller.setPhone(seller.getPhone());
            }
            if (!seller.getPassword().equals(existingSeller.getPassword())) {
                existingSeller.setPassword(seller.getPassword());
            }

            return sellerRepository.save(existingSeller); // Changed to sellerRepository
        } catch (StaleObjectStateException ex) {
            throw new OptimisticLockException("The data you were trying to update has been modified by another user. Please refresh the data and try again.");
        }
    }

    public void deleteSeller(Long id) {
        sellerRepository.deleteById(id);
    }
}
