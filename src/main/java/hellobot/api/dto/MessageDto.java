package hellobot.api.dto;

import hellobot.api.domain.message.Message;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MessageDto {

    private String id;
    private String contents;
    private List<String> imageList;
    private Integer number;
    private Integer nextInputNum;
    private String scenarioId;

    @Builder
    public MessageDto(String id, String contents, List<String> imageList, Integer number, Integer nextInputNum, String scenarioId) {
        this.id = id;
        this.contents = contents;
        this.imageList = imageList;
        this.number = number;
        this.nextInputNum = nextInputNum;
        this.scenarioId = scenarioId;
    }

    public MessageDto(Message document) {
        this.id = document.getId();
        this.contents = document.getContents();
        this.imageList = document.getImageList();
        this.number = document.getNumber();
        this.nextInputNum = document.getNextInputNum();
        this.scenarioId = document.getScenarioId();
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
