package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_template_attachments")
public class EmailTemplateAttachment {

    @Id
    @Column(name = "attachment_id", length = 36)
    private String attachmentId;

    @Column(name = "template_id", nullable = false, length = 36)
    private String templateId;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "file_path", nullable = false, length = 255)
    private String filePath;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", insertable = false, updatable = false)
    @JsonIgnore
    private EmailTemplate emailTemplate;

    public EmailTemplateAttachment() {}

    public EmailTemplateAttachment(String attachmentId, String templateId, String fileName, String filePath, EmailTemplate emailTemplate) {
        this.attachmentId = attachmentId;
        this.templateId = templateId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.emailTemplate = emailTemplate;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
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

    public EmailTemplate getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(EmailTemplate emailTemplate) {
        this.emailTemplate = emailTemplate;
    }
    
}
