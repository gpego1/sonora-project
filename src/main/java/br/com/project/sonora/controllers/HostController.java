package br.com.project.sonora.controllers;

import br.com.project.sonora.dto.HostDTO;
import br.com.project.sonora.models.Host;
import br.com.project.sonora.services.HostService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hosts")
public class HostController {

    @Autowired
    private HostService hostService;

    @GetMapping
    public ResponseEntity<List<HostDTO>> getAllHosts() {
        List<HostDTO> hosts = hostService.getAllHosts();
        return ResponseEntity.ok(hosts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HostDTO> getHostById(@PathVariable Long id) {
        HostDTO host = hostService.getHostById(id);
        if (host != null) {
            return ResponseEntity.ok(host);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<HostDTO> createHost(@RequestBody Host host) {
        HostDTO createdHost = hostService.saveHost(host);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHost(@PathVariable Long id, @RequestBody Host host) {
        try {
            HostDTO updatedHost = hostService.updateHost(id, host);
            return ResponseEntity.ok(updatedHost);
        } catch (OptimisticLockException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHost(@PathVariable Long id) {
        hostService.deleteHost(id);
        return ResponseEntity.noContent().build();
    }
}