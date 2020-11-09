package hellobot.api.dto;

import hellobot.api.domain.scenario.Scenario;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ScenarioPostRequestDto {

    private String name;
    private List<String> variables;
    private String description;
    private List<MessageDto> messageDtoList;
    private List<InputDto> inputDtoList;

    @Builder
    public ScenarioPostRequestDto(String name, List<String> variables, String description, List<MessageDto> messageDtoList, List<InputDto> inputDtoList) {
        this.name = name;
        this.variables = variables;
        this.description = description;
        this.messageDtoList = messageDtoList;
        this.inputDtoList = inputDtoList;
    }

    public Scenario toEntity() {
        return Scenario.builder()
                .name(name)
                .variables(variables)
                .description(description)
                .build();
    }

}
