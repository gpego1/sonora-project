package br.com.project.sonora.controllers;

import br.com.project.sonora.dto.UserDTO;
import br.com.project.sonora.models.Artist;
import br.com.project.sonora.models.Customer;
import br.com.project.sonora.models.Host;
import br.com.project.sonora.repositories.ArtistRepository;
import br.com.project.sonora.repositories.CustomerRepository;
import br.com.project.sonora.repositories.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final CustomerRepository customerRepository;
    private final ArtistRepository artistRepository;
    private final HostRepository hostRepository;

    public UserController(CustomerRepository customerRepository, ArtistRepository artistRepository, HostRepository hostRepository) {
        this.customerRepository = customerRepository;
        this.artistRepository = artistRepository;
        this.hostRepository = hostRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

            switch (userDTO.getUserType()) {
                case "customer" -> {
                    Customer customer = new Customer(userDTO.getName(), userDTO.getCpf(), userDTO.getEmail(), encodedPassword, userDTO.getPhone()); // Use encodedPassword
                    customerRepository.save(customer);
                }
                case "artist" -> {
                    Artist artist = new Artist(userDTO.getName(), userDTO.getCpf(), userDTO.getEmail(), encodedPassword, userDTO.getPhone()); // Use encodedPassword
                    artistRepository.save(artist);
                }
                case "host" -> {
                    Host host = new Host(userDTO.getName(), userDTO.getCpf(), userDTO.getEmail(), encodedPassword, userDTO.getPhone()); // Use encodedPassword
                    hostRepository.save(host);
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user type");
                }
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDTO userDTO) {
        try {
            Optional<Customer> customer = customerRepository.findByEmail(userDTO.getEmail());
            if (customer.isPresent() && passwordEncoder.matches(userDTO.getPassword(), customer.get().getPassword())) {
                return ResponseEntity.ok("Login successful for customer: " + customer.get().getEmail()); // Add email
            }
            Optional<Artist> artist = artistRepository.findByEmail(userDTO.getEmail());
            if (artist.isPresent() && passwordEncoder.matches(userDTO.getPassword(), artist.get().getPassword())) {
                return ResponseEntity.ok("Login successful for artist: " + artist.get().getEmail()); // Add email
            }
            Optional<Host> host = hostRepository.findByEmail(userDTO.getEmail());
            if (host.isPresent() && passwordEncoder.matches(userDTO.getPassword(), host.get().getPassword())) {
                return ResponseEntity.ok("Login successful for host: " + host.get().getEmail()); // Add email
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}