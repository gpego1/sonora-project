package br.com.project.sonora.controllers;
import br.com.project.sonora.models.GeneralMusic;
import br.com.project.sonora.services.GeneralMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genders")
public class GeneralMusicController {


    @Autowired
    private GeneralMusicService genderService;

    @GetMapping
    public List<GeneralMusic> getAllCustomers() {
        return genderService.getAllGenders();
    }

    @GetMapping("/{id}")
    public Optional<GeneralMusic>getCustomerById(@PathVariable Long id) {return genderService.getGenderById(id);}


}

