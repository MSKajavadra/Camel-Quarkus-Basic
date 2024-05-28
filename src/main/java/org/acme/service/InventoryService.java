package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.entity.Inventory;
import org.acme.repository.InventoryRepo;

@ApplicationScoped
public class InventoryService {

    @Inject
    InventoryRepo inventoryRepository;

    @Transactional
    public void updateInventory(Long storeId, Long productId, int quantity) {
        Inventory inventory = inventoryRepository.find("storeId = ?1 and productId = ?2", storeId, productId).firstResult();
        if (inventory != null) {
            inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantity);
        } else {
            inventory = new Inventory();
            inventory.setStoreId(storeId);
            inventory.setProductId(productId);
            inventory.setQuantityAvailable(quantity);
        }
        inventoryRepository.persist(inventory);
    }

}
