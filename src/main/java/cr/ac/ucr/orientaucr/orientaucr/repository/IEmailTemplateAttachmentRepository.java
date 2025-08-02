package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.EmailTemplateAttachment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmailTemplateAttachmentRepository extends JpaRepository<EmailTemplateAttachment, String> {
    List<EmailTemplateAttachment> findByTemplateId(String templateId);
}
