package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_event")
public class NotificationEvent {
    @EmbeddedId
    private NotificationEventId id;

    @ManyToOne
    @MapsId("notificationId")
    @JoinColumn(name = "notification_id")
    @JsonIgnore
    private Notification notification;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    public NotificationEventId getId() {
        return id;
    }

    public void setId(NotificationEventId id) {
        this.id = id;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

  
}