package hellobot.api.dto;

import hellobot.api.domain.input.InputType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class NextMessageDto {

    private String messageContent;
    private List<String> imageUrlList;
    private InputType inputType;
    private List<String> inputContents;
    private List<Integer> nextMessageNums;

    @Builder
    public NextMessageDto(String messageContent, List<String> imageUrlList, InputType inputType, List<String> inputContents, List<Integer> nextMessageNums) {
        this.messageContent = messageContent;
        this.imageUrlList = imageUrlList;
        this.inputType = inputType;
        this.inputContents = inputContents;
        this.nextMessageNums = nextMessageNums;
    }
}
