package br.com.project.sonora.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String address;


    @ManyToMany
    @JoinTable(
            name = "event-genders",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "gender_id")
    )
    private Set<GeneralMusic> generalMusics = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "artists-events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private Set<Artist> artists = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Tickets ticket;

    public Event(Long id, Date date, String address, Tickets ticket, Set<GeneralMusic> generalMusics, Set<Artist> artists) {
        this.id = id;
        this.date = date;
        this.address = address;
        this.ticket = ticket;
        this.generalMusics = generalMusics;
        this.artists = artists;

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event reserve = (Event) o;
        return Objects.equals(id, reserve.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", endereco='" + address + '\'' +
                ", customer=" +
                '}';
    }
}
