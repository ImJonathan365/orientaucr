package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.EmailTemplate;
import cr.ac.ucr.orientaucr.orientaucr.domain.EmailTemplateAttachment;
import cr.ac.ucr.orientaucr.orientaucr.repository.IEmailTemplateAttachmentRepository;
import cr.ac.ucr.orientaucr.orientaucr.repository.IEmailTemplateRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.IEmailTemplateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailTemplateServiceJPA implements IEmailTemplateService {

    @Autowired
    private IEmailTemplateAttachmentRepository attachmentRepository;

    @Autowired
    private IEmailTemplateRepository emailTemplateRepository;
    
    @Override
    @Transactional
    public List<EmailTemplateAttachment> findByTemplateId(String templateId) {
        if (templateId == null || templateId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de la plantilla no puede ser nulo o vacío");
        }
        return attachmentRepository.findByTemplateId(templateId);
    }
    
    @Override
    @Transactional
    public EmailTemplate findByTemplateName(String templateName) {
        if (templateName == null || templateName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la plantilla no puede ser nulo o vacío");
        }
        return emailTemplateRepository.findByTemplateName(templateName);
    }
    
}
