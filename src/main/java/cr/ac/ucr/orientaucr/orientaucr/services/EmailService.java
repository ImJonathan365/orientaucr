package cr.ac.ucr.orientaucr.orientaucr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailWithAttachment(String to, String subject, String text, File file) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("carloarobles535@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        if (file != null && file.exists()) {
            helper.addAttachment(file.getName(), file);
        }
        mailSender.send(message);
    }
}