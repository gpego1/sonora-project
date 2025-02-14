package br.com.project.sonora;

import br.com.project.sonora.models.Customer;
import br.com.project.sonora.models.Order;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Instanciando um objeto Customer
        Customer customer = new Customer();
        customer.setName("Nome do Cliente");
        customer.setCpf("123.456.789-00");
        customer.setEmail("email@example.com");
        customer.setPassword("senha_hasheada");
        customer.setPhone("(11) 99999-9999");

        Order order = new Order();
        order.setDate(new Date());
        order.setEndereco("Rua Exemplo, 123");
        order.setCustomer(customer);

        // Imprimindo os objetos para verificar
        System.out.println("Cliente: " + customer);
        System.out.println("Pedido: " + order);
    }
}
