package cr.ac.ucr.orientaucr.orientaucr.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import javax.imageio.ImageIO;
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
    
}
