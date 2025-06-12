package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.*;
import cr.ac.ucr.orientaucr.orientaucr.repository.INotificationRepository;
import cr.ac.ucr.orientaucr.orientaucr.repository.IUserRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.EmailService;
import cr.ac.ucr.orientaucr.orientaucr.services.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationServiceJPA implements INotificationService {

    @Autowired
    private INotificationRepository notificationRepo;

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private EmailService emailService;

    @Override
    public List<Notification> getAll(String search) {
        return new ArrayList<>(notificationRepo.findAll());
    }

    @Override
    public List<Notification> getAll() {
        return new ArrayList<>(notificationRepo.findAll());
    }

    @Override
    @Transactional
    public void add(Notification notification) {
        if (notification.getNotificationId() == null || notification.getNotificationId().isEmpty()) {
            notification.setNotificationId(UUID.randomUUID().toString());
        }
        if (notification.getAttachments() != null) {
            for (NotificationAttachment attachment : notification.getAttachments()) {
                if (attachment.getAttachmentId() == null || attachment.getAttachmentId().isEmpty()) {
                    attachment.setAttachmentId(UUID.randomUUID().toString());
                }
                attachment.setNotification(notification);
            }
        }
        if (notification.getNotificationEvents() != null) {
            for (NotificationEvent ne : notification.getNotificationEvents()) {
                ne.setNotification(notification);
                if (ne.getId() == null) {
                    NotificationEventId id = new NotificationEventId();
                    id.setNotificationId(notification.getNotificationId());
                    id.setEventId(ne.getEvent().getEventId());
                    ne.setId(id);
                }
            }
        }
        notificationRepo.save(notification);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void sendScheduledNotifications() {
        List<Notification> pendientes = notificationRepo.findAll().stream()
                .filter(n -> n.getNotificationSendDate() != null
                && n.getNotificationSendDate().isBefore(LocalDateTime.now())
                && !n.isSent())
                .toList();
        for (Notification notification : pendientes) {
            if (notification.getNotificationEvents() != null) {
                for (NotificationEvent ne : notification.getNotificationEvents()) {
                    String eventId = ne.getEvent().getEventId();
                    List<User> destinatarios = userRepo.findInterestedUsersWithNotificationsEnabled(eventId);
                    for (User user : destinatarios) {
                        if (notification.getAttachments() != null && !notification.getAttachments().isEmpty()) {
                            for (NotificationAttachment attachment : notification.getAttachments()) {
                                File file = new File(attachment.getFilePath());
                                try {
                                    emailService.sendEmailWithAttachment(
                                            user.getUserEmail(),
                                            notification.getNotificationTitle(),
                                            notification.getNotificationMessage(),
                                            file
                                    );
                                } catch (Exception e) {
                                }
                            }
                        } else {
                            try {
                                emailService.sendEmailWithAttachment(
                                        user.getUserEmail(),
                                        notification.getNotificationTitle(),
                                        notification.getNotificationMessage(),
                                        null
                                );
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
            notification.setSent(true);
            notificationRepo.save(notification);
        }
    }

    @Override
    @Transactional
    public void update(Notification notification) {
        Notification existing = notificationRepo.findById(notification.getNotificationId())
                .orElseThrow(() -> new RuntimeException("No existe notificación con id: " + notification.getNotificationId()));
        existing.setNotificationTitle(notification.getNotificationTitle());
        existing.setNotificationMessage(notification.getNotificationMessage());
        existing.setNotificationSendDate(notification.getNotificationSendDate());
        List<NotificationAttachment> existingAttachments = existing.getAttachments();
        existingAttachments.clear();
        if (notification.getAttachments() != null) {
            for (NotificationAttachment attachment : notification.getAttachments()) {
                if (attachment.getAttachmentId() == null || attachment.getAttachmentId().isEmpty()) {
                    attachment.setAttachmentId(UUID.randomUUID().toString());
                }
                attachment.setNotification(existing);
                existingAttachments.add(attachment);
            }
        }
        List<NotificationEvent> existingEvents = existing.getNotificationEvents();
        existingEvents.clear();
        if (notification.getNotificationEvents() != null) {
            for (NotificationEvent ne : notification.getNotificationEvents()) {
                ne.setNotification(existing);
                if (ne.getId() == null) {
                    NotificationEventId id = new NotificationEventId();
                    id.setNotificationId(existing.getNotificationId());
                    id.setEventId(ne.getEvent().getEventId());
                    ne.setId(id);
                }
                existingEvents.add(ne);
            }
        }

        notificationRepo.save(existing);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        if (!notificationRepo.existsById(id)) {
            throw new RuntimeException("No existe notificación con la id: " + id);
        }
        notificationRepo.deleteById(id);
    }

    @Override
    public Notification findById(String id) {
        return notificationRepo.findById(id).orElse(null);
    }
}
