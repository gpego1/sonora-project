package br.com.project.sonora.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Ligação entre Event e Host
    @ManyToOne
    @JoinColumn(name = "host_id")
    private Host host;

    @Column(nullable = false)
    private Date date;

    //Ligação entre Event e Endereço
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    //Ligação entre Event e Gêneros musicais
    @ManyToMany
    @JoinTable(
            name = "event-genders",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "gender_id")
    )
    private Set<GeneralMusic> generalMusics = new HashSet<>();

    //Ligação entre Event e Artistas
    @ManyToMany
    @JoinTable(
            name = "artists-events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private Set<Artist> artists = new HashSet<>();

    //Tabela de relacionamento entre Event e Artistas com setlist
    @OneToMany(mappedBy = "event")
    private Set<ArtistEventSetlist> artistEventSetlists = new HashSet<>();

    //Ligação entre Event e Ingressos
    @OneToMany(mappedBy = "event")
    private Set<Tickets> tickets = new HashSet<>();

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
