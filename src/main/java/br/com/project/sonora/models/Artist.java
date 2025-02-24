package br.com.project.sonora.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "artists")
@Getter
@Setter
@NoArgsConstructor
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "artists")
    private Set<Event> events = new HashSet<>();

    @OneToMany(mappedBy = "artist")
    private Set<ArtistEventSetlist> artistEventSetlists = new HashSet<>();

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Version
    private Integer version;

    public Artist(String name, String cpf, String email, String password, String phone, Set<Event> events,Set<ArtistEventSetlist> artistEventSetlists) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.events = events;
        this.artistEventSetlists = artistEventSetlists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(cpf, artist.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
