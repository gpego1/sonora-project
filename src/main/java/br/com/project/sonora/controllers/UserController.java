package br.com.project.sonora.controllers;

import br.com.project.sonora.dto.UserDTO;
import br.com.project.sonora.models.Artist;
import br.com.project.sonora.models.Customer;
import br.com.project.sonora.models.Host;
import br.com.project.sonora.repositories.ArtistRepository;
import br.com.project.sonora.repositories.CustomerRepository;
import br.com.project.sonora.repositories.HostRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final CustomerRepository customerRepository;
    private final ArtistRepository artistRepository;
    private final HostRepository hostRepository;

    public UserController(CustomerRepository customerRepository, ArtistRepository artistRepository, HostRepository hostRepository) {
        this.customerRepository = customerRepository;
        this.artistRepository = artistRepository;
        this.hostRepository = hostRepository;
    }

    @PostMapping("/users")
    public void createUser(@RequestBody UserDTO userDTO) {
        System.out.println("User created: " + userDTO.getName());

        switch (userDTO.getUserType()) {
            case "customer" -> {
                Customer customer = new Customer(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
                customerRepository.save(customer);
            }
            case "artist" -> {
                Artist artist = new Artist(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
                artistRepository.save(artist);
            }
            case "host" -> {
                Host host = new Host(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
                hostRepository.save(host);
            }
        }
    }
}
