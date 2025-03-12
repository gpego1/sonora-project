package br.com.project.sonora.controllers;

import br.com.project.sonora.dto.UserDTO;
import br.com.project.sonora.models.Artist;
import br.com.project.sonora.models.Customer;
import br.com.project.sonora.models.Host;
import br.com.project.sonora.repositories.ArtistRepository;
import br.com.project.sonora.repositories.CustomerRepository;
import br.com.project.sonora.repositories.HostRepository;
import br.com.project.sonora.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

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
                    Customer customer = new Customer(userDTO.getName(), userDTO.getCpf(), userDTO.getEmail(), encodedPassword, userDTO.getPhone());
                    customerRepository.save(customer);
                }
                case "artist" -> {
                    Artist artist = new Artist(userDTO.getName(), userDTO.getCpf(), userDTO.getEmail(), encodedPassword, userDTO.getPhone());
                    artistRepository.save(artist);
                }
                case "host" -> {
                    Host host = new Host(userDTO.getName(), userDTO.getCpf(), userDTO.getEmail(), encodedPassword, userDTO.getPhone());
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
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        try {
            Optional<Customer> customer = customerRepository.findByEmail(userDTO.getEmail());
            if (customer.isPresent() && passwordEncoder.matches(userDTO.getPassword(), customer.get().getPassword())) {
                String token = jwtService.generateToken(customer.get().getEmail(), customer.get().getName());
                System.out.println("Token gerado para " + customer.get().getEmail() + ": " + token);
                return ResponseEntity.ok(Map.of("token", token, "userType", "customer"));
            }
            Optional<Artist> artist = artistRepository.findByEmail(userDTO.getEmail());
            if (artist.isPresent() && passwordEncoder.matches(userDTO.getPassword(), artist.get().getPassword())) {
                String token = jwtService.generateToken(artist.get().getEmail(), artist.get().getName());
                System.out.println("Token gerado para " + artist.get().getEmail() + ": " + token);
                return ResponseEntity.ok(Map.of("token", token, "userType", "artist"));
            }
            Optional<Host> host = hostRepository.findByEmail(userDTO.getEmail());
            if (host.isPresent() && passwordEncoder.matches(userDTO.getPassword(), host.get().getPassword())) {
                String token = jwtService.generateToken(host.get().getEmail(), host.get().getName());
                System.out.println("Token gerado para " + host.get().getEmail() + ": " + token);
                return ResponseEntity.ok(Map.of("token", token, "userType", "host"));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}