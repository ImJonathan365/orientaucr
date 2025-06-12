/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.services;

import cr.ac.ucr.orientaucr.orientaucr.domain.Notification;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author luisr
 */
public interface INotificationService extends CRUD<Notification>{
    void addWithAttachments(Notification notification, List<MultipartFile> attachments);
    void updateWithAttachments(Notification notification, List<MultipartFile> attachments);
}
