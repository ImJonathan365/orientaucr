package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "notification_attachment")
public class NotificationAttachment {

    @Id
    @Column(name = "attachment_id", length = 36)
    private String attachmentId;

    @Column(name = "file_name", length = 150, nullable = false)
    private String fileName;
     
    @Column(name = "file_path", length = 255, nullable = false)
    private String filePath;

    @Column(name = "file_mime_type", length = 50, nullable = false)
    private String fileMimeType;

    @Column(name = "file_size_kb", nullable = false)
    private Integer fileSizeKb;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    @JsonIgnore
    private Notification notification;

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileMimeType() {
        return fileMimeType;
    }

    public void setFileMimeType(String fileMimeType) {
        this.fileMimeType = fileMimeType;
    }

    public Integer getFileSizeKb() {
        return fileSizeKb;
    }

    public void setFileSizeKb(Integer fileSizeKb) {
        this.fileSizeKb = fileSizeKb;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
    
}