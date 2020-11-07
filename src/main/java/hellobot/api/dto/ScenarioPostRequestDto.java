package hellobot.api.dto;

import hellobot.api.domain.scenario.Scenario;
import lombok.Getter;

import java.util.List;

@Getter
public class ScenarioPostRequestDto {

    private String name;
    private String variables;
    private String descMessage;

    public Scenario toEntity() {
        return Scenario.builder()
                .name(name)
                .variables(variables)
                .descMessage(descMessage)
                .build();
    }

}
