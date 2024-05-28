package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.entity.Notification;
import org.acme.repository.NotificationRepository;

@ApplicationScoped
public class NotificationService {

    @Inject
    NotificationRepository notificationRepository;

    @Transactional
    public void sendNotification(Notification notification) {
        notificationRepository.persist(notification);
    }
}