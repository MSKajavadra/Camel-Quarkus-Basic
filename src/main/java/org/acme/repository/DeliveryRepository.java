package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Delivery;

@ApplicationScoped
public class DeliveryRepository implements PanacheRepository<Delivery> {
}
