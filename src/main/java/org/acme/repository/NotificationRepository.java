package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Notification;

@ApplicationScoped
public class NotificationRepository implements PanacheRepository<Notification> {
}