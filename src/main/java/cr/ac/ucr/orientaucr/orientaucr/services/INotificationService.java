package cr.ac.ucr.orientaucr.orientaucr.services;

import cr.ac.ucr.orientaucr.orientaucr.domain.Notification;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface INotificationService extends CRUD<Notification>{
    void addWithAttachments(Notification notification, List<MultipartFile> attachments);
    void updateWithAttachments(Notification notification, List<MultipartFile> attachments);
}
