package br.com.project.sonora.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;


@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @JsonIgnore
    @OneToMany(mappedBy = "ticket")
    private List<Event> events;


    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalTime duration;

    @Column(nullable = false)
    private Integer qtd_sits;

    @Column(nullable = false)
    private Double price;

    public Tickets(String title, String description, LocalTime duration, Integer qtd_sits, Double price, Artist artist) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.qtd_sits = qtd_sits;
        this.price = price;
        this.artist = artist;
    }

}
