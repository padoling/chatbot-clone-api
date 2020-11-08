package hellobot.api.dto;

import hellobot.api.domain.message.Message;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageDto {

    private String contents;
    private Integer number;
    private Integer nextInputNum;
    private String scenarioId;

    @Builder
    public MessageDto(String contents, Integer number, Integer nextInputNum, String scenarioId) {
        this.contents = contents;
        this.number = number;
        this.nextInputNum = nextInputNum;
        this.scenarioId = scenarioId;
    }

    public Message toEntity() {
        return Message.builder()
                .contents(contents)
                .number(number)
                .nextInputNum(nextInputNum)
                .scenarioId(scenarioId)
                .build();
    }

}
