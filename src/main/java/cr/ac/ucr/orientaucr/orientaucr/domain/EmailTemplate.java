package cr.ac.ucr.orientaucr.orientaucr.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "email_templates")
public class EmailTemplate {

    @Id
    @Column(name = "template_id", length = 36)
    private String templateId;

    @Column(name="template_name", nullable = false, unique = true, length = 100)
    private String templateName;

    @Column(name="template_subject", nullable = false, length = 255)
    private String templateSubject;

    @Column(name="template_body", nullable = false, columnDefinition = "TEXT")
    private String templateBody;

    @Column(name="is_active", nullable = false)
    private boolean isActive;

    @Column(name = "created_by_user", nullable = false, length = 36)
    private String createdByUser;

    @Column(name = "modified_by_user", length = 36)
    private String modifiedByUser;
    
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "templateId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmailTemplateAttachment> attachments = new ArrayList<>();

    public EmailTemplate() {}

    public EmailTemplate(String templateId, String templateName, String templateSubject, String templateBody, boolean isActive, String createdByUser, String modifiedByUser, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.templateSubject = templateSubject;
        this.templateBody = templateBody;
        this.isActive = isActive;
        this.createdByUser = createdByUser;
        this.modifiedByUser = modifiedByUser;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateSubject() {
        return templateSubject;
    }

    public void setTemplateSubject(String templateSubject) {
        this.templateSubject = templateSubject;
    }

    public String getTemplateBody() {
        return templateBody;
    }

    public void setTemplateBody(String templateBody) {
        this.templateBody = templateBody;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getModifiedByUser() {
        return modifiedByUser;
    }

    public void setModifiedByUser(String modifiedByUser) {
        this.modifiedByUser = modifiedByUser;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<EmailTemplateAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<EmailTemplateAttachment> attachments) {
        this.attachments = attachments;
    }

}