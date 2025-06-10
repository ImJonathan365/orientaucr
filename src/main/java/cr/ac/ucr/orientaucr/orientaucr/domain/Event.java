package cr.ac.ucr.orientaucr.orientaucr.domain;

import jakarta.persistence.*;
import java.sql.Date;
import java.time.LocalTime;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @Column(name = "event_id", length = 36, nullable = false)
    private String eventId;

    @Column(name = "event_title", nullable = false, length = 300)
    private String eventTitle;

    @Column(name = "event_description", nullable = false, columnDefinition = "TEXT")
    private String eventDescription;

    @Column(name = "event_date", nullable = false)
    private Date eventDate;

    @Column(name = "event_time", nullable = false)
    private LocalTime eventTime;

    @Column(name = "event_modality", nullable = false)
    @Enumerated(EnumType.STRING)
    private Modality eventModality = Modality.virtual;

    @Column(name = "event_image_path", length = 255)
    private String eventImagePath;

    @Column(name = "created_by", length = 36)
    private String createdBy;

    @Column(name = "campus_id", length = 36)
    private String campusId;

    @Column(name = "subcampus_id", length = 36)
    private String subcampusId;

    public enum Modality {
        virtual,
        inPerson
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public Modality getEventModality() {
        return eventModality;
    }

    public void setEventModality(Modality eventModality) {
        this.eventModality = eventModality;
    }

    public String getEventImagePath() {
        return eventImagePath;
    }

    public void setEventImagePath(String eventImagePath) {
        this.eventImagePath = eventImagePath;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCampusId() {
        return campusId;
    }

    public void setCampusId(String campusId) {
        this.campusId = campusId;
    }

    public String getSubcampusId() {
        return subcampusId;
    }

    public void setSubcampusId(String subcampusId) {
        this.subcampusId = subcampusId;
    }
}
