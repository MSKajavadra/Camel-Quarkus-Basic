package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.entity.CustomerOrder;
import org.acme.repository.OrderRepo;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepo orderRepository;

    @Transactional
    public void placeOrder(CustomerOrder order) {
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Pending"); // Set initial status
        orderRepository.persist(order);
    }

    public List<CustomerOrder> getOrderHistory(Long customerId) {
        return orderRepository.find("customerId", customerId).list();
    }

}
