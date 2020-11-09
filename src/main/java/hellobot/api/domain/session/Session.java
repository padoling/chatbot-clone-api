package hellobot.api.domain.session;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@NoArgsConstructor
@Document("session")
public class Session {

    @Id
    private String id;

    private String userId;

    private String scenarioId;

    private Map<String, String> variables;

    private Integer messageNumber;

    @Builder
    public Session(String id, String userId, String scenarioId, Map<String, String> variables, Integer messageNumber) {
        this.id = id;
        this.userId = userId;
        this.scenarioId = scenarioId;
        this.variables = variables;
        this.messageNumber = messageNumber;
    }
}
