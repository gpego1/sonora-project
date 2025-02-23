package br.com.project.sonora.services;
import br.com.project.sonora.models.Customer;
import br.com.project.sonora.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Correct import

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    
    @Transactional
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        try {
            if (!customer.getName().equals(existingCustomer.getName())) {
                existingCustomer.setName(customer.getName());
            }
            if (!customer.getCpf().equals(existingCustomer.getCpf())) {
                existingCustomer.setCpf(customer.getCpf());
            }
            if (!customer.getEmail().equals(existingCustomer.getEmail())) {
                existingCustomer.setEmail(customer.getEmail());
            }
            if (!customer.getPhone().equals(existingCustomer.getPhone())) {
                existingCustomer.setPhone(customer.getPhone());
            }
            if (!customer.getPassword().equals(existingCustomer.getPassword())) {
                existingCustomer.setPassword(customer.getPassword());
            }
            return customerRepository.save(existingCustomer);
        } catch (StaleObjectStateException ex) {
            throw new OptimisticLockException("The data you were trying to update has been modified by another user. Please refresh the data and try again.");
        }
    }
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}