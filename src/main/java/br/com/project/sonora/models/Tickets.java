package br.com.project.sonora.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

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

    public Tickets(String title, String description, LocalTime duration, Integer qtd_sits, Double price, Customer customer, Event event) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.qtd_sits = qtd_sits;
        this.price = price;
        this.customer = customer;
        this.event = event;
    }

}
