package cr.ac.ucr.orientaucr.orientaucr.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @Column(name = "notification_id", length = 36)
    private String notificationId;

    @Column(name = "notification_title", length = 150, nullable = false)
    private String notificationTitle;

    @Column(name = "notification_message", columnDefinition = "TEXT", nullable = false)
    private String notificationMessage;

    @Column(name = "notification_send_date")
    private LocalDateTime notificationSendDate;

    @Column(name = "sender_id", length = 36)
    private String senderId;

    @Column(name = "sent")
    private boolean sent = false;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationAttachment> attachments;

@OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
private List<NotificationEvent> notificationEvents;


    public String getNotificationId() {
        return notificationId;
    }
    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }
    public String getNotificationTitle() {
        return notificationTitle;
    }
    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }
    public String getNotificationMessage() {
        return notificationMessage;
    }
    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }
    public LocalDateTime getNotificationSendDate() {
        return notificationSendDate;
    }
    public void setNotificationSendDate(LocalDateTime notificationSendDate) {
        this.notificationSendDate = notificationSendDate;
    }
    public String getSenderId() {
        return senderId;
    }
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
    public List<NotificationAttachment> getAttachments() {
        return attachments;
    }
    public void setAttachments(List<NotificationAttachment> attachments) {
        this.attachments = attachments;
    }

    public boolean isSent() {
    return sent;
   }

public void setSent(boolean sent) {
    this.sent = sent;
}

    public List<NotificationEvent> getNotificationEvents() {
        return notificationEvents;
    }

    public void setNotificationEvents(List<NotificationEvent> notificationEvents) {
        this.notificationEvents = notificationEvents;
    }
  
}