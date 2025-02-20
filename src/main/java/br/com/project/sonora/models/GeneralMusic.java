package br.com.project.sonora.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GeneralMusic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "generalMusics")
    private Set<Event> events = new HashSet<>();

    private String title;
    private String description;

    public GeneralMusic(String title, String description) {
        this.title = title;
        this.description = description;
    }

}
