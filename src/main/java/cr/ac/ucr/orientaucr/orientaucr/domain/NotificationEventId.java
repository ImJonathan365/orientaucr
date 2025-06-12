package cr.ac.ucr.orientaucr.orientaucr.domain;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class NotificationEventId implements Serializable {
    private String notificationId;
    private String eventId;

    public NotificationEventId() {}

    public NotificationEventId(String notificationId, String eventId) {
        this.notificationId = notificationId;
        this.eventId = eventId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationEventId)) return false;
        NotificationEventId that = (NotificationEventId) o;
        return Objects.equals(notificationId, that.notificationId) &&
               Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId, eventId);
    }
}