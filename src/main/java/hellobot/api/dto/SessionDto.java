package hellobot.api.dto;

import hellobot.api.domain.session.Session;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Setter
@Getter
public class SessionDto {

    private String id;
    private String userId;
    private String scenarioId;
    private Map<String, String> variables;
    private Integer messageNumber;

    @Builder
    public SessionDto(String id, String userId, String scenarioId, Map<String, String> variables, Integer messageNumber) {
        this.id = id;
        this.userId = userId;
        this.scenarioId = scenarioId;
        this.variables = variables;
        this.messageNumber = messageNumber;
    }

    public SessionDto(Session document) {
        this.id = document.getId();
        this.userId = document.getUserId();
        this.scenarioId = document.getScenarioId();
        this.variables = document.getVariables();
        this.messageNumber = document.getMessageNumber();
    }

    public Session toEntity() {
        return Session.builder()
                .userId(userId)
                .scenarioId(scenarioId)
                .variables(variables)
                .messageNumber(messageNumber)
                .build();
    }
}
