package hellobot.api.dto;

import hellobot.api.domain.input.Input;
import hellobot.api.domain.input.InputType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InputDto {

    private InputType inputType;
    private List<String> contents;
    private Integer number;
    private List<Integer> nextMessageNums;
    private String scenarioId;

    @Builder
    public InputDto(InputType inputType, List<String> contents, Integer number, List<Integer> nextMessageNums, String scenarioId) {
        this.inputType = inputType;
        this.contents = contents;
        this.number = number;
        this.nextMessageNums = nextMessageNums;
        this.scenarioId = scenarioId;
    }

    public Input toEntity() {
        return Input.builder()
                .inputType(inputType)
                .contents(contents)
                .number(number)
                .nextMessageNums(nextMessageNums)
                .scenarioId(scenarioId)
                .build();
    }
}
