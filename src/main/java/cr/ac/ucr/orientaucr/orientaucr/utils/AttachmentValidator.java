package cr.ac.ucr.orientaucr.orientaucr.utils;

import cr.ac.ucr.orientaucr.orientaucr.domain.Notification;
import org.apache.tika.Tika;
import cr.ac.ucr.orientaucr.orientaucr.domain.NotificationAttachment;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public class AttachmentValidator {

    private static final int MAX_FILE_COUNT = 5;
    private static final long MAX_FILE_SIZE_BYTES = 15 * 1024 * 1024;
    private static final long MAX_TOTAL_SIZE_BYTES = 18 * 1024 * 1024;
    private static final Set<String> BLOCKED_MIME_TYPES = Set.of(
            "application/x-msdownload", // .exe
            "application/x-msdos-program", // .com
            "application/x-sh", // .sh
            "application/x-bat", // .bat
            "application/x-msinstaller", // .msi
            "application/java-archive", // .jar
            "text/javascript", // .js
            "application/x-vbs", // .vbs
            "application/x-scr", // .scr
            "application/x-cmd", // .cmd
            "application/x-shellscript" // scripts
    );
    private static final Set<String> BLOCKED_EXTENSIONS = Set.of(
            "exe", "com", "bat", "cmd", "sh", "msi", "vbs", "scr", "js", "jar"
    );

    public static void validateAttachments(List<File> files) throws IllegalArgumentException {
        if (files == null || files.isEmpty()) {
            return;
        }
        long totalSize = 0;
        Tika tika = new Tika();
        for (File file : files) {
            if (file == null || !file.exists()) {
                throw new IllegalArgumentException("El archivo " + (file != null ? file.getName() : "nulo") + " no existe");
            }
            if (file.length() > MAX_FILE_SIZE_BYTES) {
                throw new IllegalArgumentException("El archivo " + file.getName() + " excede el límite de 15 MB");
            }
            totalSize += file.length();
            if (totalSize > MAX_TOTAL_SIZE_BYTES) {
                throw new IllegalArgumentException("El tamaño total de los archivos excede el límite de 25 MB");
            }
            String mimeType;
            try {
                mimeType = tika.detect(file);
            } catch (IOException e) {
                throw new IllegalArgumentException("No se pudo detectar el tipo del archivo " + file.getName(), e);
            }
            if (BLOCKED_MIME_TYPES.contains(mimeType)) {
                throw new IllegalArgumentException("El archivo " + file.getName() + " tiene un tipo prohibido: " + mimeType);
            }
            String extension = getFileExtension(file.getName());
            if (extension != null && BLOCKED_EXTENSIONS.contains(extension.toLowerCase())) {
                throw new IllegalArgumentException("El archivo " + file.getName() + " tiene una extensión prohibida: ." + extension);
            }
        }
    }

    public static void deleteAttachments(List<NotificationAttachment> attachments) {
        if (attachments == null || attachments.isEmpty()) {
            return;
        }
        for (NotificationAttachment attachment : attachments) {
            try {
                File file = new File(attachment.getFilePath());
                if (file.exists()) {
                    Files.delete(file.toPath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0 && index < fileName.length() - 1) {
            return fileName.substring(index + 1);
        }
        return null;
    }

    public static void processAttachments(Notification notification, MultipartFile[] files,
            List<NotificationAttachment> attachments, List<File> tempFiles) throws Exception {
        if (files == null || files.length == 0) {
            return;
        }
        if (files.length > MAX_FILE_COUNT) {
            throw new IllegalArgumentException("No se pueden adjuntar más de " + MAX_FILE_COUNT + " archivos.");
        }
        Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "notifications");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(filename);
                File tempFile = filePath.toFile();
                file.transferTo(tempFile);
                tempFiles.add(tempFile);

                NotificationAttachment attachment = new NotificationAttachment();
                attachment.setAttachmentId(UUID.randomUUID().toString());
                attachment.setFileName(file.getOriginalFilename());
                attachment.setFilePath(filePath.toString());
                attachment.setFileMimeType(file.getContentType());
                attachment.setFileSizeKb((int) (file.getSize() / 1024));
                attachment.setNotification(notification);
                attachments.add(attachment);
            }
        }
        validateAttachments(tempFiles);
    }

    public static void cleanTempFiles(List<File> tempFiles) {
        if (tempFiles != null) {
            for (File tempFile : tempFiles) {
                try {
                    Files.deleteIfExists(tempFile.toPath());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
