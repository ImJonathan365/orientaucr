package cr.ac.ucr.orientaucr.orientaucr.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public class ImageUtils {

    public static String saveImage(MultipartFile imageFile, String uploadDir) throws IOException {
        String contentType = imageFile.getContentType();
        if (contentType == null
                || !(contentType.equals("image/jpeg")
                || contentType.equals("image/png")
                || contentType.equals("image/gif")
                || contentType.equals("image/webp"))) {
            throw new IOException("Tipo de archivo no permitido. Solo JPG, PNG, GIF o WEBP.");
        }

        String originalFilename = imageFile.getOriginalFilename();
        if (originalFilename == null
                || !originalFilename.matches("^[a-zA-Z0-9._-]+\\.(jpg|jpeg|png|gif|webp)$")) {
            throw new IOException("Nombre de archivo inválido o extensión no permitida.");
        }

        try (InputStream is = imageFile.getInputStream()) {
            BufferedImage image = ImageIO.read(is);
            if (image == null) {
                throw new IOException("El archivo no es una imagen válida.");
            }
        }

        if (imageFile.getSize() > 5 * 1024 * 1024) {
            throw new IOException("El archivo excede el tamaño máximo permitido (5MB).");
        }

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + extension;
        File destFile = new File(dir, newFileName);

        imageFile.transferTo(destFile);
        return newFileName;
    }

    public static void deleteImage(String dir, String filename) {
        File file = new File(dir, filename);
        if (file.exists()) {
            file.delete();
        }
    }

    public static String saveProfilePicture(MultipartFile imageFile, String uploadDir) throws IOException {
        String contentType = imageFile.getContentType();
        if (contentType == null
                || !(contentType.equalsIgnoreCase("image/jpeg")
                || contentType.equalsIgnoreCase("image/png")
                || contentType.equalsIgnoreCase("image/gif")
                || contentType.equalsIgnoreCase("image/webp"))) {
            throw new IOException("Tipo de archivo no permitido. Solo JPG, PNG, GIF o WebP son aceptados para fotos de perfil.");
        }

        String originalFilename = imageFile.getOriginalFilename();
        if (originalFilename == null || !originalFilename.matches("^[a-zA-Z0-9_.-]+\\.(?i)(jpg|jpeg|png|gif|webp)$")) {
            throw new IOException("Nombre de archivo inválido. Solo se permiten letras, números, punto y guion, y extensiones JPG, JPEG, PNG, GIF o WEBP.");
        }

        if (originalFilename.contains("..")) {
            throw new IOException("Nombre de archivo contiene caracteres no permitidos (traversal detectado).");
        }

        if (imageFile.getSize() > 5 * 1024 * 1024) {
            throw new IOException("El archivo excede el tamaño máximo permitido (5MB).");
        }

        try (InputStream is = imageFile.getInputStream()) {
            BufferedImage image = ImageIO.read(is);
            if (image == null) {
                throw new IOException("El archivo no es una imagen válida.");
            }
            int width = image.getWidth();
            int height = image.getHeight();
            if (width < 100 || height < 100) {
                throw new IOException("La imagen es demasiado pequeña (mínimo 100x100 píxeles).");
            }
            if (width > 3000 || height > 3000) {
                throw new IOException("Dimensiones de la imagen excesivas (máximo 2000x2000 píxeles).");
            }
        }

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        String newFileName = UUID.randomUUID().toString() + extension;
        File destFile = new File(dir, newFileName);

        imageFile.transferTo(destFile);
        return newFileName;
    }

    public ResponseEntity<?> getProfilePicture(String directory, String filename) throws IOException {
        Path filePath = Paths.get(directory).resolve(filename).normalize();
        File file = filePath.toFile();

        if (!file.exists() || !file.isFile()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Imagen no encontrada: " + filename);
        }

        String mimeType = Files.probeContentType(filePath);
        if (mimeType == null || !mimeType.startsWith("image")) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("El archivo no es una imagen válida.");
        }

        try {
            Resource resource = new UrlResource(filePath.toUri());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, mimeType)
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("URL de archivo inválida: " + e.getMessage());
        }
    }

}
