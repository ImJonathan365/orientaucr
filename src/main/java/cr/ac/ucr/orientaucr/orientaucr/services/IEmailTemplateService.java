package cr.ac.ucr.orientaucr.orientaucr.services;

import cr.ac.ucr.orientaucr.orientaucr.domain.EmailTemplate;
import cr.ac.ucr.orientaucr.orientaucr.domain.EmailTemplateAttachment;
import java.util.List;

public interface IEmailTemplateService {
    List<EmailTemplateAttachment> findByTemplateId(String templateId);
    EmailTemplate findByTemplateName(String templateName);
}
