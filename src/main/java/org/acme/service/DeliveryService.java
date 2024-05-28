package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.entity.Delivery;
import org.acme.repository.DeliveryRepository;

@ApplicationScoped
public class DeliveryService {

    @Inject
    DeliveryRepository deliveryRepository;

    @Transactional
    public Delivery getDeliveryDetails(String orderId) {
        return deliveryRepository.find("orderId", orderId).firstResult();
    }
    @Transactional
    public void createDelivery(Delivery delivery) {
        deliveryRepository.persist(delivery);
    }
}