package br.com.project.sonora.repositories;

import br.com.project.sonora.models.Order;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);
    Optional<Order> findByCustomerId(Long customerId);
}
