package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Notification;
import cr.ac.ucr.orientaucr.orientaucr.domain.NotificationAttachment;
import cr.ac.ucr.orientaucr.orientaucr.domain.NotificationEvent;
import cr.ac.ucr.orientaucr.orientaucr.domain.NotificationEventId;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.repository.INotificationRepository;
import cr.ac.ucr.orientaucr.orientaucr.repository.IUserRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.EmailService;
import cr.ac.ucr.orientaucr.orientaucr.services.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NotificationServiceJPA implements INotificationService {

    @Autowired
    private INotificationRepository notificationRepo;

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private EmailService emailService;

    private static final String FILE_STORAGE_PATH = "uploads/notifications/";

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

    @Override
    @Transactional
    public void update(Notification notification) {
        Notification existing = notificationRepo.findById(notification.getNotificationId())
                .orElseThrow(() -> new RuntimeException("No existe notificación con id: " + notification.getNotificationId()));

        System.out.println("Antes de actualizar - Adjuntos existentes: " + existing.getAttachments().size());

        existing.setNotificationTitle(notification.getNotificationTitle());
        existing.setNotificationMessage(notification.getNotificationMessage());
        existing.setNotificationSendDate(notification.getNotificationSendDate());

        existing.getAttachments().clear();
            System.out.println("Adjuntos después de limpiar: " + existing.getAttachments().size());

        if (notification.getAttachments() != null && !notification.getAttachments().isEmpty()) {
            for (NotificationAttachment attachment : notification.getAttachments()) {
                attachment.setNotification(existing);
            }
            existing.getAttachments().addAll(notification.getAttachments());
                    System.out.println("Nuevos adjuntos agregados: " + notification.getAttachments().size());

        }

            System.out.println("Final - Total adjuntos en existing: " + existing.getAttachments().size());

        existing.getNotificationEvents().clear();
        if (notification.getNotificationEvents() != null && !notification.getNotificationEvents().isEmpty()) {
            for (NotificationEvent ne : notification.getNotificationEvents()) {
                ne.setNotification(existing);
                if (ne.getId() == null) {
                    NotificationEventId id = new NotificationEventId();
                    id.setNotificationId(existing.getNotificationId());
                    id.setEventId(ne.getEvent().getEventId());
                    ne.setId(id);
                }
                existing.getNotificationEvents().add(ne);
            }
        }

        notificationRepo.save(existing);
            System.out.println("Notificación actualizada y guardada con éxito.");

    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void sendScheduledNotifications() {
        List<Notification> pendientes = notificationRepo.findAll().stream()
                .filter(n -> n.getNotificationSendDate() != null
                && !n.isSent()
                && n.getNotificationSendDate().before(new Date()))
                .toList();

        for (Notification notification : pendientes) {
            if (notification.getNotificationEvents() != null) {
                for (NotificationEvent ne : notification.getNotificationEvents()) {
                    String eventId = ne.getEvent().getEventId();
                    List<User> destinatarios = userRepo.findInterestedUsersWithNotificationsEnabled(eventId);

                    List<File> attachmentFiles = new ArrayList<>();
                    if (notification.getAttachments() != null && !notification.getAttachments().isEmpty()) {
                        for (NotificationAttachment attachment : notification.getAttachments()) {
                            File file = new File(FILE_STORAGE_PATH + attachment.getFilePath());
                            if (file.exists()) {
                                attachmentFiles.add(file);
                            }
                        }
                    }
                    for (User user : destinatarios) {
                        try {
                            emailService.sendEmailWithAttachment(
                                    user.getUserEmail(),
                                    notification.getNotificationTitle(),
                                    notification.getNotificationMessage(),
                                    attachmentFiles
                            );
                        } catch (Exception e) {
                            System.err.println("Error al enviar correo a " + user.getUserEmail() + ": " + e.getMessage());
                            e.printStackTrace();
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
    public void deleteById(String id) {
        if (!notificationRepo.existsById(id)) {
            throw new RuntimeException("No existe notificación con el id: " + id);
        }
        notificationRepo.deleteById(id);
    }

    @Override
    public Notification findById(String id) {
        return notificationRepo.findById(id).orElse(null);
    }

    @Override
    public void addWithAttachments(Notification notification, List<MultipartFile> attachments) {
    }

    @Override
    public void updateWithAttachments(Notification notification, List<MultipartFile> attachments) {
    }

}
