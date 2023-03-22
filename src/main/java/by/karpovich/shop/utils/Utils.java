package by.karpovich.shop.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Utils {

    public static final String UPLOAD_PATH = "D://image//poster";
    private static String dateFormat = "dd MMMM yyyy";

    public static String mapStringFromInstant(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat)
                .withZone(ZoneId.systemDefault());

        Instant date = Instant.parse(instant.toString());

        return formatter.format(date);
    }

    public static String saveFile(MultipartFile file) {

        Path uploadPath = Paths.get(UPLOAD_PATH);

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String uuidFile = UUID.randomUUID().toString();
        String name = uuidFile + "-" + file.getOriginalFilename();

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(name);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return name;
    }

    public static byte[] getImageAsResponseEntity(String fileName) {
        String dirPath = UPLOAD_PATH + "//";
        InputStream in = null;
        byte[] media = new byte[0];
        try {
            in = new FileInputStream(dirPath + fileName);
            media = IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return media;
    }

    public static Instant mapInstantFromString(String date) {
        String fullDate = date + "T00:00:00.000+00:00";

        return Instant.parse(fullDate);
    }
}
