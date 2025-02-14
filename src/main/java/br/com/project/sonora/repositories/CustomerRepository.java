package br.com.project.sonora.repositories;
import br.com.project.sonora.models.Customer;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Customer> findById(Long id);
}
