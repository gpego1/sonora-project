package br.com.project.sonora.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private String endereco;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;



    public Order(Long id, Date date, String endereco, Customer customer) {
        this.id = id;
        this.date = date;
        this.endereco = endereco;
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order reserve = (Order) o;
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
                ", endereco='" + endereco + '\'' +
                ", customer=" + customer +
                '}';
    }
}
