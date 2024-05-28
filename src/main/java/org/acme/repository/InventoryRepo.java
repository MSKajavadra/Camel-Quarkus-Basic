package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Inventory;

@ApplicationScoped
public class InventoryRepo implements PanacheRepository<Inventory> {
}
