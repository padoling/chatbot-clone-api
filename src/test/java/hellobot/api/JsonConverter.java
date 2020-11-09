package hellobot.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JsonConverter {

    @Autowired
    private ObjectMapper objectMapper;

    public String jsonToString(String path) {
        File file;
        try {
            file = ResourceUtils.getFile(path);
            return new String(Files.readAllBytes(file.toPath()));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
