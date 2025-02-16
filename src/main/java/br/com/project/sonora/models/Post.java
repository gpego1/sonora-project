package br.com.project.sonora.models;import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Duration;


@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Duration duration;

    @Column(nullable = false)
    private Integer qtd_sits;

    @Column(nullable = false)
    private Double price;

    public Post(String title, String description, Duration duration, Integer qtd_sits, Double price, Seller seller) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.qtd_sits = qtd_sits;
        this.price = price;
        this.seller = seller;
    }

}
