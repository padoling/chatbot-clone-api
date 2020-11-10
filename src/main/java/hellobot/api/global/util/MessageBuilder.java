package hellobot.api.global.util;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageBuilder {

    public String applyVariables(String messageContent, Map<String, String> variables) {
        String result = messageContent;
        for(String key : variables.keySet()) {
            String pattern = "\\$\\{var:" + key + "}";
            result = result.replaceAll(pattern, variables.get(key));
        }
        return result;
    }
}
