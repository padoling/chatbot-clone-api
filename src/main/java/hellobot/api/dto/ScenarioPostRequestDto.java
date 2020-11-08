package hellobot.api.dto;

import hellobot.api.domain.scenario.Scenario;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ScenarioPostRequestDto {

    private String name;
    private String variables;
    private String descMessage;
    private List<MessageDto> messageDtoList;
    private List<InputDto> inputDtoList;

    @Builder
    public ScenarioPostRequestDto(String name, String variables, String descMessage, List<MessageDto> messageDtoList, List<InputDto> inputDtoList) {
        this.name = name;
        this.variables = variables;
        this.descMessage = descMessage;
        this.messageDtoList = messageDtoList;
        this.inputDtoList = inputDtoList;
    }

    public Scenario toEntity() {
        return Scenario.builder()
                .name(name)
                .variables(variables)
                .descMessage(descMessage)
                .build();
    }

}
