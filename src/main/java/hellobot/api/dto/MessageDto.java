package hellobot.api.dto;

import hellobot.api.domain.message.Message;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MessageDto {

    private String contents;
    private List<String> imageList;
    private Integer number;
    private Integer nextInputNum;
    private String scenarioId;

    @Builder
    public MessageDto(String contents, List<String> imageList, Integer number, Integer nextInputNum, String scenarioId) {
        this.contents = contents;
        this.imageList = imageList;
        this.number = number;
        this.nextInputNum = nextInputNum;
        this.scenarioId = scenarioId;
    }

    public Message toEntity() {
        return Message.builder()
                .contents(contents)
                .imageList(imageList)
                .number(number)
                .nextInputNum(nextInputNum)
                .scenarioId(scenarioId)
                .build();
    }

}
