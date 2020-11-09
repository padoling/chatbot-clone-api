package hellobot.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JsonConverter {

    @Autowired
    private ObjectMapper objectMapper;

    public <T> T jsonToObject(String path, Class<T> classOfT) {
        File file;
        try {
            file = ResourceUtils.getFile(path);
            String content = new String(Files.readAllBytes(file.toPath()));
            return objectMapper.readValue(content, classOfT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object jsonToObject(String path, TypeReference typeReference) {
        File file;
        try {
            file = ResourceUtils.getFile(path);
            String content = new String(Files.readAllBytes(file.toPath()));
            return objectMapper.readValue(content, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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
