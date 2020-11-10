package hellobot.api.global.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class FileUploader {

    @Value("${file.upload.path}")
    private String path;

    public String upload(String name, MultipartFile imageFile) throws IOException {
        String originalFilename = imageFile.getOriginalFilename();
        String extension = null;
        if (originalFilename != null) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String imageUrl = path + name + "_" + System.currentTimeMillis() + extension;

        File file = new File(imageUrl);
        log.info("[FileUploader] file path: {}", file.getAbsolutePath());
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(imageFile.getBytes());
        outputStream.close();

        return imageUrl;
    }
}