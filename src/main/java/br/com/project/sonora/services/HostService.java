package br.com.project.sonora.services;

import br.com.project.sonora.dto.HostDTO;
import br.com.project.sonora.models.Host;
import br.com.project.sonora.repositories.HostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HostService {

    @Autowired
    private HostRepository hostRepository;

    public List<HostDTO> getAllHosts() {
        return hostRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public HostDTO getHostById(Long id) {
        return hostRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public HostDTO saveHost(Host host) {
        return convertToDTO(hostRepository.save(host));
    }

    @Transactional
    public HostDTO updateHost(Long id, Host host) {
        Host existingHost = hostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Host not found"));

        try {
            if (!host.getName().equals(existingHost.getName())) {
                existingHost.setName(host.getName());
            }
            if (!host.getCpf().equals(existingHost.getCpf())) {
                existingHost.setCpf(host.getCpf());
            }
            if (!host.getEmail().equals(existingHost.getEmail())) {
                existingHost.setEmail(host.getEmail());
            }
            if (!host.getPhone().equals(existingHost.getPhone())) {
                existingHost.setPhone(host.getPhone());
            }
            if (!host.getPassword().equals(existingHost.getPassword())) {
                existingHost.setPassword(host.getPassword());
            }

            return convertToDTO(hostRepository.save(existingHost));
        } catch (StaleObjectStateException ex) {
            throw new OptimisticLockException("The data you were trying to update has been modified by another user. Please refresh the data and try again.");
        }
    }

    public void deleteHost(Long id) {
        hostRepository.deleteById(id);
    }

    private HostDTO convertToDTO(Host host) {
        return new HostDTO(
                host.getId(),
                host.getName(),
                host.getCpf(),
                host.getEmail(),
                host.getPhone()
        );
    }
}