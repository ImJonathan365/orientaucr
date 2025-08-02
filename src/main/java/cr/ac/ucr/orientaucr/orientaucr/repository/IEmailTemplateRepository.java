package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmailTemplateRepository extends JpaRepository<EmailTemplate, String> {
    EmailTemplate findByTemplateName(String templateName);
}
