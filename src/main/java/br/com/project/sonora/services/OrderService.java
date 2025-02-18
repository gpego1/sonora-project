package br.com.project.sonora.services;
import br.com.project.sonora.models.Customer;
import br.com.project.sonora.models.Order;
import br.com.project.sonora.models.Post;
import br.com.project.sonora.repositories.OrderRepository;
import br.com.project.sonora.repositories.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    public List<Order> getOrderByCustomer(Customer customer) {
        return orderRepository.findByCustomer(customer);
    }
    public List<Order> getOrderByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    public List<Order> getOrdersByPost(Post post) {
        return orderRepository.findByPost(post);
    }


    public Order saveOrder(Order order) {
        if (order.getCustomer() == null || order.getCustomer().getId() == null) {
            throw new IllegalArgumentException("The order mus have another Customer.");
        }
        return orderRepository.save(order);
    }
    @Transactional
    public Order updateOrder(Long id, Order order) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    try {
        if (order.getDate() != null) {
            existingOrder.setDate(order.getDate());
        }
        if (order.getAddress() != null) {
            existingOrder.setAddress(order.getAddress());
        }
        if (order.getCustomer() != null && order.getCustomer().getId() != null) {
            Customer customer = new Customer();
            customer.setId(order.getCustomer().getId());
            existingOrder.setCustomer(customer);
        }
        if (order.getPost() != null && order.getPost().getId() != null) {
            Post post = new Post();
            post.setId(order.getPost().getId());
            existingOrder.setPost(post);
        }
        return orderRepository.save(existingOrder);
    } catch (StaleObjectStateException ex) {
        throw new OptimisticLockException("O registro que você tentou atualizar foi modificado por outro usuário. Por favor, recarregue os dados e tente novamente.");
    }
    }
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
