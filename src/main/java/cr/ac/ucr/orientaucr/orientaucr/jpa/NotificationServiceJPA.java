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
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

    @Transactional
    public void addWithAttachments(Notification notification, List<MultipartFile> attachments) {
        if (notification.getNotificationId() == null || notification.getNotificationId().isEmpty()) {
            notification.setNotificationId(UUID.randomUUID().toString());
        }

        if (attachments != null && !attachments.isEmpty()) {
            List<NotificationAttachment> attachmentEntities = saveAttachments(notification, attachments);
            notification.setAttachments(attachmentEntities);
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

    @Transactional
    public void updateWithAttachments(Notification notification, List<MultipartFile> attachments) {
        Notification existing = notificationRepo.findById(notification.getNotificationId())
                .orElseThrow(() -> new RuntimeException("No existe notificación con id: " + notification.getNotificationId()));

        existing.setNotificationTitle(notification.getNotificationTitle());
        existing.setNotificationMessage(notification.getNotificationMessage());
        existing.setNotificationSendDate(notification.getNotificationSendDate());
        List<NotificationAttachment> existingAttachments = existing.getAttachments();
        existingAttachments.clear();

        if (attachments != null && !attachments.isEmpty()) {
            List<NotificationAttachment> newAttachments = saveAttachments(existing, attachments);
            existingAttachments.addAll(newAttachments);
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

    private List<NotificationAttachment> saveAttachments(Notification notification, List<MultipartFile> attachments) {
        List<NotificationAttachment> savedAttachments = new ArrayList<>();
        try {
            Path uploadPath = Paths.get(System.getProperty("user.dir"), FILE_STORAGE_PATH);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            for (MultipartFile file : attachments) {
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(filename);

                file.transferTo(filePath.toFile());

                NotificationAttachment attachment = new NotificationAttachment();
                attachment.setAttachmentId(UUID.randomUUID().toString());
                attachment.setFileName(file.getOriginalFilename());
                attachment.setFilePath(filename);
                attachment.setFileMimeType(file.getContentType());
                attachment.setFileSizeKb((int) (file.getSize() / 1024));
                attachment.setNotification(notification);

                savedAttachments.add(attachment);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error guardando archivo", e);
        }
        return savedAttachments;
    }

    @Override
    @Transactional
    public void add(Notification notification) {
        addWithAttachments(notification, null);
    }

    @Override
    @Transactional
    public void update(Notification notification) {
        updateWithAttachments(notification, null);
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
                                    e.printStackTrace();
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
                                e.printStackTrace();
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
